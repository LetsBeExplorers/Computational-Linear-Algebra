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
		matrixA = new int[2][2];
		vectorb = new int[2][1];


		setupTheInputFile();
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
			String row = readablefile.nextLine();
			extractMatrixAndVector();
		}
	}


}