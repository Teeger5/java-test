package hu.nye.pandragon.wumpus.service.command;

import hu.nye.pandragon.wumpus.lovel.Entities;
import hu.nye.pandragon.wumpus.model.Directions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Ez az enum a pályaszerkesztőben elérhető parancsokat tartalmazza
 */
public enum LevelEditorCommands {
	Place ("legyen {ENTITIES} osztlop_betűje sor_száma", "^legyen\\s+({ENTITIES})\\s+[a-z]\\s+\\d+$"),
	Remove ("törlés osztlop_betűje sor_száma", "^törlés\\s+[a-z]\\s+\\d+$"),
	RotateHero ("hős fordul {DIRECTIONS}", "^hős\\s+fordul\\s+({DIRECTIONS})$"),
	Test ("teszt", "^teszt$"),
	Exit ("kész | kilépés", "^(kész|kilépés)$");


	private final String usage;
	private String regex;
	private final String base;

	public boolean checkSyntax (String s) {
		return Pattern.matches(regex, s);
	}

	public Optional<CanProcessResult> matches (String input) {
		System.out.println("input: " + input);
		System.out.println("base: " + base);
		if (!input.startsWith(base)) {
			System.out.println("!input starts with base");
			return Optional.empty();
		}
		if (!checkSyntax(input)) {
			System.out.println("!regex syntax check: " + regex);
			return Optional.of(new CanProcessResult("A parancs használata: " + usage));
		}
		return Optional.of(new CanProcessResult());
	}

	LevelEditorCommands(String usage, String regex) {
		this.usage = usage
				.replace("{ENTITIES}", Entities.getAsString().toLowerCase())
				.replace("{DIRECTIONS}", "N|E|S|W");
		this.regex = regex
//				.replace('$', '-')
				.replace("{ENTITIES}", Entities.getAsString().toLowerCase())
				.replace("{DIRECTIONS}", "n|e|s|w");
//		this.regex = v;
		if (!usage.contains(" ")) {
			base = usage;
		}
		else {
			base = usage.substring(0, usage.indexOf(' '));
		}
		System.out.println("regex: " + this.regex);
	}

	public boolean equals (String s) {
		return this.name().toLowerCase().equals(s);
	}
}
