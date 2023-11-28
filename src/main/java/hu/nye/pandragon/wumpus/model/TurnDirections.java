package hu.nye.pandragon.wumpus.model;

import lombok.Getter;

@Getter
public enum TurnDirections {
	Left ("balra"), Right ("jobbra");
	private final String name;

	TurnDirections(String name) {
		this.name = name;
	}

	public static TurnDirections parse (String s) {
		s = s.toLowerCase();
		for (TurnDirections d : TurnDirections.values()) {
			if (s.equals(d.getName())) {
				return d;
			}
		}
		return null;
	}
}
