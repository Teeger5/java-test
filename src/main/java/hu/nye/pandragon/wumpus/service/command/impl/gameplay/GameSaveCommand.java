package hu.nye.pandragon.wumpus.service.command.impl.gameplay;

import hu.nye.pandragon.wumpus.model.GameplayCommands;
import hu.nye.pandragon.wumpus.model.PlayernameVO;
import hu.nye.pandragon.wumpus.persistence.impl.JdbcGameStateRepository;
import hu.nye.pandragon.wumpus.service.command.Command;
import hu.nye.pandragon.wumpus.service.command.CommandMatcherResult;
import hu.nye.pandragon.wumpus.service.game.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ez a parancs a játék mentésére szolgál
 */
public class GameSaveCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameSaveCommand.class);
	private final Level level;
	private final PlayernameVO playername;

	public GameSaveCommand(PlayernameVO playername, Level level) {
		this.level = level;
		this.playername = playername;
	}

	@Override
	public CommandMatcherResult match(String input) {
		return GameplayCommands.SaveAndQuit.matches(input);
	}

	@Override
	public void process(String input) {
		LOGGER.info("Játékállás mentése {} játékosnak", playername);
//		try {
			var repository = new JdbcGameStateRepository();
			repository.save(playername, level.toLevelVO());
//		} catch (Exception e) {
//			var msg = "Hiba történt a játékállás mentésekor";
//			LOGGER.error("{}: {}", msg, e.getMessage());
//			throw new RuntimeException(e.getMessage());
//		}
		LOGGER.info("Játékállás mentve {} játékosnak", playername);
	}
}
