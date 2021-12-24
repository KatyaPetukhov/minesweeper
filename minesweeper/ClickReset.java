package minesweeper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ClickReset implements EventHandler<ActionEvent> {
	private Game board;

	public ClickReset(Game board) {
		this.board = board;
	}

	@Override
	public void handle(ActionEvent event) {
		board.resize();
		board.redraw();
	}
}
