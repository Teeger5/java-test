package hu.nye.pandragon.wumpus.lovel.entities;

import hu.nye.pandragon.wumpus.lovel.Directions;

import java.awt.*;

/**
 * Ez az osztály a lényeket írja le
 * Ezekről tudni lehet, hogy élnek-e
 */
public abstract class LivingEntity extends Entity {
	protected boolean alive;
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

	public void kill () {
		alive = false;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public abstract Entity clone();
}
