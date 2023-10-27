package hu.nye.pandragon.wumpus.service.command.impl.editor;

import hu.nye.pandragon.wumpus.lovel.Level;
import hu.nye.pandragon.wumpus.model.Directions;
import hu.nye.pandragon.wumpus.service.command.CommandMatcherResult;
import hu.nye.pandragon.wumpus.service.command.Command;
import hu.nye.pandragon.wumpus.service.command.LevelEditorCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Ez a parancs a hős elforgatására szolgál
 */
public class EditorRotateCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(EditorRotateCommand.class);
	private final Level level;

	public EditorRotateCommand(Level level) {
		this.level = level;
	}

	@Override
	public CommandMatcherResult match(String input) {
		return LevelEditorCommands.RotateHero.matches(input);
	}

	@Override
	public void process(String input) {
		LOGGER.info("Hős elforgatása");
		var args = Command.getCommandArgs(2, input);
		var direction = Directions.parseSymbol(args[0].toUpperCase().toCharArray()[0]);
		var hero = level.getHero();
		hero.setDirection(direction);
		LOGGER.info("Hős elforgatva " + direction.name());
	}
}
