package hu.nye.pandragon.wumpus.service.command.impl.gameplay;

import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.service.game.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

class HeroMoveCommandTest {

	private static String COMMAND = "lép";

	private HeroMoveCommand heroMoveCommand;
	private Level level;
	private Hero hero;

	@BeforeEach
	public void setup () {
		level = new Level(4);
		hero = new Hero();
		hero.setAmmoAmount(3);
		level.placeEntity(3, 3, hero);
		heroMoveCommand = new HeroMoveCommand(level);
	}

	@Test
	public void shouldMatchCommand () {
		Assertions.assertTrue(heroMoveCommand.match(COMMAND).isCommandMatches());
	}

	@Test
	public void shouldNotMatchCommand () {
		Assertions.assertFalse(heroMoveCommand.match("").isCommandMatches());
	}

	@Test
	public void shouldMove () {
		var expected = new Point(3, 2);
		heroMoveCommand.process(COMMAND);
		var result = hero.getPosition();
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void shouldNotMove () {
		level.placeEntity(3, 2, hero);
		Assertions.assertThrows(RuntimeException.class, () -> heroMoveCommand.process(COMMAND));
	}
}