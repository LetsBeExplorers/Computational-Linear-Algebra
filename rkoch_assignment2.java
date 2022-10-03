import java.io.*;
import java.util.*;
import java.lang.*;

public class rkoch_assignment2 {
	public static void main(String[] args) throws IOException {

		// initiate game play
		playTheGame();
		
	}

	public static int recentTurns;
	public static String[][] gameBoard;
	public static ArrayList<int[][]> lines = new ArrayList<int[][]>();

	// controls game play and displays the board after each turn
	public static void playTheGame() throws IOException {

		// setup the input and output files
		inputReader(setupTheInputFile());
		PrintWriter resultsFile = setupTheOutputFile();

		// initializing turn number and begin main game loop
		int turnNumber = 0;

		for (int i = 0; i < lines.size(); i++) {

			// if the play is valid, then draw the line and print the game board
			if (checkPlayValidity(lines.get(i), turnNumber)) { 
				drawLine(lines.get(i), turnNumber); 
				printGameBoardToFile(resultsFile);
			}
			else { resultsFile.println("Not a valid play"); }

			turnNumber++;
		}

		resultsFile.close();

	}

	// prompts the user for an input file and returns it
	public static File setupTheInputFile() throws IOException {
		Scanner userInput = new Scanner(System.in);
		System.out.printf("Please enter the name of an input file: ");
		File inputFile = new File(userInput.nextLine());
		return inputFile;
	}

	// creates and sets up the output file for writing
	// prints a header in the file
	public static PrintWriter setupTheOutputFile() throws IOException {
		PrintWriter resultsFile = new PrintWriter(new File("rkoch_assignment2_output.txt"));
		resultsFile.printf("%s%d\n%s%d\n%s\n\n", "Board Size: ", gameBoard.length, "Cannot Reuse Row/Col: ", recentTurns, "X Goes First.");
		return resultsFile;
	}

	// reads the play input from a file and sets boardsize and recentTurns
	// fills an arraylist with the lines drawn from points in the input file
	public static void inputReader(File input) throws IOException {

		// open the file for reading
		Scanner readableFile = new Scanner(input);

		// take the first two integers and assign them to boardsize and recentTurns
		int boardSize = readableFile.nextInt();
		recentTurns = readableFile.nextInt();

		// generate the game board
		generateGameBoard(boardSize);

		// create arrays to store start and end points
		int[] startPoint = new int[2];
		int[] endPoint = new int[2];

		// read the next lines and fill the point arrays
		while (readableFile.hasNextInt()) {

			// fill the start point array
			for (int i = 0; i < startPoint.length; i++) {
				startPoint[i] = readableFile.nextInt();
			}

			// fill the end point array
			for (int j = 0; j < endPoint.length; j++) {
				endPoint[j] = readableFile.nextInt();
			}

			lines.add(buildLine(startPoint, endPoint));

		}
	}

	// generates a square game board from a given size
	public static void generateGameBoard(int size) {

		// create a new game board array
		gameBoard = new String[size][size];

		// fill the game board with periods as the starting value of each cell
		for (int row = 0; row < gameBoard.length; row++) {
		 	for (int column = 0; column < gameBoard[row].length; column++) {
				gameBoard[row][column] = ".";
			}
		}
	}

	// extracts x and y coordinates from the input file array 
	// passes the coordinate information to the line computation method
	public static int[][] buildLine(int[] start, int[] end) {

		// extract coordinates from the arrays
		int x1 = start[0];
		int y1 = start[1];
		int x2 = end[0];
		int y2 = end[1];

		// compute change in x and y
		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);

		// create a new array for storage of the line points
		int[][] line;

		// if slope is less than one
        if (dx > dy) { line = computeLine(x1, y1, x2, y2, dx, dy, false); }
        // if slope is greater than or equal to one, then swap x and y
        else { line = computeLine(y1, x1, y2, x2, dy, dx, true); }

        // testing
		for (int row = 0; row < line.length; row++) {
			for (int column = 0; column < line[row].length; column++) {
				System.out.printf("%2d", line[row][column]);
			}
			System.out.println();
		}

		return line;
	}

	// uses Bresenhams Line algorithm to obtain the integer points for a line between two points
	public static int[][] computeLine(int x1, int y1, int x2, int y2, int dx, int dy, boolean swap) {

		// create a new 2D array to hold the line points
		// format: row one is x's and row 2 is y's
        int[][] line = new int[2][dx+1];

        // error for comparison
        // multiply by 2 to avoid comparing with 1/2
        int error = 2 * dy - dx;

        // generate a new point for each interval between x1 and x2
		for (int i = 0; i <= dx; i++) {

			// fill the array with x and y values
			// swap the order if x and y were swapped
			if (!swap) {
				System.out.println(x1 + "," + y1 + "\n");
				line[0][i] = x1;
	        	line[1][i] = y1;
			} else {
				System.out.println(y1 + "," + x1 + " swapped" + "\n");
				line[0][i] = y1;
	        	line[1][i] = x1;
			}

	        // increment or decrement x depending on the slope direction
	        // and if the the error is smaller than zero, increase it
	        if (x1 < x2) { x1++; } else { x1--; }
	        if (error < 0) { error += 2 * dy; } else {

	            // increment or decrement y depending on the slope direction
	            // and decrease the error
	            if (y1 < y2) { y1++; } else { y1--; }
	            error += 2 * dy - 2 * dx;
	        }

		}

		return line;
	}

	// prints either X's or O's on a board according to a given line
	public static void drawLine(int[][] line, int turn) {
		
		for (int i = 0; i < line[0].length; i++) {

			// assign x and y values of the points on the line to variables
			int rowValue = line[0][i];
			int columnValue = line[1][i];

			// if the turn is even, then print X's. Otherwise print O's
			// fill the board cell with the x and y values from the line
			// subtract one from the indices because the cell values start at one
			if (turn%2 == 0) {
				gameBoard[rowValue-1][columnValue-1] = "X";
			} else {
				gameBoard[rowValue-1][columnValue-1] = "O";
			}
		}
	}

	// checks a play for validity by checking start and end points and perpendicularity
	public static boolean checkPlayValidity(int[][] line, int turn) {

		boolean validPlay = true;
		for (int i = 1; i <= recentTurns; i++) {

			// create start and end point arrays for both lines
			// pLine is previous line
			int[] lineStart = new int[2];
			int[] lineEnd = new int[2];
			int[] pLineStart = new int[2];
			int[] pLineEnd = new int[2];

			// if the index will not be negative
			if ((turn - i) >= 0) { 
				// then set the line to compare against some line in the previous recentTurns
				// this will start with the last line and work back to a line recentTurns ago
				int[][] prevLine = lines.get(turn-i);

				// fill the start and end points
				for (int j = 0; j < 2; j++) {
					lineStart[j] = line[j][0];
					lineEnd[j] = line[j][line[j].length-1];

					pLineStart[j] = prevLine[j][0];
					pLineEnd[j] = prevLine[j][prevLine[j].length-1];
				}

				// if the start point of the current line matches the start point of the previous line
				// or if the end point of the current line matches the end point of the previous line
				// then the play is not valid
				if (Arrays.equals(lineStart, pLineStart) || Arrays.equals(lineEnd, pLineEnd)) {
					validPlay = false;
				}

				// calculate changes in x and y for both lines
				int dx_l = lineEnd[0] - lineStart[0];
				int dy_l = lineEnd[1] - lineStart[1];
				int dx_pl = pLineEnd[0] - pLineStart[0];
				int dy_pl = pLineEnd[1] - pLineStart[1];



			}
		}

		return validPlay;
	}

	// prints the game board to a given file
	public static void printGameBoardToFile(PrintWriter file) throws IOException {

		// print the contents of each cell to a file
		for (int row = 0; row < gameBoard.length; row++) {
			for (int column = 0; column < gameBoard[row].length; column++) {
				file.printf("%s", gameBoard[row][column]);
			}
			file.println();
		}
		file.println();
	}

}