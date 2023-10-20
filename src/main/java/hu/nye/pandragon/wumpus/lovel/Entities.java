package hu.nye.pandragon.wumpus;

import hu.nye.pandragon.wumpus.lovel.*;

public enum Entities {
	Wall(new Wall()),
	Pit(new hu.nye.pandragon.wumpus.lovel.Pit()),
	Wumpus(new hu.nye.pandragon.wumpus.lovel.Wumpus()),
	Hero(new hu.nye.pandragon.wumpus.lovel.Hero()),
	Empty(new Empty());

	// Konstruktorok

	public Entity getEntity () {
		return entity;
	}

	public Entity createNewInstance () {
		return switch (this) {
			case Wall -> new Wall();
			case Pit -> new Pit();
			case Wumpus -> new Wumpus();
			case Hero -> new Hero();
			case Empty -> new Empty();
		};
	}

	// Egy mintául szolgáló példány az egyes pályaelemekből,
	// így a tulajdonságaikat könnyen le lehet kérdezni
	private Entity entity;
	Entities(Entity entity2) {
		entity = entity2;
	}

	public static String getAsString () {
		return Entities.values().toString().toLowerCase()
				.replace(",", " |");
	}
}
