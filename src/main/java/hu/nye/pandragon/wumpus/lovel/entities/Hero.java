package hu.nye.pandragon.wumpus.lovel;

import hu.nye.pandragon.wumpus.Entity;

import java.awt.*;

public class Hero extends Entity {

	private int arrows;

	public Hero() {
		super(true, "Hős", 'H', true);
		arrows = 5;
		var x = "▀ \t▁ \t▂ \t▃ \t▄ \t▅ \t▆ \t▇ \t█ \t▉ \t▊ \t▋ \t▌ \t▍ \t▎ \t▏\nU+259x \t▐ \t░ \t▒ \t▓ \t▔ \t▕ \t▖ \t▗ \t▘ \t▙ \t▚ \t▛ \t▜ \t▝ \t▞ \t▟ ";
	}

	public void onShootArrow () {
		arrows--;
	}

	public void onHit () {

	}
}
