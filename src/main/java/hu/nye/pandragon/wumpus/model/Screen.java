package hu.nye.pandragon.wumpus.model;

import hu.nye.pandragon.wumpus.ConsoleInputWrapper;
import hu.nye.pandragon.wumpus.ui.LevelPrinter;
import hu.nye.pandragon.wumpus.ui.PrintWrapper;

public abstract class Screen {
	/**
	 * Nem a legszebb megoldás,
	 * de ha jut eszembe jobb, akkor azt fogom használni
	 * Amikor ez == true, akkor a readCommands()-ban a ciklus elején ezt olvasva
	 * meg kell azt szakítani break; használatával
	 * 2023. 10. 31. Mivel még mindig nem találtam saját, praktikusabb megoldást,
	 * ez jó lesz a továbbiakban is
	 */
	protected boolean shouldExit;
	/**
	 * A PrintWrapper felel a kiíratás működtetéséért.
	 * Alapértelmezett a System.out-os megoldást használja,
	 * de be lehet állítani mást is
	 */
	protected PrintWrapper printWrapper;
	protected ConsoleInputWrapper consoleInputWrapper;

	/**
	 * Teszteléskor lehet ennek a hsználatára szükség
	 */
	protected LevelPrinter levelPrinter;

	public Screen () {
		shouldExit = false;
		printWrapper = new PrintWrapper();
		levelPrinter = new LevelPrinter(printWrapper);
		consoleInputWrapper = new ConsoleInputWrapper();
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

	public void setConsoleInputWrapper(ConsoleInputWrapper consoleInputWrapper) {
		this.consoleInputWrapper = consoleInputWrapper;
	}

	/**
	 * Ennek a meghívásakor kell átkerülnie a vezérlésnek erre a képernyőre
	 * Azaz meg meg kell benne hívni a readCommands() metódust
	 */
	public abstract void start ();

	/**
	 * Ebben kell olvasni a parancsokat:
	 *  - egy while (true) {} ciklusnak kell futnia benne
	 */
	protected abstract void readCommands ();
}
