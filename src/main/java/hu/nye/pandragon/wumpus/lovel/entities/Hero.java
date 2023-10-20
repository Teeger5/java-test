package hu.nye.pandragon.wumpus.lovel.entities;

import hu.nye.pandragon.wumpus.lovel.Entity;

public class Hero extends Entity implements LivingEntity {

	private int arrows;

	public Hero() {
		super(true, "Hős", 'H', true);
		arrows = 5;
	}

	public void onShootArrow () {
		arrows--;
	}

	public void onHit () {

	}
}
