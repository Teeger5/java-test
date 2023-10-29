package hu.nye.pandragon.wumpus.service.game;

import hu.nye.pandragon.wumpus.model.Directions;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.model.entities.Wall;
import hu.nye.pandragon.wumpus.model.entities.Wumpus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

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

		assertEquals(controllers.size(), 1);
	}

	@Test
	public void shouldGetCorrectEntityCount () {
		var hero = new Hero();
		level.placeEntity(2, 2, hero);

		assertEquals(level.getEntityCount(hero), 1);
	}

	@Test
	public void shouldGetCorrectEntityCount2 () {
		assertEquals(level.getEntityCount(new Wall()), 32);
	}

	@Test
	public void shouldRemoveEntity () {
		var hero = new Hero();
		level.placeEntity(2, 2, hero);
		var result = level.removeEntity(2, 2);

		assertSame(result, hero);
	}

	@Test
	public void shouldRemoveEntity2 () {
		var result = level.removeEntity(4, 9);

		assertEquals(result.getClass(), Wall.class);
	}

	@Test
	public void shouldGetStartPoint () {
		var hero = new Hero();
		level.placeEntity(2, 2, hero);
		var result = level.getStartPoint();

		assertEquals(result, hero.getPosition());
	}

	@Test
	public void shouldGetCorrectSize () {
		var level2 = new Level(14);

		assertEquals(level2.getSize(), 14);
	}

	@Test
	public void shouldUseLevelVOConstructorCorrectly () {
		var level2 = new Level(level.toLevelVO());

		assertEquals(level2.getSize(), 9);
	}

	@Test
	public void shouldGetCorrectMaxWumpus () {
		assertEquals(level.getMaxWumpus(), 2);
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
		assertEquals(getFirstEntityClassInDirection(Directions.East), Wall.class);
	}

	@Test
	public void shouldGetFirstEntityInDirectionSouth () {
		assertEquals(getFirstEntityClassInDirection(Directions.South), Wall.class);
	}

	@Test
	public void shouldGetFirstEntityInDirectionWest () {
		assertEquals(getFirstEntityClassInDirection(Directions.West), Wall.class);
	}
}