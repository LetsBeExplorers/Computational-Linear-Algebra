import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class rkoch_assignment4_p1 {

	public static File inputFile;


	public static void main(String[] args) throws IOException {

	// setup the input and output files
	String filename = null;
	if(args.length > 0) filename = args[0];
	setupTheInputFile(filename);
	


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
}