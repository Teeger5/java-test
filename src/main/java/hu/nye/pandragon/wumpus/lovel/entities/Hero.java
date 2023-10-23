package hu.nye.pandragon.wumpus.lovel.entities;

import hu.nye.pandragon.wumpus.lovel.Directions;
import hu.nye.pandragon.wumpus.lovel.Items;
import hu.nye.pandragon.wumpus.lovel.entities.traits.Entity;
import hu.nye.pandragon.wumpus.lovel.entities.traits.CanShoot;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Ez az osztály a játékost, mint a főhőst írja le
 */
public class Hero extends LivingEntity implements CanShoot {

	/**
	 * A játékos nyilainak száma
	 */
	private int arrows;
	private final Point position;
	private final List<Items> inventory;

	public Hero() {
		super("Hős", 'H', true);
		arrows = 3;
		direction = Directions.North;
		position = new Point(0, 0);
		inventory = new ArrayList<>();
	}

	public void decreaseArrows () {
		arrows = Math.max(0, arrows - 1);
	}

	public boolean hasItem (Items item) {
		return inventory.contains(item);
	}

	public void addItem (Items item) {
		inventory.add(item);
	}

	@Override
	public char getDisplaySymbol() {
		return direction.getDisplaySymbol();
	}

	@Override
	public Entity clone() {
		var hero = new Hero();
		hero.setAmmoAmount(arrows);
		hero.setDirection(direction);
		hero.setAlive(alive);
		return hero;
	}

	@Override
	public void onShoot() {
		decreaseArrows();
	}

	@Override
	public int getAmmoAmount() {
		return arrows;
	}

	@Override
	public void setAmmoAmount(int count) {
		arrows = count;
	}
}
