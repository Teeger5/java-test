package hu.nye.pandragon.wumpus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Utils {

	public static String readFromConsole () {
/*		try (var in = new InputStreamReader(System.in); var reader = new BufferedReader(in)) {
			return reader.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}*/
		var scanner = new Scanner(System.in);
		var line = scanner.nextLine();
//		scanner.close();
		return line;
	}

}
