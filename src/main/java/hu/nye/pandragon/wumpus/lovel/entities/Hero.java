package hu.nye.pandragon.wumpus.lovel.entities;

import hu.nye.pandragon.wumpus.lovel.Directions;
import hu.nye.pandragon.wumpus.lovel.Items;
import hu.nye.pandragon.wumpus.lovel.entities.traits.Entity;
import hu.nye.pandragon.wumpus.lovel.entities.traits.CanShoot;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Hero {\n");
		sb.append("\tarrows = ").append(arrows + ",\n");
		sb.append("\tposition = ").append(position + ",\n");
		sb.append("\tinventory = ").append(inventory + ",\n");
		sb.append("\talive = ").append(alive + ",\n");
		sb.append("\tposition = ").append(position + ",\n");
		sb.append("\tdirection = ").append(direction + ",\n");
		sb.append("\tdisplaySymbol = ").append(displaySymbol + ",\n");
		sb.append("}");
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Hero hero = (Hero) o;
		return arrows == hero.arrows && Objects.equals(position, hero.position) && Objects.equals(inventory, hero.inventory);
	}

	@Override
	public int hashCode() {
		return Objects.hash(arrows, position, inventory);
	}
}
