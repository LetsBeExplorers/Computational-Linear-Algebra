import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class rkoch_p2 {
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

		addEachMatrixPair(matrices);
	}

	// passes each matrix pair into another method for adding them
	public static void addEachMatrixPair (ArrayList matrices) throws IOException {

		// for each array in the ArrayList and its pair, pass into method for adding matrices
		for (int i = 0; i < matrices.size(); i++) {
			for (int j = i+1; j < matrices.size(); j++) {
				addMatrices((double[][])matrices.get(i), (double[][])matrices.get(j), i, j);
			}
		}
	}

	// adds two matrices and passes results into another method for printing
	// or prints an error if matrices aren't the same size
	public static void addMatrices (double[][] mat1, double[][] mat2, int i, int j) throws IOException {
		// create new array to store addition results
		double[][] newMat = new double[mat1.length][mat1[0].length];

		// check matrices are the same size
		if (mat1.length == mat2.length && mat1[0].length == mat2[0].length) {

		 	// fill the new matrix with the corresponding element in the first array 
		 	// plus the corresponding element in the second array
		 	for (int row = 0; row < mat1.length; row++) {
		 		for (int column = 0; column < mat1[row].length; column++) {
					newMat[row][column] = mat1[row][column]+mat2[row][column];
				}
		 	}

		 	// print the resulting matrix to a file
			printDoubleMatrixToFile(newMat, "rkoch_p2_out" + (i+1) + (j+1) + ".txt");
		 } else {

		 	// print the error message to a file
		 	printErrorToFile("rkoch_p2_out" + (i+1) + (j+1) + ".txt", i, j);
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
				resultsFile.printf("%-8.1f", mat[row][column]);
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
		resultsFile.println("Mat" + (i+1) + " and Mat" + (j+1) + " are not the same size, they cannot be added.");
		resultsFile.close();
	}
}