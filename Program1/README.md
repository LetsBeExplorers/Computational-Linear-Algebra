
<h1>Programming Assignment #1</h1>

<p>This is my programming assignment #1 for CS2300 (Computational Linear Algebra). It has three parts (p1, p2, p3), each part has a 
program and resulting output files. The programs are written in Java and commented for easier understanding. Each output file contains 
only one matrix with no headings or descriptive text, only the numerical matrix elements. Each column is 8 spaces wide with the row
elements left justified in each column. The rows are separated by line breaks. All decimal values are rounded to one decimal point. 
For example, the output file for a 3x2 matrix containing decimal values would look like:</p>

<h4>Part 1</h4>
<p>The first part of this assignment creates five 2D arrays (i.e. 2D matrices) according to asignment specifications, and writes them 
out to files. Given a starting value and an interval, the program fills the arrays according to either row major order (across columns 
and then rows) or column major order (across rows and then columns). </p>

<h5>Part 1 Pseudocode</h5>

    import java File
    import java IOException
    import java PrintWriter

    class called rkoch_p1
      main statement throws IOException
      store 6 in constant called FIRSTNAME
      store 4 in constant called LASTNAME

      create new array called Mat1: FIRSTNAME as rows, LASTNAME as columns 
      call method rowMajorOrder and pass the Mat1 array and start at 1 with interval 1
      call method printIntegerMatrixToFile and pass it the Mat1 array and file name

      create new array called Mat2: LASTNAME as rows, FIRSTNAME as columns 
      call method rowMajorOrder and pass the Mat2 array and start at 4 with interval 2
      call method printIntegerMatrixToFile and pass it the Mat2 array and file name

      create new array called Mat3: LASTNAME as rows, FIRSTNAME as columns 
      call method columnMajorOrder and pass the Mat3 array and start at 0.3 with interval 0.1
      call method printDoubleMatrixToFile and pass it the Mat3 array and file name

      create new array called Mat4: 9 as rows, 11 as columns 
      call method rowMajorOrder and pass the Mat4 array and start at 3 with interval 3
      call method printIntegerMatrixToFile and pass it the Mat4 array and file name

      create new array called Mat5: 9 as rows, 11 as columns 
      call method columnMajorOrder and pass the Mat5 array and start at -5 with interval 1.5
      call method printDoubleMatrixToFile and pass it the Mat5 array and file name

    method called rowMajorOrder with arguments matrix, start number, interval
      initialize count variable to start number
      for each row
        for each column
          the value equals count
          increase count by the interval

    method called columnMajorOrder with arguments matrix, start number, interval
      initialize count variable to start number
      for each column
        for each row
          the value equals count
          increase count by the interval

    method called printIntegerMatrixToFile with arguments matrix, file name throws IOException
      create a file reference variable called filename and refer to a text file
      create the file where the matrix results will be written: store in variable called resultsFile
      for each row
        for each column
          print each matrix element
      move to a new line
      close the results file

    method called printDoubleMatrixToFile with arguments matrix, file name throws IOException
      create a file reference variable called filename and refer to a text file
      create the file where the matrix results will be written: store in variable called resultsFile
      for each row
        for each column
          print each matrix element
      move to a new line
      close the results file

<h4>Part 2</h4>
<p>The program for the second part of this assignment reads the output files containing 2D arrays from part one, and adds each unique 
combination of arrays, writing the results to an output file. If the arrays are not the same size, and thus unable to be added, the 
program will write an error message to the output file instead of a new array. The error message will say that the two matrices are not the 
same size, so they cannot be added.</p>

<h5>Part 2 Pseudocode</h5>

    import java File
    import java IOException
    import java PrintWriter
    import java Scanner

    class called rkoch_p1
      main statement throws IOException
      store 6 in constant called FIRSTNAME
      store 4 in constant called LASTNAME

      create new array called Mat1: FIRSTNAME as rows, LASTNAME as columns
      call method read2DDoubleArrayFromFile: pass it Mat1 and rkoch_mat1.txt

      create new array called Mat2: LASTNAME as rows, FIRSTNAME as columns
      call method read2DDoubleArrayFromFile: pass it Mat2 and rkoch_mat2.txt

      create new array called Mat3: LASTNAME as rows, FIRSTNAME as columns
      call method read2DDoubleArrayFromFile: pass it Mat3 and rkoch_mat3.txt

      create new array called Mat4: 9 as rows, 11 as columns
      call method read2DDoubleArrayFromFile: pass it Mat4 and rkoch_mat4.txt

      create new array called Mat5: 9 as rows, 11 as columns
      call method read2DDoubleArrayFromFile: pass it Mat5 and rkoch_mat5.txt

      create an ArrayList called matrices
      add each array to the ArrayList

      call method addEachMatrixPair and pass it the ArrayList

    method called addEachMatrixPair with argument ArrayList throws IOException
      for each array in the ArrayList
        for each next array in the ArrayList
          pass both arrays and the indexing values into the addMatrices method

    method called addMatrices with arguments 2 matrices and integers i and j
      create new array with number of rows and columns matching the first matrix in the pair
      check matrices have the same number of rows and columns
        if they are, for each row
            for each column
              the element of the new array is the corresponding element in the first array plus the corresponding element in the second array
        use the method printDoubleMatrixToFile to print the new array results into a file
        if they are not, use the method printErrorToFile to print the error into a file


    method called read2DDoubleArrayFromFile with arguments file name, matrix throws IOException
      create a file reference variable called inputFileName and refer to a text file
      create a scanner for the file
      for each row
        for each column
          read the next double into the matrix element

    method called printDoubleMatrixToFile with arguments matrix, file name throws IOException
      create a file reference variable called filename and refer to a text file
      create the file where the matrix results will be written: store in variable called resultsFile
      for each row
        for each column
          print each matrix element
      move to a new line
      close the results file

    method called printErrorToFile with arguments file name, integers i and j
      create a file reference variable called filename and refer to a text file
      create the file where the matrix results will be written: store in variable called resultsFile
      print error message in file
      close the results file

<h4>Part 3</h4>
<p>The third part of the assignment also reads the output files containing 2D arrays from part one, but this program multiplies each unique 
combination of arrays, writing the results to an output file. If the number of rows in the first array does not equal the number of columns
in the second array, then they are unable to be multiplied. In this case, the program will write an error message to the output file instead 
of a new array. The error message will say that the first array does not have the same number of rows as the number of columns in the second
array, so they cannot be multiplied.</p>

<h5>Part 3 Pseudocode</h5>

    import java File
    import java IOException
    import java PrintWriter
    import java Scanner

    class called rkoch_p1
      main statement throws IOException
      store 6 in constant called FIRSTNAME
      store 4 in constant called LASTNAME

      create new array called Mat1: FIRSTNAME as rows, LASTNAME as columns
      call method read2DDoubleArrayFromFile: pass it Mat1 and rkoch_mat1.txt

      create new array called Mat2: LASTNAME as rows, FIRSTNAME as columns
      call method read2DDoubleArrayFromFile: pass it Mat2 and rkoch_mat2.txt

      create new array called Mat3: LASTNAME as rows, FIRSTNAME as columns
      call method read2DDoubleArrayFromFile: pass it Mat3 and rkoch_mat3.txt

      create new array called Mat4: 9 as rows, 11 as columns
      call method read2DDoubleArrayFromFile: pass it Mat4 and rkoch_mat4.txt

      create new array called Mat5: 9 as rows, 11 as columns
      call method read2DDoubleArrayFromFile: pass it Mat5 and rkoch_mat5.txt

      create an ArrayList called matrices
      add each array to the ArrayList

      call method addEachMatrixPair and pass it the ArrayList

    method called multiplyingEachMatrixPair with argument ArrayList throws IOException
      for each array in the ArrayList
        for each next array in the ArrayList
          pass both arrays and the indexing values into the multiplyMatrices method

    method called mulitplyMatrices with arguments 2 matrices and integers i and j
      create new array with number of rows in first matrix and number of columns in the second matrix
      check matrices are compatible: number of rows in first matrix matches number of columns in the second matrix
        if they are, for each row
            for each column
              intialize cell value to zero
              for each value of k
                mulitply each element in the row with each element in the column
              fill the matrix element with the cell value
        use the method printDoubleMatrixToFile to print the new array results into a file
        if they are not, use the method printErrorToFile to print the error into a file


    method called read2DDoubleArrayFromFile with arguments file name, matrix throws IOException
      create a file reference variable called inputFileName and refer to a text file
      create a scanner for the file
      for each row
        for each column
          read the next double into the matrix element

    method called printDoubleMatrixToFile with arguments matrix, file name throws IOException
      create a file reference variable called filename and refer to a text file
      create the file where the matrix results will be written: store in variable called resultsFile
      for each row
        for each column
          print each matrix element
      move to a new line
      close the results file

    method called printErrorToFile with arguments file name, integers i and j
      create a file reference variable called filename and refer to a text file
      create the file where the matrix results will be written: store in variable called resultsFile
      print error message in file
      close the results file

<h3>The Github Repository</h3>
The link suitable for cloning the github repository containing the executable code and the output files is []().
