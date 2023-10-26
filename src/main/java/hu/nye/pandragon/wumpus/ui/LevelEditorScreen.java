package hu.nye.pandragon.wumpus.ui;

import hu.nye.pandragon.wumpus.Utils;
import hu.nye.pandragon.wumpus.model.Directions;
import hu.nye.pandragon.wumpus.lovel.Entities;
import hu.nye.pandragon.wumpus.lovel.Level;
import hu.nye.pandragon.wumpus.lovel.entities.Empty;
import hu.nye.pandragon.wumpus.service.command.InputHandler;
import hu.nye.pandragon.wumpus.service.command.impl.EditorExitCommand;
import hu.nye.pandragon.wumpus.service.command.impl.editor.EditorRemoveEntityCommand;
import hu.nye.pandragon.wumpus.service.command.impl.editor.EditorRotateCommand;
import hu.nye.pandragon.wumpus.service.command.impl.editor.EditorPlaceEntityCommand;
import hu.nye.pandragon.wumpus.service.command.impl.editor.EditorTestCommand;
import hu.nye.pandragon.wumpus.service.command.impl.gameplay.GameplayExitCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Arrays;

/**
 * Ez az osztály a pályaszerkesztőt írja le, és működteti
 */
public class LevelEditorScreen extends Screen {

	private static final Logger logger = LoggerFactory.getLogger(LevelEditorScreen.class);

	private Level level;
	private final InputHandler inputHandler;

	public LevelEditorScreen() {
		System.out.println("Pályaszerkesztő");
		var site = requestMapSize();
		System.out.printf("A pálya %d x %d méretű lesz\n", site, site);
		level = new Level(site);
		level.setEditing(true);
		inputHandler = new InputHandler(Arrays.asList(
				new EditorPlaceEntityCommand(level),
				new EditorExitCommand(this),
				new EditorRotateCommand(level),
				new EditorRemoveEntityCommand(level),
				new EditorTestCommand(level.toLevelVO())
		));
		onStart();
	}

	public void onStart () {
		readCommands();
	}

	/**
	 * Ez a metódus bekéri a pálya méretét a felhasználótól,
	 * majd visszaadja egész száámmá alakítva
	 * @return
	 */
	private int requestMapSize () {
		while (true) {
			System.out.print("Add meg a pálya oldalhosszát: ");
			var value = Utils.readFromConsole();
			try {
				var i = Integer.parseInt(value);
				return i;
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
			LevelPrinter.printEditorLevel(level.toLevelVO());
			if (messageFromProcessing != null) {
				System.out.println(messageFromProcessing);
			}
			System.out.print("> ");
			var command = Utils.readFromConsole();
/*			if (command.equals("mentés")) {
				System.out.println("Pálya mentése...");
				save();
			}
			else if (command.equals("kész")) {
				System.out.println("Rendben, majd próbáld ki a pályát! Viszlát!");
				break;
			}
			else if (command.equalsIgnoreCase("teszt")) {
				System.out.println("Teszt indítása...");
				var gameplay = new GameplayScreen(level.toLevelVO(), "játékos");
				gameplay.start();
			}
			else {*/
//				messageFromProcessing = processBuildCommand(command);
//				try {
					inputHandler.handleInput(command);
					messageFromProcessing = null;
/*				}
				catch (RuntimeException e) {
					messageFromProcessing = e.getMessage();
				}*/
//			}
		}
	}

	/**
	 * Ez a metódus értelmezi a pályaszerkesztési parancsokat, és végre is hajtja őket
	 * Jelenleg elérhető parancsok:
	 *  - egy pályaelem létrehozása:
	 *    `legyen <pályaelem> <sorszám> <oszlopbetűjel>`
	 *  - több pályaelem létrehozása:
	 *    `legyenek <pályaelem> <kezdőpont sorszám> <kezdőpont oszlopbetűjel> <végpont sorszám> <végpont oszlopbetűjel>`
	 *  - pályaelem törlése:
	 *    `törlés <sorszám> <oszlopbetűjel>`
	 *
	 *  Érdemes lehet majd úgy átírni, hogy ne kiírja az üzenetet, hanem visszaadja azt,
	 *  és a felhasználási hely feleljen a kiírásáért
	 * @param command a bemenet a felhasználótól
	 */
	private String processBuildCommand (String command) {
		var words = command.split("\\s+");
		command = words[0].toLowerCase();
		if (command.equals("legyen")) {
			if (words.length < 2) {
				return String.format(
						"Mi \"legyen\"? Egészítsd ki a parancsot: \"legyen %s sor_száma oszloop_betűjele\n",
						Entities.getAsString()
				);
			}
			var entity = Entities.parse(words[1]);
			if (entity == null) {
				return String.format(
						"Nem ismert pályaelem: %s. A következők érhetőek el: %s\n",
						words[1], Entities.getAsString()
				);
			}
			// a pályaelem elérhető, de nincs megadva sem a sor, sem az oszlop
			if (words.length == 2) {
				return String.format(
						"Remek választás, hol legyen? Egészítsd ki a parancsot: \"legyen %s sor_száma oszlop_betűjele\n",
						entity.getEntity().getName()
				);
			}
			else if (words.length == 3) {
				try {
					var x = Integer.parseInt(words[2]);
					return String.format(
							"Hiányzik az oszlop betűjele. Egészítsd ki a parancsot: legyen %s %d oszloop_betűjele\n",
							entity.getEntity().getName(), x
					);
				}
				catch (NumberFormatException e) {
//					System.out.println("x nem szám");
					if (words[2].length() == 1) {
//						System.out.println("y hossza == 1");
						var c = Character.toLowerCase(words[2].toUpperCase().toCharArray()[0]);
						var y = c - 64;
						logger.debug(String.format("y: %c -> int: %d\n", c, y));
						if (y > 0 && y <= level.getSize()) {
							return String.format(
									"Hiányzik a sor száma. Egészítsd ki a parancsot: legyen %s sor_száma %c\n",
									entity.getEntity().getName(), c
							);
						}
						// else: itt meg lehetne adni az üzenetet, ha hiányzik az x és nem jó karakter lett megadva y-nak
						else {
							var columns = new StringBuilder();
							for (int i = 65; i < 65 + level.getSize(); i++) {
								columns.append((char) i).append(", ");
							}
							columns.setLength(columns.length() - 2);
							return String.format("Nincs ilyen betűjelű oszloop. A betűjel a következők egyike lehet: " + columns);
						}
					}
					// a 3. paraméter se nem szám, se nem 1 db karakter
					else {
						return String.format(
								"Ismeretlen paraméter: %s. A parancs használata: legyen %s sor_száma oszlop_betűjele\n",
								words[2], entity.getEntity().getName()
						);
					}
				}
			}
			int x;
			try {
				x = Integer.parseInt(words[2]);
			} catch (NumberFormatException e) {
				return String.format("Érvénytelen szám: " + words[2]);
			}
			var c = words[3].toUpperCase().toCharArray()[0];
			var y = c - 64;
			if (y < 1 || y > level.getSize()) {
				return String.format("Nincs ilyen betűjelű oszlop: " + c);
			}
			logger.debug(String.format("place entity %s -> %d %d\n", entity.getEntity().getName(), x, y));
			level.placeEntity(x, y, entity.createNewInstance());
			return String.format("%s (%c) hozzáadva %d %c helyen",
					entity.getEntity().getName(), entity.getEntity().getDisplaySymbol(), x, c);
		}
		else if (command.equals("legyenek")) {
			if (words.length != 6) {
				return String.format("Túl kevés paraméter");
			}
			try {
				logger.debug("legyenek started");
				var entity = Entities.parse(words[1]);
				var x1 = Integer.parseInt(words[2]);
				var x2 = Integer.parseInt(words[4]);
				var c1 = words[3].toUpperCase().toCharArray()[0];
				var c2 = words[5].toUpperCase().toCharArray()[0];
				var y1 = c1 - 64;
				var y2 = c2 - 64;
				level.placeEntities(new Point(x1, y1), new Point(x2, y2), entity.createNewInstance());
			} catch (NumberFormatException e) {
				return "Érvénytelen sorszám";
			}
		}
		else if (command.equals("törlés")) {
			if (words.length < 3) {
				return "Túl kevés paraméter";
			}
			try {
				var x = Integer.parseInt(words[1]);
				var c = words[2].toUpperCase().toCharArray()[0];
				var y = c - 64;
				if (y < 1 || y > level.getSize()) {
					return "Nincs ilyen betűjelű oszlop";
				}
				level.placeEntity(x, y, new Empty());
			} catch (NumberFormatException e) {
				return "Érvénytelen sorszám";
			}
		}
		else if (command.equals("hős")) {
			if (words[1].equals("fordul")) {
				if (words.length == 3) {
					var c = Character.toUpperCase(words[2].toCharArray()[0]);
					var direction = Directions.parseSymbol(c);
					if (direction == Directions.Unknown || words[2].length() != 1) {
						return String.format("Érvénytelen irány: %s. Érvényes irányok: N | E | S | W", words[2]);
					}
					var hero = level.getHero();
					if (hero == null) {
						return "Nincs hős a pályán";
					}
					hero.setDirection(direction);
					return String.format("Hős iránya beállítva: %c | %c", c, hero.getDisplaySymbol());
				}
			}
		}
		return "Ismeretlen parancs: " + command;
	}

	private void save () {

	}
}
