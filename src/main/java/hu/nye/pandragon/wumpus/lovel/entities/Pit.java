package hu.nye.pandragon.wumpus.lovel.entities;

import hu.nye.pandragon.wumpus.lovel.entities.traits.ActionOnHeroEnters;
import hu.nye.pandragon.wumpus.lovel.entities.traits.StaticEntity;

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
