package hu.nye.pandragon.wumpus.ui;

import hu.nye.pandragon.wumpus.model.LevelEditorCommands;
import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.model.Screen;
import hu.nye.pandragon.wumpus.service.command.InputHandler;
import hu.nye.pandragon.wumpus.service.command.impl.editor.*;
import hu.nye.pandragon.wumpus.service.game.Level;

import java.util.Arrays;

/**
 * Ez az osztály a pályaszerkesztőt írja le, és működteti
 */
public class LevelEditorScreen extends Screen {

	private Level level;
	private InputHandler inputHandler;

	public LevelEditorScreen() {
		printWrapper.println("Pályaszerkesztő");
	}

	/**
	 * Inicializálja a pályaszerkesztőt
	 * Azaz bekéri a pálya méretét, és létrehozza a példányt belőle
	 */
	public void init () {
		var size = requestMapSize();
		level = new Level(size);
		levelPrinter.setLevel(level);
		inputHandler = new InputHandler(Arrays.asList(
				new EditorPlaceEntityCommand(level),
				new EditorExitCommand(this),
				new EditorRotateCommand(level),
				new EditorRemoveEntityCommand(level),
				new EditorTestCommand(level)
		));
		inputHandler.setPrintWrapper(printWrapper);
		level.setEditing(true);
		if (size == -1) {
			printWrapper.println("Kilépés a pályaszerkesztőből...");
			shouldExit = true;
		}
	}

	public void start () {
		if (level == null) {
			init();
		}
		if (!shouldExit) {
			readCommands();
		}
	}

	/**
	 * Ez a metódus bekéri a pálya méretét a felhasználótól,
	 * majd visszaadja egész száámmá alakítva
	 * Csak 6 - 20 közötti értéket fogad el
	 * @return a pálya mérete
	 */
	private int requestMapSize () {
		while (true) {
			printWrapper.print("Add meg a pálya oldalhosszát (-1 = kilépés a pályaszerkesztőből): ");
			var value = consoleInputWrapper.readFromConsole();
			try {
				var i = Integer.parseInt(value);
				if ((i < 6 || i > 20) && i != -1) {
					printWrapper.println("A pálya mérete min 6, max 20 egység lehet");
				}
				else {
					return i;
				}
			}
			catch (NumberFormatException e) {
				printWrapper.println("A pálya hosszát számjegy karakterekkel kell megadni!");
			}
		}
	}

	/**
	 * Ez a metódus fogadja a parancsokat a felhasználótól
	 * Egy végtelen while ciklus fut benne, amit a kilépés parancsa szakít meg
	 */
	protected void readCommands () {
		printWrapper.println(LevelEditorCommands.getMenuText());
		var messageFromProcessing = "Próbáld ki az egyik parancsot";
		while (true) {
			if (shouldExit) {
				if (level != null) {
					printWrapper.println("Rendben, majd próbáld ki a pályát!");
				}
				break;
			}
			levelPrinter.printEditorLevel();
			if (messageFromProcessing != null) {
				printWrapper.println(messageFromProcessing);
			}
			var command = consoleInputWrapper.requestUserInput();
			try {
				inputHandler.handleInput(command);
				messageFromProcessing = null;
			}
			catch (RuntimeException e) {
				messageFromProcessing = e.getMessage();
			}
		}
	}

	public LevelVO getLevelVO () {
		return level.toLevelVO();
	}
}
