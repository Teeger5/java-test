package hu.nye.pandragon.wumpus.service.command;

import hu.nye.pandragon.wumpus.service.command.impl.DefaultCommand;
import hu.nye.pandragon.wumpus.ui.PrintWrapper;

import java.util.List;

/**
 * Ez az osztály kezeli a felhasználótól származó bemenetet,
 * megpróbál kiválasztani és futtatni egy annak megfelelő parancsot
 */
public class InputHandler {

	//    private final EnumMap<GameplayCommands, Command> commandList;
	private final List<Command> commandList;

	/**
	 * Egy PrintWrapper objektum, ami hasznos lehet teszteléskor
	 */
	private PrintWrapper printWrapper;

	/**
	 * Itt a parancsokat előre listába kell szedni egy olyan helyen, ahol elérhető a Level objektum,
	 * mivel az kell hozzájuk, és itt nem lesz elérhető
	 * @param commandList a parancsok listája, amit ez az InputHandler kezel
	 */
	public InputHandler(List<Command> commandList) {
		this.commandList = commandList;
		printWrapper = new PrintWrapper();
	}

	public void setPrintWrapper(PrintWrapper printWrapper) {
		this.printWrapper = printWrapper;
	}

	/**
	 * A megadott {@link Command} objektumok listájából az első,
	 * a bemenetre alkalmazható parancs lesz végrehajtva.
	 * A bemenetet a metódus kisbetűssé alakítja, erre külön nincs szükség
	 * @param input bemenet a felhasználótól.
	 */
	public void handleInput(String input) {
		input = input.trim().toLowerCase();
		for (Command command : commandList) {
			var result = command.match(input);
			if (result.isCommandMatches()) {
				var message = result.getMessage();
				if (message == null) {
					command.process(input);
				}
				else {
					printWrapper.println(message);
				}
				return;
			}
		}
		new DefaultCommand().process(input);
	}
}
