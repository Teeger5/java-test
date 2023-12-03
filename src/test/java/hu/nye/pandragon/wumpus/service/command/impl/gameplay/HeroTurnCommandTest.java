package hu.nye.pandragon.wumpus.service.command.impl.gameplay;

import hu.nye.pandragon.wumpus.model.Directions;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.service.game.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HeroTurnCommandTest {

	private static String COMMAND_RIGHT = "fordul jobbra";
	private static String COMMAND_LEFT = "fordul balra";

	private HeroTurnCommand heroTurnCommand;
	private Level level;
	private Hero hero;

	@BeforeEach
	public void setup () {
		level = new Level(4);
		hero = new Hero();
		hero.setAmmoAmount(3);
		level.placeEntity(3, 3, hero);
		heroTurnCommand = new HeroTurnCommand(level);
	}

	@Test
	public void shouldMatchCommand () {
		Assertions.assertTrue(heroTurnCommand.match(COMMAND_LEFT).isCommandMatches());
	}

	@Test
	public void shouldNotMatchCommand () {
		Assertions.assertNotNull(heroTurnCommand.match("fordul Doragon raidaa desu ka").getMessage());
	}

	@Test
	public void shouldTurnRight () {
		heroTurnCommand.process(COMMAND_RIGHT);
		Assertions.assertEquals(Directions.East, hero.getDirection());
	}

	@Test
	public void shouldTurnLeft () {
		heroTurnCommand.process(COMMAND_LEFT);
		Assertions.assertEquals(Directions.West, hero.getDirection());
	}
}