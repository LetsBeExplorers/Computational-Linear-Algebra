import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.math.*;

public class rkoch_assignment3_p3 {
	public static int numLines = 0;
	public static File inputFile;

	public static void main(String[] args) throws IOException {

		// setup the input file and count the number of lines in it
		setupTheInputFile();
		countNumberOfLines();

	}

	// prompts the user for an input file and returns it
	public static void setupTheInputFile() throws IOException {
		Scanner userInput = new Scanner(System.in);
		System.out.printf("Please enter the name of an input file: ");
		inputFile = new File(userInput.nextLine());
	}

	// counts the number of lines in a given input file
	public static void countNumberOfLines() throws IOException{
		Scanner readableFile = new Scanner(inputFile);

		int count = 0;
		while (readableFile.hasNextDouble()) {
			readableFile.nextDouble();
			count++;
		}

		numLines = count/3;
	}

}