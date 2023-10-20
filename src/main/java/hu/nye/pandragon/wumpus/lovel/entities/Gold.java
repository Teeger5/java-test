package hu.nye.pandragon.wumpus.lovel.entities;

import hu.nye.pandragon.wumpus.lovel.Entity;

public class Gold extends Entity {

	public Gold() {
		super(true, "Arany", 'A', true);
		var x = "▀ \t▁ \t▂ \t▃ \t▄ \t▅ \t▆ \t▇ \t█ \t▉ \t▊ \t▋ \t▌ \t▍ \t▎ \t▏\nU+259x \t▐ \t░ \t▒ \t▓ \t▔ \t▕ \t▖ \t▗ \t▘ \t▙ \t▚ \t▛ \t▜ \t▝ \t▞ \t▟ ";
	}

	public void onStepOn (Hero hero) {

	}
}
