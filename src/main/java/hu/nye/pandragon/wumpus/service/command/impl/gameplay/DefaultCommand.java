package hu.nye.pandragon.wumpus.service.command.impl.gameplay;

import hu.nye.pandragon.wumpus.service.command.CanProcessResult;
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
    public Optional<CanProcessResult> canProcess(String input) {
        return Optional.of(new CanProcessResult());
    }

    @Override
    public void process(String input) {
        LOGGER.info("Performing default command");
        System.out.println(UNKNOWN_COMMAND_MESSAGE);
    }

}
