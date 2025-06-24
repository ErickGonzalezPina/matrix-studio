import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class MatrixTool extends Frame implements ActionListener {
	String command = "";
	int key;
	int n;
	int colWidth = 35;
	boolean found;
	Font text = new Font("SansSerif", Font.BOLD, 12);
	Font heading = new Font("SansSerif", Font.BOLD, 16);
	Font f2 = new Font("SanSerif", Font.BOLD, 30);

	Matrix originalArray;
	Matrix secondArray;
	Matrix result;

	public static void main(String[] args) {
		Frame frame = new MatrixTool();
		frame.setResizable(true);
		frame.setSize(1700, 1200);
		frame.setVisible(true);
	}

	public MatrixTool() {
		setTitle("CSC 229 - Project 6 - Matrix");

		// Create Menu

		MenuBar mb = new MenuBar();
		setMenuBar(mb);

		Menu fileMenu = new Menu("File");
		mb.add(fileMenu);

		MenuItem miAbout = new MenuItem("About");
		miAbout.addActionListener(this);
		fileMenu.add(miAbout);

		MenuItem miExit = new MenuItem("Exit");
		miExit.addActionListener(this);
		fileMenu.add(miExit);

		Menu actionMenu = new Menu("Two Dimensional Array");
		mb.add(actionMenu);

		Menu mCreate = new Menu("Create Array");
		actionMenu.add(mCreate);

		MenuItem miRandom = new MenuItem("Randomly");
		miRandom.addActionListener(this);
		mCreate.add(miRandom);

		MenuItem miFile = new MenuItem("From File");
		miFile.addActionListener(this);
		mCreate.add(miFile);

		Menu statsMenu = new Menu("Statistics");
		actionMenu.add(statsMenu);

		MenuItem miMinimum = new MenuItem("Minimum");
		miMinimum.addActionListener(this);
		statsMenu.add(miMinimum);

		MenuItem miMaximum = new MenuItem("Maximum");
		miMaximum.addActionListener(this);
		statsMenu.add(miMaximum);

		MenuItem miAverage = new MenuItem("Average");
		miAverage.addActionListener(this);
		statsMenu.add(miAverage);

		MenuItem miSTD = new MenuItem("Standard Deviation");
		miSTD.addActionListener(this);
		statsMenu.add(miSTD);

		MenuItem miRCstat = new MenuItem("Row & Column Stats");
		miRCstat.addActionListener(this);
		statsMenu.add(miRCstat);

		MenuItem miSearch = new MenuItem("Search");
		miSearch.addActionListener(this);
		actionMenu.add(miSearch);

		// array operations
		Menu opsMenu = new Menu("Operations");

		actionMenu.add(opsMenu);

		MenuItem miAdd = new MenuItem("Add");
		miAdd.addActionListener(this);
		opsMenu.add(miAdd);

		MenuItem miSubtract = new MenuItem("Subtract");
		miSubtract.addActionListener(this);
		opsMenu.add(miSubtract);

		MenuItem miMultiply = new MenuItem("Multiply");
		miMultiply.addActionListener(this);
		opsMenu.add(miMultiply);
		// End program when window is closed

		WindowListener l = new WindowAdapter() {

			// window closed - exit program
			public void windowClosing(WindowEvent ev) {
				System.exit(0);
			}

			// window resized or refocused - redraw
			public void windowStateChanged(WindowEvent ev) {
				repaint();
			}

		};

		ComponentListener k = new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				repaint();
			}
		};

		// register listeners

		this.addWindowListener(l);
		this.addComponentListener(k);

	}

	// ******************************************************************************
	// called by windows manager whenever the application window performs an action
	// (select a menu item, close, resize, ....
	// ******************************************************************************
	public void actionPerformed(ActionEvent ev) {
		// figure out which command was issued

		command = ev.getActionCommand();

		// take action accordingly
		switch (command) {
			case "About": {
				JOptionPane.showMessageDialog(null,
						"This program is a tool for creating, analyzing, and manipulating two-dimensional arrays."
								+ " It provides a graphical interface for users to understant two-dimensional arrays"
								+ "\n" +
								"Functions include:" + "\n" +
								"- Array creation (randomly or from file data)" + "\n" +
								"- Array statistics (minimum, maximum, average, standard deviation, row and column stats)"
								+ "\n" +
								"- Avility to search for a specific valeu" + "\n" +
								"- Array operations (add, subtract, multiply)" + "\n" +
								" " + "\n" +
								"Input: Arrays can be created randomly or loaded from a file." + "\n" +
								"Output: A visual array representation in a grid format with detailed statistics and with the results of operations.",
						"Information About The Program",
						JOptionPane.INFORMATION_MESSAGE);
				repaint();
				break;
			}
			case "Exit": {
				System.exit(0);
				break;
			}
			case "Randomly": {
				originalArray = new Matrix();
				originalArray.createRandomly();
				secondArray = null;
				result = null;
				repaint();
				break;
			}
			case "From File": {
				originalArray = new Matrix();
				originalArray.createFromFile();
				secondArray = null;
				result = null;
				repaint();
				break;
			}
			case "Minimum": {
				repaint();
				break;
			}
			case
					"Maximum": {
				repaint();
				break;
			}
			case "Average": {
				repaint();
				break;
			}
			case "Standard Deviation": {
				repaint();
				break;
			}
			case "Row & Column Stats": {
				repaint();
				break;
			}
			case "Search": {

				String input = JOptionPane.showInputDialog(null, "Please enter an integer Number to Search for:",
						"Search Key", JOptionPane.QUESTION_MESSAGE);
				key = Integer.parseInt(input);
				found = originalArray.search(key);
				repaint();
				break;
			}
			case "Add":
			case "Subtract":
			case "Multiply": {
				if (originalArray == null) {
					originalArray = new Matrix();
					originalArray.createRandomly();
				}
				secondArray = new Matrix();
				secondArray.createRandomly();

				if (command.equals("Add"))
					result = originalArray.add(secondArray);
				if (command.equals("Subtract"))
					result = originalArray.subtract(secondArray);
				if (command.equals("Multiply"))
					result = originalArray.multiply(secondArray);
				repaint();
				break;
			}
		}
	}

	// ********************************************************
	// called by repaint() to redraw the screen
	// ********************************************************
	public void paint(Graphics g) {
		int ww = this.getWidth();
		g.setFont(text);

		switch (command) {
			case "Randomly":
			case "From File": {
				int y = 100;
				long[][] t = originalArray.getArray();
				// calculate #pixels needed to display one column
				colWidth = digits(originalArray);
				// calculate the distance from the left border
				int x = (ww - colWidth * t[0].length) / 2;
				System.out.println(" ww = " + ww + " colWidth = " + colWidth + " x = " + x);

				displayMatrixWithGrid(g, t, x, y, colWidth, 25);
				break;
			}

			case "Minimum":
			case "Maximum":
			case "Average":
			case "Standard Deviation": {
				int y = 100;
				long[][] t = originalArray.getArray();
				colWidth = digits(originalArray);
				int x = (ww - colWidth * t[0].length) / 2;
				int currentY = y + 30 + t.length * 25 + 20;
				displayMatrixWithGrid(g, t, x, y + 30, colWidth, 25);

				g.setFont(heading);
				g.setColor(Color.RED);

				switch (command) {
					case "Minimum": {
						long min = originalArray.getMinimum();
						g.drawString("Minimum = " + min, ww / 2 - 130, currentY);
						break;
					}
					case "Maximum": {
						g.drawString("Maximum = " + originalArray.getMaximum(), ww / 2 - 130, currentY);
						break;
					}
					case "Average": {
						double avg = originalArray.getAverage();
						g.drawString("Average = " + avg, ww / 2 - 130, currentY);
						break;
					}
					case "Standard Deviation": {
						double std = originalArray.getSTD();
						g.drawString("Standard Deviation = " + std, ww / 2 - 130, currentY);
						break;
					}
				}
				break;
			}

			case "Row & Column Stats": {
				int y = 100;
				long[][] t = originalArray.getArray();

				if (t == null || t.length == 0 || t[0].length == 0) {
					g.setColor(Color.RED);
					g.drawString("Error: Original array is empty or not initialized!", 50, 50);
					break;
				}
				colWidth = digits(originalArray);
				int x = (ww - colWidth * t[0].length) / 2;
				displayMatrixWithGrid(g, t, x, y, colWidth, 25);

				// Display Row Stats
				long[][] rowStats = originalArray.getRowStat();
				if (rowStats != null && rowStats.length > 0) {
					int rowStatsX = x + colWidth * t[0].length + 50;
					int rowStatsTitleX = rowStatsX + colWidth - 40;
					g.setFont(heading);
					g.setColor(Color.RED);
					g.drawString("Row Statistics", rowStatsTitleX, y + 5);
					displayMatrixWithGrid(g, rowStats, rowStatsX, y + 30, colWidth, 25);
				}

				// Display Column Stats
				long[][] colStats = originalArray.getColStat();
				if (colStats != null && colStats.length > 0) {
					int colStatsY = y + t.length * 25 + 50;
					int colStatsTitleX = x + colWidth - 40;

					g.setFont(heading);
					g.setColor(Color.RED);
					g.drawString("Column Statistics", colStatsTitleX + colStatsTitleX / 4, colStatsY + 5);
					displayMatrixWithGrid(g, colStats, x, colStatsY + 30, colWidth, 25);
				}
				break;
			}

			case "Search": {
				int y = 100;
				long[][] t = originalArray.getArray();
				colWidth = digits(originalArray);
				int x = (ww - colWidth * t[0].length) / 2;
				displayMatrixWithGrid(g, t, x, y + 30, colWidth, 25);

				// complete - Perform the search logic
				boolean found = originalArray.search(key);

				// display the result
				g.setFont(heading);
				g.setColor(Color.RED);
				int currentY = y + 30 + t.length * 25 + 20;
				if (found) {
					g.drawString(" Search key ---> " + key + " <--- " + "Found", ww / 2 - 150, currentY + 20);
				} else {
					g.drawString(" Search key ---> " + key + " <--- " + "NOT Found", ww / 2 - 150, currentY + 20);
				}
				break;
			}

			case "Add":
			case "Subtract":
			case "Multiply": {
				long[][] a = originalArray.getArray();
				long[][] b = secondArray.getArray();
				long[][] c = result.getArray();

				String operator = "";
				switch (command) {
					case "Add":
						operator = "+";
						break;
					case "Subtract":
						operator = "-";
						break;
					case "Multiply":
						operator = "*";
						break;
				}

				int y = 100;
				int cellHeight = 25;

				// determine the column width for A
				int colWidthA = digits(originalArray);
				// determine the column width for B
				int colWidthB = digits(secondArray);
				// determine the column width for C
				int colWidthC = digits(result);

				int xA = (ww - colWidthA * a[0].length) / 4;
				int xB = (3 * ww - colWidthB * b[0].length) / 4;
				int xC = (ww - colWidthC * c[0].length) / 2;

				// Draw Matrix A
				g.setColor(Color.RED);
				g.setFont(heading);
				g.drawString("A", xA + (colWidthA * a[0].length) / 2 - 10, y - 10);
				g.setFont(text);
				displayMatrixWithGrid(g, a, xA, y, colWidthA, cellHeight);

				// Draw Matrix B
				g.setColor(Color.RED);
				g.setFont(heading);
				g.drawString("B", xB + (colWidthB * b[0].length) / 2 - 10, y - 10);
				g.setFont(text);
				displayMatrixWithGrid(g, b, xB, y, colWidthB, cellHeight);

				// Draw operator between A and B
				g.setFont(heading.deriveFont(Font.BOLD, 30f));
				g.setColor(Color.RED);
				g.drawString(operator, (xA + colWidthA * a[0].length + xB) / 2 - 10, y + (a.length * cellHeight) / 2);
				g.setFont(text);

				// Draw Matrix C (Result)
				int yC = y + Math.max(a.length, b.length) * cellHeight + 60;
				g.setColor(Color.RED);
				g.setFont(heading);
				g.drawString("C", xC + (colWidthC * c[0].length) / 2 - 10, yC - 10);
				g.setFont(text);
				displayMatrixWithGrid(g, c, xC, yC, colWidthC, cellHeight);

				break;
			}
		}
	}

	private void displayMatrixWithGrid(Graphics g, long[][] matrix, int x, int y, int colWidth, int cellHeight) {
		// Draw grid
		g.setColor(Color.BLACK);
		// Draw horizontal lines
		for (int i = 0; i <= matrix.length; i++) {
			int lineY = y + i * cellHeight;
			g.drawLine(x, lineY, x + matrix[0].length * colWidth, lineY);
		}
		// Draw vertical lines
		for (int j = 0; j <= matrix[0].length; j++) {
			int lineX = x + j * colWidth;
			g.drawLine(lineX, y, lineX, y + matrix.length * cellHeight);
		}

		// Draw numbers centered in each cell
		g.setColor(Color.BLACK);
		FontMetrics fm = g.getFontMetrics();
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				String value = String.valueOf(matrix[row][col]);
				int textWidth = fm.stringWidth(value);
				int textHeight = fm.getAscent();
				int cellX = x + col * colWidth;
				int cellY = y + row * cellHeight;
				// Center text in cell
				int textX = cellX + (colWidth - textWidth) / 2;
				int textY = cellY + (cellHeight + textHeight) / 2 - 4;
				g.drawString(value, textX, textY);
			}
		}
	}

	public int digits(Matrix a) {
		/*
		 * The digits function calculates the width (in pixels) needed to display the
		 * largest
		 * number in a matrix. It determines how many digits the largest or smallest
		 * value has, then returns a value
		 * that can be used as the column width for drawing the matrix so that all
		 * numbers fit neatly in their cells. \
		 */
		int s = 1;
		long max = a.getMaximum();
		long min = a.getMinimum();
		if (max < 0 || min < 0) {
			max = Math.max(Math.abs(max), Math.abs(min));
			s = 2;
		}

		while (max > 0) {
			max = max / 10;
			s++;
		}
		System.out.println(" s = " + s);
		return 10 * s + 5;
	}
}
