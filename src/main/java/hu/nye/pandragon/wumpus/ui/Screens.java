package hu.nye.pandragon.wumpus.ui;

public enum Screens {
	Gameplay (1),
	LoadFromDB (2),
	LevelEditor (3),
			Unknown(-1);

	private int id;
	public int getId() {
		return id;
	}

	Screens(int id) {
		this.id = id;
	}

	public static Screens parseID (int id) {
		for (Screens s : Screens.values()) {
			if (s.getId() == id) {
				return s;
			}
		}
		return Unknown;
	}
}
