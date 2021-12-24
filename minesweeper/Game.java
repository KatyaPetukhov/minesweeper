package minesweeper;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game extends Application {
	public static String getStyle(String value) {
		switch (value) {
		case " ":
			// return "-fx-background-color: LightGrey";
			break;
		case "?":
			// return "-fx-background-color: White";
			break;
		case "1":
			return "-fx-text-fill: Green";
		case "2":
			return "-fx-text-fill: Blue";
		case "3":
			return "-fx-text-fill: Purple";
		case "4":
			return "-fx-text-fill: Black";
		case "5":
			return "-fx-text-fill: Yellow";
		case "6":
			return "-fx-text-fill: Red";
		case "7":
			return "-fx-text-fill: Red";
		case "8":
			return "-fx-text-fill: Red";
		}
		return "";
	}

	private static final int DEFAULT_SIZE = 10;

	private Miner mines;
	private boolean loose;
	private boolean won;
	private int height;
	private int width;
	private int numOfMines;
	private GridPane root;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) {
		height = DEFAULT_SIZE;
		width = DEFAULT_SIZE;
		numOfMines = DEFAULT_SIZE;
		loose = false;
		won = false;
		root = new GridPane();
		mines = new Miner(height, width, numOfMines);
		mines.registerOnOpen(new OpenCell(root, width));
		redraw();
		VBox box = new VBox();
		box.getChildren().addAll(this.root);
		Scene scene = new Scene(box);
		stage.setScene(scene);
		stage.setTitle("Mines Game");
		stage.show();
	}

	public void resize() {
		GridPane left = (GridPane) root.getChildren().get(0);
		try {
			width = Integer.parseInt(((TextField) left.getChildren().get(2)).getText());
		} catch (NumberFormatException ex) {
		}
		try {
			height = Integer.parseInt(((TextField) left.getChildren().get(4)).getText());
		} catch (NumberFormatException ex) {
		}
		try {
			numOfMines = Integer.parseInt(((TextField) left.getChildren().get(6)).getText());
		} catch (NumberFormatException ex) {
		}
		loose = false;
		won = false;
		mines = new Miner(height, width, numOfMines);
		mines.registerOnOpen(new OpenCell(root, width));
	}

	public void redraw() {
		if (mines != null) {
			if (mines.getFailCount() != 0) {
				loose = true;
			}
			if (mines.isComplete()) {
				won = true;
			}
		}
		root.getChildren().clear();
		createLeft();
		createRight();
	}

	private void createRight() {
		GridPane right = new GridPane();
		if (mines == null) {
			return;
		}
		if (mines.getFailCount() != 0) {
			loose = true;
		}
		if (mines.isComplete()) {
			won = true;
		}
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				String value = mines.getCellValue(x, y, won || loose);
				Button b = new Button(value);
				b.setPrefSize(30, 30);
				if (!won && !loose) {
					b.setOnAction(new ClickCell(mines, this, x, y));
				}
				String style = getStyle(value);
				if (!style.equals("")) {
					b.setStyle(style);
				}
				if (won && value.equals("*")) {
					b.setStyle("-fx-background-color: Green");
				}
				if (loose && value.equals("X")) {
					b.setStyle("-fx-background-color: Red");
				}
				right.add(b, x, y);
			}
		}
		right.setPadding(new Insets(10));
		root.add(right, 1, 0);
	}

	private void createLeft() {
		GridPane left = new GridPane();
		Button reset = new Button("New Game");
		reset.setOnAction(new ClickReset(this));
		Label widthLabel = new Label("Width");
		Label heightLabel = new Label("Height");
		Label minesLabel = new Label("# Mines");
		TextField widthText = new TextField();
		widthText.setText("" + width);
		TextField heightText = new TextField();
		heightText.setText("" + height);
		TextField minesText = new TextField();
		minesText.setText("" + numOfMines);
		left.add(reset, 1, 0);
		left.add(widthLabel, 0, 1);
		left.add(widthText, 1, 1);
		left.add(heightLabel, 0, 2);
		left.add(heightText, 1, 2);
		left.add(minesLabel, 0, 3);
		left.add(minesText, 1, 3);
		left.setHgap(30);
		left.setVgap(10);
		left.setPadding(new Insets(10));
		if (won) {
			Label wonLabel = new Label("You won!!!");
			left.add(wonLabel, 1, 4);
		}
		if (loose) {
			Label looseLabel = new Label("You lose :( Try arain.");
			left.add(looseLabel, 1, 4);
		}
		root.add(left, 0, 0);
	}
}
