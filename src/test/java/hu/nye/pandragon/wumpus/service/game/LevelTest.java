package hu.nye.pandragon.wumpus.service.game;

import hu.nye.pandragon.wumpus.model.Directions;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.model.entities.Pit;
import hu.nye.pandragon.wumpus.model.entities.Wall;
import hu.nye.pandragon.wumpus.model.entities.Wumpus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

class LevelTest {
	Level level;

	@BeforeEach
	public void setup () {
		level = new Level(9);
	}

	@Test
	public void shouldReturnEntityControllers () {
		var hero = new Hero();
		var wumpus = new Wumpus();
		level.placeEntity(2, 2, hero);
		level.placeEntity(4, 4, wumpus);
		var controllers = level.getEntityControllers();

		Assertions.assertEquals(controllers.size(), 1);
	}

	@Test
	public void shouldGetCorrectEntityCount () {
		var hero = new Hero();
		level.placeEntity(2, 2, hero);

		Assertions.assertEquals(level.getEntityCount(hero), 1);
	}

	@Test
	public void shouldGetCorrectEntityCount2 () {
		Assertions.assertEquals(level.getEntityCount(new Wall()), 32);
	}

	@Test
	public void shouldRemoveLivingEntity () {
		var hero = new Hero();
		level.placeEntity(2, 2, hero);
		var result = level.removeLivingEntity(2, 2);

		Assertions.assertSame(result, hero);
	}

	@Test
	public void shouldremoveLivingEntity2 () {
		var result = level.removeStaticEntity(4, 9);

		Assertions.assertEquals(result.getClass(), Wall.class);
	}

	@Test
	public void shouldRemoveIfExistsHero () {
		var hero = new Hero();
		level.placeEntity(2, 2, hero);
		Assertions.assertEquals(level.getEntityCount(hero), 1);
		var result = level.removeEntityIfExists(hero);
		Assertions.assertSame(result, hero);
	}

	@Test
	public void shouldRemoveIfExistsPit () {
		var pit = new Pit();
		level.placeEntity(2, 2, pit);
		Assertions.assertEquals(level.getEntityCount(pit), 1);
		var result = level.removeEntityIfExists(pit);
		Assertions.assertSame(result, pit);
	}

	@Test
	public void shouldRemoveIfExistsNull () {
		var pit = new Pit();
		level.placeEntity(2, 2, pit);
		Assertions.assertEquals(level.getEntityCount(pit), 1);
		var result = level.removeEntityIfExists(null);
		Assertions.assertNull(result);
	}

	@Test
	public void shouldGetStartPoint () {
		var hero = new Hero();
		level.placeEntity(2, 2, hero);
		var result = level.getStartPoint();

		Assertions.assertEquals(result, hero.getPosition());
	}

	@Test
	public void shouldGetCorrectSize () {
		var level2 = new Level(14);

		Assertions.assertEquals(level2.getSize(), 14);
	}

	@Test
	public void shouldUseLevelVOConstructorCorrectly () {
		var level2 = new Level(level.toLevelVO());

		Assertions.assertEquals(level2.getSize(), 9);
	}

	@Test
	public void shouldGetCorrectMaxWumpus () {
		Assertions.assertEquals(level.getMaxWumpus(), 2);
	}

	public int getPossibleMovesCount (int x, int y) {
		var hero = new Hero();
		level.placeEntity(x, y, hero);
		return level.getPossibleMoves(hero.getPosition()).size();
	}

	@Test
	public void shouldGetPossibleMoves1 () {
		Assertions.assertEquals(getPossibleMovesCount(2, 2), 2);
	}

	@Test
	public void shouldGetPossibleMoves2 () {
		Assertions.assertEquals(getPossibleMovesCount(8, 2), 2);
	}

	@Test
	public void shouldGetPossibleMoves3 () {
		Assertions.assertEquals(getPossibleMovesCount(8, 8), 2);
	}

	@Test
	public void shouldGetPossibleMoves4 () {
		Assertions.assertEquals(getPossibleMovesCount(2, 8), 2);
	}

	public Class getFirstEntityClassInDirection (Directions direction) {
		var level2 = new Level(14);
		var hero = new Hero();
		level2.placeEntity(2, 2, hero);
		hero.setDirection(direction);
		var result = level2.getFirstEntityInDirection(hero.getPosition(), hero.getDirection());
		return result.getClass();
	}

	@Test
	public void shouldGetFirstEntityInDirectionEast () {
		Assertions.assertEquals(getFirstEntityClassInDirection(Directions.East), Wall.class);
	}

	@Test
	public void shouldGetFirstEntityInDirectionSouth () {
		Assertions.assertEquals(getFirstEntityClassInDirection(Directions.South), Wall.class);
	}

	@Test
	public void shouldGetFirstEntityInDirectionWest () {
		Assertions.assertEquals(getFirstEntityClassInDirection(Directions.West), Wall.class);
	}

	@Test
	public void shouldGetEntityControllers () {
		Wumpus w1 = new Wumpus(),
				w2 = new Wumpus(),
				w3 = new Wumpus();

		level.placeEntity(2, 2, w1);
		level.placeEntity(3, 2, w2);
		level.placeEntity(4, 2, w3);

		var entityControllers = new HashSet<>(Arrays.asList(
				new EntityController(level, w1),
				new EntityController(level, w2),
				new EntityController(level, w3)
		));

		var result = new HashSet<>(level.getEntityControllers());
		Assertions.assertEquals(result, entityControllers);
	}
}