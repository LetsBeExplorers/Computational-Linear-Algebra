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

		// setup the input file
		// count the number of lines in it
		// fill the point arrays
		setupTheInputFile();
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
	// on each line of the input matrix having 3 numeric values
	public static void countNumberOfLines() throws IOException{
		Scanner readableFile = new Scanner(inputFile);

		int count = 0;
		while (readableFile.hasNextDouble()) {
			readableFile.nextDouble();
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

}