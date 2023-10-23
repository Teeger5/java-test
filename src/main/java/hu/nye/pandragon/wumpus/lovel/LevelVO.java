package hu.nye.pandragon.wumpus.lovel;

import hu.nye.pandragon.wumpus.lovel.entities.LivingEntity;
import hu.nye.pandragon.wumpus.lovel.entities.traits.Entity;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Ez az osztály egy pályát ír le, működés nélkül
 * Meg kell adni
 *  - a nem élő pályaelemeket,
 *  - a pályán lévú lényeket,
 *  - a pálya oldalméretét
 *  - a kezdőpontot
 * A kezdőpont pálya beolvasásakor a hős aktuális helye lehetne
 */
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
	private final Map<Point, LivingEntity> livingEnties;
	/**
	 * A pálya egy oldalának mérete
	 */
	private final int size;
	private final Point startPoint;

	public LevelVO(
			Map<Point, Entity> staticEntities,
			Map<Point, LivingEntity> livingEnties,
			int size,
			Point startPoint) {
		this.staticEntities = staticEntities;
		this.livingEnties = livingEnties;
		this.size = size;
		this.startPoint = startPoint;
	}

	public Map<Point, Entity> getStaticEntities() {
		return deepCopy(staticEntities);
	}

	public Map<Point, LivingEntity> getLivingEnties() {
		return deepCopyLiving(livingEnties);
	}

	public int getSize() {
		return size;
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
