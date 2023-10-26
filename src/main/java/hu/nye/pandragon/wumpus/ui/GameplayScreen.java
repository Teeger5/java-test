package hu.nye.pandragon.wumpus.ui;

import hu.nye.pandragon.wumpus.Utils;
import hu.nye.pandragon.wumpus.lovel.EntityController;
import hu.nye.pandragon.wumpus.lovel.Items;
import hu.nye.pandragon.wumpus.lovel.Level;
import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.lovel.entities.Hero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Ez az osztály a játékot, mint a játékmenetet írja le és irányítja
 */
public class GameplayScreen extends Screen {

	private static final Logger LOGGER  = LoggerFactory.getLogger(GameplayScreen.class);

	/**
	 * A pálya
	 */
	private final Level level;
	/**
	 * A hős
	 */
	private final Hero hero;
	/**
	 * Hány lépést tesz meg a játékos
	 */
	private int numberOfMoves;
	/**
	 * A játékos neve
	 */
	private final String playerName;
	/**
	 * A pályán lévő lények EntityController objektumai
	 * Ezekkel lehet irányítani a mozgásukat
	 * Csak a Wumpus-ok vannak bennük
	 */
	private final List<EntityController> entityControllers;
	private final EntityController heroController;

	public GameplayScreen(LevelVO levelVO, String playerName) {
		this.level = new Level(levelVO);
		this.playerName = playerName;
		numberOfMoves = 0;
		this.hero = level.getHero();
		LOGGER.debug("Hős: " + hero);
		this.entityControllers = level.getEntityControllers();
		heroController = new EntityController(level, hero);
	}

	public void start () {
		readCommands();
	}

	protected void readCommands () {
		System.out.println("A játék elkezdődött\nJátékos: " + playerName);
		System.out.println("""
  Elérhető parancsok:
    lép: a hős előre lép egyet
    fordul balra | jobbra: a hős elfordul balra vagy jobbra
    lő: a hős nyilat lő egyenesen, a nézési irányában
    aranyat felszed: a hős felveszi az aranyat, ha egy pozíción áll vele
    felad: kilépés ebből a játékból""");
		var messageFromCommandProcessing = "A cél eljutni az aranyhoz, felvenni, és visszahozni ugyanide";
		while (true) {
			if (!hero.isAlive()) {
				System.out.printf("Sajnos meghalt a karaktered.\n%d lépést tettél meg.\n", numberOfMoves);
				System.out.println("Nyomj meg egy billentyűt a folytatáshoz...");
				Utils.readFromConsole();
				break;
			}
			if (hero.getPosition().equals(level.getStartPoint()) && hero.hasItem(Items.Gold)) {
				System.out.printf("Győztél, sikeresen visszahoztad az aranyat a kiindulási helyre\n Megtettél %d lépést.\n", numberOfMoves);
				break;
			}
			LevelPrinter.printGameLevel(level.toLevelVO(), hero);
			System.out.println(messageFromCommandProcessing);
			System.out.printf(
					"Hős: %c | %d %s | %d nyíl\n",
					hero.getDisplaySymbol(),
					hero.getPosition().y,
					(char) (hero.getPosition().x + 64),
					hero.getAmmoAmount());
			System.out.print("> ");
			var command = Utils.readFromConsole().trim().toLowerCase();
			if (command.equals("felad")) {
				System.out.println("Kilépés a játékból...");
				break;
			}
			else {
				messageFromCommandProcessing = processCommands(command);
			}
		}
	}

	private String processCommands (String command) {
		var words = command.split("\\s+");
		command = words[0];
		if (command.equals("lép")) {
			if (heroController.moveForward()) {
				return "A hős előőre lépett egyet";
			}
			return "Nem tud előre lépni a hős";
		}
		else if (command.equals("fordul")) {
			if (words.length < 2) {
				return "Meg kell adni egy irányt is: fordul jobbra | balra";
			}
			if (words[1].equals("jobbra")) {
				heroController.turnRight();
			}else if (words[1].equals("balra")) {
				heroController.turnLeft();
			}
			return "A hős elfordult " + words[1];
		}
		else if (command.equals("lő")) {

		}else if (command.equals("aranyat") && words[1].equals("felszed")) {

		}
		return "Ismeretlen parancs: " + command;

	}
}
