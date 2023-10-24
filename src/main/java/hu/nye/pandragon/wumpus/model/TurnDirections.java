package hu.nye.pandragon.wumpus.model;

public enum TurnDirections {
	Left, Right;
	public static TurnDirections parse (String s) {
		s = s.toLowerCase();
		for (TurnDirections d : TurnDirections.values()) {
			if (s.equals(d.name().toLowerCase())) {
				return d;
			}
		}
		return null;
	}
}
