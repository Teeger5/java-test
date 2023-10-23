package hu.nye.pandragon.wumpus.lovel.entities;

import hu.nye.pandragon.wumpus.lovel.entities.traits.Entity;

/**
 * Ez az osztály az pályán lévő üres részt írja le
 * Ahol nincs más pályaelem, ott ez van
 * Elvégre legalább semmi mindig van
 */
public class Empty extends Entity {

	public Empty() {
		super(false, "Út", '_', false, false);
		displaySymbol = ' ';
	}

	@Override
	public Entity clone() {
		return new Empty();
	}
}
