package hu.nye.pandragon.wumpus.ui;

import hu.nye.pandragon.wumpus.model.Entities;
import hu.nye.pandragon.wumpus.model.LevelEditorCommands;
import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.model.Screen;
import hu.nye.pandragon.wumpus.service.command.InputHandler;
import hu.nye.pandragon.wumpus.service.command.impl.editor.*;
import hu.nye.pandragon.wumpus.service.game.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Ez az osztály a pályaszerkesztőt írja le, és működteti
 */
public class LevelEditorScreen extends Screen {

	private static final Logger logger = LoggerFactory.getLogger(LevelEditorScreen.class);

	private final Level level;
	private final InputHandler inputHandler;

	public LevelEditorScreen() {
		printWrapper.println("Pályaszerkesztő");
		var site = requestMapSize();
		level = new Level(site);
		inputHandler = new InputHandler(Arrays.asList(
				new EditorPlaceEntityCommand(level),
				new EditorExitCommand(this),
				new EditorRotateCommand(level),
				new EditorRemoveEntityCommand(level),
				new EditorTestCommand(level.toLevelVO())
		));
		inputHandler.setPrintWrapper(printWrapper);
		if (site == -1) {
			printWrapper.println("Kilépés a pályaszerkesztőből...");
			shouldExit = true;
		}
	}

	public void start () {
		if (!shouldExit) {
			readCommands();
		}
	}

	/**
	 * Ez a metódus bekéri a pálya méretét a felhasználótól,
	 * majd visszaadja egész száámmá alakítva
	 * @return
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
		String entitiesAvailable = Entities.getAsString();
/*		printWrapper.printf(
				"A következő parancsokat tudom végrehajtani:\n" +
						" - uj pályaelem létrehozása: legyen %s sor_száma oszlop_betűjele\n" +
						" - több pályaelem létrehozása kát pont között: legyenek %s kezdőpont_sor_száma kezdőpont_oszlop betűjele, végpont_sor_száma végpont_oszlop_betűjele\n" +
						" - Mentés: mentés\n" +
						" - Vissza a főmenübe: kész\n",
				entitiesAvailable, entitiesAvailable
		);*/
		printWrapper.println(LevelEditorCommands.getMenuText());
		var messageFromProcessing = "Próbáld ki az egyik parancsot";
		while (true) {
			if (shouldExit) {
				printWrapper.println("Rendben, majd próbáld ki a pályát!");
				break;
			}
			levelPrinter.printEditorLevel(level.toLevelVO());
			if (messageFromProcessing != null) {
				printWrapper.println(messageFromProcessing);
			}
			printWrapper.print("> ");
			var command = consoleInputWrapper.readFromConsole();
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
