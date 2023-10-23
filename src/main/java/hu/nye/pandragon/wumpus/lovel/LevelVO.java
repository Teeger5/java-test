package hu.nye.pandragon.wumpus.lovel;

import hu.nye.pandragon.wumpus.lovel.entities.LivingEntity;
import hu.nye.pandragon.wumpus.lovel.entities.traits.Entity;

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
public class LevelVO {

	/**
	 * Ez a map tartalmazza a pályaelemeket és a pozíciójukat
	 * pozíció -> pályaelem
	 */
	private final Map<Point2, Entity> staticEntities;
	/**
	 * Ez a map tartalmazza a pályán lévő lényeket és a pozíciójukat
	 * pozíció -> lény
	 */
	private final Map<Point2, LivingEntity> livingEnties;
	/**
	 * A pálya egy oldalának mérete
	 */
	private final int size;
	private final Point2 startPoint;

	public LevelVO(
			Map<Point2, Entity> staticEntities,
			Map<Point2, LivingEntity> livingEnties,
			int size,
			Point2 startPoint) {
		this.staticEntities = staticEntities;
		this.livingEnties = livingEnties;
		this.size = size;
		this.startPoint = startPoint;
	}

	public Map<Point2, Entity> getStaticEntities() {
		return deepCopy(staticEntities);
	}

	public Map<Point2, LivingEntity> getLivingEnties() {
		return deepCopyLiving(livingEnties);
	}

	public int getSize() {
		return size;
	}

	private static Map<Point2, Entity> deepCopy (Map<Point2, Entity> map) {
		var cloned = new HashMap<Point2, Entity>();
		for (Map.Entry<Point2, Entity> e : map.entrySet()) {
			var point = e.getKey();
			cloned.put(new Point2(point.x, point.y), e.getValue().clone());
		}
		return cloned;
	}

	private static Map<Point2, LivingEntity> deepCopyLiving (Map<Point2, LivingEntity> map) {
		var cloned = new HashMap<Point2, LivingEntity>();
		for (Map.Entry<Point2, LivingEntity> e : map.entrySet()) {
			var point = e.getKey();
			cloned.put(new Point2(point.x, point.y), (LivingEntity) e.getValue().clone());
		}
		return cloned;
	}
}
