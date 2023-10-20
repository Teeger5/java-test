package hu.nye.pandragon.wumpus.lovel;

import hu.nye.pandragon.wumpus.Utils;
import hu.nye.pandragon.wumpus.lovel.entities.Empty;

import java.awt.*;

public class LevelEditor {
	private Level level;

	public LevelEditor () {
		onStart();
	}

	public void onStart () {
		System.out.println("Pályaszerkesztő");
		var site = requestMapSize();
		System.out.printf("A pálya %d x %d méretű lesz\n", site, site);
		level = new Level(site);
		readCommands();
	}

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

	public void readCommands () {
		var entitiesAvailable = Entities.getAsString();
		System.out.printf(
				"A következő parancsokat tudom végrehajtani:\n" +
						" - uj pályaelem létrehozása: legyen %s sor_száma oszlop_betűjele\n" +
						" - több pályaelem létrehozása kát pont között: legyenek %s kezdőpont_sor_száma kezdőpont_oszlop betűjele, végpont_sor_száma végpont_oszlop_betűjele\n" +
						" - Mentés: mentés\n" +
						" - Vissza a főmenübe: kész\n",
				entitiesAvailable, entitiesAvailable
		);
		while (true) {
			System.out.println(level.drawLevel());
			System.out.print("> ");
			var command = Utils.readFromConsole();
			if (command.equals("mentés")) {
				System.out.println("Pálya mentése...");
				save();
			}
			else if (command.equals("kész")) {
				System.out.println("Rendben, majd próbáld ki a pályát! Viszlát!");
				break;
			}
			else {
				processBuildCommand(command);
			}
		}
	}

	private void processBuildCommand (String command) {
		var words = command.split("\\s+");
		command = words[0];
		if (command.equalsIgnoreCase("legyen")) {
			if (words.length < 2) {
				System.out.printf(
						"Mi \"legyen\"? Egészítsd ki a parancsot: \"legyen %s sor_száma oszloop_betűjele\n",
						Entities.getAsString()
				);
				return;
			}
			var entity = Entities.parse(words[1]);
			if (entity == null) {
				System.out.printf(
						"Nem ismert pályaelem: %s. A következők érhetőek el: %s\n",
						words[1], Entities.getAsString()
				);
				return;
			}
			// a pályaelem elérhető, de nincs megadva sem a sor, sem az oszlop
			if (words.length == 2) {
				System.out.printf(
						"Remek választás, hol legyen? Egészítsd ki a parancsot: \"legyen %s sor_száma oszlop_betűjele\n",
						entity.getEntity().getName()
				);
				return;
			}
			else if (words.length == 3) {
				try {
					var x = Integer.parseInt(words[2]);
					System.out.printf(
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
						System.out.printf("y: %c -> int: %d\n", c, y);
						if (y > 0 && y <= level.getSize()) {
							System.out.printf(
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
							System.out.println("Nincs ilyen betűjelű oszloop. A betűjel a következők egyike lehet: " + columns);
						}
					}
					// a 3. paraméter se nem szám, se nem 1 db karakter
					else {
						System.out.printf(
								"Ismeretlen paraméter: %s. A parancs használata: legyen %s sor_száma oszlop_betűjele\n",
								words[2], entity.getEntity().getName()
						);
					}
				}
				return;
			}
			var x = Integer.parseInt(words[2]);
			var c = words[3].toUpperCase().toCharArray()[0];
			var y = c - 64;
			if (y < 1 || y > level.getSize()) {
				System.out.println("Nincs ilyen betűjelű oszlop: " + c);
				return;
			}
			System.out.printf("place entity %s -> %d %d\n", entity.getEntity().getName(), x, y);
			level.placeEntity(x, y, entity.createNewInstance());
		}
		else if (command.equals("legyenek")) {
			if (words.length != 6) {
				System.out.println("Túl kevés paraméter");
				return;
			}
			try {
				var entity = Entities.parse(words[1]);
				var x1 = Integer.parseInt(words[2]);
				var x2 = Integer.parseInt(words[4]);
				var c1 = words[3].toUpperCase().toCharArray()[0];
				var c2 = words[5].toUpperCase().toCharArray()[0];
				var y1 = c1 - 64;
				var y2 = c2 - 64;
				level.placeEntities(new Point(x1, y1), new Point(x2, y2), entity);
			} catch (NumberFormatException e) {
				System.out.println("Érvénytelen sorszám");
			}
		}
		else if (command.equals("törlés")) {
			if (words.length != 3) {
				System.out.println("Túl kevés paraméter");
				return;
			}
			try {
				var x = Integer.parseInt(words[1]);
				var c = words[2].toUpperCase().toCharArray()[0];
				var y = c - 64;
				if (y < 1 || y > level.getSize()) {
					System.out.println("Nincs ilyen betűjelű oszlop");
					return;
				}
				level.placeEntity(x, y, new Empty());
			} catch (NumberFormatException e) {
				System.out.println("Érvénytelen sorszám");
			}
		}
		else {
			System.out.println("Ismeretlen parancs: " + command);
		}
	}

	private void save () {

	}
}
