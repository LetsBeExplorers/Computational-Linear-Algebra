import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.math.*;

public class rkoch_assignment3_p2 {
	// this program depends on the matrices being these sizes
	public static double[][] matrixA = new double[2][2];
	public static double[][] diagnolMatrix = new double[2][2];
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


		} else { resultsFile.println("No real eigenvalues"); }
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
			ev1 = bigDecimal.doubleValue();
		} else { realValue = false; }

		if (Double.isFinite(ev2) {
			BigDecimal bigDecimal = new BigDecimal(ev2);
			bigDecimal = bigDecimal.round(new MathContext(4));
			ev2 = bigDecimal.doubleValue();
		} else { realValue = false; }

		return realValue;
	}
}