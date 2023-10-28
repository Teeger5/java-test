package hu.nye.pandragon.wumpus.model.entities;

import hu.nye.pandragon.wumpus.service.traits.ActionOnHeroEnters;
import hu.nye.pandragon.wumpus.service.traits.StaticEntity;

/**
 * Ez az osztály a vermet, mint pályaelemet írja le
 */
public class Pit extends Entity implements ActionOnHeroEnters, StaticEntity {

	public Pit() {
		super(false, "Verem", 'P', false, true);
		displaySymbol = '░';
	}

	@Override
	public void onHeroEnters(Hero hero) {
		hero.decreaseArrows();
	}

	@Override
	public Entity clone() {
		return new Pit();
	}
}
