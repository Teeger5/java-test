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
	private final LevelPrinter levelPrinter;

	public HeroMoveCommand(Level level, LevelPrinter levelPrinter) {
		this.level = level;
		this.levelPrinter = levelPrinter;
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
		controller.moveForward();
		LOGGER.info("A hős új pozíciója: " + hero.getPosition());
		LevelPrinter.printGameLevel(level.toLevelVO(), hero);
	}
}
