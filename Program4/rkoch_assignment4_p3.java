import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.MathContext;

public class rkoch_assignment4_p3 {

	public static Matrix webPages;
	public static File inputFile;
	public static PrintWriter resultsFile;

	public static void main(String[] args) throws IOException {

		// setup the input and output files
		String filename = null;
		if(args.length > 0) filename = args[0];
		setupTheInputFile(filename);
		if(args.length > 1) filename = args[1];
		setupTheOutputFile(filename);

		readMatrixFromFile();

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
		
		// if filenames were not entered on the command line, then prompt for them
		if(filename == null) {
			Scanner userInput = new Scanner(System.in);
			System.out.printf("Please enter the name of an output file: ");
			filename = userInput.nextLine();
		} 

		resultsFile = new PrintWriter(new File(filename));
	}

	// count the number of lines in the input file
	public static int determineMatrixSize() throws IOException {
		Scanner scanLines = new Scanner(inputFile);

		int count = 0;
		while (scanLines.hasNextLine()) {
			scanLines.nextLine();
			count++;
		}
		return count;
	}

	// reads the matrix values in from a file
	public static void readMatrixFromFile() throws IOException {
		int numLines = determineMatrixSize();
		webPages = new Matrix(numLines, numLines);

		// open the file for reading
		Scanner file = new Scanner(inputFile);

		for (int row = 0; row < webPages.rows; row++) {
			for (int column = 0; column < webPages.columns; column++) {
				webPages.setValue(row, column, file.nextDouble());
			}
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

	// prints a matrix to a given file
	public static void printVectorToFile(Vector vector) throws IOException {
		for (int i = 0; i < 3; i++) {
			resultsFile.printf("%-8.4g", roundToSignificantDigits(vector.coords[i], 4));
		}
		resultsFile.println();
	}

	// causes the program to print a failure message and exit
	public static void failFast() {
		resultsFile.println("Invalid input.");
		resultsFile.close();

		System.exit(1);
	}


	// prints a matrix to a given file
	public static void printMatrix(Matrix matrix) {
		for (int row = 0; row < matrix.rows; row++) {
			for (int column = 0; column < matrix.columns; column++) {
				resultsFile.printf("%-8.4g", roundToSignificantDigits(matrix.getValue(row, column), 4));
			}
			resultsFile.println();
		}
	}
}