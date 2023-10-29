package hu.nye.pandragon.wumpus;

import hu.nye.pandragon.wumpus.ui.GameMainScreen;
import hu.nye.pandragon.wumpus.ui.PrintWrapper;

public class Main {

	public static void main(String[] args) {
		new GameMainScreen(new PrintWrapper(), new ConsoleInputWrapper());
	}
}