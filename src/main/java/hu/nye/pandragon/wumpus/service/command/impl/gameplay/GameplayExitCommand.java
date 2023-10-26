package hu.nye.pandragon.wumpus.service.command.impl.gameplay;

import hu.nye.pandragon.wumpus.service.command.CanProcessResult;
import hu.nye.pandragon.wumpus.service.command.Command;
import hu.nye.pandragon.wumpus.service.command.GameplayCommands;
import hu.nye.pandragon.wumpus.ui.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Command used to exit from the game.
 */
public class GameplayExitCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameplayExitCommand.class);


    private final Screen screen;

    public GameplayExitCommand(Screen screen) {
        this.screen = screen;
    }

    @Override
    public Optional<CanProcessResult> canProcess(String input) {
        return GameplayCommands.GiveUp.matches(input);
    }

    @Override
    public void process(String input) {
        LOGGER.info("Performing exit command");
        screen.setShouldExit(true);
    }

}
