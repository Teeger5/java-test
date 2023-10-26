package hu.nye.pandragon.wumpus.service.command.impl.gameplay;

import hu.nye.pandragon.wumpus.lovel.EntityController;
import hu.nye.pandragon.wumpus.lovel.Level;
import hu.nye.pandragon.wumpus.service.command.CanProcessResult;
import hu.nye.pandragon.wumpus.service.command.Command;
import hu.nye.pandragon.wumpus.service.command.GameplayCommands;
import hu.nye.pandragon.wumpus.ui.LevelPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Command used to write a number to a given field of the map.
 */
public class HeroMoveCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(HeroMoveCommand.class);
	private final Level level;

	public HeroMoveCommand(Level level) {
		this.level = level;
	}

	@Override
	public Optional<CanProcessResult> canProcess(String input) {
		return GameplayCommands.MoveForward.matches(input);
	}

	@Override
	public void process(String input) {
		LOGGER.info("A előre lép egyet");
		var hero = level.getHero();
		var controller = new EntityController(level, hero);
//		if (controller.canMoveForward() && ) {}
		if (!controller.moveForward()) {
			LOGGER.warn("Nem lehet lépni ebben az irányban");
			throw new RuntimeException("Nem lehet lépni ebben az irányban");
		}
		LOGGER.info("A hős új pozíciója: " + hero.getPosition());
	}
}
