package minesweeper;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class OpenCell implements Miner.OnOpen {
	private GridPane root;
	private int width;

	public OpenCell(GridPane root, int width) {
		this.root = root;
		this.width = width;
	}

	@Override
	public void f(int x, int y, String value) {
		GridPane rightPane = (GridPane) root.getChildren().get(1);
		int index = y * width + x;
		Button b = (Button) rightPane.getChildren().get(index);
		b.setText(value);
		String style = Game.getStyle(value);
		if (!style.equals("")) {
			b.setStyle(style);
		}
	}
}
