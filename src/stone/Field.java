package stone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Field {

	private BufferedReader input = new BufferedReader(new InputStreamReader(
			System.in));
	private final int[][] array;
	private final int rowCount;
	private final int columnCount;
	private long startTime = System.currentTimeMillis();
	private long time = 0;
	Random rnd = new Random();

	public Field(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		array = new int[rowCount][columnCount];
	}

	public Field() {
		this(4, 4);
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getArray(int row, int column) {
		return array[row][column];
	}

	public long getTime(long time) {
		time = ((System.currentTimeMillis() - startTime)) / 1000;
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void newGameStarted() throws WrongFormatException {
		generate();
		sufle();
		do {
			printBoard();
			System.out.println("Enter: 'new' for new game");
			System.out.println("Enter: 'exit' for exit game");
			System.out
					.println("Enter: 'w/up, a/left, s/down, d/right' for move");
			System.out.println("Time is: " + getTime(time) + " second");
			processInput();
		} while (true);
	}

	private void generate() {
		int count = 0;
		for (int i = 0; i < getRowCount(); i++) {
			for (int j = 0; j < getColumnCount(); j++) {
				array[i][j] = count;
				count++;
			}

		}
	}

	private void sufle() throws WrongFormatException {
		int increment = 0;
		do {
			int random = rnd.nextInt(4);
			switch (random) {
			case 0:
				handleInput("w");
				break;
			case 1:
				handleInput("a");
				break;
			case 2:
				handleInput("s");
				break;
			case 3:
				handleInput("d");
				break;
			default:
				break;
			}
			increment++;
		} while (increment < 50);
	}

	private void printBoard() {
		for (int i = 0; i < getRowCount(); i++) {
			for (int j = 0; j < getColumnCount(); j++) {
				if (array[i][j] < 10) {
					System.out.print(array[i][j] + "  ");
				} else {
					System.out.print(array[i][j] + " ");
				}
			}
			System.out.println();
		}
	}

	private void handleInput(String input) throws WrongFormatException {
		Pattern pattern = Pattern
				.compile("(new)?(exit)?([w]{1}|[a]{1}|[s]{1}|[d]{1}|up{1}|left{1}|down{1}|right{1})?");
		Matcher matcher = pattern.matcher(input);

		if (matcher.matches()) {
			if (matcher.group(1) != null && matcher.group(1).equals("new")) {
				System.out.println("New game!");
				newGameStarted();
			} else if (matcher.group(2) != null
					&& matcher.group(2).equals("exit")) {
				System.out.println("Exit game!");
				System.exit(0);
			} else if (matcher.group(3) != null) {
				move(matcher);
			}
		} else {
			System.out.println("Wrong format input");
			processInput();
		}
	}

	private void move(Matcher matcher) {
		for (int i = 0; i < getRowCount(); i++) {
			for (int j = 0; j < getColumnCount(); j++) {
				if (array[i][j] == 0) {
					switch (matcher.group(3)) {
					case "w":
					case "up":
						moveUp(i, j);
						break;
					case "a":
					case "left":
						moveLeft(i, j);
						break;
					case "s":
					case "down":
						moveDown(i, j);
						break;
					case "d":
					case "right":
						moveRight(i, j);
						break;
					}
					return;
				}
			}
		}
	}

	public void moveRight(int i, int j) {
		if (j > 0) {
			array[i][j] = getArray(i, j - 1);
			array[i][j - 1] = 0;
			System.out.println("Move right");
		}
	}

	public void moveDown(int i, int j) {
		if (i > 0) {
			array[i][j] = getArray(i - 1, j);
			array[i - 1][j] = 0;
			System.out.println("Move down");
		}
	}

	public void moveLeft(int i, int j) {
		if (j < getColumnCount() - 1) {
			array[i][j] = getArray(i, j + 1);
			array[i][j + 1] = 0;
			System.out.println("Move left");
		}
	}

	public void moveUp(int i, int j) {
		if (i < getRowCount() - 1) {
			array[i][j] = getArray(i + 1, j);
			array[i + 1][j] = 0;
			System.out.println("Move up");
		}
	}

	private boolean isSolved() {
		boolean state = false;
		for (int i = 0; i < getRowCount(); i++) {
			for (int j = 0; j < getColumnCount(); j++) {
				if (array[i][j] == array[i][j + 1] - 1
						&& array[getRowCount()][getColumnCount()] == 0) {
					state = true;
				} else {
					state = false;
				}
			}
		}
		return state;
	}

	private void processInput() {
		try {
			handleInput(readLine());
		} catch (WrongFormatException e) {
			e.printStackTrace();
		}
	}

	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}
}
