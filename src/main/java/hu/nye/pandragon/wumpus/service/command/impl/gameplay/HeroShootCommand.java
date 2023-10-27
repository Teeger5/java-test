package hu.nye.pandragon.wumpus.service.command.impl.gameplay;

import hu.nye.pandragon.wumpus.lovel.EntityController;
import hu.nye.pandragon.wumpus.lovel.Level;
import hu.nye.pandragon.wumpus.service.command.CommandMatcherResult;
import hu.nye.pandragon.wumpus.service.command.Command;
import hu.nye.pandragon.wumpus.service.command.GameplayCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Command used to write a number to a given field of the map.
 */
public class HeroShootCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(HeroShootCommand.class);
	private final Level level;

	public HeroShootCommand(Level level) {
		this.level = level;
	}

	@Override
	public CommandMatcherResult match(String input) {
		return GameplayCommands.Shoot.matches(input);
	}

	@Override
	public void process(String input) {
		LOGGER.info("A hős lő");
		var hero = level.getHero();
		var controller = new EntityController(level, hero);
		if (hero.getAmmoAmount() < 1) {
			LOGGER.error("A hősnek nincs nyila");
			throw new RuntimeException("A hősnek nincs nyila");
		}
		controller.shoot();
		LOGGER.info("Hős lőtt {} irányban, {} nyila maradt", hero.getDirection(), hero.getAmmoAmount());
	}
}
