package hu.nye.pandragon.wumpus.service.command.impl.gameplay;

import hu.nye.pandragon.wumpus.model.GameplayCommands;
import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.model.PlayernameVO;
import hu.nye.pandragon.wumpus.persistence.impl.JdbcGameStateRepository;
import hu.nye.pandragon.wumpus.service.command.Command;
import hu.nye.pandragon.wumpus.service.command.CommandMatcherResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * Ez a parancs a játék mentésére szolgál
 */
public class GameSaveCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameSaveCommand.class);
	private final LevelVO levelVO;
	private final PlayernameVO playername;

	public GameSaveCommand(PlayernameVO playername, LevelVO levelVO) {
		this.levelVO = levelVO;
		this.playername = playername;
	}

	@Override
	public CommandMatcherResult match(String input) {
		return GameplayCommands.SaveAndQuit.matches(input);
	}

	@Override
	public void process(String input) {
		LOGGER.info("Játékállás mentése");
		try {
			var repository = new JdbcGameStateRepository();
			repository.save(playername, levelVO);
		} catch (SQLException e) {
			var msg = "Hiba történt a játékállás mentésekor";
			LOGGER.error("{}: {}", msg, e.getMessage());
			throw new RuntimeException(msg);
		}
		LOGGER.info("Játékállás mentve");
	}
}
