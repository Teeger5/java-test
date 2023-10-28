package hu.nye.pandragon.wumpus.model.entities;

import hu.nye.pandragon.wumpus.service.game.Level;
import hu.nye.pandragon.wumpus.service.traits.ActionOnHeroEnters;

public class Wumpus extends LivingEntity implements ActionOnHeroEnters {

	public Wumpus() {
		super("Wumpus", 'U', false);
	}

	@Override
	public void onHeroEnters(Hero hero) {
		hero.kill(null);
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
