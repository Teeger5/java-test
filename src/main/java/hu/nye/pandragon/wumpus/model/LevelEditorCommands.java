package hu.nye.pandragon.wumpus.model;

import hu.nye.pandragon.wumpus.service.command.CommandMatcherResult;

import java.util.regex.Pattern;

/**
 * Ez az enum a pályaszerkesztőben elérhető parancsokat tartalmazza
 */
public enum LevelEditorCommands {
	Place ("legyen {ENTITIES} osztlop_betűje sor_száma", "^legyen ({ENTITIES}) [a-z] \\d+$"),
	Remove ("törlés osztlop_betűje sor_száma", "^törlés [a-z] \\d+$"),
	RotateHero ("hős fordul {DIRECTIONS}", "^hős fordul ({DIRECTIONS})$"),
	Test ("teszt", "^teszt$"),
	Exit ("kész | kilépés | vissza", "^(kész|kilépés|vissza)$");

	private final String usage;
	private final String regex;
	private final String base;

	public boolean checkSyntax (String s) {
		return Pattern.matches(regex, s);
	}

	/**
	 * Ellenőrzi, hogy a megadott bemenet erre a parancsra vonatkozik-e
	 * először megnézni, hogy az első szó egyezik-e
	 * Ha igen, akkor a parancs egész regex-ével ellenőrzi
	 * Ha annak is megfelel, akkor ez az a parancs
	 * Az eredményt egy CommandMatcherResult nevű objektumban adja vissza, amiben 3 fajta eredmény lehet:
	 * - ez nem az a parancs
	 * - ez az a parancs, de szintaktikai probléma van (ekkor benne van a parancs használati leírása)
	 * - ez az a parancs, és helyesen van megadva
	 * @param input a bemenet az ellenrzéshez
	 * @return az eredmény egy CommandMatcherResult objektumban
	 */
	public CommandMatcherResult matches (String input) {
		if (!input.startsWith(base)) {
			return CommandMatcherResult.ofNotMatchingCommand();
		}
		if (!checkSyntax(input)) {
			return CommandMatcherResult.ofInproperSyntax("A parancs használata: " + usage);
		}
		return CommandMatcherResult.ofCorrectMatchingCommand();
	}

	LevelEditorCommands(String usage, String regex) {
		this.usage = usage
				.replace("{ENTITIES}", Entities.getAsString().toLowerCase())
				.replace("{DIRECTIONS}", "N|E|S|W");
		this.regex = regex
				.replaceAll("[ ]+", "\\\\s+")
				.replace("{ENTITIES}", Entities.getAsString().toLowerCase())
				.replace("{DIRECTIONS}", "n|e|s|w");
//		this.regex = v;
		if (!usage.contains(" ")) {
			base = usage;
		}
		else {
			base = usage.substring(0, usage.indexOf(' '));
		}
	}

	public static String getMenuText () {
		var stringBuilder = new StringBuilder("Elérhető parancsok\n");
		for (LevelEditorCommands command : values()) {
			stringBuilder.append(String.format(" - %s\n", command.usage));

		}
		return stringBuilder.toString();
	}
}
