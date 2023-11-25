package hu.nye.pandragon.wumpus.model;

import hu.nye.pandragon.wumpus.model.entities.*;

public enum Entities {
	Wall(new Wall()),
	Gold(new Gold()),
	Pit(new Pit()),
	Wumpus(new Wumpus()),
	Hero(new Hero());

	// Konstruktorok

	public Entity getEntity () {
		return entity;
	}

	public String getName () {
		return entity.getName();
	}

	public Entity createNewInstance () {
		return switch (this) {
			case Wall -> new Wall();
			case Gold -> new Gold();
			case Pit -> new Pit();
			case Wumpus -> new Wumpus();
			case Hero -> new Hero();
		};
	}
/*
	public static XmlEntity toXmlEntity (Entity entity) {
		return switch (Entities.parseSymbol(entity.getCompatibilitySymbol())) {
			case Wall -> null;
			case Gold -> null;
			case Pit -> null;
			case Wumpus -> null;
			case Hero hero -> new XmlHero(hero);
		};
	}*/

	public boolean isLivingEntity () {
		return getEntity() instanceof LivingEntity;
	}

	/**
	 * Egy mintául szolgáló példány az egyes pályaelemekből,
	 * így a tulajdonságaikat könnyen le lehet kérdezni
	 * Ezek a dolgok statikusak alapból,
	 * nem lesz belőlük új létrehozva az enum használatakor
 	 */
	private final Entity entity;
	Entities(Entity entity) {
		this.entity = entity;
	}

	/**
	 * Visszaadja az elérhető pályaelemek listáját a következő formában:
	 * A|B|C|...
	 * Ez egy regex esetén hasznos lehet
	 * @return az elérhető pályaelemek nevei | jelekkel elválasztva
	 */
	public static String getAsString () {
		var b = new StringBuilder();
		for (Entities e : Entities.values()) {
			b.append(e.getEntity().getName()).append("|");
		}
		b.setLength(b.length() - 1);
		return b.toString().toLowerCase();
	}

	/**
	 * Pályaelem megtalálása a neve alapján
	 * Nem az enum érték neve, hanem a 'name' mezőjének értéke
	 * @param name a pályaelem neve
	 * @return a pályaelem enum értéke, vagy null, ha nem található
	 */
	public static Entities parseName (String name) {
		name = name.toLowerCase();
		for (Entities e : Entities.values()) {
			if (name.equals(e.getEntity().getName().toLowerCase())) {
				return e;
			}
		}
		return null;
	}

	/**
	 * Ez a compatibilitySymbol tulajdonság alapján
	 * keres meg egy Entities értéket
	 * @param symbol a pályaelem compatibilitySymbol tulajdonsága
	 * @return a pályaelemhez taratozó Entities érték
	 */
	public static Entities parseSymbol (char symbol) {
		for (Entities e : Entities.values()) {
			if (symbol == e.getEntity().getCompatibilitySymbol()) {
				return e;
			}
		}
		return null;
	}
}
