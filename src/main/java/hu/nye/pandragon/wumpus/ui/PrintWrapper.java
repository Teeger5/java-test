package hu.nye.pandragon.wumpus.ui;

/**
 * Ez az osztály kiíratásra szolgál.
 * Teszteléskor jöhet jól
 */
public class PrintWrapper {

	public void print(String s) {
		System.out.print(s);
	}

	public void println (String s) {
		System.out.println(s);
	}

	public void printf (String format, Object ...objects) {
		System.out.printf(format, objects);
	}
}
