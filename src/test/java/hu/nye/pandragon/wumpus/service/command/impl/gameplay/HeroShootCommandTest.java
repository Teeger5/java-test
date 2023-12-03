package hu.nye.pandragon.wumpus.service.command.impl.gameplay;

import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.model.entities.Wumpus;
import hu.nye.pandragon.wumpus.service.game.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HeroShootCommandTest {

	private static String COMMAND = "lő";

	private HeroShootCommand heroShootCommand;
	private Level level;
	private Hero hero;

	@BeforeEach
	public void setup () {
		level = new Level(4);
		hero = new Hero();
		hero.setAmmoAmount(3);
		level.placeEntity(3, 3, hero);
		heroShootCommand = new HeroShootCommand(level);
	}

	@Test
	public void shouldMatchCommand () {
		Assertions.assertTrue(heroShootCommand.match(COMMAND).isCommandMatches());
	}

	@Test
	public void shouldNotMatchCommand () {
		Assertions.assertFalse(heroShootCommand.match("Doragon raidaa desu ka").isCommandMatches());
	}

	@Test
	public void shouldHitWumpus () {
		var wumpus = new Wumpus();
		level.placeEntity(3, 2, wumpus);
		heroShootCommand.process(COMMAND);

		Assertions.assertFalse(wumpus.isAlive());
	}

	@Test
	public void shouldThrowExceptionWhenHeroOutOfAmmo () {
		hero.setAmmoAmount(0);
		Assertions.assertThrows(RuntimeException.class, () -> heroShootCommand.process(COMMAND));
	}
}