import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.MathContext;

public class rkoch_assignment4_p3 {

	public static final double tolerance = .0005;
	public static final int maxIterations = 20;
	public static Vector eigenVector;
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
		printVectorToFile(findEigenVector());
		printVectorToFile(organizeRank());

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

	// counts the number of lines in the input file
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

	// uses the power method to find the dominant eigenvector
	public static Vector findEigenVector() throws IOException {
		ArrayList<Vector> r_list = new ArrayList<Vector>();
		ArrayList<Double> λ_list = new ArrayList<Double>();

		Vector r = new Vector(webPages.rows);

		// initialize first vector r to all 1s and add to list
		for (int i = 0; i < r.getSize(); i++) {
			r.setValue(i, 1);
		}
		r_list.add(r);

		// initialize lambda and add to the list
		Double λ = 0.00;
		λ_list.add(λ);

		// start computation loop
		for (int k = 1; k < maxIterations; k++) {
			Vector y = webPages.multiplyWithVector(r_list.get(k-1));

			// find the infinity norm of y
			double y_infinitynorm = 0;
			for (int i = 0; i < y.getSize(); i++) {
				if (y.coords[i] > y_infinitynorm) {
					y_infinitynorm = y.coords[i];
				}
			}

			// add both values to the lists
			λ_list.add(y_infinitynorm);
			r_list.add(y.scalarMultiply(1/y_infinitynorm));

			// check if we found the dominant eigenvector by comparing eigenvalues
			if (Math.abs(λ_list.get(k) - λ_list.get(k-1)) < tolerance) {
				eigenVector = r_list.get(k);
				return eigenVector;
			}
		}

		eigenVector = r_list.get(maxIterations);
		return eigenVector;
	}

	public static Vector organizeRank() {
		int j = 0;
		Vector pageRank = new Vector(eigenVector.getSize());
		while( j < eigenVector.getSize()) {
			double maxValue = 0;
			int page = 0;
			for (int i = 0; i < eigenVector.getSize(); i++) {

				if (eigenVector.coords[i] > maxValue) {
					maxValue = eigenVector.coords[i];
					page = i+1;
				}
			}
			pageRank.coords[j] = page;
			eigenVector.coords[page-1] = 0;
			j++;
		}

		return pageRank;
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
		for (int i = 0; i < vector.getSize(); i++) {
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

	public static class Matrix {
		public final double[][] matrix;
		public final int rows;
		public final int columns;

		// a matrix needs a number of rows and columns
		public Matrix(int rows, int columns) { 
			this.matrix = new double[rows][columns];
			this.rows = rows;
			this.columns = columns;
		}

		// sets a value of the matrix
		public void setValue(int row, int column, double value) {
			this.matrix[row][column] = value;
		}

		// gets a value from a matrix
		public double getValue(int row, int column) {
			return this.matrix[row][column];
		}

		// subtracts one matrix from another
		// depends on the matrices being the same size
		public Matrix subtract(Matrix that) {
			Matrix newMatrix = new Matrix(rows, columns);

			// for each row, for each column, subtract the right from the left
			for (int row = 0; row < newMatrix.rows; row++) {
				for (int column = 0; column < newMatrix.columns; column++) {
					newMatrix.setValue(row, column, (getValue(row, column) - that.getValue(row, column)));
				}
			}
			return newMatrix;
		}

		// adds one matrix to another
		public Matrix add(Matrix that) {
			Matrix newMatrix = new Matrix(rows, columns);

			// for each row, for each column, subtract the right from the left
			for (int row = 0; row < newMatrix.rows; row++) {
				for (int column = 0; column < newMatrix.columns; column++) {
					newMatrix.setValue(row, column, (getValue(row, column) + that.getValue(row, column)));
				}
			}
			return newMatrix;
		}

		// multiplies a matrix by a scalar value
		public Matrix scalarMultiply(double scalar) {
			Matrix scaledMatrix = new Matrix(rows, columns);

			// for each row, for each column, subtract the right from the left
			for (int row = 0; row < scaledMatrix.rows; row++) {
				for (int column = 0; column < scaledMatrix.columns; column++) {
					scaledMatrix.setValue(row, column, (getValue(row, column)*scalar));
				}
			}
			return scaledMatrix;
		}

		// transposes a given matrix
		public Matrix transpose() {
			Matrix transposedMatrix = new Matrix(rows, columns);

			// for each row, for each column, transpose the row and columns
			for (int column = 0; column < transposedMatrix.columns; column++) {
				for (int row = 0; row < transposedMatrix.rows; row++) {
					transposedMatrix.setValue(row, column, getValue(column, row));
				}
			}
			return transposedMatrix;
		}

		// multiplies a matrix by another matrix and fills a new matrix with the result
		// user must ensure the matrices being multiplied are compatible
		public Matrix multiply(Matrix that) {
			Matrix newMatrix = new Matrix(rows, that.columns);

			for (int row = 0; row < newMatrix.rows; row++) {
				for (int column = 0; column < newMatrix.columns; column++) {
					double total = 0;

					for (int i = 0; i < columns; i++) {
						total += getValue(row, i)*that.getValue(i, column);
					}

					newMatrix.setValue(row, column, total);
				}
			}
			return newMatrix;
		}

		// multiplies a matrix by a vector
		public Vector multiplyWithVector(Vector that) {
			Vector newVector = new Vector(rows);

			for (int row = 0; row < newVector.getSize(); row++) {
				double total = 0;

				for (int i = 0; i < columns; i++) {
					total += getValue(row, i)*that.coords[i];
				}
				newVector.setValue(row, total);
			}
			return newVector;
		}

	}

	public static class Vector {
		public final double[] coords;
		public static int size;

		// vector takes a size
		public Vector(int size) { 
			this.size = size;
			coords = new double[size];
		}

		// set a given value of the vector
		public void setValue(int index, double value) {
			coords[index] = value;
		}

		// returns the vector size
		public int getSize() {
			return size;
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

		// adds one vector to another
		public Vector add(Vector that) {
			Vector newVector = new Vector(getSize());
			for (int i = 0; i < this.coords.length; i++) {
				newVector.coords[i] = this.coords[i] + that.coords[i];
			}
			return newVector;
		}

		// subtracts one vector from another
		public Vector subtract(Vector that) {
			Vector newVector = new Vector(getSize());
			for (int i = 0; i < this.coords.length; i++) {
				newVector.coords[i] = this.coords[i] - that.coords[i];
			}
			return newVector;
		}

		// multiplies a vector with scalar
		public Vector scalarMultiply(double scalar) {
			Vector newVector = new Vector(getSize());
			for (int i = 0; i < this.coords.length; i++) {
				newVector.coords[i] = scalar * this.coords[i];
			}
			return newVector;
		}

		// computes the unit vector of a given vector
		public Vector unit() {
			return scalarMultiply(1/norm());
		}

		// projects the argument vector onto this vector
		// only works if this vector is not zero
		public Vector vectorProjection(Vector that) {
			double scalar = innerProduct(that)/(norm()*norm());
			return scalarMultiply(scalar);
		}
	}
}