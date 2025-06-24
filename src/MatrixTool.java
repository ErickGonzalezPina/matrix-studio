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
	private Matrix createMatrix(String prompt) {
		String rows = JOptionPane.showInputDialog("Enter number of rows for " + prompt + " array:");
		String cols = JOptionPane.showInputDialog("Enter number of columns for " + prompt + " array:");
		String lowerBound = JOptionPane.showInputDialog("Enter lower bound for " + prompt + " array:");
		String upperBound = JOptionPane.showInputDialog("Enter upper bound for " + prompt + " array:");

		return new Matrix(Integer.parseInt(rows), Integer.parseInt(cols), Integer.parseInt(lowerBound),
				Integer.parseInt(upperBound));
	}

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
					"Maximum":

			{
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
					originalArray = createMatrix("the first");
				}
				secondArray = createMatrix("the second");

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

		// Check Command issued, take action accordingly
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

				drawGrid(g, x, y, t.length, t[0].length); // Pass the matrix dimensions
				displayArray(g, t, x, y, ww, command);
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
				System.out.println(" ww = " + ww + " colWidth = " + colWidth + " x = " + x);

				// Display the original array
				int currentY = displayArray(g, t, x, y, ww, "Original Array");

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
				// complete - Done
				drawGrid(g, x, y, t.length, t[0].length);
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
				// Display Original Array
				colWidth = digits(originalArray);
				int x = (ww - colWidth * t[0].length) / 2;
				System.out.println(" ww = " + ww + " colWidth = " + colWidth + " x = " + x);
				int currentY = displayArray(g, t, x, y, ww, "Original Array");
				drawGrid(g, x, y, t.length, t[0].length);

				// Display Row Stats
				long[][] rowStats = originalArray.getRowStat();
				if (rowStats != null && rowStats.length > 0) {

					int rowStatsX = x + colWidth * t[0].length + 50;
					int rowStatsTitleX = rowStatsX + colWidth - 40;

					g.setFont(heading);
					g.setColor(Color.RED);
					g.drawString("Row Statistics", rowStatsTitleX, y + 5);

					currentY = displayArray(g, rowStats, rowStatsX, y, ww, "");
					// Draw the grid of row
					drawGrid(g, rowStatsX, y, rowStats.length, 3);
				}

				// Display Column Stats
				long[][] colStats = originalArray.getColStat();
				if (colStats != null && colStats.length > 0) {
					// set teh location of the col matrix
					int colStatsY = y + t.length * 25 + 50;
					int colStatsTitleX = x + colWidth - 40;

					g.setFont(heading);
					g.setColor(Color.RED);
					g.drawString("Column Statistics", colStatsTitleX + colStatsTitleX / 4, colStatsY + 5);

					currentY = displayArray(g, colStats, x, colStatsY, ww, "");
					// Draw the grid of col
					drawGrid(g, x, colStatsY, colStats.length, colStats[0].length);
				}
				break;
			}

			case "Search": {
				int y = 100;
				long[][] t = originalArray.getArray();
				colWidth = digits(originalArray);
				int x = (ww - colWidth * t[0].length) / 2;
				System.out.println(" ww = " + ww + " colWidth = " + colWidth + " x = " + x);
				int currentY = displayArray(g, t, x, y, ww, "Original Array");

				// complete - Perform the search logic
				boolean found = originalArray.search(key);

				// display the result
				g.setFont(heading);
				g.setColor(Color.RED);
				if (found) { // key in the array
					g.drawString(" Search key ---> " + key + " <--- " + "Found",
							ww / 2 - 150, currentY + 20);
				} else {
					g.drawString(" Search key ---> " + key + " <--- " + "NOT Found",
							ww / 2 - 150, currentY + 20);
				}
				// complete - Done
				drawGrid(g, x, y, t.length, t[0].length);
				break;
			}
			case "Add":
			case "Subtract":
			case "Multiply": {
				long[][] a, b, c;
				String operator = "";

				a = originalArray.getArray();
				b = secondArray.getArray();
				c = result.getArray();

				// determine the column width for A
				int colWidthA = digits(originalArray);
				// determine the column width for B
				int colWidthB = digits(secondArray);
				// determine the column width for C
				int colWidthC = digits(result);

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

				// Display matrices A and B
				int y = 100;

				g.setColor(Color.RED);
				int xA = (ww - colWidthA * a[0].length) / 4;
				int titleXA = (xA + (colWidthA * a[0].length - "A".length() * 9) / 2) - 100;
				// draw A
				g.drawString("A", titleXA, y - 5);
				int currentY = displayArray(g, a, xA, y, ww, "");
				drawGrid(g, xA, y, a.length, a[0].length);

				// draw b
				g.setColor(Color.RED);
				int xB = (3 * ww - colWidthB * b[0].length) / 4;
				int titleXB = (xB + (colWidthB * b[0].length - "B".length() * 9) / 2) - 100;
				g.drawString("B", titleXB, y - 5);
				currentY = displayArray(g, b, xB - 50, y, ww, "");
				drawGrid(g, xB - 50, y, b.length, b[0].length);

				// draw operator
				Font originalFont = g.getFont();
				Font largerFont = originalFont.deriveFont(Font.BOLD, 25f);
				g.setFont(largerFont); // Set the larger font

				g.setColor(Color.RED);
				g.drawString("" + operator, titleXB - 400, y + 100);
				g.setFont(originalFont);

				// Display the resulting matrix C
				int xC = (ww - colWidthC * c[0].length) / 2; // C is centered
				currentY = displayArray(g, c, xC, currentY + 50, ww, "C");
				drawGrid(g, xC, currentY - 300, c.length, c[0].length); // Draw grid for Result Matrix C

				g.setFont(heading);
				g.setColor(Color.RED);
			}
		}
	}

	public int displayArray(Graphics g, long[][] a, int x, int y, int ww, String title) {
		g.setFont(heading);
		g.setColor(Color.RED);
		g.drawString(title, ww / 2 - title.length() * 9, y);
		g.setFont(text);
		g.setColor(Color.BLACK);
		y += 30;
		int xs = x;

		// display the array
		for (int row = 0; row < a.length; row++) {
			for (int col = 0; col < a[row].length; col++) {
				if (command.equals("Search") && a[row][col] == key) {
					g.setColor(Color.RED);
					g.drawString("" + a[row][col], x, y);
					g.setColor(Color.BLACK);
				} else
					g.drawString("" + a[row][col], x, y);
				x += colWidth;
			}
			x = xs;
			y += 25;
		}
		return y + 20;
	}

	// Done
	public void drawGrid(Graphics g, int x, int y, int r, int c) {
		// Determine the cell width and height based on the current column width
		int cellWidth = colWidth;
		int cellHeight = 25;
		// Move grid down and left by adjusting the starting x and y values
		int offsetX = 10;
		int offsetY = 10;
		g.setColor(Color.BLACK);

		// Draw horizontal lines
		for (int i = 0; i <= r; i++) {
			int lineY = y + offsetY + i * cellHeight; // Add offsetY to lineY
			g.drawLine(x - offsetX, lineY, x - offsetX + c * cellWidth, lineY);
		}
		// Draw vertical lines
		for (int j = 0; j <= c; j++) {
			int lineX = x + offsetX + j * cellWidth; // Add offsetX to lineX
			g.drawLine(lineX - offsetX - 10, y + offsetY, lineX - offsetX - 10, y + offsetY + r * cellHeight);
		}
	}

	public int digits(Matrix a) {
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
