package hu.nye.pandragon.wumpus.service.command;

import hu.nye.pandragon.wumpus.lovel.Entities;
import hu.nye.pandragon.wumpus.model.Directions;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Ez az enum a pályaszerkesztőben elérhető parancsokat tartalmazza
 */
public enum LevelEditorCommands {
	Place ("legyen {ENTITIES} sor_száma osztlop_betűje", "^legyen\\s+({ENTITIES})\\s+\\d+\\s+[a-z]$"),
	Remove ("törlés sor_száma osztlop_betűje", "^törlés\\s+\\d+\\s+\\d+\\s+[a-z]$"),
	RotateHero ("hős fordul {DIRECTIONS}", "^hős\\s+fordul\\s+({DIRECTIONS})$");

	private final String usage;
	private final String regex;
	private final String base;

	public String getUsage() {
		return usage;
	}

	public String getRegex() {
		return regex;
	}

	public String getBase() {
		return base;
	}

	public boolean checkSyntax (String s) {
		return Pattern.matches(regex, s);
	}

	public Optional<CanProcessResult> matches (String input) {
		if (!input.startsWith(base)) {
			return Optional.empty();
		}
		if (!checkSyntax(input)) {
			return Optional.of(new CanProcessResult("A parancs használata: " + usage));
		}
		return Optional.of(new CanProcessResult());
	}

	LevelEditorCommands(String usage, String regex) {
		this.usage = usage
				.replace("{ENTITIES}", Entities.getAsString().toLowerCase())
				.replace("{DIRECTIONS}", "N|E|S|W");
		this.regex = regex
				.replace("{ENTITIES}", Entities.getAsString().toLowerCase())
				.replace("{DIRECTIONS}", "n|e|s|w");
		if (!usage.contains(" ")) {
			base = usage;
		}
		else {
			base = usage.substring(0, usage.indexOf(' '));
		}
	}

	public boolean equals (String s) {
		return this.name().toLowerCase().equals(s);
	}
}
