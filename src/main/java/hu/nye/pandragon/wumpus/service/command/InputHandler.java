package hu.nye.pandragon.wumpus.service.command;

import hu.nye.pandragon.wumpus.service.command.impl.gameplay.DefaultCommand;

import java.util.EnumMap;
import java.util.List;

/**
 * Component that handles a given input.
 */
public class InputHandler {

//    private final EnumMap<GameplayCommands, Command> commandList;
    private final List<Command> commandList;

    /**
     * Itt a parancsokat előre listába kell szedni egyolyan helyen, ahol elérhető a Level objektum,
     * mivel az kell hozzájuk, és itt nem lesz elérhető
     * @param commandList
     */
    public InputHandler(List<Command> commandList) {
        this.commandList = commandList;
    }

    /**
     * A megadott {@link Command} objektumok listájából az első,
     * a bemenetre alkalmazható parancs lesz végrehajtva.
     * A bemenetet a metódus kisbetűssé alakítja, erre külön nincs szükség
     * @param input bemenet a felhasználótól.
     */
    public void handleInput(String input) {
        for (Command command : commandList) {
            var result = command.canProcess(input);
            if (result.isPresent()) {
                var content = result.get();
                if (content.canProcess()) {
                    command.process(input);
                    break;
                }
            }
        }
        new DefaultCommand().process(input);
    }

}
