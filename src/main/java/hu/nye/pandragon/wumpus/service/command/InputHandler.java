package hu.nye.pandragon.wumpus.service.command;

import hu.nye.pandragon.wumpus.service.command.impl.DefaultCommand;

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
        input = input.trim().toLowerCase();
        for (Command command : commandList) {
            var result = command.match(input);
            if (result.isCommandMatches()) {
                var message = result.getMessage();
                if (message == null) {
                    command.process(input);
                }
                else {
                    System.out.println(message);
                }
                return;
            }
        }
        new DefaultCommand().process(input);
    }

}
