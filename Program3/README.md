
<h1>Programming Assignment #3</h1>

<p>This is my programming assignment #3 for CS2300 (Computational Linear Algebra). It has three parts (p1, p2, p3), three input files, and each part has a program and resulting output files. The programs are written in Java and commented for easier understanding. Each output file contains matrices with no headings or descriptive text, only the numerical matrix elements. Each column is 8 spaces wide with the row elements left justified in each column. The rows are separated by line breaks. All decimal values are rounded to four significant digits. There are a total of 7 output files, 3 java programs, and 3 test input files. The java IO, Utility, and Math libraries were imported for very limited use in items such as handling the IO, Scanner, FIle and PrintWriter functions, and Big decimal/MAth context to handle the rounding to four significant digits.</p>

<h4>Part 1</h4>
<p>The first part of this assignment reads a 2x3 matrix from a file and separates it into a 2x2 matrix A and a 2x1 vector b. It then solves for
x in Ax=b for general A. In order to solve for x, this program performs Gaussian elimination by taking matrix A, performing forward elimination and then 
back substitution on it. If there is a unique solution, the output file contains a single 2x1 matrix with two lines and one number per line. If the 
system is unsolvable or inconsistent, then the output file is a single line reading "System inconsistent". If the system is underdetermined, the output 
file is a single line reading "System underdetermined". </p>
    
<p> Due to the nature of the computations involved in the back substitution, the input is checked for a few edge cases and reworked into a standard form if needed. It also uses the facts that dividing by zero will yield infinity and zero divided by zero is not a number in order to determine whether the system is inconsistent or underdetermined. This program depends on the inputs being of a certain size and formatting in order for it to work. There are two output files from this part, generated from the 2D testing input files
    
<h5>Part 1 Pseudocode</h5>

    class rkoch_assignment3_p1
    this program will depends on the matrices being the size 2x2
    static variables matrixA, shearedMatrixA, vectorb, shearedVectorb, solution, inputFile and resultsFile

    main
      setup the input and output file
      read the matrix from a file and check edge cases
      perform Gauss elimination and print the solution to a file

    method called setupTheInputFile: prompts the user for an input file and returns it
      scanner to read user input
      prompt for input file
      set the input as the static input file

    method called setupTheOutputFile: creates and sets up the output file for writing
      setups and creates the output file, assigns it to the static output file
      prints the file header

    method called readMatrixFromFile: reads a matrix from a file and puts each value into a 2D array
      opens the file for reading
      for each row in the input file, take each double and put it into a matrix
        first two on each line fill matrix A 
        the last double fills vector b

    method called checkEdgeCases: checks for edge cases and puts them into a standard form
      if the top left corner of matrix A is a zero, row swap it and vector b
      if the top row of matrix A are zeros, row swap it and vector b
      if the right column of matrix A are zeros, column swap it

    method called rowSwap: swaps the rows of a matrix and it's vector
      swap the matrix rows
      swap the vector rows

    method called columnSwap: performs a column swap for the purpose of checking if a system is underdetermined
      swap the matrix columns

    method called gaussElimination: performs Gauss elimination
      forward elimination
      back substitution

    method called forwardElimination: performs forward elimination by creating a shear matrix and mulitplying it by 
        MatrixA and vectorb
      fill the shear matrix
      multiply matrix A and vector b by the shear matrix

    method called matrixMultiply: multiplies a matrix by another matrix and fills a new matrix with the result
      for each row, for each column, multiplies the row by the column and adds them together

    method called backSubstitution: performs back substitution to solve for 2 unknown variables
    dependent on forward elmination being performed first
      set up variables we are solving for
      setup matrix a values
        first number is row and second number is column
        a11 is matrix A, row 1, column 1
      solve for x1 and x2
      sets solution matrix equal to x1 and x2

    method called roundToSignificantDigits: rounds a double to a specified number of significant digits

    method called printSolutiontoFile: checks if the system is inconsistent or underdetermined. if not, it prints the solution
      if vectorb is zero, the solution is the zero vector
      if the system is inconsistent, then it can't be solved. 
        solution matrix will contain infinities
      if a row or column is all zeros, then the system is underdetermined. 
        solution matrix will include NaN or one inifinite value and one finite value
      otherwise, print the solution

    method called printMatrixToFile: prints a matrix to a given file

<h4>Part 2</h4>
<p>The program for the second part of this assignment reads a 2x3 matrix from a file and separates it into a 2x2 matrix A and throws away the last column 
vector. Given the matrix A, this program computes the following: 1) the diagonal matrix whose diagonal elements are the corresponding eigenvalues (the dominant eigenvalue is the top left-hand corner), 2) the 2x2 matrix, R, whose ith column vector is the normalized eigenvector corresponding to λi for i=1,2, 3) the matrix composition, 4) the comparison between A and the matrix composition. The output file has seven lines, where the first two lines correspond to the 2x2 diagnol matrix, the next two lines are R, the next two lines are the output of the matrix composition, and the last line is 
one binary number corresponding to either yes, matrix A is the same (1), or no (0). The first six lines have two numbers per line, while the last line has one binary number. If there are no real eigenvalues, the output is a single line reading "No real eigenvalues". </p>

<p> The eigenvalues are found by using the coefficients of the characteristic equation to solve the quadratic equation, if they are not real values then the program stops here. If they are real, in order to solve for the eigenvectors, first, A − λI is found for both eigenvalues. By the Cayley–Hamilton theorem, assuming neither matrix is zero, the columns of each A − λI must include eigenvectors for the other eigenvalue. Again by the Cayley–Hamilton theorem, if either matrix is zero (this is checked by the program), then A is a multiple of the identity and any non-zero vector is an eigenvector. Wikipedia was used to find this theorem. </p>

<h5>Part 2 Pseudocode</h5>

    class rkoch_assignment3_p2
        this program will depends on the matrices being the size 2x2
        static variables matrixA, eigenValues, diagnolMatrix, matrixR, transposedMatrixR, matrixComposite, inputFile and 
            resultsFile

    main
      setup the input and output file
      read the matrix from a file
      if the eigenvalues are real, then continue calculations
      otherwise print there are no real eigenvalues
        fill and print the diagnol matrix
        solve for the eigenvectors and print R
        transpose R for the matrix composition
        compare the two matrices and print the result

    method called setupTheInputFile: prompts the user for an input file and returns it
      scanner to read user input
      prompt for input file
      set the input as the static input file

    method called setupTheOutputFile: creates and sets up the output file for writing
      setups and creates the output file, assigns it to the static output file
      prints the file header

    method called readMatrixFromFile: reads a matrix from a file and puts each value into a 2D array
      open the file for reading
      for each row in the input file, take each double and put it into a matrix
        first two on each line fill matrix A 
        throw away the last value on each row

    method called solveForEigenValues: solves for the eigenvalues and returns a boolean if they are real or not
      setup variables for the eigenvalues we are solving for
      setup matrix a values
        first number is row and second number is column
        a11 is matrix A, row 1, column 1
      find coefficients of the characteristic equations
      use the quadratic equation to solve for the eigenvalues
      check that the solutions are finite, if so, set as eigenvalues
      if not, return false for realValue

    method called roundToSignificantDigits: rounds a double to a specified number of significant digits

    method called fillTheDiagnolMatrix: fills the diagnol matrix with the eigenvalues
      if the second eigenvalue is greater than the first
        then it is the dominant eigenvalue
      otherwise they are equal or the first eigenvalue is dominant

    method called solveForEigenVectors: solves for the eigenvectors given eigenvalues
      create a 2x2 identity matrix
      bring in eigenvalues
      solve for λI
      solve for A − λI for both values of λ
      setup an array of zeros to compare
      setup the values of r
      by the Cayley–Hamilton theorem: Assuming neither matrix is zero, 
      the columns of each must include eigenvectors for the other eigenvalue
      for each eigen value	
        if the column is all zeros, move to the next one
        if the column is not all zeros, set this as the eigenvector
      again by the Cayley–Hamilton theorem: If either matrix is zero, 
      then A is a multiple of the identity and any non-zero vector is an eigenvector.
      for each eigenvalue
        if the length of the eigenvector is not already one, then normalize it

    method called scalarMultiply: multiplies a matrix by a scalar value
      for each row, for each column, mulitply the matrix value by the scalar

    method called matrixSubtraction: subtracts one matrix from another
      for each row, for each column, subtract the right from the left

    method called normalizeVectorValue: normalizes a value in a vector of length 2 given both values of the vector
      normalizes the first value given
      divide the value by the length of the vector	

    method called transposeMatrix: transposes a given matrix
      for each row, for each column, transpose the row and columns

    method called matrixComposition: performs matrix composition, user must ensure the matrices are the same size
      mulitply the first two matrices, then multiply that by the last matrix

    method called matrixMultiply: multiplies a matrix by another matrix and fills a new matrix with the result
    user must ensure the matrices being multiplied are the same size
      for each row, for each column, multiplies the row by the column and adds them together

    method called matrixCompare: compares two matrices and returns 1 if they are the same and 0 if they are not
      for each row, for each column, check that the value is the same	

    method called printMatrixToFile: prints a matrix to a given file

<h4>Part 3</h4>
<p> The third part of this assignment can read in both a 2x3 matrix and a 3x3 matrix. The columns of the input file correspond to the point coordinates. First, this program computes the area of the triangle using the equation A=1/2bh where height is the orthogonal distance from a point to the line formed from the other two (obtained by projecting the point onto the line and finding the distance). Second, using the first two points, this program constructs a line if 2D or a plane if 3D (the bisector of the two points) and finds the distance of the third point to that line or plane. This is calculated by finding the midpoint of the first two points and forming a vector between the midpoint and that third point. Then projecting this vector onto the vector formed by the first two points and finding the magnitude. The output contains two numbers on separate lines, the first number is the area of the triangle, and the second number is the distance between the point and either the line or the plane. </p>

<h5>Part 3 Pseudocode</h5>

    class rkoch_assignment3_p3
    static variables numLines, point1, point2, point3, solution, inputFile and resultsFile

    main
      setup the input and output files
      count the number of lines in the input file
      fill the point arrays
      the area of the triangle is the first entry in the solution matrix
      the distance from either the point or the plane is the second entry
      print the solution matrix and close the file

    method called setupTheInputFile: prompts the user for an input file and returns it
      if a filename was not entered, then prompt for one
        scanner to read user input
        prompt for input file
        set the input as the static input file

    method called setupTheOutputFile: creates and sets up the output file for writing
      if a filename was not entered, then prompt for one
        setups and creates the output file, assigns it to the static output file
        prints the file header

    method called countNumberOfLines: counts the number of lines in a given input file
      count the number of doubles and divide by 3
      depends on each line of the input matrix having only 3 numeric values

    method called readMatrixFromFile: reads a matrix from a file and puts each value into a 2D array
      fill the point arrays, always have only 1 column
      open the file for reading
      for each row in the input file, take each double and put it into a matrix
        put each column vector into a point

    method called triangleArea: finds the area of a triangle given 3 points
      setup vectors v and w between point 1 and 2 and point 1 and 3
      height = w orthogonal to v
      find the length of the base and height
      area of a triangle is 1/2 base times height

    method called distanceCalculator: finds the distance between a point and a line or a point and a plane
      find the midpoint
      setup a vector between the first two points and a vector between the third point and the midpoint of the first two
      take the projection of v onto w, the length of that is the distance

    method called midpointCalculator: finds the midpoint of two points
      for each row, for each column, find the midpoint

    method called distanceTwoPoints: finds the distance between two points
      distance is the magnitude of the vector between two points

    method called roundToSignificantDigits: rounds a double to a specified number of significant digits

    method called matrixSubtraction: subtracts one matrix from another
      for each row, for each column, subtract the right from the left

    method called matrixAddition: adds one matrix to another
      for each row, for each column, add the right to the left

    method called vectorLength: finds the length of a given vector
      for each row, for each column, add the square of the value to the total
      the total is the the square root of the sums

    method called scalarMultiply: multiplies a matrix by a scalar value
      for each row, for each column, mulitply the matrix value by the scalar

    method called dotProduct: find the dot product between two matrices
      for each row, for each column, add the product of the matrix values to the total

    method called matrixProjection: projects matrix2 onto matrix1, only works if matrix1 is not zero
      projection is the dot product divided by the length squared, multiplied by the vector

    method called printMatrixToFile: prints a matrix to a given file

<h3>The Github Repository</h3>
The link suitable for cloning the github repository containing the executable code and the output files is https://github.com/LetsBeExplorers/cs2300-coursework.git.
