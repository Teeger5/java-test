package hu.nye.pandragon.wumpus.lovel;

import hu.nye.pandragon.wumpus.lovel.entities.*;
import hu.nye.pandragon.wumpus.model.Directions;
import hu.nye.pandragon.wumpus.model.LevelVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Ez az osztály egy játékpályát ír le, és ad lehetőséget a szerkesztésére
 * Azért van egyben a kettő, mert a pálya minden elemét egy Map tartalmazza benne
 */
public class Level {

	private static final Logger LOGGER = LoggerFactory.getLogger(Level.class);

	/**
	 * A pálya egy oldalának mérete
	 */
	private final int size;
	/**
	 * A pályán lévő Wumpus lények max száma
	 */
	private int maxWumpus;
	/**
	 * Ez a map tartalmazza a pályaelemeket és az elhelyezkedésüket
	 * koordináta -> pályaelem
	 */
	private final Map<Point, Entity> staticEntites;
	/**
	 * A pálya szerkesztés alatt áll-e
	 */
	private boolean isEditing;
	/**
	 * A játékos kiindulóhelye
	 */
	private final Point startpoint;
	private final Map<Point, LivingEntity> livingEntities;

	public Level(int size) {
		this.size = size;
		livingEntities = new HashMap<>();
		staticEntites = new HashMap<>();
		for (int i = 1; i <= size; i++) {
			placeEntity(1, i, new Wall());
			placeEntity(size, i, new Wall());
			placeEntity(i, 1, new Wall());
			placeEntity(i, size, new Wall());
		}
		startpoint = new Point(1, 1);
		determineStartPoint();
		determineMaxWumpus();
	}

	public Level (LevelVO levelVO) {
		size = levelVO.getSize();
		staticEntites = levelVO.getStaticEntities();
		livingEntities = levelVO.getLivingEntities();
		startpoint = new Point(2, 2);
		determineStartPoint();
		determineMaxWumpus();
	}

	public LevelVO toLevelVO () {
		return new LevelVO(staticEntites, livingEntities, size);
	}

	private <V extends Entity> Entity removeEntityIfExists (Entity entity, Map<Point, V> map) {
//		var map = living ? livingEntities : staticEntites;
		var it = map.values().iterator();
		while (it.hasNext()) {
			var value = it.next();
			if (entity.getClass() == value.getClass()) {
				it.remove();
				return value;
			}
		}
		return null;
	}

	public Entity removeEntityIfExists (Entity entity) {
		var map = entity instanceof LivingEntity ? livingEntities : staticEntites;
		return removeEntityIfExists(entity, map);
	}

	public int getEntityCount (Entity entity) {
		var map = entity instanceof LivingEntity ? livingEntities : staticEntites;
		return getEntityCount(entity, map);
	}

	private <V extends Entity> int getEntityCount (Entity entity, Map<Point, V> map) {
		int count = 0;
		for (Entity value : map.values()) {
			if (entity.getClass() == value.getClass()) {
				count++;
			}
		}
		return count;
	}

	public Hero getHero () {
		for (LivingEntity e : livingEntities.values()) {
			if (e instanceof Hero h) {
				return h;
			}
		}
		return null;
	}

	/**
	 * Ez a metódus összegyűjti a pályán lévő lényeket,
	 * létrehoz mindhez egy Entitycontroller-t,
	 * majd ezek listáját adja vissza
	 * @return az Entitycontroller-ek listája
	 */
	public List<EntityController> getEntityControllers () {
		var controllers = new ArrayList<EntityController>();
		for (LivingEntity e : livingEntities.values()) {
			if (!(e instanceof Hero)) {
				controllers.add(new EntityController(this, e));
			}
		}
		return controllers;
	}

	public Point getStartPoint () {
		return new Point(startpoint);
	}

	/**
	 * Ez a metódus megkeresi és beállítja a kezdőhely pontját
	 * Ez azért kell, hogy tudjuk, hová kell visszavinnie a hősnek az aranyat
	 * A hős pozíciója a kezdőhely, és nem hoz létre hozzá új Point objektumot
	 */
	private void determineStartPoint () {
		var hero = getHero();
		if (hero != null) {
			var position = hero.getPosition();
			startpoint.setLocation(position.x, position.y);
		}
	}

	private void determineMaxWumpus () {
		if (size < 9) {
			maxWumpus = 1;
		}
		else if (size < 15) {
			maxWumpus = 2;
		}
		else {
			maxWumpus = 3;
		}
	}

	/**
	 * Ez a metódus összegyűjti az adott pozíció körüli járható pozíciókat,
	 * ahová egy lény léphet.
	 * Minden pozíció járható, amelyiken nincs blokkoló elem, pl. fal
	 * Így a járható pozíciók között lehet akár verem, vagy Wumpus is
	 * @param position a körüljárandó pozíció
	 * @return a szomszédos járható pozíciók
	 */
	public Map<Directions, Point> getPossibleMoves (Point position) {
		var possibleDirections = new EnumMap<Directions, Point>(Directions.class);
		var checkingPoint = new Point(position.x, position.y);
		checkingPoint.y--;
		var entity = staticEntites.get(checkingPoint);
		if (entity == null || entity != null && !entity.isBlocking()) {
			possibleDirections.put(Directions.North, new Point(checkingPoint.x, checkingPoint.y));
		}
		checkingPoint.y++;
		checkingPoint.x++;
		entity = staticEntites.get(checkingPoint);
		if (entity == null || entity != null && !entity.isBlocking()) {
			possibleDirections.put(Directions.East, new Point(checkingPoint.x, checkingPoint.y));
		}
		checkingPoint.x--;
		checkingPoint.y++;
		entity = staticEntites.get(checkingPoint);
		if (entity == null || entity != null && !entity.isBlocking()) {
			possibleDirections.put(Directions.South, new Point(checkingPoint.x, checkingPoint.y));
		}
		checkingPoint.y--;
		checkingPoint.x--;
		entity = staticEntites.get(checkingPoint);
		if (entity == null || entity != null && !entity.isBlocking()) {
			possibleDirections.put(Directions.West, new Point(checkingPoint.x, checkingPoint.y));
		}
		return possibleDirections;
	}

	public void setEditing(boolean editing) {
		isEditing = editing;
	}

	/**
	 * Új pályaelem hozzáadása
	 * Lehet statikus (pl. fal) és lény is
	 * A lényt az elhelyezkedése teszi egyedivé
	 * Egy statikus és egy élő pályaelem lehet egy pozíción, ezek szét vannak választva
	 * Egy pályaelemet a maga kategóriájában az elhelyezkedése tesz egyedivé
	 * Viszont referencia alapján teszünk különbséget köztük,
	 * így oda kell figyelni arra, hogy ne kerüljön két azonos típus egy pozícióra
	 * @param entity pályaelem
	 * @param x sor száma
	 * @param y oszlop száma
	 */
	public void placeEntity (int x, int y, Entity entity) {
		if (entity.isUnique()) {
			var e = removeEntityIfExists(entity);
			if (e != null) {
				entity = e;
			}
		}
		if (entity instanceof LivingEntity e) {
			e.setPosition(x, y);
			livingEntities.put(e.getPosition(), e);
			if (entity instanceof Hero) {
				determineStartPoint();
			}
		}
		else {
			staticEntites.put(new Point(x, y), entity);
			alignWalls();
		}
	}

	/**
	 * Eltávolít egy pályaelemet a pozíciója alapján
	 * Először a lényeket nézi, majd ha azt nem talál,
	 * akkor a statikusakat
	 * @param x x koordináta (oszlop)
	 * @param y y koordináta (sor)
	 * @return az eltávolított pályaelem, vagy null, ha üres
	 */
	public Entity removeEntity (int x, int y) {
		Entity entity = livingEntities.remove(new Point(x, y));
		if (entity == null) {
			entity = staticEntites.remove(new Point(x, y));
		}
		return entity;
	}

	public void placeEntities (Point from, Point to, Entity type) {
		if (from.x < to.x) {
			if (from.y < to.y) {
				for (int i = from.x; i <= to.x; i++) {
					placeEntity(i, from.y, type);
				}
			}
		}
/*		if (type.getEntity().isUnique()) {
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
		}*/
		// Ez még nincs befejezve
	}

	public Entity getFirstEntityInDirection (Point from, Directions direction) {
		Entity entity = null;
		var point = new Point(from.x, from.y);
		int dx = 0, dy = 0;
		switch (direction) {
			case North: dy = -1; break;
			case East: dx = 1; break;
			case South: dy = 1; break;
			case West: dx = -1; break;
		}
		point.x += dx;
		point.y += dy;
		while (entity == null) {
			entity = livingEntities.get(point);
			if (entity == null) {
				entity = staticEntites.get(point);
			}
			point.x += dx;
			point.y += dy;
		}
		return entity;
	}

	private void alignWalls () {
		for (Map.Entry<Point, Entity> e : staticEntites.entrySet()) {
			if (e.getValue() instanceof Wall w) {
				w.fitShape(staticEntites, e.getKey());
			}
		}
	}

	public int getSize() {
		return size;
	}

	public int getMaxWumpus() {
		return maxWumpus;
	}
}
