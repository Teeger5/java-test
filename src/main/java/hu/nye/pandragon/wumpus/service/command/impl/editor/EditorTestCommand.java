package hu.nye.pandragon.wumpus.service.command.impl.editor;

import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.model.PlayernameVO;
import hu.nye.pandragon.wumpus.service.command.CommandMatcherResult;
import hu.nye.pandragon.wumpus.service.command.Command;
import hu.nye.pandragon.wumpus.model.LevelEditorCommands;
import hu.nye.pandragon.wumpus.ui.GameplayScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	public CommandMatcherResult match(String input) {
		return LevelEditorCommands.Test.matches(input);
	}

	@Override
	public void process(String input) {
		LOGGER.info("Pálya tesztelésének indítása...");

		var gameplay = new GameplayScreen(levelVO, new PlayernameVO("Teszt"));
		gameplay.start();

		LOGGER.info("A játékos kilépett a pálya teszteléséből");
	}
}
