package hu.nye.pandragon.wumpus;

import java.util.Scanner;

public class Utils {

	public static String readFromConsole () {
		var scanner = new Scanner(System.in);
		var line = scanner.nextLine();
		return line;
	}

}
