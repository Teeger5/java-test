package hu.nye.pandragon.wumpus.model;

public enum Screens {
	LevelEditor (1, "Pályaszerkesztő indítása"),
	LoadFromDB (2, "Betöltés az adatbázisból"),
	Gameplay (3, "Játék indítása"),
	Exit (4, "Kilépés"),
	Unknown (-1, "");

	private final int id;
	private final String name;
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

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
		for (Screens s : Screens.values()) {
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
