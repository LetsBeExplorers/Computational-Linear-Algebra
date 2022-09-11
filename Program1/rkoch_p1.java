import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class rkoch_p1 {
	public static void main(String[] args) throws IOException {
		final int FIRSTNAME = 6;
		final int LASTNAME = 4;

		// create the arrays, fill them, and print them to files
		int[][] Mat1 = new int[FIRSTNAME][LASTNAME];
		rowMajorOrder(Mat1, 1, 1);
		printIntegerMatrixToFile(Mat1, "rkoch_mat1.txt");

		int[][] Mat2 = new int[LASTNAME][FIRSTNAME];
		rowMajorOrder(Mat2, 4, 2);
		printIntegerMatrixToFile(Mat2, "rkoch_mat2.txt");

		double[][] Mat3 = new double[LASTNAME][FIRSTNAME];
		columnMajorOrder(Mat3, 0.3, 0.1);
		printDoubleMatrixToFile(Mat3, "rkoch_mat3.txt");

		int[][] Mat4 = new int[9][11];
		rowMajorOrder(Mat4, 3, 3);
		printIntegerMatrixToFile(Mat4, "rkoch_mat4.txt");

		double[][] Mat5 = new double[9][11];
		columnMajorOrder(Mat5, -5, 1.5);
		printDoubleMatrixToFile(Mat5, "rkoch_mat5.txt");

	}

	// This will fill a given matrix across columns first and then rows
	// given a starting value and an interval
	public static void rowMajorOrder (int[][] mat, int start, int interval) {
		
		// initialize count variable to start number
		int count = start;

		// iterate across columns and then rows, filling each matrix value with an integer
		for (int row = 0; row < mat.length; row++) {
			for (int column = 0; column < mat[row].length; column++) {

				// fill matrix value with count, then increase count by the interval
				mat[row][column] = count;
				count+=interval;
			}
		}
	}

	// This will fill a given matrix across rows first and then columns
	// given a starting value and an interval
	public static void columnMajorOrder (double[][] mat, double start, double interval) {
		
		// initialize count variable to start number
		double count = start;

		// iterate across rows and then columns, filling each matrix value with an integer
		for (int column = 0; column < mat[0].length; column++) {
			for (int row = 0; row < mat.length; row++) {
				
				// fill matrix value with count, then increase count by the interval
				mat[row][column] = count;
				count+=interval;
			}
		}
	}

	// This will print each integer matrix to a file
	// It will display each row of the matrix with values separated by 8 spaces
	public static void printIntegerMatrixToFile (int[][] mat, String name) throws IOException {
		
		// setup the file reference variable to refer to a text file
		File filename = new File(name);

		// creates the file that the matrix data will be written to
		PrintWriter resultsFile = new PrintWriter(filename);

		// write the details for each matrix position into the file
		for (int row = 0; row < mat.length; row++) {
			for (int column = 0; column < mat[row].length; column++) {
				resultsFile.printf("%-8d", mat[row][column]);
			}
			resultsFile.println();
		}
		resultsFile.close();
	}

	// This will print each double matrix to a file
	// It will display each row of the matrix with values separated by 8 spaces
	public static void printDoubleMatrixToFile (double[][] mat, String name) throws IOException {
		
		// setup the file reference variable to refer to a text file
		File filename = new File(name);

		// creates the file that the matrix data will be written to
		PrintWriter resultsFile = new PrintWriter(filename);

		// write the details for each matrix position into the file
		for (int row = 0; row < mat.length; row++) {
			for (int column = 0; column < mat[row].length; column++) {
				resultsFile.printf("%-8.1f", mat[row][column]);
			}
			resultsFile.println();
		}
		resultsFile.close();
	}
}
