package hu.nye.pandragon.wumpus.ui;

public enum Screens {
	Gameplay (1, "Játék indítása"),
	LoadFromDB (2, "Betöltés az adatbázisból"),
	LevelEditor (3, "Pályaszerkesztő indítása"),
	Unknown (-1, "");

	private int id;
	private String name;
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

	public static Screens parseID (int id) {
		for (Screens s : Screens.values()) {
			if (s.getId() == id) {
				return s;
			}
		}
		return Unknown;
	}

	public static String getMenuText () {
		var stringBuilder = new StringBuilder("Menü\n");
		for (Screens screen : values()) {
			stringBuilder.append(String.format("%2d - %s\n", screen.getId(), screen.getName()));
		}
		return stringBuilder.toString();
	}
}
