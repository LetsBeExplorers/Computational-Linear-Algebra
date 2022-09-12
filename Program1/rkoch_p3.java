import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class rkoch_p3 {
	public static void main(String[] args) throws IOException {
		final int FIRSTNAME = 6;
		final int LASTNAME = 4;

		// create new 2D arrays and read in the matrix elements from files
		double[][] Mat1 = new double[FIRSTNAME][LASTNAME];
		read2DDoubleArrayFromFile("rkoch_mat1.txt", Mat1);

		double[][] Mat2 = new double[LASTNAME][FIRSTNAME];
		read2DDoubleArrayFromFile("rkoch_mat2.txt", Mat2);

		double[][] Mat3 = new double[LASTNAME][FIRSTNAME];
		read2DDoubleArrayFromFile("rkoch_mat3.txt", Mat3);

		double[][] Mat4 = new double[9][11];
		read2DDoubleArrayFromFile("rkoch_mat4.txt", Mat4);

		double[][] Mat5 = new double[9][11];
		read2DDoubleArrayFromFile("rkoch_mat5.txt", Mat5);

		// add arrays to an ArrayList for iteration
		ArrayList<double[][]> matrices = new ArrayList<double[][]>(5);
		matrices.add(Mat1);
		matrices.add(Mat2);
		matrices.add(Mat3);
		matrices.add(Mat4);
		matrices.add(Mat5);

		multiplyEachMatrixPair(matrices);
	}

	// passes each matrix pair into another method for multiplying them
	public static void multiplyEachMatrixPair (ArrayList matrices) throws IOException {

		// for each array in the ArrayList and its pair, pass into method for mulitplying matrices
		for (int i = 0; i < matrices.size(); i++) {
			for (int j = i+1; j < matrices.size(); j++) {
				multiplyMatrices((double[][])matrices.get(i), (double[][])matrices.get(j), i, j);
			}
		}
	}

	// multiplies two matrices and passes results into another method for printing
	// or prints an error if matrices aren't compatible for multiplying
	public static void multiplyMatrices (double[][] mat1, double[][] mat2, int i, int j) throws IOException {
		// create new array to store addition results
		double[][] newMat = new double[mat1.length][mat2[0].length];

		// check the number of rows in the first matrix matches the number of columns in the second matrix
		if (mat1.length == mat2[0].length) {

			// iterate across columns and then rows, filling each matrix element
		 	for (int row = 0; row < mat1.length; row++) {
		 		for (int column = 0; column < mat2[0].length; column++) {
		 			
		 			// intialize cell value
		 			double cellValue = 0;

		 			// mulitply each element in the row with each element in the column
		 			for (int k = 0; k < mat2.length; k++) {
		 				cellValue += mat1[row][k]*mat2[k][column];
		 			}

		 			// fill the matrix element with the cell value
		 			newMat[row][column] = cellValue;
				}
		 	}

		 	// print the resulting matrix to a file
			printDoubleMatrixToFile(newMat, "rkoch_p3_out" + (i+1) + (j+1) + ".txt");
		 } else {

		 	// print the error message to a file
		 	printErrorToFile("rkoch_p3_out" + (i+1) + (j+1) + ".txt", i, j);
		 }
	}

	// reads doubles from a file and input them as elements in a 2D array
	public static void read2DDoubleArrayFromFile (String file, double[][] mat) throws IOException {
		
		// setup the file reference variable to refer to a text file
		File inputFileName = new File(file);

		// open the file for reading by creating a scanner for the file
		Scanner inputFile = new Scanner(inputFileName);

		// iterate across columns and then rows, filling each matrix element with an double
		for (int row = 0; row < mat.length; row++) {
			for (int column = 0; column < mat[row].length; column++) {
				mat[row][column] = inputFile.nextDouble();
			}
		}
	}

	// This will print a double matrix to a file
	public static void printDoubleMatrixToFile (double[][] mat, String name) throws IOException {
		
		// setup the file reference variable to refer to a text file
		File filename = new File(name);

		// creates the file that the matrix data will be written to
		PrintWriter resultsFile = new PrintWriter(filename);

		// write the details for each matrix position into the file
		for (int row = 0; row < mat.length; row++) {
			for (int column = 0; column < mat[row].length; column++) {
				resultsFile.printf("%-8.0f", mat[row][column]);
			}
			resultsFile.println();
		}
		resultsFile.close();
	}

	public static void printErrorToFile (String name, int i, int j) throws IOException {
		// setup the file reference variable to refer to a text file
		File filename = new File(name);

		// creates the file that the matrix data will be written to
		PrintWriter resultsFile = new PrintWriter(filename);

		// print error messages into file
		resultsFile.println("Mat" + (i+1) + " does not have the same number of rows as the number of columns in Mat" + (j+1) + ", they cannot be multiplied.");
		resultsFile.close();
	}
}