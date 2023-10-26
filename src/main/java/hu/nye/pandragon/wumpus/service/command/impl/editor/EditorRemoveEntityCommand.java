package hu.nye.pandragon.wumpus.service.command.impl.editor;

import hu.nye.pandragon.wumpus.lovel.EntityController;
import hu.nye.pandragon.wumpus.lovel.Level;
import hu.nye.pandragon.wumpus.lovel.entities.Hero;
import hu.nye.pandragon.wumpus.model.TurnDirections;
import hu.nye.pandragon.wumpus.service.command.CanProcessResult;
import hu.nye.pandragon.wumpus.service.command.Command;
import hu.nye.pandragon.wumpus.service.command.LevelEditorCommands;
import hu.nye.pandragon.wumpus.service.util.CommandUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Ez a parancs a hős elforgatására szolgál
 */
public class EditorRemoveEntityCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(EditorRemoveEntityCommand.class);
	private final Level level;

	public EditorRemoveEntityCommand(Level level) {
		this.level = level;
	}

	@Override
	public Optional<CanProcessResult> canProcess(String input) {
		return LevelEditorCommands.Remove.matches(input);
	}

	@Override
	public void process(String input) {
		var args = Command.getCommandArgs(input);
		LOGGER.info("Pályaelem eltávolítása: {} {}", args[0], args[1]);

		var position = CommandUtils.getCoordinates(args[0], args[1], level.getSize());
		var entity = level.removeEntity(position.x, position.y);
		if (entity == null) {
			LOGGER.warn("Nem lett eltávolítva pályaelem, a megadott helyen nincs");
			throw new RuntimeException("Az a pozíció üres");
		}

		LOGGER.info("Pályaelem eltávolítva: {} -> {} {}", entity.getName(), position.x, position.y);
//		LevelPrinter.printEditorLevel(level.toLevelVO());
	}
}
