package hu.nye.pandragon.wumpus.model;

import hu.nye.pandragon.wumpus.model.entities.Entity;
import hu.nye.pandragon.wumpus.model.entities.LivingEntity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Ez az osztály egy pályát ír le, működés nélkül
 * Meg kell adni
 *  - a nem élő pályaelemeket,
 *  - a pályán lévú lényeket,
 *  - a pálya oldalméretét
 *  - a kezdőpontot
 * A kezdőpont pálya beolvasásakor a hős aktuális helye lehetne
 */
@EqualsAndHashCode
@ToString
public class LevelVO {

	/**
	 * Ez a map tartalmazza a pályaelemeket és a pozíciójukat
	 * pozíció -> pályaelem
	 */
	private final Map<Point, Entity> staticEntities;
	/**
	 * Ez a map tartalmazza a pályán lévő lényeket és a pozíciójukat
	 * pozíció -> lény
	 */
	private final Map<Point, LivingEntity> livingEntities;
	/**
	 * A pálya egy oldalának mérete
	 */
	private final int size;
	/**
	 * A hős kiinduló pozíciója
	 */
	private final Point startpoint;

	public LevelVO(
			Map<Point, Entity> staticEntities,
			Map<Point, LivingEntity> livingEntities,
			int size,
			Point startpoint) {
		this.staticEntities = staticEntities;
		this.livingEntities = livingEntities;
		this.size = size;
		this.startpoint = startpoint;
	}

	public Map<Point, Entity> getStaticEntities() {
		return deepCopy(staticEntities);
	}

	public Map<Point, LivingEntity> getLivingEntities() {
		return deepCopyLiving(livingEntities);
	}

	public int getSize() {
		return size;
	}

	public Point getStartpoint () {
		return new Point(startpoint);
	}

	private static Map<Point, Entity> deepCopy (Map<Point, Entity> map) {
		var cloned = new HashMap<Point, Entity>();
		for (Map.Entry<Point, Entity> e : map.entrySet()) {
			var point = e.getKey();
			cloned.put(new Point(point.x, point.y), e.getValue().clone());
		}
		return cloned;
	}

	private static Map<Point, LivingEntity> deepCopyLiving (Map<Point, LivingEntity> map) {
		var cloned = new HashMap<Point, LivingEntity>();
		for (Map.Entry<Point, LivingEntity> e : map.entrySet()) {
			var point = e.getKey();
			cloned.put(new Point(point.x, point.y), (LivingEntity) e.getValue().clone());
		}
		return cloned;
	}
}
