package hu.nye.pandragon.wumpus.lovel.entities;

import hu.nye.pandragon.wumpus.lovel.entities.traits.Entity;
import hu.nye.pandragon.wumpus.lovel.WallShape;
import hu.nye.pandragon.wumpus.lovel.entities.traits.StaticEntity;

import java.awt.*;
import java.util.Map;

public class Wall extends Entity implements StaticEntity {

	private WallShape shape;

	public Wall () {
		super(true, "Fal", '_', false, true);
		displaySymbol = ' ';
		shape = WallShape.Single;
	}

	public WallShape getShape() {
		return shape;
	}

	public void setShape(WallShape shape) {
		this.shape = shape;
	}

	@Override
	public char getDisplaySymbol() {
		return shape.getSymbol();
	}

	@Override
	public Entity clone() {
		var clone = new Wall();
		clone.setShape(shape);
		return clone;
	}

	/**
	 * Ez a metődus a megadott pályán az aktuális fal elem környezetét ellenőrzi,
	 * és megpróbálja pontosítani a falak formáját.
	 * Ehhez megváltoztatja a karaktert, amely ezt az elemet képviselné.
	 * Azaz,
	 *  - ha ez az elem része egy vízszintes irányú falnak,
	 * akkor vízszintesen hosszú falként fog megjelenni,
	 *  - ha egy függőleges fal része, akkor függőlegesként,
	 *  - a sarkokat sarkot formálóak is
	 * @param entities a pályaelemek térképe, a falakra van csak szükség igazából
	 */
	public void fitShape (Map<Point, Entity> entities, Point currentPosition) {
		var x = currentPosition.x;
		var y = currentPosition.y;

		var top = entities.get(new Point(x, y - 1)) instanceof Wall;
		var right = entities.get(new Point(x + 1, y)) instanceof Wall;
		var bottom = entities.get(new Point(x, y + 1)) instanceof Wall;
		var left = entities.get(new Point(x - 1, y)) instanceof Wall;
//		System.out.printf("wall: %2d, %2d -> %s %s %s %s\n", x, x, top, right, bottom, left);
		shape = WallShape.getShape(top, right, bottom, left);
//		System.out.println(" - shape -> " + shape.getSymbol());
	}
}
