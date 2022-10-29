import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

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

		// setup the input file and read the matrix
		setupTheInputFile();
		readMatrixFromFile();

		// setup the output file
		setupTheOutputFile();

		gaussElimination();

	}

	// prompts the user for an input file and returns it
	public static void setupTheInputFile() throws IOException {
		Scanner userInput = new Scanner(System.in);
		System.out.printf("Please enter the name of an input file: ");
		inputFile = new File(userInput.nextLine());
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

	// creates and sets up the output file for writing
	public static void setupTheOutputFile() throws IOException {
		Scanner userInput = new Scanner(System.in);
		System.out.printf("Please enter the name of an output file: ");
		resultsFile = new PrintWriter(new File(userInput.nextLine()));
	}

	public static void gaussElimination() {
		forwardElimination();

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

	public static void backSubstitution() {
		
		// variables we are solving for
		int x1, x2;

		// first number is row and second number is column
		// a11 is matrix A, row 1, column 1
		int a11 = shearedMatrixA[0][0];
		int a12 = shearedMatrixA[0][1];
		int a22 = shearedMatrixA[1][1];
		int b1 = shearedVectorb[0][0];
		int b2 = shearedVectorb[1][0];

		// solve for x1 and x2
		x2 = b2/a22;
		x1 = (b1-(x2*a12))/a11;

		// fill the solution matrix
		solution[0][0] = x1;
		solution[1][0] = x2;
	}
}