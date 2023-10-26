package hu.nye.pandragon.wumpus.service.command.impl.editor;

import hu.nye.pandragon.wumpus.lovel.Entities;
import hu.nye.pandragon.wumpus.lovel.EntityController;
import hu.nye.pandragon.wumpus.lovel.Level;
import hu.nye.pandragon.wumpus.lovel.entities.Hero;
import hu.nye.pandragon.wumpus.lovel.entities.LivingEntity;
import hu.nye.pandragon.wumpus.lovel.entities.Wumpus;
import hu.nye.pandragon.wumpus.service.command.CanProcessResult;
import hu.nye.pandragon.wumpus.service.command.Command;
import hu.nye.pandragon.wumpus.service.command.GameplayCommands;
import hu.nye.pandragon.wumpus.service.command.LevelEditorCommands;
import hu.nye.pandragon.wumpus.ui.LevelPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Command used to write a number to a given field of the map.
 */
public class LevelPlaceEntityCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(LevelPlaceEntityCommand.class);
	private final Level level;

	public LevelPlaceEntityCommand(Level level) {
		this.level = level;
	}

	@Override
	public Optional<CanProcessResult> canProcess(String input) {
		return LevelEditorCommands.Place.matches(input);
	}

	@Override
	public void process(String input) {
		LOGGER.info("A előre lép egyet");
		var args = Command.getCommandArgs(input);
		var entity = Entities.parse(args[0]).createNewInstance();
		if (entity instanceof Wumpus && level.getEntityCount(entity) >= level.getMaxWumpus()) {
			var error = String.format("Wumpusból max %d lehet", level.getMaxWumpus());
			LOGGER.error(error);
			throw new RuntimeException(error);
		}

		int x = args[1].toCharArray()[0] - 96;
		if (x < 1 || x > level.getSize()) {
			LOGGER.error("Nincs ilyen azonosítójú osztlop: {} ({})", x, args[1]);
			throw new RuntimeException("Nincs ilyen azonosítójú oszlop: " + args[1]);
		}

		int y = Integer.parseInt(args[2]);
		if (y < 1 || y > level.getSize()) {
			var error = "Nincs ilyen azonosítójú sor: " + args[2];
			LOGGER.error(error);
			throw new RuntimeException(error);
		}

		if (isOnSide(x, level.getSize()) || isOnSide(y, level.getSize())) {
			LOGGER.error("placeEntity a pálya szélén");
			throw new RuntimeException("Nem lehet pályaelemeket a pálya szélére tenni");
		}
		level.placeEntity(x, y, entity);
		LOGGER.info("Új pályaelem: {} -> {}, {}", entity.getName(), x, y);
		LevelPrinter.printEditorLevel(level.toLevelVO());

	}

	private boolean isOnSide (int n, int size) {
		return n < 1 || n > size;
	}
}
