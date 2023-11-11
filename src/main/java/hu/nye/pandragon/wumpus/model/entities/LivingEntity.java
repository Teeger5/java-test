package hu.nye.pandragon.wumpus.model.entities;

import hu.nye.pandragon.wumpus.model.Directions;
import hu.nye.pandragon.wumpus.service.game.Level;

import java.awt.*;

/**
 * Ez az osztály a lényeket írja le
 * Ezekről tudni lehet, hogy élnek-e
 */
public abstract class LivingEntity extends Entity {
	/**
	 * Él-e a lénynek ez a példánya
	 */
	protected boolean alive;
	/**
	 * A lény pozíciója a pályán
	 */
	protected final Point position;
	/**
	 * Merre néz a hős?
	 */
	protected Directions direction;

	public LivingEntity(
			String name,
			char compatibilitySymbol,
			boolean unique
	) {
		super(false, name, compatibilitySymbol, unique, false);
		alive = true;
		this.position = new Point(0, 0);
	}
//	void move (Map<Point, Entity> entities, int x, int y);

	public void setPosition (int x, int y) {
		position.setLocation(x, y);
	}

	public void setPosition (Point point) {
		position.setLocation(point.x, point.y);
	}

	public Point getPosition () {
		return position;
	}

	public Directions getDirection() {
		return direction;
	}

	public void setDirection(Directions direction) {
		this.direction = direction;
	}

	public void kill (Level level) {
		alive = false;
		level.removeEntity(position.x, position.y);
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("LivingEntity {\n");
		sb.append("\talive = ").append(alive + ",\n");
		sb.append("\tposition = ").append(position + ",\n");
		sb.append("\tdirection = ").append(direction + ",\n");
		sb.append("}");
		return sb.toString();
	}

	public abstract Entity clone();

/*	public String toString () {
		return new StringBuilder(name).append(" @ ")
				.append('[').append(position.x).append(" ; ").append(position.y).append(']')
				.toString();
	}*/
}
