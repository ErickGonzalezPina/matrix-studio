import javax.swing.*;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class Matrix {
	// class attributes
	private long[][] array;
	private long low;
	private long high;

	// Constructors
	public Matrix(int r, int c, int l, int h) {
		array = new long[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				array[i][j] = l + (int) ((h - l + 1) * Math.random());
			}
		}
	}

	public Matrix() {
		low = 1;
		high = 100;
		array = new long[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				array[i][j] = low + (int) ((high - low + 1) * Math.random());
			}
		}
	}

	// Methods
	public long[][] getArray() {
		return array;
	}

	public long getLow() {
		return low;
	}

	public long getHigh() {
		return high;
	}

	public void createRandomly() {
		/*
		 * Prompts the user for matrix size and value range, then fills the matrix with
		 * random values in that range.
		 */
		String input = JOptionPane.showInputDialog(null, "Please enter an integer < 50:",
				"# Rows of Two-Dimensional Array", JOptionPane.QUESTION_MESSAGE);
		int r = Integer.parseInt(input);

		input = JOptionPane.showInputDialog(null, "Please enter an integer < 50:",
				"# Colums of Two-Dimensional Array", JOptionPane.QUESTION_MESSAGE);
		int c = Integer.parseInt(input);

		array = new long[r][c];
		input = JOptionPane.showInputDialog(null, "Please enter an integer > 0:",
				"Lowest Value in the Array", JOptionPane.QUESTION_MESSAGE);
		low = Integer.parseInt(input);
		input = JOptionPane.showInputDialog(null, "Please enter an integer < 1000,000:",
				"Highest Value in the Array", JOptionPane.QUESTION_MESSAGE);
		high = Integer.parseInt(input);
		for (int i = 0; i < array.length; i++)
			for (int j = 0; j < array[i].length; j++)
				array[i][j] = low + (long) ((high - low + 1) * Math.random());
	}

	public void createFromFile() {
		/* Lets the user select a file and loads matrix data from it. */
		String dataFilePath = null;
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setDialogTitle("Open Text File");
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			dataFilePath = chooser.getSelectedFile().getPath();
		}
		try {
			readFileIntoArray(dataFilePath);
		} catch (IOException ioe) {
			System.exit(0);
		}
	}

	public void readFileIntoArray(String dataFilePath) throws IOException {
		/* Reads matrix data from a file at the given path and fills the matrix. */
		if (dataFilePath != null) {
			Scanner integerTextFile = new Scanner(new File(dataFilePath));

			int rows = integerTextFile.nextInt();
			int cols = integerTextFile.nextInt();
			array = new long[rows][cols];

			while (integerTextFile.hasNextInt()) {
				for (int i = 0; i < rows; i++)
					for (int j = 0; j < cols; j++)
						array[i][j] = integerTextFile.nextInt();
			}
			low = this.getMinimum();
			high = this.getMaximum();
			// end of file detected
			integerTextFile.close();
		}
	}

	public long getMaximum() {
		/* Returns the maximum value in the matrix. */
		long max = Long.MIN_VALUE;
		for (int row = 0; row < array.length; row++) {
			for (int col = 0; col < array[0].length; col++) {
				if (array[row][col] > max)
					max = array[row][col];
			}
		}
		return max;
	}

	public long getMinimum() {
		/* Returns the minimum value in the matrix. */
		long min = Integer.MAX_VALUE;
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (array[i][j] < min) {
					min = array[i][j];
				}
			}
		}
		return min;
	}

	public double getAverage() {
		/* Calculates and returns the average (mean) of all matrix elements. */
		int rows = array.length;
		int cols = array[0].length;
		int N = rows * cols;
		// Calculate the average
		double sum = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				sum += array[i][j];
			}
		}
		double average = sum / N;
		return average;
	}

	public double getSTD() {
		/* Calculates and returns the standard deviation of all matrix elements. */
		int rows = array.length;
		int cols = array[0].length;
		int N = rows * cols;
		// Calculate the mean(average)
		double sum = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				sum += array[i][j];
			}
		}
		double average = sum / N;
		// Calculate the sum of squared differences from the mean
		double numerator = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				numerator += Math.pow(array[i][j] - average, 2);
			}
		}
		double std = Math.sqrt((numerator / N));
		return std;
	}

	public boolean search(int key) {
		/*
		 * Searches for a value in the matrix; returns true if found, otherwise false.
		 */
		for (int row = 0; row < array.length; row++) {
			for (int col = 0; col < array[row].length; col++) {
				if (array[row][col] == key) {
					return true;
				}
			}
		}
		return false;
	}

	public long[][] getRowStat() {
		/* Returns a 2D array with each row’s minimum, maximum, and sum. */
		long rowMin, rowMax, rowSum;
		long[][] rowStat = new long[array.length][3];

		for (int row = 0; row < array.length; row++) {
			// Initialize rowMin and rowMax to the first element of the row
			rowMin = array[row][0];
			rowMax = array[row][0];
			rowSum = 0;

			// Iterate through the row to calculate stats
			for (int col = 0; col < array[row].length; col++) {
				long value = array[row][col];
				rowSum += value;

				if (value < rowMin) {
					rowMin = value;
				}
				if (value > rowMax) {
					rowMax = value;
				}
			}

			// Store the calculated statistics in the array
			rowStat[row][0] = rowMin; // Minimum value
			rowStat[row][1] = rowMax; // Maximum value
			rowStat[row][2] = rowSum; // Sum of the row
		}
		return rowStat;
	}

	public long[][] getColStat() {
		/* Returns a 2D array with each column’s minimum, maximum, and sum. */
		long colMin, colMax, colSum;
		long[][] colStat = new long[3][array[0].length];
		// calculate column statistics
		for (int col = 0; col < array[0].length; col++) {
			colMin = array[0][col];
			colMax = array[0][col];
			colSum = 0;

			// Traverse through each row for the current column
			for (int row = 0; row < array.length; row++) {
				long value = array[row][col];
				colSum += value;

				if (value < colMin) {
					colMin = value;
				}
				if (value > colMax) {
					colMax = value;
				}
			}

			// Store the calculated statistics in the array
			colStat[0][col] = colMin; // Minimum value of the column
			colStat[1][col] = colMax; // Maximum value of the column
			colStat[2][col] = colSum; // Sum of the column
		}
		return colStat;
	}

	public Matrix add(Matrix x) {
		/*
		 * Adds this matrix to another matrix x (element-wise), returns the result as a
		 * new matrix.
		 */

		if (array.length != x.array.length || array[0].length != x.array[0].length) {
			JOptionPane.showMessageDialog(null,
					"Error: Matrices must have the same dimensions for addition.",
					"Dimension Mismatch",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		int maxRows = array.length;
		int maxCols = array[0].length;

		/* create result matrix */
		Matrix result = new Matrix(maxRows, maxCols, 0, 0);

		for (int i = 0; i < maxRows; i++) {
			for (int j = 0; j < maxCols; j++) {
				/*
				 * long value1 = (i < array.length && j < array[i].length) ? array[i][j] : 0;
				 * long value2 = (i < x.array.length && j < x.array[i].length) ? x.array[i][j] :
				 * 0;
				 */
				long value1 = array[i][j];
				long value2 = x.array[i][j];
				result.array[i][j] = value1 + value2;
			}
		}
		return result;
	}

	public Matrix subtract(Matrix x) {
		/*
		 * Subtracts another matrix x from this matrix (element-wise), returns the
		 * result as a new matrix.
		 */
		if (array.length != x.array.length || array[0].length != x.array[0].length) {
			JOptionPane.showMessageDialog(null,
					"Error: Matrices must have the same dimensions for subtraction.",
					"Dimension Mismatch",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		int maxRows = array.length;
		int maxCols = array[0].length;

		/* create matrix */
		Matrix result = new Matrix(maxRows, maxCols, 0, 0);

		for (int i = 0; i < maxRows; i++) {
			for (int j = 0; j < maxCols; j++) {
				long value1 = array[i][j];
				long value2 = x.array[i][j];
				result.array[i][j] = value1 - value2;
			}
		}
		return result;
	}

	public Matrix multiply(Matrix x) {
		/*
		 * multiplies this matrix by another matrix x,
		 * returns the result as a new matrix, or null if dimensions are incompatible.
		 */

		// Check if multiplication is possible
		if (array[0].length != x.array.length) {
			JOptionPane.showMessageDialog(null,
					"Error: Matrices have incompatible dimensions.",
					"Dimension Mismatch",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		// Dimensions for the resulting matrix
		int resultRows = array.length;
		int resultCols = x.array[0].length;
		int sharedDim = array[0].length;
		Matrix result = new Matrix(resultRows, resultCols, 0, 0);

		// Do multiplication
		for (int i = 0; i < resultRows; i++) {
			for (int j = 0; j < resultCols; j++) {
				result.array[i][j] = 0;
				for (int k = 0; k < sharedDim; k++) {
					result.array[i][j] += this.array[i][k] * x.array[k][j];
				}
			}
		}
		return result;
	}

}
