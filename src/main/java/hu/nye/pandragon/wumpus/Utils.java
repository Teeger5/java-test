package hu.nye.pandragon.wumpus;

import java.util.Scanner;

public class Utils {

	/**
	 * Bemenet olvasása a felhasználótól
	 * @return a beírt szöveg
	 */
	public static String readFromConsole () {
		var scanner = new Scanner(System.in);
		var line = scanner.nextLine();
		return line;
	}

	/**
	 * Bemenet olvasása a felhasználótól
	 * Kiír a sor elejére egy > jelet is
	 * @return a beolvasott szöveg
	 */
	public static String requestUserInput () {
		System.out.print("> ");
		return readFromConsole().trim();
	}
}
