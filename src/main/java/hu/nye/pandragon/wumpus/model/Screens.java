package hu.nye.pandragon.wumpus.model;

import lombok.Getter;

@Getter
public enum Screens {
	LevelEditor (1, "Pályaszerkesztő indítása"),
	Gameplay (2, "Játék indítása"),
	LoadFromDB (3, "Játék folytatása"),
	Highscores(4, "Toplista"),
	Exit (5, "Kilépés"),
	Unknown (-1, "");

	private final int id;
	private final String name;

	Screens(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public static Screens parseID (String id) {
		int parsedId;
		try {
			parsedId = Integer.parseInt(id);
		}
		catch (NumberFormatException e) {
			return Unknown;
		}
		for (Screens s : values()) {
			if (s.getId() == parsedId) {
				return s;
			}
		}
		return Unknown;
	}

	public static String getMenuText () {
		var stringBuilder = new StringBuilder("Menü\n");
		for (Screens screen : values()) {
			if (screen != Unknown) {
				stringBuilder.append(String.format("%2d - %s\n", screen.getId(), screen.getName()));
			}
		}
		return stringBuilder.toString();
	}
}
