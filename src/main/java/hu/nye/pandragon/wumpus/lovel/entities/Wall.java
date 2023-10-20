package hu.nye.pandragon.wumpus.lovel;

import hu.nye.pandragon.wumpus.Entity;

import java.awt.*;

public class Wall extends Entity {

	public Wall () {
		super(true, "Fal", '▉', false);
		var x = "▀ \t▁ \t▂ \t▃ \t▄ \t▅ \t▆ \t▇ \t█ \t▉ \t▊ \t▋ \t▌ \t▍ \t▎ \t▏\nU+259x \t▐ \t░ \t▒ \t▓ \t▔ \t▕ \t▖ \t▗ \t▘ \t▙ \t▚ \t▛ \t▜ \t▝ \t▞ \t▟ ";
	}
}
