package minesweeper;

import java.util.Scanner;

public class TextGame {
	private static boolean turn(Miner game, int x, int y) {
		try {
			if (!game.open(x, y)) {
				System.out.println("You have chosen already opened cell. Exiting.");
				return false;
			}
		} catch (Miner.Boom e) {
			printBoard(game, true);
			System.out.println("Boom!");
			return false;
		}
		printBoard(game, false);
		if (game.isComplete()) {
			System.out.println("Congratulations!");
			return false;
		}
		return true;
	}

	private static void printBoard(Miner game, boolean showHidden) {
		boolean isFirstLeft = true;
		printBorder(game.getWidth());
		for (int i = 0; i < game.getHeight(); i++) {
			for (int j = 0; j < game.getWidth(); j++) {
				if (isFirstLeft) {
					System.out.print("|");
					isFirstLeft = false;
				}
				System.out.print(game.getCellValue(i, j, showHidden));
			}
			System.out.print("|");
			isFirstLeft = true;
			System.out.println();
		}
		printBorder(game.getWidth());
	}

	private static void printBorder(int width) {
		System.out.print("*");
		for (int i = 0; i < width; i++) {
			System.out.print("-");
		}
		System.out.print("*");
		System.out.println();
	}

	public static void main(String[] args) {
		int numberOfMines = 0;
		int boardSize = 10;
		System.out.println("Please enter the board size and number of mines: ");
		Scanner in = new Scanner(System.in);
		boardSize = in.nextInt();
		numberOfMines = in.nextInt();
		Miner game = new Miner(boardSize, boardSize, numberOfMines);
		while (true) {
			int x = 0;
			int y = 0;
			System.out.println("Please enter your x and y for the next turn: ");
			x = in.nextInt();
			y = in.nextInt();
			if (!turn(game, x, y)) {
				in.close();
				return;
			}
		}
	}
}
