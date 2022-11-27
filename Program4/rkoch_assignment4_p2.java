import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.NoSuchElementException;

public class rkoch_assignment4_p2 {

	public static final double tolerance = .000001;
	public static ArrayList<Vector> points = new ArrayList<Vector>();
	public static ArrayList<Plane> planes = new ArrayList<Plane>();
	public static File inputFile;
	public static PrintWriter resultsFile1;
	public static PrintWriter resultsFile2;

	public static void main(String[] args) throws IOException {
		
		// setup the input and output files
		String filename = null;
		String filename1 = null;
		if(args.length > 0) filename = args[0];
		setupTheInputFile(filename);
		if(args.length > 1) filename = args[1];
		if(args.length > 2) filename1 = args[2];
		setupTheOutputFile(filename, filename1);

		// handle the input routing to create planes and points
		part1InputHandler();

		// for each point and plane, calculate the distance and print it for part 1
		for (int i = 0; i < planes.size(); i++) {
			printNumberToFile(planes.get(i).distanceTo(points.get(0)), resultsFile1);
		}

		resultsFile1.close();
		resultsFile2.close();
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

	// creates and sets up the output files for writing
	public static void setupTheOutputFile(String filename, String filename1) throws IOException {
		
		// if filenames were not entered on the command line, then prompt for them
		if(filename == null) {
			Scanner userInput = new Scanner(System.in);
			System.out.printf("Please enter the name of an output file for part 1: ");
			filename = userInput.nextLine();
			resultsFile1 = new PrintWriter(new File(filename));
		} else { resultsFile1 = new PrintWriter(new File(filename)); }

		if(filename1 == null) {
			Scanner userInput = new Scanner(System.in);
			System.out.printf("Please enter the name of an output file for part 2: ");
			filename1 = userInput.nextLine();
			resultsFile2 = new PrintWriter(new File(filename1));
		} else { resultsFile2 = new PrintWriter(new File(filename1)); }
	}

	// handles the input being routed to the correct functions
	public static void part1InputHandler() throws IOException {

		try {
			// open the file for reading
			Scanner file = new Scanner(inputFile);

			// fill array lists with planes and points
			while(file.hasNextDouble()) {
				planes.add(readPlaneFromFile(file));
				points.add(readVectorFromFile(file));
			}
		}

		// prints an error message if the input is not in the correct format
		catch(NoSuchElementException e) {
			failFast();
		}
	}

	// reads in a vector from a given file
	protected static Vector readVectorFromFile(Scanner file) throws IOException {
		double x = file.nextDouble();
		double y = file.nextDouble();
		double z = file.nextDouble();
		return new Vector(x, y, z);
	}

	// reads in a plane from a given file
	public static Plane readPlaneFromFile(Scanner file) throws IOException {
		Vector point = readVectorFromFile(file);
		Vector normal = readVectorFromFile(file);

		// prints an error message if the normal vector is the zero vector
		if (normal.normsq() < tolerance) {
			failFast();
		}
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

	// causes the program to print a failure message and exit
	public static void failFast() {
		resultsFile1.println("Not valid input.");
		resultsFile1.close();
		resultsFile2.println("Not valid input.");
		resultsFile2.close();
		System.exit(1);
	}

	// prints a vector to a given file
	public static void printVectorToFile(Vector vector, PrintWriter file) throws IOException {
		for (int i = 0; i < 3; i++) {
			file.printf("%-6.4g", roundToSignificantDigits(vector.coords[i], 4));
		}
	}

	// prints a number to a given file
	public static void printNumberToFile(Double num, PrintWriter file) throws IOException {
		file.printf("%-8.4g\n", roundToSignificantDigits(num, 4));
	}
}