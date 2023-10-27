package hu.nye.pandragon.wumpus.service.command.impl.menu;

import hu.nye.pandragon.wumpus.service.command.CommandMatcherResult;
import hu.nye.pandragon.wumpus.service.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Ez a parancs a játékból való kilépésre szolgál
 * Érdemes lehet átírni a főmenü parancsait is ilyenekké,
 * de nem feltétlenül muszáj, ha bonyolult
 */
public class GameMenuExitCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameMenuExitCommand.class);

    @Override
    public CommandMatcherResult match(String input) {
        return CommandMatcherResult.ofNotMatchingCommand();
    }

    @Override
    public void process(String input) {
        LOGGER.info("Kilépés...");
        System.exit(0);
    }

}
