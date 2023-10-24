package hu.nye.pandragon.wumpus.service.command.impl;

import hu.nye.pandragon.wumpus.model.GameState;
import hu.nye.pandragon.wumpus.service.command.Command;
import hu.nye.pandragon.wumpus.ui.MapPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Command used to request the printing of the current state
 * of the game map.
 */
public class PrintCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintCommand.class);

    private static final String PRINT_COMMAND = "print";

    private final GameState gameState;
    private final MapPrinter mapPrinter;

    public PrintCommand(GameState gameState, MapPrinter mapPrinter) {
        this.gameState = gameState;
        this.mapPrinter = mapPrinter;
    }

    @Override
    public boolean canProcess(String input) {
        return PRINT_COMMAND.equals(input);
    }

    @Override
    public void process(String input) {
        LOGGER.info("Performing print command");
        mapPrinter.printMap(gameState.getCurrentMap());
    }

}
