package hu.nye.pandragon.wumpus.service.command.impl.editor;

import hu.nye.pandragon.wumpus.lovel.Entities;
import hu.nye.pandragon.wumpus.lovel.Level;
import hu.nye.pandragon.wumpus.lovel.entities.Wumpus;
import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.service.command.CanProcessResult;
import hu.nye.pandragon.wumpus.service.command.Command;
import hu.nye.pandragon.wumpus.service.command.LevelEditorCommands;
import hu.nye.pandragon.wumpus.service.util.CommandUtils;
import hu.nye.pandragon.wumpus.ui.GameplayScreen;
import hu.nye.pandragon.wumpus.ui.LevelPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Command used to write a number to a given field of the map.
 */
public class EditorTestCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(EditorTestCommand.class);
	private final LevelVO levelVO;

	public EditorTestCommand(LevelVO levelVO) {
		this.levelVO = levelVO;
	}

	@Override
	public Optional<CanProcessResult> canProcess(String input) {
		return LevelEditorCommands.Test.matches(input);
	}

	@Override
	public void process(String input) {
		LOGGER.info("Pálya tesztelésének indítása...");

		var gameplay = new GameplayScreen(levelVO, "Teszt");
		gameplay.start();

		LOGGER.info("Új pályaelem: {} -> {}, {}");
	}
}
