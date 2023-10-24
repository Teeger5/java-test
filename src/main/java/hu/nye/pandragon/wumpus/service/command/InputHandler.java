package hu.nye.pandragon.wumpus.service.command;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Component that handles a given input.
 */
public class InputHandler {

    private final EnumMap<HeroCommands, Command> commandList;

    /**
     * Itt a parancsokat előre listába kell szedni egyolyan helyen, ahol elérhető a Level objektum,
     * mivel az kell hozzájuk, és itt nem lesz elérhető
     * @param commandList
     */
    public InputHandler(List<Command> commandList) {
        this.commandList = commandList;
    }

    /**
     * Handles an input through a list of {@link Command}s.
     *
     * Only the first applicable command will be run.
     *
     * @param input the input as a string to be handled
     */
    public void handleInput(String input) {
        for (HeroCommands command : commandList.keySet()) {
            var result = command.matches(input);
            if (result.isPresent()) {
                var content = result.get();
                if (content.canProcess()) {
                    commandList.get(command).process(input);
                    break;
                }
            }
        }
    }

}
