import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.math.BigDecimal;
import java.math.MathContext;
import Vector;
import Plane;

public class rkoch_assignment4_p1 {

	public static Plane projPlane;
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



	public static void readVectorsFromFile() throws IOException {

		// open the file for reading
		Scanner file = new Scanner(inputFile);

		projPlane = readPlaneFromFile(file);

		// create vector for parallel direction
		Vector parallel = readVectorFromFile(file);

	}

	// reads in a vector from a given file
	protected static Vector readVectorFromFile(Scanner file) throws IOException {
		double x = file.nextDouble();
		double y = file.nextDouble();
		double z = file.nextDouble();
		return new Vector(x, y, z);
	}

	// reads in a plane from a given file
	public static void readPlaneFromFile(Scanner file) throws IOException {
		Vector point = readVectorFromFile(file);
		Vector normal = readVectorFromFile(file);
		return new Plane(point, normal);
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