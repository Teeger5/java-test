package hu.nye.pandragon.wumpus.model;

import hu.nye.pandragon.wumpus.service.game.Level;
import hu.nye.pandragon.wumpus.service.command.Command;
import hu.nye.pandragon.wumpus.service.command.CommandMatcherResult;
import hu.nye.pandragon.wumpus.service.command.impl.gameplay.HeroMoveCommand;
import hu.nye.pandragon.wumpus.service.command.impl.gameplay.HeroTurnCommand;

import java.util.EnumMap;
import java.util.regex.Pattern;

/**
 * Ez az enum a hős számára a játékban elérhető parancsokat tartalmazza
 */
public enum GameplayCommands {
	Turn ("fordul jobbra|balra", "^fordul (jobbra|balra)$"),
	PickUpGold ("aranyat felszed", "^aranyat felszed$"),
	Shoot ("lő", "^lő$"),
	MoveForward ("lép", "^lép$"),
	GiveUp ("felad", "^felad$");

	private final String usage;
	private final String regex;
	private final String base;

	/**
	 * Ez a metódus eldönti egy bemenetről, hogy erre a parancsra vonatkozik-e, vagy sem
	 * Ha üres Optional-t ad vissza, akkor ez nem az a parancs,
	 * ha nem üres, akkor a benne lévő matchResult dönti el, hogy végre lehet-e hajtani.
	 * Ha abban van üzenet, akkor valamilyen hiba van a parancsban (pl. hiányzó argumentum),
	 * ha nincs, akkor formailag nincs probléma a paranccsal
	 * @param input a parancs
	 * @return ez-e az a parancs, illetve helyesen használják-e
	 */
	public CommandMatcherResult matches (String input) {
		if (!input.startsWith(base)) {
			return CommandMatcherResult.ofNotMatchingCommand();
		}
		if (!Pattern.matches(regex, input)) {
			return CommandMatcherResult.ofInproperSyntax("A parancs használata: " + usage);
		}
		return CommandMatcherResult.ofCorrectMatchingCommand();
	}

	public Command getCommand (Level level) {
		switch (this) {
			case Turn -> new HeroTurnCommand(level);
			case MoveForward -> new HeroMoveCommand(level);
		}
		return null;
	}

	GameplayCommands(String usage, String regex) {
		this.usage = usage;
		this.regex = regex
				.replaceAll("[ ]+", "\\\\s+");
		if (!usage.contains(" ")) {
			base = usage;
		}
		else {
			base = usage.substring(0, usage.indexOf(' '));
		}
	}

	public static EnumMap<GameplayCommands, Command> toCommandMap (Level level) {
		var commands = new EnumMap<GameplayCommands, Command>(GameplayCommands.class);
		for (GameplayCommands command : GameplayCommands.values()) {
			commands.put(command, command.getCommand(level));
		}
		return commands;
	}
}
