package hu.nye.pandragon.wumpus.service.command.impl;

import hu.nye.pandragon.wumpus.service.command.CanProcessResult;
import hu.nye.pandragon.wumpus.service.command.Command;
import hu.nye.pandragon.wumpus.service.command.LevelEditorCommands;
import hu.nye.pandragon.wumpus.ui.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Ez a parancs egy képernyőből való kilépésre szolgál
 * Megszakítja az aktuálisat, és visszalép az előzőbe
 */
public class EditorExitCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(EditorExitCommand.class);

    private final Screen screen;

    public EditorExitCommand(Screen screen) {
        this.screen = screen;
    }

    @Override
    public Optional<CanProcessResult> canProcess(String input) {
        return LevelEditorCommands.Exit.matches(input);
    }

    @Override
    public void process(String input) {
        LOGGER.info("Performing exit command");
        screen.setShouldExit(true);
    }

}
