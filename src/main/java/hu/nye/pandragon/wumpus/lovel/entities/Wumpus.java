package hu.nye.pandragon.wumpus.lovel;

import hu.nye.pandragon.wumpus.Entity;

import java.awt.*;

public class Wumpus extends Entity {

	public Wumpus() {
		super(true, "Wumpus", 'W', false);
		var x = "▀ \t▁ \t▂ \t▃ \t▄ \t▅ \t▆ \t▇ \t█ \t▉ \t▊ \t▋ \t▌ \t▍ \t▎ \t▏\nU+259x \t▐ \t░ \t▒ \t▓ \t▔ \t▕ \t▖ \t▗ \t▘ \t▙ \t▚ \t▛ \t▜ \t▝ \t▞ \t▟ ";
	}
}
