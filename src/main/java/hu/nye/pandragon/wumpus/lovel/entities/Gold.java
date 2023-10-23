package hu.nye.pandragon.wumpus.lovel.entities;

import hu.nye.pandragon.wumpus.lovel.entities.traits.Entity;

/**
 * Ez az osztály az aranyat, mint pályaelemet írja le
 */
public class Gold extends Entity {

	public Gold() {
		super(false, "Arany", 'G', true, false);
	}

	@Override
	public Entity clone() {
		return new Gold();
	}
}
