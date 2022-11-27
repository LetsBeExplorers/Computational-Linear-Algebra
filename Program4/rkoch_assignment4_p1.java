import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.NoSuchElementException;

public class rkoch_assignment4_p1 {

	public static final double tolerance = .000001;
	public static Plane projPlane;
	public static Vector parallel;
	public static ArrayList<Vector> inputs = new ArrayList<Vector>();
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

		// processes the input
		inputHandler();

		// creates a line from each point and the parallel vector for part 1
		// then finds the intersection of that line with the plane and prints it
		int count = 0;
		for (int i = 0; i < inputs.size(); i++) {
			Line line = new Line(inputs.get(i), parallel);
			printVectorToFile(line.intersection(projPlane), resultsFile1);

			// handles printing a new line after each 3 points
			count += 1;
			if (count % 3 == 0) {
				resultsFile1.println();
			}
		}

		// creates a line from each point and negative x for part 2
		// then finds the intersection of that line with the plane and prints it
		count = 0;
		for (int i = 0; i < inputs.size(); i++) {
			Line line = new Line(inputs.get(i), Vector.zero.subtract(inputs.get(i)));
			printVectorToFile(line.intersection(projPlane), resultsFile2);

			// handles printing a new line after each 3 points
			count += 1;
			if (count % 3 == 0) {
				resultsFile2.println();
			}
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
	public static void inputHandler() throws IOException {

		try {
			// open the file for reading
			Scanner file = new Scanner(inputFile);

			// create the plane
			projPlane = readPlaneFromFile(file);

			// create vector for parallel direction
			parallel = readVectorFromFile(file);

			// check that the parallel vector is not parallel to the plane
			if (parallel.innerProduct(projPlane.normal) < tolerance) {
				failFast();
			}

			// fill array list with additional points
			while(file.hasNextDouble()) {
				inputs.add(readVectorFromFile(file));
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

	// prints a matrix to a given file
	public static void printVectorToFile(Vector vector, PrintWriter file) throws IOException {
		for (int i = 0; i < 3; i++) {
			file.printf("%-6.4g", roundToSignificantDigits(vector.coords[i], 4));
		}
	}

	// vector has coordinates and a zero vector
	public static class Vector {
		public final double[] coords;
		public static final Vector zero = new Vector(0,0,0);
	
		public Vector(double x, double y, double z) { 
			coords = new double[3];
			coords[0] = x;
			coords[1] = y;
			coords[2] = z;
		}
	
		public double normsq() {
			double sum = 0.0;
			for(int i = 0; i < coords.length; i++) { sum += coords[i]*coords[i]; }
			return sum;
		}
	
		public double norm() { return Math.sqrt(this.normsq()); }
	
		// finds the inner product between two vectors
		public double innerProduct(Vector that) {
			double total = 0;
			for (int i = 0; i < this.coords.length; i++) {
				total += this.coords[i]*that.coords[i];
			}
			return total;
		}
	
		// adds one vector to another
		public Vector add(Vector that) {
			Vector newVector = new Vector(0, 0, 0);
			for (int i = 0; i < this.coords.length; i++) {
				newVector.coords[i] = this.coords[i] + that.coords[i];
			}
			return newVector;
		}
	
		// subtracts one vector from another
		public Vector subtract(Vector that) {
			Vector newVector = new Vector(0, 0, 0);
			for (int i = 0; i < this.coords.length; i++) {
				newVector.coords[i] = this.coords[i] - that.coords[i];
			}
			return newVector;
		}
	
		// multiplies a vector with scalar
		public Vector scalarMultiply(double scalar) {
			Vector newVector = new Vector(0, 0, 0);
			for (int i = 0; i < this.coords.length; i++) {
				newVector.coords[i] = scalar * this.coords[i];
			}
			return newVector;
		}
	
		// computes the unit vector of a given vector
		public Vector unit() {
			return scalarMultiply(1/norm());
		}

	}

	// plane has a point and normal vector
	public static class Plane {
		public final Vector point;
		public final Vector normal;

		public Plane(Vector point, Vector normal) { 
			this.point = point;
			this.normal = normal;
		}
	}

	// line has a point and parallel vector
	public static class Line {
		public final Vector point;
		public final Vector parallel;

		public Line(Vector point, Vector parallel) { 
			this.point = point;
			this.parallel = parallel;
		}

		// finds the intersection point of a line and plane
		public Vector intersection(Plane that) {
			double numerator = that.point.subtract(this.point).innerProduct(that.normal);
			double denominator = this.parallel.innerProduct(that.normal);
			return this.point.add(this.parallel.scalarMultiply(numerator/denominator));
		}
	}

}