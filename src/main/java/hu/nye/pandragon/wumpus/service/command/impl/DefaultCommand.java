package hu.nye.pandragon.wumpus.service.command.impl;

import hu.nye.pandragon.wumpus.service.command.CommandMatcherResult;
import hu.nye.pandragon.wumpus.service.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * A default command, which should be run when no other {@link Command}
 * implementations were able to process the input.
 */
public class DefaultCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCommand.class);

    private static final String UNKNOWN_COMMAND_MESSAGE = "Unknown command";

    @Override
    public CommandMatcherResult match(String input) {
        return CommandMatcherResult.ofCorrectMatchingCommand();
    }

    @Override
    public void process(String input) {
        LOGGER.info("Performing default command");
        System.out.println(UNKNOWN_COMMAND_MESSAGE);
    }

}
