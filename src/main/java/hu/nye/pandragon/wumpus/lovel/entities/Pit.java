package hu.nye.pandragon.wumpus.lovel;

import hu.nye.pandragon.wumpus.Entity;

import java.awt.*;

public class Pit extends Entity {

	public Pit() {
		super(false, "Verem", 'O', false);
		var x = "▀ \t▁ \t▂ \t▃ \t▄ \t▅ \t▆ \t▇ \t█ \t▉ \t▊ \t▋ \t▌ \t▍ \t▎ \t▏\nU+259x \t▐ \t░ \t▒ \t▓ \t▔ \t▕ \t▖ \t▗ \t▘ \t▙ \t▚ \t▛ \t▜ \t▝ \t▞ \t▟ ";
	}
}
