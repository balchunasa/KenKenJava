public class KKSquare {
	private int value;
	private int group;
	
	private String expression;


	private Cell cell;
	private String[][]	printableVal = {{"==","==","==","==","==","=="},
										{"||","  ","  ","  ","  ","||"},
										{"||","  ","  ","  ","  ","||"},
										{"||","  ","  ","  ","  ","||"},
										{"||","  ","  ","  ","  ","||"},
										{"==","==","==","==","==","=="},
										};

	private class Cell {
		int row;
		int col;

		public Cell(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}

	public KKSquare(int i, int j) {
		this.cell = new Cell(i, j);
	}

	public int getValue() { return value; }
	public void setValue(int value) { this.value = value; 
		if (value != 0) {
		printableVal[3][2] = value + " ";
		}
		else {
			printableVal[3][2] =  "  ";
		}
	}
	
	public void removeValue() { this.value = 0; }

	public int getGroup() { return group; }
	public void setGroup(int group) { this.group = group; }

	public void setExpression(String expression) { this.expression = expression; }
	public String getExpression() { return expression; }

	public int getRow() { return cell.row; }
	public int getCol() { return cell.col; }

	public String[][] getPrintableVal() { return printableVal; }

	public void setOutputString(String[] arr) {
		// 4 x 4 output with walls on "UP" "LEFT" "RIGHT" "DOWN"
		// depending on arr values

		for (String wall : arr) {
			if (wall != null) {
				fill(wall);
			}
		}

		// if the cell has an expression
		if (expression != null) {
			for (int i = 0; i < expression.length(); i++) {
				printableVal[1][i+1] = expression.substring(i, i + 1);
			}

			// pad the spacing
			for (int i = 0; i < expression.length(); i++) {
				printableVal[1][4] += " ";
			}
		}
	}

	private void fill(String wall) {
		if (wall.equals("UP")) {
			for (int i = 0; i < printableVal[0].length; i++) {
				printableVal[0][i] = "++";
			}
		}
		if (wall.equals("DOWN")) {
			for (int i = 0; i < printableVal[0].length; i++) {
				printableVal[5][i] = "++";
			}
		}
		if (wall.equals("LEFT")) {
			for (int i = 0; i < printableVal.length; i++) {
				printableVal[i][0] = "++";
			}
		}
		if (wall.equals("RIGHT")) {
			for (int i = 0; i < printableVal.length; i++) {
				printableVal[i][5] = "++";
			}
		}
	}

	public String toStringGroup() {
		return "<group: " + group + " expression: " + expression + ", cell: (" + cell.row + ", " + cell.col + ")>";
	}

	/*
	 Something like:
	 ++++++++++++
	   11/     ++
	           ++
	           ++
	           ++
	 ++++++++++++
	*/
	public String toString() {
		String result = "";

		for (String[] row : printableVal) {
			for (String col : row) {
				result += col;
			}
			result += "\n";
		}

		return result;
	}

	public static void main(String[] args) {
		KKSquare test = new KKSquare(0,0);
		test.setExpression("11/");

		String[] walls = {"UP", "LEFT"};
		test.setOutputString(walls);

		System.out.print(test);
	}
}