import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.math.*;

public class rkoch3_p1 {
	// this program depends on the matrices being these sizes
	public static double[][] matrixA = new double[2][2];
	public static double[][] shearedMatrixA = new double[2][2];
	public static double[][] vectorb = new double[2][1];
	public static double[][] shearedVectorb = new double[2][1];
	public static double[][] solution = new double[2][1];;
	public static File inputFile;
	public static PrintWriter resultsFile;

	public static void main(String[] args) throws IOException {

		// setup the input and output file
		setupTheInputFile();
		setupTheOutputFile();
		
		// read the matrix from a file and check edge cases
		readMatrixFromFile();
		checkEdgeCases();

		// perform Gauss elimination and print the solution to a file
		gaussElimination();
		printSolutiontoFile();

	}

	// prompts the user for an input file and returns it
	public static void setupTheInputFile() throws IOException {
		Scanner userInput = new Scanner(System.in);
		System.out.printf("Please enter the name of an input file: ");
		inputFile = new File(userInput.nextLine());
	}

	// creates and sets up the output file for writing
	public static void setupTheOutputFile() throws IOException {
		Scanner userInput = new Scanner(System.in);
		System.out.printf("Please enter the name of an output file: ");
		resultsFile = new PrintWriter(new File(userInput.nextLine()));
	}

	// reads a matrix from a file and puts each value into a 2D array
	public static void readMatrixFromFile() throws IOException {

		// open the file for reading
		Scanner readableFile = new Scanner(inputFile);

		// take each double and put it into a matrix
		// first two on each line fill matrix A 
		// the last double fills vector b
		// depends on the input matrix being a 2x3
		for (int row = 0; row < 2; row++) {
			matrixA[row][0] = readableFile.nextDouble();
			matrixA[row][1] = readableFile.nextDouble();
			vectorb[row][0] = readableFile.nextDouble();
		}
	}

	// checks for edge cases and puts them into a form
	// that can be processed like everything else
	public static void checkEdgeCases() {
		if (matrixA[0][0] == 0 && matrixA[0][1] != 0 && matrixA[1][0] != 0) {
 			rowSwap(matrixA, vectorb);
		} else if (matrixA[0][0] == 0 && matrixA[0][1] == 0 && matrixA[1][0] != 0){
			rowSwap(matrixA, vectorb);
		} else if (matrixA[0][1] == 0 && matrixA[1][1] == 0) {
			columnSwap(matrixA);
		}
	}

	// swaps the rows of a matrix and it's vector
	public static void rowSwap(double[][] matrix, double[][] vector) {
		
		// swap the matrix rows
		double[] swap = matrix[0];
        matrix[0] = matrix[1];
        matrix[1] = swap;

        // swap the vector rows
		swap = vector[0];
		vector[0] = vector[1];
		vector[1] = swap;
	}

	// only performs a column swap for the purpose of checking if a system
	// is underdetermined. This is not algebraically sound to obtain a 
	// numerical solution to a system of equations. 
	public static void columnSwap(double[][] matrix) {

		// swap the matrix columns
		double[][] swap = new double[2][1];
        swap[0][0] = matrix[0][0];
        swap[1][0] = matrix[1][0];
        matrix[0][0] = matrix[0][1];
        matrix[1][0] = matrix[1][1];
        matrix[0][1] = swap[0][0];
        matrix[1][1] = swap[1][0];
	}

	// performs Gauss elimination
	public static void gaussElimination() {
		
		// checks if the matrix is in upper triangular form
		// if not, shear it to the e1 axis
		if (matrixA[1][0] != 0){
			forwardElimination();
		} else {
			shearedMatrixA = matrixA;
			shearedVectorb = vectorb;
		}

		backSubstitution();
	}

	// performs forward elimination by creating a shear matrix
	// and mulitplying it by MatrixA and vectorb
	public static void forwardElimination() {
		double[][] shearMatrix = new double[2][2];

		// fill the shear matrix
		shearMatrix[0][0] = 1;
		shearMatrix[0][1] = 0;
		shearMatrix[1][0] = -matrixA[1][0]/matrixA[0][0];
		shearMatrix[1][1] = 1;

		// multiply matrix A and vector b by the shear matrix
		matrixMultiply(shearMatrix, matrixA, shearedMatrixA);
		matrixMultiply(shearMatrix, vectorb, shearedVectorb);

	}

	// multipies a 2x2 matrix by another matrix and fills a new matrix with the result
	public static void matrixMultiply(double[][] operator, double[][] operand, double[][] result) {
		for (int row = 0; row < result.length; row++) {
			for (int column = 0; column < result[row].length; column++) {
				result[row][column] = operator[row][0]*operand[0][column] + operator[row][1]*operand[1][column];
			}
		}
	}

	// performs back substitution to solve for 2 unknown variables
	// dependent on forward elmination being performed first
	public static void backSubstitution() {
		
		// variables we are solving for
		double x1, x2;

		// first number is row and second number is column
		// a11 is matrix A, row 1, column 1
		double a11 = shearedMatrixA[0][0];
		double a12 = shearedMatrixA[0][1];
		double a22 = shearedMatrixA[1][1];
		double b1 = shearedVectorb[0][0];
		double b2 = shearedVectorb[1][0];

		// solve for x1 and x2
		x2 = b2/a22;
		x1 = (b1-(x2*a12))/a11;

		// checks that the solutions are finite, if so rounds to 4 sig figs
		// if not, exceptions are handled later and answers don't need rounding
		if (Double.isFinite(x1)) {
			BigDecimal bigDecimal = new BigDecimal(x1);
			bigDecimal = bigDecimal.round(new MathContext(4));
			solution[0][0] = bigDecimal.doubleValue();
		} else { solution[0][0] = x1; }

		if (Double.isFinite(x2)) {
			BigDecimal bigDecimal = new BigDecimal(x2);
			bigDecimal = bigDecimal.round(new MathContext(4));
			solution[1][0] = bigDecimal.doubleValue();
		} else { solution[1][0] = x2; }

	}

	// checks if the system is inconsistent or underdetermined
	// if not, it prints the solution
	public static void printSolutiontoFile() throws IOException {

		// if vectorb is zero, the solution is the zero vector
		if (shearedVectorb[0][0] == 0 && shearedVectorb[1][0] == 0){
			solution[0][0] = 0;
			solution[1][0] = 0;
			printMatrixToFile();
		}

		// if the system is inconsistent, then it can't be solved
		else if (shearedMatrixA[1][0] == 0 && shearedMatrixA[1][1] == 0 && shearedVectorb[1][0] != 0) {
			resultsFile.println("System inconsistent");
		} 

		// if a row or column is all zeros, then the system is underdetermined
		else if (shearedMatrixA[1][0] == 0 && shearedMatrixA[1][1] == 0 && shearedVectorb[1][0] == 0 ||
					shearedMatrixA[0][0] == 0 && shearedMatrixA[1][0] == 0) {
			resultsFile.println("System underdetermined");
		}

		// otherwise, print the solution
		else {
			printMatrixToFile();
		}
		resultsFile.close();
	}

	// prints a matrix to a given file
	public static void printMatrixToFile() throws IOException {
		resultsFile.printf("%.4g\n%.4g", solution[0][0], solution[1][0]);
	}
}