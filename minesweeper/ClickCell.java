package minesweeper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import minesweeper.Miner.Boom;

public class ClickCell implements EventHandler<ActionEvent> {
	private Miner mines;
	private Game board;
	private int x;
	private int y;

	public ClickCell(Miner mines, Game board, int x, int y) {
		this.mines = mines;
		this.board = board;
		this.x = x;
		this.y = y;
	}

	@Override
	public void handle(ActionEvent event) {
		try {
			mines.open(x, y);
		} catch (Boom e) {
			board.redraw();
		}
	}
}
