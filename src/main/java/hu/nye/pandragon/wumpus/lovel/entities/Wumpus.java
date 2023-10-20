package hu.nye.pandragon.wumpus.lovel.entities;

import hu.nye.pandragon.wumpus.lovel.Entity;

public class Wumpus extends Entity implements LivingEntity {

	public Wumpus() {
		super(true, "Wumpus", 'W', false);
		var x = "▀ \t▁ \t▂ \t▃ \t▄ \t▅ \t▆ \t▇ \t█ \t▉ \t▊ \t▋ \t▌ \t▍ \t▎ \t▏\nU+259x \t▐ \t░ \t▒ \t▓ \t▔ \t▕ \t▖ \t▗ \t▘ \t▙ \t▚ \t▛ \t▜ \t▝ \t▞ \t▟ ";
	}
}
