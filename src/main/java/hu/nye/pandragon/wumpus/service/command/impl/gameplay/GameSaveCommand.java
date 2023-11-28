package hu.nye.pandragon.wumpus.service.command.impl.gameplay;

import hu.nye.pandragon.wumpus.model.GameplayCommands;
import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.model.PlayernameVO;
import hu.nye.pandragon.wumpus.persistence.impl.JdbcGameStateRepository;
import hu.nye.pandragon.wumpus.service.command.Command;
import hu.nye.pandragon.wumpus.service.command.CommandMatcherResult;
import hu.nye.pandragon.wumpus.service.game.Level;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Ez a parancs a játék mentésére szolgál
 */
@Slf4j
public class GameSaveCommand implements Command {
	private final LevelVO level;
	private final PlayernameVO playername;

	public GameSaveCommand(PlayernameVO playername, Level level, AtomicInteger steps) {
		log.debug("Eddig megtett lépések száma: " + steps.get());
		this.level = level.toLevelVO(steps.get());
		this.playername = playername;
	}

	@Override
	public CommandMatcherResult match(String input) {
		return GameplayCommands.SaveAndQuit.matches(input);
	}

	@Override
	public void process(String input) {
		log.info("Játékállás mentése {} játékosnak", playername);
		try {
			var repository = new JdbcGameStateRepository();
			repository.save(playername, level);
		} catch (Exception e) {
			var msg = "Hiba történt a játékállás mentésekor";
			log.error("{}: {}", msg, e.getMessage());
			throw e instanceof RuntimeException runtimeException ?
					runtimeException : new RuntimeException(msg);
		}
		log.info("Játékállás mentve {} játékosnak", playername);
	}
}
