import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class rkoch3_p1 {
	public static int[][] matrixA;
	public static int[][] vectorb;
	public static int[][] solution;
	public static File inputFile;
	public static PrintWriter resultsFile;

	public static void main(String[] args) throws IOException {
		// matrix A size is 2x2
		matrixA = new int[2][2];
		// vector b size is 2x1
		vectorb = new int[2][1];

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

	// reads a matrix from a file and puts the rows into a string for extraction
	public static void readMatrixFromFile() throws IOException {

		// open the file for reading
		Scanner readableFile = new Scanner(inputFile);

		// read each row into a string and feed it into the extraction method
		for (int i = 0; i < 2; i++) {
			String row = readableFile.nextLine();
			extractMatrixAndVectorRows(row, i);
		}
	}

	// extracts a matrix row and vector row from a string
	// first two numbers of the string are the matrix row 
	// and the last number is the vector row
	public static void extractMatrixAndVectorRows(String row, int i) {
		matrixA[i][0] = Character.getNumericValue(row.charAt(0));
		matrixA[i][1] = Character.getNumericValue(row.charAt(2));
		vectorb[i][0] = Character.getNumericValue(row.charAt(4));
	}

	public static void gaussElimination() {
		forwardElimination();
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

	}

	// creates and sets up the output file for writing
	public static void setupTheOutputFile() throws IOException {
		Scanner userInput = new Scanner(System.in);
		System.out.printf("Please enter the name of an output file: ");
		resultsFile = new PrintWriter(new File(userInput.nextLine()));
	}
}