package hu.nye.pandragon.wumpus;

import java.awt.*;

public class Entity {
	protected final Point position;
	protected final boolean blocking;
	protected final String name;
	protected final char symbol;
	protected final boolean unique;

	public Entity(boolean blocking, String name, char symbol, boolean unique) {
		this.position = new Point();
		this.blocking = blocking;
		this.name = name;
		this.symbol = symbol;
		this.unique = unique;
	}

	public String getName() {
		return name;
	}

	public Point getLocation() {
		return position;
	}

	public void setLocation(int x, int y) {
		position.setLocation(x, y);
	}

	public void setPosition(Point point) {
		position.setLocation(point);
	}

	public boolean isBlocking() {
		return blocking;
	}

	public char getSymbol() {
		return symbol;
	}

	public boolean isUnique() {
		return unique;
	}
}
