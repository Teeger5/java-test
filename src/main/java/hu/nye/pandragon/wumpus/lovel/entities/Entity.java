package hu.nye.pandragon.wumpus.lovel.entities;

public abstract class Entity {
	/**
	 * A pályaelem neve
	 */
	protected final String name;
	/**
	 * Az a szimbólum, amely a pályán jelképezi ezt a pályaelemet
	 */
	protected char displaySymbol;
	/**
	 * Ez egy egységes szimbólum, ami mindenhol ezt a típusú pályaelemet azonosítja
	 * Hasznos például fájlból való beolvasááskor
	 */
	protected final char compatibilitySymbol;
	/**
	 * Át lehet-e menni rajta, vagy beleütközünk-e?
	 */
	protected final boolean blocking;
	/**
	 * Csak egy lehet belőle a pályán?
	 */
	protected final boolean unique;
	/**
	 * Kirajzoláskor az egész cellát ki kell-e törltenie,
	 * vagy egyszer jelenjen meg középen a szimbóluma?legy
	 */
	protected final boolean extendsInCell;

	public Entity(boolean blocking, String name, char compatibilitySymbol, boolean unique, boolean extendsInCell) {
		this.blocking = blocking;
		this.name = name;
		this.compatibilitySymbol = compatibilitySymbol;
		this.unique = unique;
		this.extendsInCell = extendsInCell;
		this.displaySymbol = compatibilitySymbol;
	}

	public String getName() {
		return name;
	}

	public boolean isBlocking() {
		return blocking;
	}

	public char getDisplaySymbol() {
		return displaySymbol;
	}

	public char getCompatibilitySymbol() {
		return compatibilitySymbol;
	}

	public boolean shouldExtendInCell() {
		return extendsInCell;
	}

	public boolean isUnique() {
		return unique;
	}

	public int getMaxNumber () {
		return Integer.MAX_VALUE;
	}

	public abstract Entity clone ();
}
