package hu.nye.pandragon.wumpus.lovel.entities;

import hu.nye.pandragon.wumpus.lovel.entities.traits.HasInventory;
import hu.nye.pandragon.wumpus.model.Directions;
import hu.nye.pandragon.wumpus.model.Items;
import hu.nye.pandragon.wumpus.lovel.entities.traits.CanShoot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Ez az osztály a játékost, mint a főhőst írja le
 */
public class Hero extends LivingEntity implements CanShoot, HasInventory {

	/**
	 * A játékos nyilainak száma
	 */
	private int arrows;
	private final List<Items> inventory;

	public Hero() {
		super("Hős", 'H', true);
		arrows = 0;
		direction = Directions.North;
		inventory = new ArrayList<>();
	}

	public void decreaseArrows () {
		arrows = Math.max(0, arrows - 1);
	}

	@Override
	public List<Items> getInventory() {
		return inventory;
	}

	@Override
	public char getDisplaySymbol() {
		return direction.getDisplaySymbol();
	}

	@Override
	public Entity clone() {
		var clone = new Hero();
		clone.setAmmoAmount(arrows);
		clone.setPosition(position.x, position.y);
		clone.setDirection(direction);
		clone.setAlive(alive);
		for (Items item : inventory) {
			clone.addItem(item);
		}
		return clone;
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
