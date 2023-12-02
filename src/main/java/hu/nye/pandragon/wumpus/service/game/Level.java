package hu.nye.pandragon.wumpus.service.game;

import hu.nye.pandragon.wumpus.model.Directions;
import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.model.entities.Entity;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.model.entities.LivingEntity;
import hu.nye.pandragon.wumpus.model.entities.Wall;
import hu.nye.pandragon.wumpus.service.traits.StaticEntity;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Ez az osztály egy játékpályát ír le, és ad lehetőséget a szerkesztésére
 * Azért van egyben a kettő, mert a pálya minden elemét egy Map tartalmazza benne
 */
public class Level {

	private static final Logger LOGGER = LoggerFactory.getLogger(Level.class);

	/**
	 * A pálya egy oldalának mérete
	 */
	@Getter
	private final int size;
	/**
	 * A pályán lévő Wumpus lények max száma
	 */
	@Getter
	private int maxWumpus;
	/**
	 * Ez a map tartalmazza a pályaelemeket és az elhelyezkedésüket
	 * koordináta -> pályaelem
	 */
	private final Map<Point, Entity> staticEntites;
	/**
	 * A játékos kiindulóhelye
	 */
	private final Point startpoint;
	private final Map<Point, LivingEntity> livingEntities;
	@Getter
	@Setter
	private boolean editing;

	public Level(int size) {
		this.size = size;
		this.editing = false;
		livingEntities = new HashMap<>();
		staticEntites = new HashMap<>();
		for (int i = 1; i <= size; i++) {
			placeEntity(1, i, new Wall());
			placeEntity(size, i, new Wall());
			placeEntity(i, 1, new Wall());
			placeEntity(i, size, new Wall());
		}
		startpoint = new Point(0, 0);
		determineMaxWumpus();
	}

	public Level (LevelVO levelVO) {
		size = levelVO.getSize();
		staticEntites = levelVO.getStaticEntities();
		livingEntities = levelVO.getLivingEntities();
		startpoint = levelVO.getStartpoint();
		determineMaxWumpus();
		alignWalls();
	}

	/**
	 * LevelVO objektum létrehozása a pálya adataival
	 * @return a LevelVO objektum
	 */
	public LevelVO toLevelVO (int steps) {
		return new LevelVO(staticEntites, livingEntities, size, startpoint, steps);
	}

	public LevelVO toLevelVO () {
		return toLevelVO(0);
	}

	private <V extends Entity> Entity removeEntityIfExists (Entity entity, Map<Point, V> map) {
		if (entity == null) {
			return null;
		}
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

	/**
	 * Pályaelem eltávolítása referencia alapján
	 * @param entity a pályaelem
	 * @return maga a pályaelem, vagy null, ha nem található
	 */
	public Entity removeEntityIfExists (Entity entity) {
		var map = entity instanceof LivingEntity ? livingEntities : staticEntites;
		return removeEntityIfExists(entity, map);
	}

	/**
	 * Visszaadja az az adott típusba (statikus vagy élő) pályaelemek számát
	 * @param entity a pályaelemek fajtája függ ettől
	 * @return az ilyen típusú pályaelemek száma
	 */
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

	/**
	 * Meghatározza és beállítja a Wumpus-ok max számát
	 */
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
		if (entity == null || !entity.isBlocking()) {
			possibleDirections.put(Directions.North, new Point(checkingPoint.x, checkingPoint.y));
		}
		checkingPoint.y++;
		checkingPoint.x++;
		entity = staticEntites.get(checkingPoint);
		if (entity == null || !entity.isBlocking()) {
			possibleDirections.put(Directions.East, new Point(checkingPoint.x, checkingPoint.y));
		}
		checkingPoint.x--;
		checkingPoint.y++;
		entity = staticEntites.get(checkingPoint);
		if (entity == null || !entity.isBlocking()) {
			possibleDirections.put(Directions.South, new Point(checkingPoint.x, checkingPoint.y));
		}
		checkingPoint.y--;
		checkingPoint.x--;
		entity = staticEntites.get(checkingPoint);
		if (entity == null || !entity.isBlocking()) {
			possibleDirections.put(Directions.West, new Point(checkingPoint.x, checkingPoint.y));
		}
		return possibleDirections;
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
		LOGGER.info("Új pályaelem létrehozása: {} -> {} {}", entity.getName(), x, y);
		if (entity.isUnique()) {
			var e = removeEntityIfExists(entity);
			if (e != null) {
				entity = e;
			}
		}
		if (entity instanceof LivingEntity e) {
			e.setPosition(x, y);
			livingEntities.put(e.getPosition(), e);
			LOGGER.debug("add living entity: entities: " + livingEntities);
			if (entity instanceof Hero && (startpoint.x == 0 || editing)) {
				determineStartPoint();
			}
		}
		else {
			staticEntites.put(new Point(x, y), entity);
			alignWalls();
		}
	}

	public Entity removeLivingEntity (Point position) {
		return livingEntities.remove(position);
	}

	public Entity removeStaticEntity (Point position) {
			return staticEntites.remove(position);
	}

	/**
	 * Eltávolít egy lényt a pozíciója alapján
	 * @param x x koordináta (oszlop)
	 * @param y y koordináta (sor)
	 * @return az eltávolított pályaelem, vagy null, ha üres
	 */
	public Entity removeLivingEntity (int x, int y) {
		return removeLivingEntity(new Point(x, y));
	}

	public Entity removeStaticEntity (int x, int y) {
		return removeStaticEntity(new Point(x, y));
	}

	public Entity getFirstEntityInDirection (Point from, Directions direction) {
		return getFirstEntityInDirection(from, direction, false);
	}

	/**
	 * Megkeresi az első pályaelemet az adott pontból kiindulva az adott irányban haladva
	 * @param from a kiindulási pont
	 * @param direction az irány
	 * @param goesThroughNonBlocking ha true, akkor az első blokkoló pályaelemig megy, pl. egy falig
	 * @return az első pályaelem a fenti paraméterek alapján
	 */
	public Entity getFirstEntityInDirection (Point from, Directions direction, boolean goesThroughNonBlocking) {
		Entity entity = null;
		var point = new Point(from.x, from.y);
		int dx = 0, dy = 0;
		switch (direction) {
			case North: dy = -1; break;
			case East: dx = 1; break;
			case South: dy = 1; break;
			case West: dx = -1; break;
		}
		while (entity == null || entity instanceof StaticEntity && goesThroughNonBlocking && !entity.isBlocking()) {
			point.x += dx;
			point.y += dy;
			entity = livingEntities.get(point);
			if (entity == null) {
				entity = staticEntites.get(point);
			}
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

	public void setStartpoint (int x, int y) {
		startpoint.setLocation(x, y);
	}
}
