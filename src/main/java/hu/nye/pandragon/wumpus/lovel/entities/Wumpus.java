package hu.nye.pandragon.wumpus.lovel.entities;

import hu.nye.pandragon.wumpus.lovel.entities.traits.ActionOnHeroEnters;

public class Wumpus extends LivingEntity implements ActionOnHeroEnters {

	public Wumpus() {
		super("Wumpus", 'U', false);
	}

	@Override
	public void onHeroEnters(Hero hero) {
		hero.kill();
	}

	@Override
	public void kill() {
		System.out.println("Wumpus: Áááá!");
	}

	@Override
	public boolean isAlive() {
		return false;
	}

	@Override
	public Entity clone() {
		var clone = new Wumpus();
		clone.setPosition(position);
		clone.setAlive(alive);
		return clone;
	}
}
