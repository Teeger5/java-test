package hu.nye.pandragon.wumpus.service.game;

import hu.nye.pandragon.wumpus.model.Directions;
import hu.nye.pandragon.wumpus.model.TurnDirections;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.model.entities.Pit;
import hu.nye.pandragon.wumpus.model.entities.Wall;
import hu.nye.pandragon.wumpus.model.entities.Wumpus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class EntityControllerTest {

	Hero hero;
	Level level;
	EntityController controller;

	@BeforeEach
	public void setup () {
		hero = new Hero();
		level = new Level(6);
		controller = new EntityController(level, hero);
	}

	@Test
	public void shouldMoveForwardNorth () {
		level.placeEntity(3, 3, hero);
		assertTrue(controller.moveForward());
	}

	@Test
	public void couldMoveForwardNorth () {
		var postitionFrom = hero.getPosition();
		level.placeEntity(3, 3, hero);
		controller.moveForward();
		var positionTo = hero.getPosition();
		assertEquals(hero.getPosition(), new Point(3, 2));
	}

	@Test
	public void shouldMoveForwardEast () {
		level.placeEntity(3, 3, hero);
		hero.setDirection(Directions.East);
		assertTrue(controller.moveForward());
	}

	@Test
	public void shouldMoveForwardSouth () {
		level.placeEntity(3, 3, hero);
		hero.setDirection(Directions.South);
		assertTrue(controller.moveForward());
	}

	@Test
	public void shouldMoveForwardWest () {
		level.placeEntity(3, 3, hero);
		hero.setDirection(Directions.West);
		assertTrue(controller.moveForward());
	}

	@Test
	public void shouldActionOnHeroEnters () {
		level.placeEntity(3, 3, hero);
		level.placeEntity(3, 2, new Pit());
		controller.moveForward();
		assertEquals(hero.getAmmoAmount(), 0);
	}

	@Test
	public void shouldNotMoveForward () {
		level.placeEntity(2, 2, hero);
		assertFalse(controller.moveForward());
	}

	@Test
	public void shouldTurnRight () {
		level.placeEntity(2, 2, hero);
		controller.turnRight();
		var result = hero.getDirection();
		assertEquals(Directions.East, result);
	}

	@Test
	public void shouldTurnRight2 () {
		level.placeEntity(2, 2, hero);
		controller.turn(TurnDirections.Right);
		assertEquals(hero.getDirection(), Directions.East);
	}

	@Test
	public void shouldTurnLeft2 () {
		level.placeEntity(2, 2, hero);
		controller.turn(TurnDirections.Left);
		assertEquals(hero.getDirection(), Directions.West);
	}

	@Test
	public void shouldTurnLeft () {
		level.placeEntity(2, 2, hero);
		controller.turnLeft();
		var result = hero.getDirection();
		assertEquals(Directions.West, result);
	}

	@Test
	public void shouldShootHitWumpus () {
		var wumpus = new Wumpus();
		level.placeEntity(2, 4, hero);
		level.placeEntity(2, 2, wumpus);
		controller.shoot();
		assertFalse(wumpus.isAlive());
	}

	@Test
	public void shouldShootNotHitWumpus () {
		var wumpus = new Wumpus();
		level.placeEntity(3, 4, hero);
		level.placeEntity(2, 2, wumpus);
		controller.shoot();
		assertTrue(wumpus.isAlive());
	}

	@Test
	public void shouldShootHitWall () {
		level.placeEntity(2, 4, hero);
		controller.shoot();
		assertEquals(level.getFirstEntityInDirection(hero.getPosition(), hero.getDirection()).getClass(), Wall.class);
	}

}