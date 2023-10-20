package hu.nye.pandragon.wumpus;

import hu.nye.pandragon.wumpus.lovel.Wall;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

public class Level {
	/*
	A különböző elemek mind külön listában lesznek tárolva
	Emellett érdemes lehet létrehozni egy map-ot
	amiben egyben van minden, hogy könnyebben meg lehessen találni,
	ha ütközés lenne a pályán
	 */
	private int size;
	int maxWumpus;
	private Map<Point, Entity> entitiesByPosition;

	public Level(int size) {
		this.size = size;
		if (size < 9) {
			maxWumpus = 1;
		}
		else if (size < 15) {
			maxWumpus = 2;
		}
		else {
			maxWumpus = 3;
		}
		entitiesByPosition = new HashMap<>();
		for (int i = 0; i < size; i++) {
			entitiesByPosition.put(new Point(0, i), new Wall());
			entitiesByPosition.put(new Point(size - 1, i), new Wall());
			entitiesByPosition.put(new Point(i, 0), new Wall());
			entitiesByPosition.put(new Point(i, size - 1), new Wall());
		}
	}

	public void placeEntity (int x, int y, Entity entity) {
		entitiesByPosition.put(new Point(x, y), entity);
		entity.setLocation(x, y);
	}

	public String placeEntities (Point from, Point to, Entities type) {
		if (type.getEntity().isUnique()) {
			return type + "-típusú elemből csak egy lehet a pályán";
		}
		if (from.x > to.x) {
			var temp = from;
			from = to;
			to = temp;
		}
		float distanceX = to.x - from.x;
		float distanceY = to.y - from.y;

		if (distanceX > distanceY) {
			float qX = 1;
			float qY = distanceY / distanceX;

			float j = from.y;
			for (int i = from.x; i < to.x; i++) {
				int row = i * size;
				int column = (int) j;
				entitiesByPosition.put(new Point(row, column), type.createNewInstance());
				j = j + qY;
			}
		}
		// Ez még nincs befejezve
		return "";
	}

	public void shootArrow (Point from, Point to) {

	}

	public String drawLevel () {
		var drawing = new StringBuilder();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				var entity = entitiesByPosition.get(new Point(i, j));
				if (entity != null) {

				}
				else { drawing.append("   "); }
			}
			drawing.append('\n');
		}
		return "";
	}
}
