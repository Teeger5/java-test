package hu.nye.pandragon.wumpus.service.traits;

import hu.nye.pandragon.wumpus.model.entities.LivingEntity;
import hu.nye.pandragon.wumpus.service.game.Level;

/**
 * Az ezt implementáló pályaelemekre lépő
 * lénnyen alkalmazva lesz az itt leírt művelet, vagy műveletek
 */
public interface ActionOnLivingEntityEnters {
	/**
	 * Ez a metódus akkor fut le, amikor egy lény rálép erre a pályaelemre
	 * @param livingEntity a lény, aki ide lépett
	 */
	void onLivingEntityEnters (Level level, LivingEntity livingEntity);
}
