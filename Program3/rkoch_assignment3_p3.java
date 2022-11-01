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
	public static double[][] solution = new double[2][1];
	public static File inputFile;
	public static PrintWriter resultsFile;

	public static void main(String[] args) throws IOException {

		// setup the input and output files
		// count the number of lines in the input file
		// fill the point arrays
		String filename = null;
		if(args.length > 0) filename = args[0];
		setupTheInputFile(filename);
		filename = null;
		if(args.length > 1) filename = args[1];
		setupTheOutputFile(filename);
		countNumberOfLines();
		readMatrixFromFile();

		// the area of the triangle is the first entry in the solution matrix
		solution[0][0] = triangleArea(point1, point2, point3);
		// the distance from either the point or the plane is the second entry
		solution[1][0] = distanceCalculator(point1, point2, point3);
		
		// print the solution matrix and close the file
		printMatrixToFile(solution);
		resultsFile.close();
	}

	// prompts the user for an input file and returns it
	public static void setupTheInputFile(String filename) throws IOException {
		
		// if a filename was not entered, then prompt for one
		if(filename == null) {
			Scanner userInput = new Scanner(System.in);
			System.out.printf("Please enter the name of an input file: ");
			filename = userInput.nextLine();
		}

		inputFile = new File(filename);
	}

	// creates and sets up the output file for writing
	public static void setupTheOutputFile(String filename) throws IOException {
		
		// if a filename was not entered, then prompt for one
		if(filename == null) {
			Scanner userInput = new Scanner(System.in);
			System.out.printf("Please enter the name of an output file: ");
			filename = userInput.nextLine();
		}
		resultsFile = new PrintWriter(new File(filename));
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

	// finds the area of a triangle given 3 points
	public static double triangleArea(double[][] point1, double[][] point2, double[][] point3) {
		
		// setup vectors v and w between point 1 and 2 and point 1 and 3
		double[][] vectorv = matrixSubtraction(point2, point1);
		double[][] vectorw = matrixSubtraction(point3, point1);

		// height = w orthogonal to v
		double[][] vectorh = matrixSubtraction(vectorw, matrixProjection(vectorv, vectorw));

		// find the length of the base and height
		double base = vectorLength(vectorv);
		double height = vectorLength(vectorh);

		// are of a triangle is 1/2 base times height
		double area = 0.5*base*height;
		return area;
	}

	// finds the distance between a point and a line or a point and a plane
	public static double distanceCalculator(double[][] point1, double[][] point2, double[][] point3) {
		double[][] midpoint = midpointCalculator(point1, point2);

		// setup a vector between the first two points and a vector
		// between the third point and the midpoint of the first two
		double[][] vectorv = matrixSubtraction(point2, point1);
		double[][] vectorw = matrixSubtraction(midpoint, point3);

		// take the projection of v onto w, the length of that is the distance
		double[][] vectord = matrixProjection(vectorv, vectorw);
		double distance = vectorLength(vectord);

		return distance;

	}

	// finds the midpoint of two points
	public static double[][] midpointCalculator(double[][] point1, double[][] point2) {
		double[][] midpoint = new double[point1.length][point1[0].length];

		// for each row, for each column, find the midpoint
		for (int row = 0; row < midpoint.length; row++) {
			for (int column = 0; column < midpoint[0].length; column++) {
				midpoint[row][column] = (point1[row][column] + point2[row][column])/2;
			}
		}

		return midpoint;

	}

	// finds the distance between two points
	public static double distanceTwoPoints(double[][] point1, double[][] point2) {
		
		// distance is the magnitude of the vector between two points
		double[][] newMatrix = matrixSubtraction(point2, point1);
		double distance = vectorLength(newMatrix);
		return distance;
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

		// for each row, for each column, subtract the right from the left
		for (int row = 0; row < newMatrix.length; row++) {
			for (int column = 0; column < newMatrix[0].length; column++) {
				newMatrix[row][column] = left[row][column] - right[row][column];
			}
		}

		return newMatrix;
	}

	// adds one matrix to another
	public static double[][] matrixAddition(double[][] left, double[][] right) {
		double[][] newMatrix = new double[left.length][left[0].length];

		// for each row, for each column, add the right to the left
		for (int row = 0; row < newMatrix.length; row++) {
			for (int column = 0; column < newMatrix[0].length; column++) {
				newMatrix[row][column] = left[row][column] + right[row][column];
			}
		}

		return newMatrix;
	}

	// finds the length of a given vector
	public static double vectorLength(double[][] matrix) {
		double total = 0;

		// for each row, for each column, add the square of the value to the total
		for (int row = 0; row < matrix.length; row++) {
			for (int column = 0; column < matrix[0].length; column++) {
				total += matrix[row][column]*matrix[row][column];
			}
		}

		// the total is the the square root of the sums
		total = Math.sqrt(total);
		return total;
	}

	// multiplies a matrix by a scalar value
	public static double[][] scalarMultiply(double scalar, double[][] matrix) {
		double[][] scaledMatrix = new double[matrix.length][matrix[0].length];

		// for each row, for each column, mulitply the matrix value by the scalar
		for (int row = 0; row < scaledMatrix.length; row++) {
			for (int column = 0; column < scaledMatrix[0].length; column++) {
				scaledMatrix[row][column] = matrix[row][column]*scalar;
			}
		}

		return scaledMatrix;
	}

	// find the dot product between two matrices
	public static double dotProduct(double[][] matrix1, double[][] matrix2) {
		double total = 0;

		// for each row, for each column, add the product of the matrix values to the total
		for (int row = 0; row < matrix1.length; row++) {
			for (int column = 0; column < matrix1[0].length; column++) {
				total += matrix1[row][column]*matrix2[row][column];
			}
		}

		return total;
	}

	// projects matrix2 onto matrix1
	// only works if matrix1 is not zero
	public static double[][] matrixProjection(double[][] matrix1, double[][] matrix2) {
		
		// projection is the dot product divided by the length squared, multiplied by the vector
		double scalar = dotProduct(matrix1, matrix2)/(vectorLength(matrix1)*vectorLength(matrix1));
		double[][] projectedMatrix = scalarMultiply(scalar, matrix1);

		return projectedMatrix;
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