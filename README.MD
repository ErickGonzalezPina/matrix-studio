# Matrix Studio

**Matrix Studio** is a Java GUI application for creating, visualizing, and performing operations on two-dimensional matrices. It provides an interactive environment to explore matrix statistics, search, and arithmetic operations, all with a user-friendly graphical interface.

---

## Features

### 🟩 Matrix Creation

- **Random Generation:** Create a matrix of any size (up to 50x50) with random values within a user-specified range.
- **File Import:** Load a matrix from a text file, supporting flexible data input.

### 🟦 Visualization

- **Grid Display:** Matrices are displayed in a clear, grid-based format.
- **Dynamic Sizing:** The grid and cell sizes adjust based on matrix dimensions and values.
- **Highlighting:** Search results are visually highlighted within the matrix.

### 🟨 Matrix Statistics

- **Minimum & Maximum:** Instantly find the smallest and largest values in the matrix.
- **Average:** Calculate the mean of all matrix elements.
- **Standard Deviation:** Measure the spread of matrix values.
- **Row & Column Stats:** View per-row and per-column minimum, maximum, and sum.

### 🟧 Matrix Operations

- **Addition:** Add two matrices of the same size.
- **Subtraction:** Subtract one matrix from another (same size required).
- **Multiplication:** Multiply compatible matrices (columns of A = rows of B).
- **Result Visualization:** All operation results are displayed as new matrices.

### 🟥 Search Functionality

- **Value Search:** Search for a specific value in the matrix; all matches are highlighted.

### 🟪 User Interface

- **Menu-Driven:** All features are accessible via intuitive menus.
- **Dialog-Based Input:** User-friendly dialogs for entering matrix dimensions, value ranges, and search keys.
- **About Section:** Learn about the application's capabilities and usage.

---

## How to Run

1. **Requirements:**

   - Java Development Kit (JDK) 8 or higher

2. **Compile:**  
   Open a terminal in the `src` directory and run:
   ```sh
   javac MatrixTool.java Matrix.java
   ```
