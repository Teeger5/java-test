package hu.nye.pandragon.wumpus.ui;

public abstract class Screen {
	/**
	 * Nem a legszebb megoldás,
	 * de ha jut eszembe jobb, akkor azt fogom használni
	 * Amikor ez == true, akkor a readCommands()-ban a ciklus elején ezt olvasva
	 * meg kell azt szakítani break; használatával
	 */
	protected boolean shouldExit;

	public void setShouldExit(boolean shouldExit) {
		this.shouldExit = shouldExit;
	}

	/**
	 * Ebben kell olvasni a parancsokat:
	 *  - egy while (true) {} ciklusnak kell futnia benne
	 */
	protected abstract void readCommands ();
}
