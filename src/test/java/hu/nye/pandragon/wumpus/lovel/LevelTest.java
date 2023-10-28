package hu.nye.pandragon.wumpus.lovel;

import hu.nye.pandragon.wumpus.lovel.entities.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hu.nye.pandragon.wumpus.lovel.entities.Hero;
import hu.nye.pandragon.wumpus.lovel.entities.Wumpus;
import org.mockito.Mockito;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {
	@BeforeEach
	public void setup () {

	}

	@Test
	public void testPlaceEntityValidCoordinates() {
		// Arrange
		Level level = new Level(10);
		Hero hero = Mockito.mock(Hero.class);
		Wumpus wumpus = Mockito.mock(Wumpus.class);

		// Act
		Entity placedHero = level.placeEntity(2, 2, hero);
		Entity placedWumpus = level.placeEntity(3, 3, wumpus);

		// Assert
		assertEquals(hero, placedHero);
		assertEquals(wumpus, placedWumpus);
		assertEquals(hero, level.getEntityAt(2, 2));
		assertEquals(wumpus, level.getEntityAt(3, 3));
		assertEquals(new Point(2, 2), hero.getPosition());
		assertEquals(new Point(3, 3), wumpus.getPosition());
	}

	@Test
	public void testPlaceEntityInvalidCoordinates() {
		// Arrange
		Level level = new Level(10);
		Hero hero = Mockito.mock(Hero.class);

		// Act and Assert
		assertThrows(IllegalArgumentException.class, () -> level.placeEntity(0, 0, hero));
		assertThrows(IllegalArgumentException.class, () -> level.placeEntity(11, 11, hero));
	}

	@Test
	public void testPlaceEntityOverwriteExisting() {
		// Arrange
		Level level = new Level(10);
		Hero hero = Mockito.mock(Hero.class);
		Wumpus wumpus = Mockito.mock(Wumpus.class);
		Gold gold = Mockito.mock(Gold.class);
		level.placeEntity(2, 2, hero);
		level.placeEntity(3, 3, wumpus);

		// Act
		Entity placedGold1 = level.placeEntity(2, 2, gold);
		Entity placedGold2 = level.placeEntity(3, 3, gold);

		// Assert
		assertEquals(gold, placedGold1);
		assertEquals(gold, placedGold2);
		assertEquals(gold, level.getEntityAt(2, 2));
		assertEquals(gold, level.getEntityAt(3, 3));
	}


}