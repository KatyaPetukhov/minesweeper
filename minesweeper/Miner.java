package minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Miner {
	public interface OnOpen {
		/**
		 * Called each time cell is opened.
		 * 
		 * @param x
		 * @param y
		 * @param value to display on a cell.
		 */
		public void f(int x, int y, String value);
	}

	public class Boom extends Exception {
		/**
		 * Raised when opened cell contains a Mine.
		 */
		private static final long serialVersionUID = 1L;
	}

	private int height;
	private int width;
	private int numMines;
	private int numVisible;
	private int failCount;
	private List<OnOpen> onOpens;
	private List<List<Cell>> board;

	public Miner(int height, int width, int numMines) {
		this(height, width, numMines, "X", "*", "?", " ");
	}

	public Miner(int height, int width, int numMines, String blownMine, String hiddenMine, String hidden,
			String noNeighbours) {
		this.onOpens = new ArrayList<OnOpen>();
		this.height = height;
		this.width = width;
		this.numMines = 0;
		this.numVisible = 0;
		this.failCount = 0;
		board = new ArrayList<List<Cell>>();
		for (int i = 0; i < height; i++) {
			board.add(new ArrayList<Cell>());
			for (int j = 0; j < width; j++) {
				board.get(i).add(new Cell(blownMine, hiddenMine, hidden, noNeighbours));
			}
		}
		addMines(numMines);
	}

	public void registerOnOpen(OnOpen onOpen) {
		this.onOpens.add(onOpen);
	}

	public int getFailCount() {
		// We may allow more than 1 failure, so it's better to return count.
		return failCount;
	}

	public boolean open(int x, int y) throws Boom {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return false;
		}
		Cell c = board.get(y).get(x);
		if (c.isVisible()) {
			return false;
		}
		c.setVisible();
		if (!onOpens.isEmpty()) {
			for (OnOpen o : onOpens) {
				o.f(x, y, c.toString());
			}
		}
		if (c.isMine()) {
			failCount++;
			throw new Boom();
		}
		numVisible++;
		if (c.hasNeighbourMines()) {
			return true;
		}
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (i < 0 || i >= width || j < 0 || j >= height) {
					continue;
				}
				if (x == i && y == j) {
					continue;
				}
				open(i, j);
			}
		}
		return true;
	}

	public boolean isComplete() {
		return numMines + numVisible == height * width;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public String getCellValue(int x, int y, boolean showHidden) {
		if (x < 0 || x >= width) {
			throw new IllegalArgumentException("X is incorrect.");
		}
		if (y < 0 || y >= height) {
			throw new IllegalArgumentException("Y is incorrect.");
		}
		Cell c = board.get(y).get(x);
		if (showHidden) {
			return c.display();
		}
		return c.toString();
	}

	private void addMines(int numMines) {
		int nonMines = height * width - this.numMines;
		if (nonMines - numMines <= 0) {
			throw new IllegalArgumentException("Number of mines is too big.");
		}
		Random rand = new Random();
		for (; numMines > 0; numMines--, nonMines--) {
			int newMineIndex = rand.nextInt(nonMines);
			for (int i = 0; i < height && newMineIndex >= 0; i++) {
				for (int j = 0; j < width && newMineIndex >= 0; j++) {
					if (board.get(i).get(j).isMine()) {
						continue;
					}
					if (newMineIndex == 0) {
						addNewMine(i, j);
					}
					newMineIndex--;
				}
			}
		}
	}

	private boolean addNewMine(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return false;
		}
		Cell c = board.get(y).get(x);
		if (c.isMine()) {
			return false;
		}
		c.setMine();
		numMines++;
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (i < 0 || i >= width || j < 0 || j >= height) {
					continue;
				}
				if (x == i && y == j) {
					continue;
				}
				board.get(j).get(i).addNeighbourMine();
			}
		}
		return true;
	}
}
