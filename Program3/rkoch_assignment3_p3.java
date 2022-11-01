import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.math.*;

public class rkoch_assignment3_p3 {
	public static int numLines = 0;
	public static double[][] point1;
	public static double[][] point2;
	public static double[][] point3;
	public static File inputFile;
	public static PrintWriter resultsFile;

	public static void main(String[] args) throws IOException {

		// setup the input and output files
		// count the number of lines in the input fil
		// fill the point arrays
		setupTheInputFile();
		setupTheOutputFile();
		countNumberOfLines();
		readMatrixFromFile();

		resultsFile.close();
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

	// counts the number of lines in a given input file by
	// counting the number of doubles and diving by 3, depends
	// on each line of the input matrix having only 3 numeric values
	public static void countNumberOfLines() throws IOException{
		Scanner scanLines = new Scanner(inputFile);

		int count = 0;
		while (scanLines.hasNextDouble()) {
			scanLines.nextDouble();
			count++;
		}

		numLines = count/3;
	}

	// reads a matrix from a file and puts each value into a 2D array
	public static void readMatrixFromFile() throws IOException {

		// fill the point arrays, always have only 1 column
		point1 = new double[numLines][1];
		point2 = new double[numLines][1];
		point3 = new double[numLines][1];

		// open the file for reading
		Scanner readableFile = new Scanner(inputFile);

		// take each double and put it into a matrix
		// first two on each line fill matrix A 
		// depends on the input matrix being a 2x3
		for (int row = 0; row < numLines; row++) {
			point1[row][0] = readableFile.nextDouble();
			point2[row][0] = readableFile.nextDouble();
			point3[row][0] = readableFile.nextDouble();
		}
	}

	// rounds a double to a specified number of significant digits
	public static double roundToSignificantDigits(double number, int digits) {
		double roundedNum;

		BigDecimal bigDecimal = new BigDecimal(number);
		bigDecimal = bigDecimal.round(new MathContext(digits));
		roundedNum = bigDecimal.doubleValue();

		return roundedNum;
	}

	// subtracts one matrix from another
	public static double[][] matrixSubtraction(double[][] left, double[][] right) {
		double[][] newMatrix = new double[left.length][left[0].length];

		for (int row = 0; row < newMatrix.length; row++) {
			for (int column = 0; column < newMatrix[0].length; column++) {
				newMatrix[row][column] = left[row][column] - right[row][column];
			}
		}

		return newMatrix;
	}

	// finds the length of a given matrix
	public static double matrixLength(double[][] matrix) {
		double total = 0;

		for (int row = 0; row < matrix.length; row++) {
			for (int column = 0; column < matrix[0].length; column++) {
				total += matrix[row][column]*matrix[row][column];
			}
		}

		total = Math.sqrt(total);
		return total;
	}

	// multiplies a matrix by a scalar value
	public static double[][] scalarMultiply(double scalar, double[][] matrix) {
		double[][] scaledMatrix = new double[matrix.length][matrix[0].length];

		for (int row = 0; row < scaledMatrix.length; row++) {
			for (int column = 0; column < scaledMatrix[0].length; column++) {
				scaledMatrix[row][column] = matrix[row][column]*scalar;
			}
		}

		return scaledMatrix;
	}

	// multiplies a matrix by another matrix and fills a new matrix with the result
	// user must ensure the matrices being multiplied are the same size
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
				resultsFile.printf("%-8.4g", roundToSignificantDigits(matrix[row][column], 4));
			}
			resultsFile.println();
		}
	}


}