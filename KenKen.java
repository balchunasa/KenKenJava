import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class KenKen {
	private KKSquare[][] puzzle;
	private KKSquare[][] groups;

	private int dim;
	private final int pixelSize = 6;

	public KenKen() {
		fillPuzzle();
		setWalls();
	}

	public KKSquare[][] getPuzzle() { return puzzle; }

	private void fillPuzzle() {

		try {
		File f = new File("input");
		Scanner sc = new Scanner(f);

		dim = sc.nextInt();

		// create dim x dim puzzle of squares
		puzzle = new KKSquare[dim][dim];

		// initialize the rows/cols of the squares
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[0].length; j++) {
				puzzle[i][j] = new KKSquare(i, j);
			}
		}

		int nGroups = sc.nextInt();

		// create placeholder for ragged array of groups
		groups = new KKSquare[nGroups][];

		for (int i = 0; i < nGroups; i++) {
			int expressionRow = sc.nextInt();
			int expressionCol = sc.nextInt();

			puzzle[expressionRow][expressionCol].setGroup(i);

			// parse the row / column information
			int counter = 1;

			while (sc.hasNextInt()) {
				int r = sc.nextInt();
				int c = sc.nextInt();

				puzzle[r][c].setGroup(i);
				counter++;
			}

			groups[i] = new KKSquare[counter];

			// set the expression for the first element
			String op = sc.next();
			int groupTotal = sc.nextInt();
			puzzle[expressionRow][expressionCol].setExpression(groupTotal + op);
		}

		setGroups();
		sc.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Something went wrong.");
		}
	}

	private int getDim() { return dim; }

	private void setGroups() {
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle.length; j++) {
				int currGroup = puzzle[i][j].getGroup();
				push(groups[currGroup], puzzle[i][j]);
			}
		}
	}

	private void push(KKSquare[] arr, KKSquare item) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == null) {
				arr[i] = item;
				return;
			}
		}
		throw new Error("Something went wrong. Somehow group array is full.");
	}

	private void setWalls() {

		for (int r = 0; r < puzzle.length; r++) {
			for (int c = 0; c < puzzle.length; c++) {
				int counter = 0;
				String[] walls = new String[4];
					
				if (r != 0 && puzzle[r][c].getGroup() != puzzle[r-1][c].getGroup()) walls[counter++] = "UP";
				if (r != puzzle.length - 1 && puzzle[r][c].getGroup() != puzzle[r+1][c].getGroup()) walls[counter++] = "DOWN";
				if (c != 0 && puzzle[r][c].getGroup() != puzzle[r][c-1].getGroup()) walls[counter++] = "LEFT";
				if (c != puzzle[0].length - 1 && puzzle[r][c].getGroup() != puzzle[r][c+1].getGroup()) walls[counter++] = "RIGHT";
				
				puzzle[r][c].setOutputString(walls);
			}
		}

	}


	public String toString() {

		// set up a scaled grid
		String[][] temp = new String[pixelSize * dim][pixelSize * dim];

		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[0].length; j++) {
				KKSquare currentSquare = puzzle[i][j];
				String[][] currentSquareText = currentSquare.getPrintableVal();

				for (int row = 0; row < currentSquareText.length; row++) {
					for (int col = 0; col < currentSquareText[0].length; col++) {
						temp[i*pixelSize + row][j*pixelSize + col] = currentSquareText[row][col];
					}
				}
			}
		}

		String line = "";
		// actually print the temp array
		for (String[] subarr : temp) {
			for (String c :  subarr) {
				line += c;
			}
			line += "\n";
		}

		return line;
	}

	public boolean isSolved() {

		for (KKSquare[] group : groups) {
			int[] groupNums = new int[group.length];
			for (int i = 0; i < group.length; i++) {
				groupNums[i] = group[i].getValue();
			}

			if (!expressionSatisfied(group[0].getExpression(), groupNums)) return false;
		}

		return true;
	}

	private boolean expressionSatisfied(String expression, int[] groupNums) {
		String operator = expression.substring(expression.length() - 1);

		int expressionValue = (new Integer(expression.substring(0, expression.length() - 1))).intValue();
		int groupValue = 0;

		if (operator.equals("+")) {
			System.out.println("Here");
			for (int i : groupNums) { groupValue += i; }
			return groupValue == expressionValue;
		}
		if (operator.equals("-")) {
			// only two values for subtraction:
			return expressionValue == groupNums[0] - groupNums[1] || expressionValue == groupNums[1] - groupNums[2];
		}
		if (operator.equals("*")) {
			for (int i : groupNums) { groupValue *= i; }
			return groupValue == expressionValue;
		}
		if (operator.equals("/")) {
			// only two values for division:
			return expressionValue == groupNums[0] / groupNums[1] || expressionValue == groupNums[1] / groupNums[2];
		}

		return false;
	}

	private void setValue(int row, int col, int val) {
		puzzle[row - 1][col - 1].setValue(val);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		KenKen kkGame = new KenKen();

		int row = 1, col = 1, val = 0;
		int dim = kkGame.getDim();

		while (!kkGame.isSolved()) {
			System.out.print(kkGame);
			do {
				System.out.print("Give row col num: ");
				row = sc.nextInt();
				col = sc.nextInt();
				val = sc.nextInt();
			} while (row > dim || col > dim || row <= 0 || col <= 0);

			kkGame.setValue(row, col, val);
		}

	}
}