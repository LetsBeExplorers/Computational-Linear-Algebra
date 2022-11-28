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
	public static ArrayList<Triangle> triangles = new ArrayList<Triangle>();
	public static Line line;
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

		// handle the input routing to create planes and points for part 1
		part1InputHandler();

		// for each point and plane, calculate the distance and print it for part 1
		for (int i = 0; i < planes.size(); i++) {
			printNumberToFile(planes.get(i).distanceTo(points.get(0)), resultsFile1);
		}

		// handle the input routing to create a line and triangles for part 2
		part2InputHandler();

		// for each triangle, compute the intersection point with the line
		for (int i = 0; i < triangles.size(); i++) {
			Vector p = triangles.get(i).intersect(line);

			if (p != null) {
				printVectorToFile(p, resultsFile2);
			} else { resultsFile2.println("Does not intersect."); }

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

	// handles the input being routed to the correct functions for part 1
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

	// handles the input being routed to the correct functions for part 2
	public static void part2InputHandler() throws IOException {

		try {
			// open the file for reading
			Scanner file = new Scanner(inputFile);

			// create a line from the given points 
			Vector point1 = readVectorFromFile(file);
			Vector point2 = readVectorFromFile(file);
			line = new Line(point1, point2.subtract(point1));

			// throwaway the next 3 numbers by moving to the next line
			file.nextLine();

			// fill array lists with planes and points
			while(file.hasNextDouble()) {
				triangles.add(readTriangleFromFile(file));
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

	// reads in a triangle from a given file
	public static Triangle readTriangleFromFile(Scanner file) throws IOException {
		Vector vertex1 = readVectorFromFile(file);
		Vector vertex2 = readVectorFromFile(file);
		Vector vertex3 = readVectorFromFile(file);
		return new Triangle(vertex1, vertex2, vertex3);
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
			file.printf("%-8.4g", roundToSignificantDigits(vector.coords[i], 4));
		}
		file.println();
	}

	// prints a number to a given file
	public static void printNumberToFile(Double num, PrintWriter file) throws IOException {
		file.printf("%-8.4g\n", roundToSignificantDigits(num, 4));
	}

	public static class Vector {
		public final double[] coords;
		public static final Vector zero = new Vector(0,0,0);

		// vector takes three coordinates
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

		// magnitude of the vector
		public double norm() { return Math.sqrt(this.normsq()); }

		// finds the inner product between two vectors
		public double innerProduct(Vector that) {
			double total = 0;
			for (int i = 0; i < this.coords.length; i++) {
				total += this.coords[i]*that.coords[i];
			}
			return total;
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

		// projects the argument vector onto this vector
		// only works if this vector is not zero
		public Vector vectorProjection(Vector that) {
			double scalar = innerProduct(that)/(norm()*norm());
			return scalarMultiply(scalar);
		}

		// computes the cross product of 2 vectors
		public Vector crossProduct(Vector that) {
			double i = this.coords[1]*that.coords[2]-this.coords[2]*that.coords[1];
			double j = this.coords[0]*that.coords[2]-this.coords[2]*that.coords[0];
			double k = this.coords[0]*that.coords[1]-this.coords[1]*that.coords[0];
			return new Vector(i, -j, k);
		}
	}

	public static class Line {
		public final Vector point;
		public final Vector parallel;

		// line needs a point and a parallel vector
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

	public static class Plane {
		public final Vector point;
		public final Vector normal;

		// plane needs a point and a normal vector
		public Plane(Vector point, Vector normal) { 
			this.point = point;
			this.normal = normal;
		}
	}

	public static class Triangle {
		public final Vector vertex1;
		public final Vector vertex2;
		public final Vector vertex3;
		public final Plane plane;

		// triangle needs 3 vertices and forms a plane
		public Triangle(Vector vertex1, Vector vertex2, Vector vertex3) { 
			this.vertex1 = vertex1;
			this.vertex2 = vertex2;
			this.vertex3 = vertex3;
			this.plane = new Plane(vertex1, vertex1.subtract(vertex2).crossProduct(vertex3.subtract(vertex2)));
		}

		// checks if a line intersects a bounded triangle plane
		public Vector intersect(Line line) {

			// find intersection of the line and the plane containing the triangle
			Vector p = line.intersection(plane);

			// create vectors between each of the 3 vertices
			Vector ab = vertex2.subtract(vertex1);
			Vector bc = vertex3.subtract(vertex2);
			Vector ca = vertex1.subtract(vertex3);

			// create vectors between the intersection point and the vertices
			Vector pa = vertex1.subtract(p);
			Vector pb = vertex2.subtract(p);
			Vector pc = vertex3.subtract(p);

			// project the vectors from the intersection point and the endpoints onto the triangle sides
			Vector proj_pa_ab = ab.vectorProjection(pa);
			Vector proj_pb_ab = ab.vectorProjection(pb);
			Vector proj_pb_bc = bc.vectorProjection(pb);
			Vector proj_pc_bc = bc.vectorProjection(pc);
			Vector proj_pc_ca = ca.vectorProjection(pc);
			Vector proj_pa_ca = ca.vectorProjection(pa);

			// dot products between the projections on each triangle side
			double dot_ab = proj_pa_ab.innerProduct(proj_pb_ab);
			double dot_bc = proj_pb_bc.innerProduct(proj_pc_bc);
			double dot_ca = proj_pc_ca.innerProduct(proj_pa_ca);

			// if the dot products are negative then the projections are going in opposite directions 
			// which means the point is in between the endpoints of the triangle side
			// check all three sides
			if(dot_ab < 0 && dot_bc < 0 && dot_ca < 0) {rkoch_output_1_B2.txt
				return p;
			} else { return null; }
		}
	}
}