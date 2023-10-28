package hu.nye.pandragon.wumpus.service.command.impl;

import hu.nye.pandragon.wumpus.service.command.Command;
import hu.nye.pandragon.wumpus.service.command.CommandMatcherResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A default command, which should be run when no other {@link Command}
 * implementations were able to process the input.
 */
public class DefaultCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCommand.class);

    private static final String UNKNOWN_COMMAND_MESSAGE = "Ismeretlen parancs: ";

    @Override
    public CommandMatcherResult match(String input) {
        return CommandMatcherResult.ofCorrectMatchingCommand();
    }

    @Override
    public void process(String input) {
        LOGGER.info("Ismeretlen parancs: " + input);
        System.out.println(UNKNOWN_COMMAND_MESSAGE + input);
    }

}
