package hu.nye.pandragon.wumpus.model;

public enum Items {
	Gold ("Arany");

	private final String name;

	Items(String name) {
		this.name = name;
	}

	/**
	 * Nem teljesen biztos, hogy erre szükség van,
	 * de még nem biztos
	 * @return a tárgy neve
	 */
	public String toString () {
		return name;
	}
}
