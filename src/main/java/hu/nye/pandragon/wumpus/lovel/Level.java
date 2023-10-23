package hu.nye.pandragon.wumpus.lovel;

import hu.nye.pandragon.wumpus.lovel.entities.Empty;
import hu.nye.pandragon.wumpus.lovel.entities.Hero;
import hu.nye.pandragon.wumpus.lovel.entities.LivingEntity;
import hu.nye.pandragon.wumpus.lovel.entities.Wall;
import hu.nye.pandragon.wumpus.lovel.entities.traits.Entity;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Ez az osztály egy játékpályát ír le, és ad lehetőséget a szerkesztésére
 * Azért van egyben a kettő, mert a pálya minden elemét egy Map tartalmazza benne
 */
public class Level {
	/**
	 * A pálya egy oldalának mérete
	 */
	private int size;
	/**
	 * A pályán lévő Wumpus lények max száma
	 */
	int maxWumpus;
	/**
	 * Ez a map tartalmazza a pályaelemeket és az elhelyezkedésüket
	 * koordináta -> pályaelem
	 */
	private Map<Point, Entity> staticEntites;
	/**
	 * A pálya szerkesztés alatt áll-e
	 */
	private boolean isEditing;
	/**
	 * A játékos kiindulóhelye
	 */
	private Point startpoint;
	private final Map<Point, LivingEntity> livingEntities;

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
		livingEntities = new HashMap<>();
		staticEntites = new HashMap<>();
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

	/**
	 * Új lény hozzáadása a pályához
	 * Nem sikerül, ha a lény egyedi és már létezik a pályán
	 * @param x sor száma
	 * @param y oszlop száma
	 * @param entity lény
	 * @return true, ha sikerült hozzáadni
	 */
	public boolean addLivingEntity (LivingEntity entity, int x, int y) {
		return addLivingEntity(entity, x, y, false);
	}

	/**
	 * Új lény hozzáadása a pályához
	 *
	 * @param x sor száma
	 * @param y oszlop száma
	 * @param entity lény
	 * @param replace felül legyen-e írva, ha már létezik a pályán
	 * @return true, ha sikerült hozzáadni
	 */
	public boolean addLivingEntity (LivingEntity entity, int x, int y, boolean replace) {
		if (entity.isUnique() && !replace && livingEntities.containsValue(entity)) {
			return false;
		}
		entity.setPosition(x, y);
		livingEntities.put(entity.getPosition(), entity);
		return true;
	}

	public Hero getHero () {
		for (LivingEntity e : livingEntities.values()) {
			if (e instanceof Hero h) {
				return h;
			}
		}
		return null;
	}

	public List<EntityController> getEntityControllers () {
		var controllers = new ArrayList<EntityController>();
		for (LivingEntity e : livingEntities.values()) {
			controllers.add(new EntityController(this, e));
		}
		return controllers;
	}

	public Point getStartPoint () {
		return new Point(startpoint.x, startpoint.y);
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
		var possibleDirections = new HashMap<Directions, Point>();
		var checkingPoint = new Point(position.x, position.y);
		checkingPoint.y--;
		var entity = staticEntites.get(checkingPoint);
		if (entity != null && !entity.isBlocking()) {
			possibleDirections.put(Directions.North, new Point(checkingPoint.x, checkingPoint.y));
		}
		checkingPoint.y++;
		checkingPoint.x++;
		entity = staticEntites.get(checkingPoint);
		if (entity != null && !entity.isBlocking()) {
			possibleDirections.put(Directions.East, new Point(checkingPoint.x, checkingPoint.y));
		}
		checkingPoint.x--;
		checkingPoint.y++;
		entity = staticEntites.get(checkingPoint);
		if (entity != null && !entity.isBlocking()) {
			possibleDirections.put(Directions.South, new Point(checkingPoint.x, checkingPoint.y));
		}
		checkingPoint.y--;
		checkingPoint.x--;
		entity = staticEntites.get(checkingPoint);
		if (entity != null && !entity.isBlocking()) {
			possibleDirections.put(Directions.West, new Point(checkingPoint.x, checkingPoint.y));
		}
		return possibleDirections;
	}

	/**
	 * 1. A mozgás történhet az EntityController-ben, érdemes lehet átnevezni EntityMovementController-ré
	 * A mozgáshoz nincs szükség a térkép ismeretére mostmár, mivel a pozíció a lények belső tulajdonsága
	 * A Level-től le lehet kérdezni az egy pozíció körüli szabad / járható pozíciókat,
	 * ami azt jelenti, hogy ezek alapján meg tudjuk mondani, érvényes-e egy lépés vagy sem
	 * Így végül a Level-nek nem kell felelnie a lények mozgatásáért
	 * A lények részei a Level-nek a livingEntities listában, ami alapján ezeket is ki lehet rajzolni a pálya rajzolásakor
	 * Végül el lehetne jutni arra, hogy a mozgás is tulajdonsága legyen a lényeknek, viszont ez túl bonyolulttá tehetné az osztályaikat
	 * A mozgás lehet annyira összetett folyamat, hogy az egész logika egy külön osztályba kerüljön,
	 * amelyhez lényt és pályát lehet rendelni, és felel a lény mozgásáért
	 * Akár a MovementController is lehetne a Level-ben tárolva a lények helyett, bár talán ez nem indokolt, elvégre csak a lényre van szükség ott
	 * A játékmenetért felelős osztályban lenne a legcélszerőbb tárolni a MovementController-eket, elévégre a Level is ott van
	 * Talán ezt az egészet össze lehetne foglalni egy LevelController osztályban
	 * Ezzel az eredeti elképzeléshez közeli működés valósítható meg, azaz a pálya és minden rajta lévő lény vezérlése közel lenne egymáshoz
	 * A kérdés viszont az lehet, hogy érdemes-e így tenni
	 * Rendezetté teheti a kódot a LevelController, viszont nem feltétlenül könnyítene bármin is
	 * Egy LevelEntityManager osztály viszont hasznos lehet, ha kialakul a lények teljes vezérlésének folyamata a Gameplay osztályban
	 * Hosszú folyamat lesz kitapasztalni a legoptimálisabb megoldást
	 * @param entity
	 * @param position
	 */
	public void moveEntity (LivingEntity entity, Point position) {

	}

	public void setEditing(boolean editing) {
		isEditing = editing;
	}

	public void placeEntity (int x, int y, Entity entity) {
		if (entity.isUnique()) {
			for (Map.Entry<Point, Entity> e : staticEntites.entrySet()) {
				if (e.getValue().getClass() == entity.getClass()) {
					staticEntites.put(e.getKey(), new Empty());
				}
			}
//			staticEntites.entrySet().removeIf(e -> e.getValue().getClass() == entity.getClass());
		}
		staticEntites.put(new Point(y, x), entity);
	}

	public void placeEntity (int x, int y, Entities entity) {
		staticEntites.put(new Point(y, x), entity.createNewInstance());
	}

	public void placeEntities (Point from, Point to, Entities type) {
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

	public boolean hasHeroRetrievedGold () {
		return false;
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
		while (entity instanceof Empty) {
			entity = staticEntites.get(point);
			if (entity instanceof Empty) {
				entity = livingEntities.get(point);
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
				var entity = staticEntites.get(new Point(j, i));
				if (entity.shouldExtendInCell()) {
					if (entity instanceof Wall w) {
						var c = switch (w.getShape()) {
							case Middle, Horizontal, TopRight, BottomRight, HorizontalBottom, HorizontalTop, VerticalLeft, Single -> WallShape.Horizontal.getSymbol();
							default -> ' ';
						};
						drawing.append(c).append(w.getDisplaySymbol());

						c = switch (w.getShape()) {
							case Middle, Horizontal, HorizontalBottom, HorizontalTop, BottomLeft, VerticalRight, TopLeft, Single -> WallShape.Horizontal.getSymbol();
							default -> ' ';
						};
						drawing.append(c);
					}
					else {
						char c = entity.getDisplaySymbol();
						drawing.append(c).append(c).append(c);
					}
				}
				else {
					var c = entity.getDisplaySymbol();
					if (isEditing && entity instanceof Empty) {
						c = '•';
					}
					drawing.append(' ').append(c).append(' ');
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
