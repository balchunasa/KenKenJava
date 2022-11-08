import java.util.Scanner;

public class KenKen {
	private KKSquare[][] puzzle;
	private KKSquare[][] groups;

	public KenKen() {
		fillPuzzle();
		setWalls();
	}

	private void fillPuzzle() {
		Scanner sc = new Scanner(System.in);

		int dim = sc.nextInt();

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
		for (int r = 0; r < groups.length; r++) {
			// stores UP DOWN LEFT RIGHT for walls
			String[] walls = new String[4];

			for (int c = 0; c < groups[r].length; c++) {
				KKSquare currSquare = groups[r][c];
				int counter = 0;
				if (c != groups[r].length - 1) { // first squares depend on next, last depends on previous
					KKSquare nextSquare = groups[r][c + 1];

					if (currSquare.getRow() == nextSquare.getRow()) {
						if (currSquare.getRow() != 0) {
							walls[counter++] = "UP";
						}
						if (currSquare.getRow() != puzzle.length -1) {
							walls[counter++] = "DOWN";
						}
					}

					if (currSquare.getCol() == nextSquare.getCol()) {
						if (currSquare.getCol() != 0) {
							walls[counter++] = "LEFT";
						}
						if (currSquare.getCol() != puzzle[0].length - 1) {
							walls[counter++] = "RIGHT";
						}
					}
				}
				else { // last square depends on previous
					KKSquare prevSquare = groups[r][c - 1];

					if (currSquare.getRow() == prevSquare.getRow()) {
						if (currSquare.getRow() != 0) {
							walls[counter++] = "UP";
						}
						if (currSquare.getRow() != puzzle.length -1) {
							walls[counter++] = "DOWN";
						}
					}
					if (currSquare.getCol() == prevSquare.getCol()) {
						if (currSquare.getCol() != 0) {
							walls[counter++] = "LEFT";
						}
						if (currSquare.getCol() != puzzle[0].length - 1) {
							walls[counter++] = "RIGHT";
						}
					}
					
				}

				// first element : has expression
				if (c == 0) {
					groups[r][c].setOutputString(walls, true);
				}
				else {
					groups[r][c].setOutputString(walls, false);
				}
			}
		}
	}


	private void printGroups() {

		for (int i = 0; i < puzzle[0].length; i++) {
			System.out.print(puzzle[0][i]);
		}

	}

	public static void main(String[] args) {
		System.out.println("This is a test!");

		KenKen kkGame = new KenKen();
		kkGame.printGroups();
	}
}