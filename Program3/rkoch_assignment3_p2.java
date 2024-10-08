import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.math.BigDecimal;
import java.math.MathContext;

public class rkoch_assignment3_p2 {
	// this program depends on the matrices being these sizes
	public static double[][] matrixA = new double[2][2];
	public static double[][] eigenValues = new double[2][1];
	public static double[][] diagnolMatrix = new double[2][2];
	public static double[][] matrixR = new double[2][2];
	public static double[][] transposedMatrixR = new double[2][2];
	public static double[][] matrixComposite = new double[2][2];
	public static File inputFile;
	public static PrintWriter resultsFile;

	public static void main(String[] args) throws IOException {

		// setup the input and output file
		setupTheInputFile();
		setupTheOutputFile();

		// read the matrix from a file
		readMatrixFromFile();

		// if the eigenvalues are real, then continue calculations
		// otherwise print there are no real eigenvalues
		if (solveForEigenValues()) {

			// fill and print the diagnol matrix
			fillTheDiagnolMatrix();
			printMatrixToFile(diagnolMatrix);

			// solve for the eigenvectors and print R
			solveForEigenVectors();
			printMatrixToFile(matrixR);

			// transpose R for the matrix composition
			transposedMatrixR = transposeMatrix(matrixR);
			matrixComposite = matrixComposition(matrixR, diagnolMatrix, transposedMatrixR);
			printMatrixToFile(matrixComposite);

			// compare the two matrices and print the result
			if (matrixCompare(matrixA, matrixComposite)) {
				resultsFile.printf("%d", 1);
			} else { resultsFile.printf("%d", 0); }

		} else { resultsFile.println("No real eigenvalues"); }

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

	// reads a matrix from a file and puts each value into a 2D array
	public static void readMatrixFromFile() throws IOException {

		// open the file for reading
		Scanner readableFile = new Scanner(inputFile);

		// take each double and put it into a matrix
		// first two on each line fill matrix A 
		// depends on the input matrix being a 2x3
		for (int row = 0; row < 2; row++) {
			matrixA[row][0] = readableFile.nextDouble();
			matrixA[row][1] = readableFile.nextDouble();
			// throw away the last value on each row
			readableFile.nextDouble();
		}
	}

	// solves for the eigenvalues and returns a boolean inidicating
	// if they are real or not
	public static boolean solveForEigenValues() {
		boolean realValue = true;

		// these are the eigenvalues we are solving for.
		double ev1, ev2;

		// first number is row and second number is column
		// a11 is matrix A, row 1, column 1
		double a11 = matrixA[0][0];
		double a12 = matrixA[0][1];
		double a21 = matrixA[1][0];
		double a22 = matrixA[1][1];

		// find coefficients of the characteristic equations
		int a = 1;
		double b = -(a11 + a22);
		double c = -(a21*a12 - a11*a22);

		// use the quadratic equation to solve for the eigenvalues
		ev1 = (-b + Math.sqrt((b*b) - 4*a*c))/(2*a);
		ev2 = (-b - Math.sqrt((b*b) - 4*a*c))/(2*a);

		// check that the solutions are finite, if so round to 4 sig figs
		// if not, return false for realValue
		if (Double.isFinite(ev1) && Double.isFinite(ev2)) {
			eigenValues[0][0] = ev1;
			eigenValues[1][0] = ev2;
		} else { realValue = false; }

		return realValue;
	}

	// rounds a double to a specified number of significant digits
	public static double roundToSignificantDigits(double number, int digits) {
		double roundedNum;

		BigDecimal bigDecimal = new BigDecimal(number);
		bigDecimal = bigDecimal.round(new MathContext(digits));
		roundedNum = bigDecimal.doubleValue();

		return roundedNum;
	}

	// fills the diagnol matrix with the eigenvalues
	public static void fillTheDiagnolMatrix() {

		// if the second eigenvalue is greater than the first
		// then it is the dominant eigenvalue
		if (eigenValues[1][0] > eigenValues[0][0]) {
			diagnolMatrix[0][0] = eigenValues[1][0];
			diagnolMatrix[1][1] = eigenValues[0][0];
		} 

		// otherwise they are equal or
		// the first eigenvalue is dominant
		else {
			diagnolMatrix[0][0] = eigenValues[0][0];
			diagnolMatrix[1][1] = eigenValues[1][0];
		}
	}

	// solves for the eigenvectors given eigenvalues
	public static void solveForEigenVectors() throws IOException {
		// create a 2x2 identity matrix
		double[][] identity = new double[2][2];
		identity[0][0] = 1;
		identity[0][1] = 0;
		identity[1][0] = 0;
		identity[1][1] = 1;

		// bring in eigenvalues
		double λ_1 = eigenValues[0][0];
		double λ_2 = eigenValues[1][0];

		// solve for λI
		double[][] λ_1I = scalarMultiply(λ_1, identity);
		double[][] λ_2I = scalarMultiply(λ_2, identity);

		// solve for A − λI for both values of λ
		double[][] aλ_1I = matrixSubtraction(matrixA, λ_1I);
		double[][] aλ_2I = matrixSubtraction(matrixA, λ_2I);

		// setup an array of zeros to compare
		double[] zero = {0, 0};

		// setup the values of r
		double r1_1 = 0, r1_2 = 0, r2_1 = 0, r2_2 = 0;

		// by the Cayley–Hamilton theorem: Assuming neither matrix is zero, 
		// the columns of each must include eigenvectors for the other eigenvalue
		if (aλ_1I[0] != zero && aλ_1I[1] != zero && aλ_2I[0] != zero && aλ_2I[1] != zero) {
			// if the column is all zeros, move to the next one
			if (aλ_2I[0][0] == 0 && aλ_2I[1][0] == 0) {
				r1_1 = aλ_2I[0][1];
				r1_2 = aλ_2I[1][1];
			} 

			// if the column is not all zeros, set this as the eigenvector
			else {
				r1_1 = aλ_2I[0][0];
				r1_2 = aλ_2I[1][0];
			}

			// if the column is all zeros, move to the next one
			if (aλ_1I[0][0] == 0 && aλ_1I[1][0] == 0) {
				r2_1 = aλ_1I[0][1];
				r2_2 = aλ_1I[1][1];
			} 

			// if the column is not all zeros, set this as the eigenvector
			else {
				r2_1 = aλ_1I[0][0];
				r2_2 = aλ_1I[1][0];
			}
		} 

		// again by the Cayley–Hamilton theorem: If either matrix is zero, 
		// then A is a multiple of the identity and any non-zero vector is an eigenvector.
		else {
			r1_1 = 1;
			r1_2 = 2;

			r2_1 = 3;
			r2_2 = 4;
		}


		// if the length of the eigenvector is not already one, then normalize it
		if (((r1_1*r1_1) + (r1_2*r1_2)) != 1) {
			double normr1_1 = normalizeVectorValue(r1_1, r1_2);
			double normr1_2 = normalizeVectorValue(r1_2, r1_1);

			matrixR[0][0] = normr1_1;
			matrixR[1][0] = normr1_2;
		} else {
			matrixR[0][0] = r1_1;
			matrixR[1][0] = r1_2;
		}

		// if the length of the eigenvector is not already one, then normalize it
		if (((r1_1*r1_1) + (r1_2*r1_2)) != 1) {
			double normr2_1 = normalizeVectorValue(r2_1, r2_2);
			double normr2_2 = normalizeVectorValue(r2_2, r2_1);

			matrixR[0][1] = normr2_1;
			matrixR[1][1] = normr2_2;
		} else {
			matrixR[0][1] = r2_1;
			matrixR[1][1] = r2_2;
		}

	}

	// multiplies a matrix by a scalar value
	public static double[][] scalarMultiply(double scalar, double[][] matrix) {
		double[][] scaledMatrix = new double[matrix.length][matrix[0].length];

		// for each row, for each column, mulitply the matrix value by the scalar
		for (int row = 0; row < scaledMatrix.length; row++) {
			for (int column = 0; column < scaledMatrix[0].length; column++) {
				scaledMatrix[row][column] = matrix[row][column]*scalar;
			}
		}

		return scaledMatrix;
	}

	// subtracts one matrix from another
	public static double[][] matrixSubtraction(double[][] left, double[][] right) {
		double[][] newMatrix = new double[left.length][left[0].length];

		// for each row, for each column, subtract the right from the left
		for (int row = 0; row < newMatrix.length; row++) {
			for (int column = 0; column < newMatrix[0].length; column++) {
				newMatrix[row][column] = left[row][column] - right[row][column];
			}
		}

		return newMatrix;
	}

	// normalizes a value in a vector of length 2 given both values of the vector
	// normalizes the first value given
	public static double normalizeVectorValue(double x1, double x2) {
		// divide the value by the length of the vector
		double norm = x1/Math.sqrt((x1*x1) + (x2*x2));
		return norm;
	}

	// transposes a given matrix
	public static double[][] transposeMatrix(double[][] matrix) throws IOException {
		double[][] transposedMatrix = new double[matrix.length][matrix[0].length];

		// for each row, for each column, transpose the row and columns
		for (int column = 0; column < transposedMatrix[0].length; column++) {
			for (int row = 0; row < transposedMatrix.length; row++) {
				transposedMatrix[row][column] = matrix[column][row];
			}
		}

		return transposedMatrix;
	}

	// performs matrix composition
	// user must ensure the matrices are the same size
	public static double[][] matrixComposition(double[][] matrix1, double[][] matrix2, double[][] matrix3) {
		double[][] matrixComposition = new double[matrix1.length][matrix1[0].length];
		double[][] holdingMatrix = new double[matrix1.length][matrix1[0].length];

		// mulitply the first two matrices, then multiply that by the last matrix
		matrixMultiply(matrix1, matrix2, holdingMatrix);
		matrixMultiply(holdingMatrix, matrix3, matrixComposition);

		return matrixComposition;
	}

	// multiplies a matrix by another matrix and fills a new matrix with the result
	// user must ensure the matrices being multiplied are the same size
	public static void matrixMultiply(double[][] operator, double[][] operand, double[][] result) {
		for (int row = 0; row < result.length; row++) {
			for (int column = 0; column < result[row].length; column++) {
				result[row][column] = operator[row][0]*operand[0][column] + operator[row][1]*operand[1][column];
			}
		}
	}

	// compares two matrices and returns 1 if they are the same and 0 if they are not
	public static boolean matrixCompare(double[][] matrix1, double[][] matrix2) {
		boolean match = true;

		// for each row, for each column, check that the value is the same
		for (int row = 0; row < matrix1.length; row++) {
			for (int column = 0; column < matrix1[0].length; column++) {
				match = match && (matrix1[row][column] == matrix2[row][column]);
			}
		}

		return match;
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