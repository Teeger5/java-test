package hu.nye.pandragon.wumpus.service.command.impl.gameplay;

import hu.nye.pandragon.wumpus.model.entities.Gold;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.service.game.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HeroPickUpCommandTest {
	static String PICKUP_COMMAND = "aranyat felszed";

	HeroPickUpCommand heroPickUpCommand;

	Level level;
	Hero hero;

	@BeforeEach
	public void setup () {
		level = new Level(4);
		hero = new Hero();
		heroPickUpCommand = new HeroPickUpCommand(level);
		level.placeEntity(2, 2, hero);
	}

	@Test
	public void shouldMatchCommand () {
		Assertions.assertTrue(heroPickUpCommand.match(PICKUP_COMMAND).isCommandMatches());
	}

	@Test
public void shouldNotMatchCommand () {
		Assertions.assertFalse(heroPickUpCommand.match("Tokidoki geemu o shimasu").isCommandMatches());
}
	@Test
	public void shouldThrowExceptionNoItem () {
		Assertions.assertThrows(RuntimeException.class, () -> heroPickUpCommand.process(PICKUP_COMMAND));
	}

	@Test
	public void shouldPickUpItem () {
		level.placeEntity(2, 2, new Gold());
		heroPickUpCommand.process(PICKUP_COMMAND);
		Assertions.assertNull(level.toLevelVO().getStaticEntities().get(hero.getPosition()));
	}
}