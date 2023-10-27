package hu.nye.pandragon.wumpus.lovel;

import hu.nye.pandragon.wumpus.lovel.entities.*;
import hu.nye.pandragon.wumpus.lovel.entities.Entity;

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

	public static Entities parse (String name) {
		for (Entities e : Entities.values()) {
			if (name.toLowerCase().equals(e.getEntity().getName().toLowerCase())) {
				return e;
			}
		}
		return null;
	}
}
