import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.math.BigDecimal;
import java.math.MathContext;

public class rkoch_assignment4_p1 {

	public static File inputFile;
	public static PrintWriter resultsFile;

	public static void main(String[] args) throws IOException {

	// setup the input and output files
	String filename = null;
	if(args.length > 0) filename = args[0];
	setupTheInputFile(filename);
	if(args.length > 1) filename = args[1];
	setupTheOutputFile(filename);


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

	// rounds a double to a specified number of significant digits
	public static double roundToSignificantDigits(double number, int digits) {
		double roundedNum;

		BigDecimal bigDecimal = new BigDecimal(number);
		bigDecimal = bigDecimal.round(new MathContext(digits));
		roundedNum = bigDecimal.doubleValue();

		return roundedNum;
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