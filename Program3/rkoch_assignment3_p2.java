import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.math.*;

public class rkoch_assignment3_p2 {
	// this program depends on the matrices being these sizes
	public static double[][] matrixA = new double[2][2];
	public static double[][] eigenValues = new double[2][1];
	public static double[][] diagnolMatrix = new double[2][2];
	public static double[][] matrixR = new double[2][2];
	public static File inputFile;
	public static PrintWriter resultsFile;

	public static void main(String[] args) throws IOException {

		// setup the input and output file
		setupTheInputFile();
		setupTheOutputFile();

		// read the matrix from a file
		readMatrixFromFile();
		controlMethod();

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
		// depends on the input matrix being a 2x3
		for (int row = 0; row < 2; row++) {
			matrixA[row][0] = readableFile.nextDouble();
			matrixA[row][1] = readableFile.nextDouble();
			// throw away the last value on each row
			readableFile.nextDouble();
		}
	}

	// controls the flow of operation
	public static void controlMethod() throws IOException {

		if (solveForEigenValues()) {
			fillTheDiagnolMatrix();
			printMatrixToFile(diagnolMatrix);
			for (int i = 0; i < 2; i++) {
				solveForEigenVectors(diagnolMatrix[i][i], i);
			}
			printMatrixToFile(matrixR);

		} else { resultsFile.println("No real eigenvalues"); }

		resultsFile.close();
	}

	// solves for the eigenvalues and returns a boolean inidicating
	// if they are real or not
	public static boolean solveForEigenValues() {
		boolean realValue = true;

		// these are the eigenvalues we are solving for.
		double ev1, ev2;

		// first number is row and second number is column
		// a11 is matrix A, row 1, column 1
		double a11 = matrixA[0][0];
		double a12 = matrixA[0][1];
		double a21 = matrixA[1][0];
		double a22 = matrixA[1][1];

		// find coefficients of the characteristic equations
		int a = 1;
		double b = -(a11 + a22);
		double c = -(a21*a12 - a11*a22);

		// use the quadratic equation to solve for the eigenvalues
		ev1 = (-b + Math.sqrt((b*b) - 4*a*c))/(2*a);
		ev2 = (-b - Math.sqrt((b*b) - 4*a*c))/(2*a);

		// check that the solutions are finite, if so round to 4 sig figs
		// if not, return false for realValue
		if (Double.isFinite(ev1)) {
			BigDecimal bigDecimal = new BigDecimal(ev1);
			bigDecimal = bigDecimal.round(new MathContext(4));
			eigenValues[0][0] = bigDecimal.doubleValue();
		} else { realValue = false; }

		if (Double.isFinite(ev2)) {
			BigDecimal bigDecimal = new BigDecimal(ev2);
			bigDecimal = bigDecimal.round(new MathContext(4));
			eigenValues[1][0] = bigDecimal.doubleValue();
		} else { realValue = false; }

		return realValue;
	}

	// fills the diagnol matrix with the eigenvalues
	public static void fillTheDiagnolMatrix() {

		// if the second eigenvalue is greater than the first
		// then it is the dominannt eigenvalue
		if (eigenValues[1][0] > eigenValues[0][0]) {
			diagnolMatrix[0][0] = eigenValues[1][0];
			diagnolMatrix[1][1] = eigenValues[0][0];
		} 

		// otherwise they are equal or
		// the first eigenvalue is dominant
		else {
			diagnolMatrix[0][0] = eigenValues[0][0];
			diagnolMatrix[1][1] = eigenValues[1][0];
		}
	}

	// solves for the eigenvectors given eigenvalues
	// i determines which eigenvalue is being used
	public static void solveForEigenVectors(double value, int i) throws IOException {
		
		// uses A-I*lambda to solve for the eigenvector
		double[][] matrixAWithLambda = new double[2][2];
		matrixAWithLambda[0][0] = matrixA[0][0] - value;
		matrixAWithLambda[1][1] = matrixA[1][1] - value;
		matrixAWithLambda[0][1] = matrixA[0][1];
		matrixAWithLambda[1][0] = matrixA[1][0];

		// performs forward elimination so matri is in upper triangular form
		double[][] shearedMatrix = forwardElimination(matrixAWithLambda);

		double r1 = 1;
		double r2 = (0 - (r1*shearedMatrix[0][0]))/matrixAWithLambda[0][1];

		if (i == 0) {
			matrixR[0][0] = r1/Math.sqrt((r1*r1) + (r2*r2));
			matrixR[1][0] = r2/Math.sqrt((r1*r1) + (r2*r2));
		} else {
			matrixR[0][1] = r1/Math.sqrt((r1*r1) + (r2*r2));
			matrixR[1][1] = r2/Math.sqrt((r1*r1) + (r2*r2));
		}


	}

	// performs forward elimination by creating a shear matrix
	// and mulitplying it by a given matrix
	public static double[][] forwardElimination(double[][] matrix) {
		double[][] shearMatrix = new double[2][2];
		double[][] shearedMatrix = new double[2][2];

		// fill the shear matrix
		shearMatrix[0][0] = 1;
		shearMatrix[0][1] = 0;
		shearMatrix[1][0] = -matrix[1][0]/matrix[0][0];
		shearMatrix[1][1] = 1;

		// multiply the input matrix by the shear matrix
		matrixMultiply(shearMatrix, matrix, shearedMatrix);

		return shearedMatrix;
	}

	// multipies a 2x2 matrix by another matrix and fills a new matrix with the result
	public static void matrixMultiply(double[][] operator, double[][] operand, double[][] result) {
		for (int row = 0; row < result.length; row++) {
			for (int column = 0; column < result[row].length; column++) {
				result[row][column] = operator[row][0]*operand[0][column] + operator[row][1]*operand[1][column];
			}
		}
	}

	// prints a matrix to a given file
	public static void printMatrixToFile(double[][] matrix) throws IOException {
		for (int row = 0; row < matrix.length; row++) {
			for (int column = 0; column < matrix[row].length; column++) {
				resultsFile.printf("%-8.4g", matrix[row][column]);
			}
			resultsFile.println();
		}
	}
}