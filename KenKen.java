import java.util.Scanner;

public class KenKen {
	private KKSquare[][] puzzle;
	private KKSquare[][] groups;

	private int dim;
	private final int pixelSize = 6;

	public KenKen() {
		fillPuzzle();
		setWalls();
	}

	private void fillPuzzle() {
		Scanner sc = new Scanner(System.in);

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

			puzzle[expressionRow][expressionCol].setGroupTotal(groupTotal);
			puzzle[expressionRow][expressionCol].setExpression(groupTotal + op);
		}

		setGroups();
	}

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


	private void printGroups() {

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

		// actually print the temp array
		for (String[] subarr : temp) {
			String line = "";
			for (String c :  subarr) {
				line += c;
			}
			System.out.println(line);
		}

	}

	public static void main(String[] args) {
		System.out.println("This is a test!");

		KenKen kkGame = new KenKen();
		kkGame.printGroups();
	}
}