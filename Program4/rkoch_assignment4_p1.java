import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.NoSuchElementException;

public class rkoch_assignment4_p1 {

	public static Plane projPlane;
	public static Vector parallel;
	public static ArrayList<Vector> inputs = new ArrayList<Vector>();
	public static File inputFile;
	public static PrintWriter resultsFile;

	public static void main(String[] args) throws IOException {

		// setup the input and output files
		String filename = null;
		if(args.length > 0) filename = args[0];
		setupTheInputFile(filename);
		if(args.length > 1) filename = args[1];
		setupTheOutputFile(filename);

		// processes the input
		inputHandler();

		// creates a line from each point and the parallel vector
		// then finds the intersection of that line with the plane and prints it
		int count = 0;
		for (int i = 0; i < inputs.size(); i++) {
			Line line = new Line(inputs.get(i), parallel);
			printVectorToFile(line.intersection(projPlane));

			// handles printing a new line after each 3 points
			count += 1;
			if (count % 3 == 0) {
				resultsFile.println();
			}
		}

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
		
		// if a filename was not entered, then prompt for one
		if(filename == null) {
			Scanner userInput = new Scanner(System.in);
			System.out.printf("Please enter the name of an output file: ");
			filename = userInput.nextLine();
		}
		resultsFile = new PrintWriter(new File(filename));
	}

	// handles the input being routed to the correct functions
	public static void inputHandler() throws IOException {

		try {
			// open the file for reading
			Scanner file = new Scanner(inputFile);

			// create the plane
			projPlane = readPlaneFromFile(file);

			// create vector for parallel direction
			parallel = readVectorFromFile(file);

			// fill array list with additional points
			while(file.hasNextDouble()) {
				inputs.add(readVectorFromFile(file));
			}
		}

		// prints an error message if the input is not in the correct format
		catch(NoSuchElementException e) {
			resultsFile.println("Not valid input.");
			resultsFile.close();
			System.exit(1);
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
		if (normal.normsq() < 0.000001) {
			resultsFile.println("Not valid input.");
			resultsFile.close();
			System.exit(1);
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

	// prints a matrix to a given file
	public static void printVectorToFile(Vector vector) throws IOException {
		for (int i = 0; i < 3; i++) {
			resultsFile.printf("%-6.4g", roundToSignificantDigits(vector.coords[i], 4));
		}
	}

}