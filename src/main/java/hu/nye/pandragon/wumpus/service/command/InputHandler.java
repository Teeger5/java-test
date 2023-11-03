package hu.nye.pandragon.wumpus.service.command;

import hu.nye.pandragon.wumpus.service.command.impl.DefaultCommand;
import hu.nye.pandragon.wumpus.ui.PrintWrapper;

import java.util.List;

/**
 * Component that handles a given input.
 */
public class InputHandler {

//    private final EnumMap<GameplayCommands, Command> commandList;
    private final List<Command> commandList;

    /**
     * Itt a parancsokat előre listába kell szedni egy olyan helyen, ahol elérhető a Level objektum,
     * mivel az kell hozzájuk, és itt nem lesz elérhető
     * @param commandList a parancsok listája, amit ez az InputHandler kezel
     */
    public InputHandler(List<Command> commandList) {
        this.commandList = commandList;
    }

    /**
     * A megadott {@link Command} objektumok listájából az első,
     * a bemenetre alkalmazható parancs lesz végrehajtva.
     * A bemenetet a metódus kisbetűssé alakítja, erre külön nincs szükség
     * @param input bemenet a felhasználótól.
     * @param printWrapper kiíratáshoz használt PrintWrapper
     */
    public void handleInput(String input, PrintWrapper printWrapper) {
        input = input.trim().toLowerCase();
        for (Command command : commandList) {
            var result = command.match(input);
            if (result.isCommandMatches()) {
                var message = result.getMessage();
                if (message == null) {
                    command.process(input);
                }
                else {
                    printWrapper.println(message);
                }
                return;
            }
        }
        new DefaultCommand().process(input);
    }
}
