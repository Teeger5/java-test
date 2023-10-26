package hu.nye.pandragon.wumpus.service.game;

import hu.nye.pandragon.wumpus.service.command.InputHandler;
import hu.nye.pandragon.wumpus.service.input.UserInputReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ez az osztály a vezérlésben a következő helyet foglalhatja el:
 * GameMain -> Játék (konstruktorban a használható parancsok listájával egy inputHandler létrehozva és átadva ide) -> ez az osztály
 * Ez egy működőképes elképzelésnek tűnik
 * Kiderül, tényleg megvalósítható-e így egyszerűen
 * Egy hasonló osztály tartozhatna a pályaszerkesztőhöz is a parancsok feldolgozásához
 */
public class GameStepPerformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameStepPerformer.class);

    private final UserInputReader userInputReader;
    private final InputHandler inputHandler;

    public GameStepPerformer(UserInputReader userInputReader, InputHandler inputHandler) {
        this.userInputReader = userInputReader;
        this.inputHandler = inputHandler;
    }

    /**
     * Performs a game step.
     *
     * A game step consists of taking the input from the user, then handling
     * the input.
     */
    public void performGameStep() {
        String input = userInputReader.readInput();
        LOGGER.info("Read user input = '{}'", input);
        inputHandler.handleInput(input);
    }

}
