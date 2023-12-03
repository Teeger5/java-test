package hu.nye.pandragon.wumpus.model.entities;

import hu.nye.pandragon.wumpus.service.game.Level;
import hu.nye.pandragon.wumpus.service.traits.ActionOnLivingEntityEnters;

public class Wumpus extends LivingEntity implements ActionOnLivingEntityEnters {

	public Wumpus() {
		super("Wumpus", 'U', false);
	}

	@Override
	public void onLivingEntityEnters(Level level, LivingEntity livingEntity) {
		if (livingEntity instanceof Hero hero) {
			hero.kill(level);
		}
	}

	@Override
	public void kill(Level level) {
		System.out.println("Wumpus: Áááá!");
		super.kill(level);
	}

	@Override
	public Entity clone() {
		var clone = new Wumpus();
		clone.setPosition(position);
		clone.setAlive(alive);
		return clone;
	}
}
