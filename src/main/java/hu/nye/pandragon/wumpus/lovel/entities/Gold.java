package hu.nye.pandragon.wumpus.lovel;

import hu.nye.pandragon.wumpus.Entity;

import java.awt.*;

public class Gold extends Entity {

	public Gold() {
		super(true, "Arany", 'A', true);
		var x = "▀ \t▁ \t▂ \t▃ \t▄ \t▅ \t▆ \t▇ \t█ \t▉ \t▊ \t▋ \t▌ \t▍ \t▎ \t▏\nU+259x \t▐ \t░ \t▒ \t▓ \t▔ \t▕ \t▖ \t▗ \t▘ \t▙ \t▚ \t▛ \t▜ \t▝ \t▞ \t▟ ";
	}

	public void onStepOn (Hero hero) {

	}
}
