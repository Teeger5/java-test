package hu.nye.pandragon.wumpus.lovel;

import hu.nye.pandragon.wumpus.lovel.entities.Empty;
import hu.nye.pandragon.wumpus.lovel.entities.Hero;
import hu.nye.pandragon.wumpus.lovel.entities.LivingEntity;
import hu.nye.pandragon.wumpus.lovel.entities.Wall;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
	private Hero hero;

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
/*		new TreeMap<>((a, b) -> {
			if (a.x > b.x) return 1;
			if (a.x == b.x && b.y > a.x) return -1;
			return 0;
		});*/
		for (int i = 1; i <= size; i++) {
			placeEntity(1, i, new Wall());
			placeEntity(size, i, new Wall());
			placeEntity(i, 1, new Wall());
			placeEntity(i, size, new Wall());
		}
		for (int i = 2; i < size; i++) {
			for (int j = 2; j < size; j++) {
				placeEntity(i, j, Entities.Empty.createNewInstance());
			}
		}
	}

	public void placeEntity (int x, int y, Entity entity) {
		entitiesByPosition.put(new Point(y, x), entity);
		entity.setLocation(y, x);
	}

	public void placeEntities (Point from, Point to, Entities type) {
		if (type.getEntity().isUnique()) {
//			return type + "-típusú elemből csak egy lehet a pályán";
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
				placeEntity(row, column, type.createNewInstance());
				j = j + qY;
			}
		}
		// Ez még nincs befejezve
	}

	public void shootArrow (Point from, Point to) {

	}

	private void alignWalls () {
		for (Map.Entry<Point, Entity> e : entitiesByPosition.entrySet()) {
			if (e.getValue() instanceof Wall w) {
				w.fitShape(entitiesByPosition, e.getKey());
			}
		}
	}

	public String drawLevel () {
		var drawing = new StringBuilder();
		alignWalls();
		drawing.append("    ");
		for (int i = 0; i < size; i++) {
			drawing.append(' ').append((char) (65 + i)).append(' ');
		}
		drawing.append('\n');
		for (int i = 1; i <= size; i++) {
			drawing.append(String.format(" %2d ", i));
			for (int j = 1; j <= size; j++) {
				var entity = entitiesByPosition.get(new Point(j, i));
				if (entity.isUnique() || entity instanceof LivingEntity) {
					drawing.append(' ').append(entity.getSymbol()).append(' ');
				}
				else if (entity instanceof Empty) {
					var c = '•';
					drawing.append(' ').append(c).append(' ');
				}
				else {
					if (entity instanceof Wall w) {
						var c = switch (w.getShape()) {
							case Middle, Horizontal, TopRight, BottomRight, HorizontalBottom, HorizontalTop, VerticalLeft, Single -> WallShape.Horizontal.getSymbol();
							default -> ' ';
						};
						drawing.append(c).append(w.getSymbol());

						c = switch (w.getShape()) {
							case Middle, Horizontal, HorizontalBottom, HorizontalTop, BottomLeft, VerticalRight, TopLeft, Single -> WallShape.Horizontal.getSymbol();
							default -> ' ';
						};
						drawing.append(c);
					}
					else {
						char c = entity.getSymbol();
						drawing.append(c).append(c).append(c);
					}
				}
			}
			drawing.append('\n');
		}
		return drawing.toString();
	}

	public int getSize() {
		return size;
	}
}
