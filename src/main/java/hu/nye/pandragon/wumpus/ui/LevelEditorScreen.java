package hu.nye.pandragon.wumpus.ui;

import hu.nye.pandragon.wumpus.Utils;
import hu.nye.pandragon.wumpus.lovel.Entities;
import hu.nye.pandragon.wumpus.lovel.Level;
import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.service.command.InputHandler;
import hu.nye.pandragon.wumpus.service.command.impl.EditorExitCommand;
import hu.nye.pandragon.wumpus.service.command.impl.editor.EditorPlaceEntityCommand;
import hu.nye.pandragon.wumpus.service.command.impl.editor.EditorRemoveEntityCommand;
import hu.nye.pandragon.wumpus.service.command.impl.editor.EditorRotateCommand;
import hu.nye.pandragon.wumpus.service.command.impl.editor.EditorTestCommand;
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
		System.out.println("Pályaszerkesztő");
		var site = requestMapSize();
		level = new Level(site);
		inputHandler = new InputHandler(Arrays.asList(
				new EditorPlaceEntityCommand(level),
				new EditorExitCommand(this),
				new EditorRotateCommand(level),
				new EditorRemoveEntityCommand(level),
				new EditorTestCommand(level.toLevelVO())
		));
		if (site == -1) {
			System.out.println("Kilépés a pályaszerkesztőből...");
			return;
		}
		System.out.printf("A pálya %d x %d méretű lesz\n", site, site);
	}

	public void start () {
		readCommands();
	}

	/**
	 * Ez a metódus bekéri a pálya méretét a felhasználótól,
	 * majd visszaadja egész száámmá alakítva
	 * @return
	 */
	private int requestMapSize () {
		while (true) {
			System.out.print("Add meg a pálya oldalhosszát (-1 = kilépés a pályaszerkesztőből): ");
			var value = Utils.readFromConsole();
			try {
				var i = Integer.parseInt(value);
				if ((i < 6 || i > 20) && i != -1) {
					System.out.println("A pálya mérete min 6, max 20 egység lehet");
				}
				else {
					return i;
				}
			}
			catch (NumberFormatException e) {
				System.out.println("A pálya hosszát számjegy karakterekkel kell megadni!");
			}
		}
	}

	/**
	 * Ez a metódus fogadja a parancsokat a felhasználótól
	 * Egy végtelen while ciklus fut benne, amit a kilépés parancsa szakít meg
	 */
	protected void readCommands () {
		String entitiesAvailable = Entities.getAsString();
		System.out.printf(
				"A következő parancsokat tudom végrehajtani:\n" +
						" - uj pályaelem létrehozása: legyen %s sor_száma oszlop_betűjele\n" +
						" - több pályaelem létrehozása kát pont között: legyenek %s kezdőpont_sor_száma kezdőpont_oszlop betűjele, végpont_sor_száma végpont_oszlop_betűjele\n" +
						" - Mentés: mentés\n" +
						" - Vissza a főmenübe: kész\n",
				entitiesAvailable, entitiesAvailable
		);
		var messageFromProcessing = "Próbáld ki az egyik parancsot";
		while (true) {
			if (shouldExit) {
				System.out.println("Rendben, majd próbáld ki a pályát!");
				break;
			}
			LevelPrinter.printEditorLevel(level.toLevelVO());
			if (messageFromProcessing != null) {
				System.out.println(messageFromProcessing);
			}
			System.out.print("> ");
			var command = Utils.readFromConsole();
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
