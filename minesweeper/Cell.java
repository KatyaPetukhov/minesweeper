package minesweeper;

public class Cell {
	private boolean visible;
	private boolean mine;
	private int numNeighboursMines;
	private String blownMine;
	private String hiddenMine;
	private String hidden;
	private String noNeighbours;

	public Cell(String blownMine, String hiddenMine, String hidden, String noNeighbours) {
		this.blownMine = blownMine;
		this.hiddenMine = hiddenMine;
		this.hidden = hidden;
		this.noNeighbours = noNeighbours;
		visible = false;
		mine = false;
		numNeighboursMines = 0;
	}

	@Override
	public String toString() {
		if (!visible) {
			return hidden;
		}
		return display();
	}

	public String display() {
		if (mine && visible) {
			return blownMine;
		}
		if (mine) {
			return hiddenMine;
		}
		if (numNeighboursMines == 0) {
			return noNeighbours;
		}
		return String.format("%d", numNeighboursMines);
	}

	public void setVisible() {
		visible = true;
	}

	public boolean isVisible() {
		return visible;
	}

	public void addNeighbourMine() {
		numNeighboursMines++;
	}

	public boolean hasNeighbourMines() {
		return numNeighboursMines != 0;
	}

	public boolean isMine() {
		return mine;
	}

	public void setMine() {
		mine = true;
	}

}
