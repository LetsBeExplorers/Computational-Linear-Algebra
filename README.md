<h1>Programming Assignment #2</h1>
<p>This is my programming assignment #2 for CS2300 (Computational Linear Algebra). The assignment was to recreate the base of a 
  game called Linear Domination. There are two players of the game, X and O, and a square game board. Starting with X, each player
  gets a turn to play a line on the board. They specify the line they want to draw by choosing a starting cell and ending cell. 
  The line must not have the same starting or ending points as the past K turns, and must not be perpendicular to any of the past
  K lines. If the line violates any of the aforementioned rules, then the play is invalid. K is specified at the beginning of the 
  game, and so is the board size. Cells may be flipped from one player to another if the play is valid. The winner has the most cells
  marked. </p>

<p> As the program is written, input is taken via a file only. The input file format must be exact, as provided by the assignment. The program 
  will prompt for an input file when it is run, and will crash if the file does not exist. A new board is printed to the output file after each 
  move, even if the move is invalid. The invalid line/move will not be drawn and the board will be the same as after the last move. The current 
  score will print in the output file under each new board. The invalid line is stored and used to compare for violations as discussed in the 
  last paragraph.</p>
  
 <h5>Pseudocode</h5>
 
    class rkoch_assignment2
    static variables K, game board, list of lines, the input and output files

    main
    setup and read the input file
    setup the output file and header
    initiate game play

    method called playTheGame: controls game play and displays the board after each turn
      initialize turn number
      begin main game loop
      for each line
        if the play is valid
          then draw the line
        print the game board
        print the score
        iterate turn number
      close the file

    method called setupTheInputFile: prompts the user for an input file and returns it
      scanner to read user input
      prompt for input file
      set the input as the static input file

    method called setupTheOutputFile: creates and sets up the output file for writing
      setups and creates the output file, assigns it to the static output file
      prints the file header

    method called inputReader: reads the play input from a file
      open the input file for reading
      take the first two integers and assign them to boardsize and recentTurns

      generate the game board

      create arrays to store start and end points
      while the output file has more integers
        fill the start point array
        fill the end point array

        fill the lines array list with lines built using the start and end points

    method called generateGameBoard: generates a square game board from a given size
        create a new game board using the size
        fill the game board with periods as the starting value of each cell

    method called buildLine: extracts x and y coordinates from the input file arrays 
      and passes the coordinate information to the line computation method

      extract coordinates from the arrays
      create a new array for storage of the line points
      call the compute line method, pass it x and y coordinates
      returns a line: array of arrays containing points

    method called computeLine: uses Bresenhams Line algorithm to obtain the integer points for a line between two point
      compute the magnitude of change in x and y
      handle the case where slope is greater than zero 
            compute line again with x and y swapped
            reverse the x and y from output

        create a new 2D array to hold the line points
          format: row one is x's and row 2 is y's
          # of columns is +1 to include the end point

            create an error variable for comparison
              multiply by 2 to avoid comparing with 1/2

        for each interval between x1 and x2, generate a new point
          fill the array with x and y values
          increment or decrement x depending on the slope direction
              if the the error is smaller than zero, increase it
                  else increment or decrement y depending on the slope direction
                  and decrease the error
        return the 2D line array

    method called drawLine: prints either X's or O's on a board according to a given line
        for each item in the line arraylist
          assign x and y values of the points on the line to variables

          set the gameboard cell value equal to a variable for passing around
            subtract one from the indices because the cell values start at one

          if the turn is even, then set the variable cell equal to X
          otherwise set the cell variable equal to O
          flip the cell if there is already a value in it

          set the gameboard cell value equal to the variable's new value

    methodcalle flipTheCell: flips a cell from one given value to another
      the cell variable is equal to the value being passed into the method
      return the cell variable

    mathodcalled checkPLayValidity: checks a play for validity by checking start and end points and perpendicularity
      returns a boolean

      set the boolean value to true
        for each integer interval of K
          create start and end point arrays for both lines

          if the index will not be negative
            then set the line to compare against as some line in the previous K turns
            start with the last line and work back to a line K turns ago

            fill the start and end points

            if the start point of the current line matches the start point of the previous line
            or if the end point of the current line matches the end point of the previous line
              then the play is not valid

            calculate changes in x and y for both lines

            if the lines are perpendicular (i.e. slopes are negative inverses of each other)
              then the play is not valid

            if the move is just a point with no line
              then the move is invalid

      return the boolean value

    method called printGameBoardToFile: prints the game board to a given file
      for each cell in the gameboard
        print the cellcontents to a file

    method called printTheScore: prints the current score
      initialize the count variables

      for each cell in the gameboard
        counting the X's and O's
      print the current score to the output file
    
<h5> Assignment Requirements </h5>
<p> • Define an array for the game board, with a method for generating the board of size N and another for printing/display. 
  For this assignment, you should use “printing” to display the board and use X and O for the two players (as opposed to two colors).
  
  This requirement is fulfilled by 1) the 2D array called gameBoard of size NxN, which is generated using the method generateGameBoard(), and
  2) the method printGameBoardToFile(), which prints the game board to a file. X's and O's are used for the two players. 
 
• Define a method accepting input for play, which needs to allow input from a file at least as described below[in the asignment].
  
  The method that accepts input is called inputReader(), it reads in a file of the format specified in the assignment document.
  There is also a method called setupTheInput() which creates and sets up the input file.
  
• Define a representation for lines played in the game.
  
  Lines are represented by X's and O's drawn by the function drawLine(). The lines are stored in an ArrayList called lines, which contains 2D arrays each
  holding points for a line. Each line holds at least two points, the start and end points. buildLine() and computeLine() are the methods which construct
  the points for the line, each point corresponding to the cell that needs to be marked.
  
• Define a method for testing each play for validity (empty end cells and not perpendicular to previous plays).
  
  The method for testing the validity of a move is called checkPlayValidity(). It checks to make sure the current line does not have the 
  same start or end points as a line from the past K turns (valid or not) and that is is not perpendicular to any lines from the last K turns.
  
• Define a method for flipping the cells in the grid.
  
  The method for flipping cells is called flipTheCell().
  
• Define a method for computing/display the current score.
  
  The method for computing and displaying the current score is called printTheScore(), and it prints the current score after each new board is printed
  in the output file.
  
• Define a control method that calls the other methods, displaying the game board after each play. 

  The control method is called playTheGame(), and it controls the main game loop to create and draw the lines, to check play validity, and to draw the 
  game board and print the current score. It also keeps track of the turn number. This method will draw a game board after each play, regardless of 
  validity. However if the play is invalid, then the line will not be drawn and the board printed will look the same as after the last play. </p>