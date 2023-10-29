package hu.nye.pandragon.wumpus;

import java.util.Scanner;

public class ConsoleInputWrapper {

	/**
	 * Bemenet olvasása a felhasználótól
	 * @return a beírt szöveg
	 */
	public String readFromConsole () {
		var scanner = new Scanner(System.in);
		var line = scanner.nextLine();
		return line;
	}

	/**
	 * Bemenet olvasása a felhasználótól
	 * Kiír a sor elejére egy > jelet is
	 * @return a beolvasott szöveg
	 */
	public String requestUserInput () {
		System.out.print("> ");
		return readFromConsole().trim();
	}
}
