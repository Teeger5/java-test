package hu.nye.pandragon.wumpus.model.entities;

import hu.nye.pandragon.wumpus.service.game.Level;
import hu.nye.pandragon.wumpus.service.traits.ActionOnLivingEntityEnters;
import hu.nye.pandragon.wumpus.service.traits.StaticEntity;

/**
 * Ez az osztály a vermet, mint pályaelemet írja le
 */
public class Pit extends Entity implements StaticEntity, ActionOnLivingEntityEnters {

	public Pit() {
		super(false, "Verem", 'P', false, true);
		displaySymbol = '░';
	}

	@Override
	public void onLivingEntityEnters(Level level, LivingEntity livingEntity) {
		if (livingEntity instanceof Hero hero) {
			hero.decreaseArrows();
		}
	}

	@Override
	public Entity clone() {
		return new Pit();
	}
}
