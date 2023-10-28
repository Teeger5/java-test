package hu.nye.pandragon.wumpus.model;

import hu.nye.pandragon.wumpus.ui.LevelPrinter;
import hu.nye.pandragon.wumpus.ui.PrintWrapper;

public abstract class Screen {
	/**
	 * Nem a legszebb megoldás,
	 * de ha jut eszembe jobb, akkor azt fogom használni
	 * Amikor ez == true, akkor a readCommands()-ban a ciklus elején ezt olvasva
	 * meg kell azt szakítani break; használatával
	 */
	protected boolean shouldExit;
	protected PrintWrapper printWrapper;

	protected LevelPrinter levelPrinter;

	public Screen () {
		shouldExit = false;
		printWrapper = new PrintWrapper();
		levelPrinter = new LevelPrinter();
	}

	public void setShouldExit(boolean shouldExit) {
		this.shouldExit = shouldExit;
	}

	public void setPrintWrapper(PrintWrapper printWrapper) {
		this.printWrapper = printWrapper;
	}

	public void setLevelPrinter(LevelPrinter levelPrinter) {
		this.levelPrinter = levelPrinter;
	}

	public abstract void start ();

	/**
	 * Ebben kell olvasni a parancsokat:
	 *  - egy while (true) {} ciklusnak kell futnia benne
	 */
	protected abstract void readCommands ();
}
