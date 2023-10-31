package hu.nye.pandragon.wumpus.model;

import hu.nye.pandragon.wumpus.service.command.CommandMatcherResult;

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

	/**
	 * Ez a mtódus az elérhető parancsok alapján létrehozza
	 * az elérhető parancsok listájának szövegét
	 * @return a parancslista szövege
	 */
	public static String getMenuText () {
		var stringBuilder = new StringBuilder("Elérhető parancsok\n");
		for (GameplayCommands command : values()) {
			stringBuilder.append(String.format(" - %s\n", command.usage));

		}
		return stringBuilder.toString();
	}
}
