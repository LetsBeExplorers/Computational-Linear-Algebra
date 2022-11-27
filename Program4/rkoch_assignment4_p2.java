import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.NoSuchElementException;

public class rkoch_assignment4_p2 {
	public static void main(String[] args) throws IOException {
		
		// setup the input and output files
		String filename = null;
		String filename1 = null;
		if(args.length > 0) filename = args[0];
		setupTheInputFile(filename);
		if(args.length > 1) filename = args[1];
		if(args.length > 2) filename1 = args[2];
		setupTheOutputFile(filename, filename1);

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
}