package hu.nye.pandragon.wumpus.service.command.impl.editor;

import hu.nye.pandragon.wumpus.lovel.Entities;
import hu.nye.pandragon.wumpus.lovel.EntityController;
import hu.nye.pandragon.wumpus.lovel.Level;
import hu.nye.pandragon.wumpus.lovel.entities.Hero;
import hu.nye.pandragon.wumpus.lovel.entities.Wumpus;
import hu.nye.pandragon.wumpus.model.TurnDirections;
import hu.nye.pandragon.wumpus.service.command.CanProcessResult;
import hu.nye.pandragon.wumpus.service.command.Command;
import hu.nye.pandragon.wumpus.service.command.LevelEditorCommands;
import hu.nye.pandragon.wumpus.ui.LevelPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Ez a parancs a hős elforgatására szolgál
 */
public class EditorRotateCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(EditorRotateCommand.class);
	private final Hero hero;

	public EditorRotateCommand(Hero hero) {
		this.hero = hero;
	}

	@Override
	public Optional<CanProcessResult> canProcess(String input) {
		return LevelEditorCommands.RotateHero.matches(input);
	}

	@Override
	public void process(String input) {
		LOGGER.info("Hős elforgatása");
		var args = Command.getCommandArgs(input);
		var direction = TurnDirections.parse(args[0]);
		var controller = new EntityController(null, hero);
		controller.turn(direction);
		LOGGER.info("Hős elforgatva " + direction.name());
//		LevelPrinter.printEditorLevel(level.toLevelVO());
	}
}
