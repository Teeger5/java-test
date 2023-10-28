package hu.nye.pandragon.wumpus.service.command;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Ez az interface egy felhasználó számára használható
 * parancs metódusait írja le
 */
public interface Command {

	/**
	 * Ez a metódus eldönti egy bemenetről, hogy erre a parancsra vonatkozik-e, vagy sem
	 * 3 állapota lehet: nem az a parancs, szintaktikai probléma, helyes
	 * @param input a parancs
	 * @return ez-e az a parancs, illetve helyesen használják-e
	 */
	CommandMatcherResult match (String input);

	/**
	 * A parancs végrehajtása
	 * @param input a parancs
	 */
	void process (String input);

	/**
	 * Visszaadja a parancs paramétereit, feltételezve, hogy
	 * a parancs maga a bemenet első szava
	 * @param input a bemenet a felhasználótól
	 * @return a parancs argumentumai
	 */
	static String[] getCommandArgs (String input) {
		return getCommandArgs(1, input);
	}

	/**
	 * Ez a metódus a parancs argumentumait adja vissza
	 * Például ha a parancs 'fordul jobbra', és from = 1,
	 * akkor a visszaadott tömb ['jobbra'] lesz
	 * @param from hány szó maga a parancs
	 * @param input felhasználói bemenet, azaz a parancs
	 * @return a parancs argumentumai
	 */
	static String[] getCommandArgs (int from, String input) {
		var list = new ArrayList<>(Arrays.asList(input.split("\\s+")));
		if (from > 0) {
			list.subList(0, from).clear();
		}
		return list.toArray(new String[0]);
	}
}
