package hu.nye.pandragon.wumpus.ui;

import hu.nye.pandragon.wumpus.model.*;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.service.command.InputHandler;
import hu.nye.pandragon.wumpus.service.command.impl.gameplay.*;
import hu.nye.pandragon.wumpus.service.game.EntityController;
import hu.nye.pandragon.wumpus.service.game.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
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
	private final PlayernameVO playerName;
	/**
	 * A pályán lévő lények EntityController objektumai
	 * Ezekkel lehet irányítani a mozgásukat
	 * Csak a Wumpus-ok vannak bennük
	 */
	private final List<EntityController> entityControllers;
	private final InputHandler inputHandler;

	public GameplayScreen(LevelVO levelVO, PlayernameVO playerName) {
		this.level = new Level(levelVO);
		this.playerName = playerName;
		numberOfMoves = 0;
		this.hero = level.getHero();
		if (hero == null) {
			throw new RuntimeException("Nincs hős a pályán. Használd a pályaszerkesztőt, hogy hozzáadd a pályához.");
		}
		LOGGER.debug("Hős: " + hero);

		this.entityControllers = level.getEntityControllers();
		inputHandler = new InputHandler(Arrays.asList(
				new HeroMoveCommand(level),
				new HeroTurnCommand(level),
				new HeroShootCommand(level),
				new GameplayExitCommand(this),
				new HeroPickUpCommand(level)
		));
		inputHandler.setPrintWrapper(printWrapper);
	}

	public void start () {
		readCommands();
	}

	protected void readCommands () {
		LOGGER.info("Játék parancsok olvasásának kezdése");
		LOGGER.debug("Pálya start hely: " + level.getStartPoint());
		printWrapper.println("A játék elkezdődött\nJátékos: " + playerName);
		printWrapper.println(GameplayCommands.getMenuText());
		numberOfMoves = 0;
		var messageFromCommandProcessing = "A cél eljutni az aranyhoz, felvenni, és visszahozni ugyanide";
		while (true) {
			if (!hero.isAlive()) {
				LOGGER.debug("A hős meghalt, megtett lépések száma: " + numberOfMoves);
				printWrapper.printf("Sajnos meghalt a karaktered.\n%d lépést tettél meg.\n", numberOfMoves);
				printWrapper.println("Nyomj meg egy billentyűt a folytatáshoz...");
				consoleInputWrapper.readFromConsole();
				shouldExit = true;
			}
			if (hero.hasItem(Items.Gold) && hero.getPosition().equals(level.getStartPoint())) {
				LOGGER.debug("A hős nyert, pozíciója: {}, pálya start hely pozíciója: {}", hero.getPosition(), level.getStartPoint());
				printWrapper.printf("Győztél, sikeresen visszahoztad az aranyat a kiindulási helyre\n Megtettél %d lépést.\n", numberOfMoves);
				shouldExit = true;
			}
			if (shouldExit) {
				LOGGER.debug("shouldExit = " + shouldExit);
				printWrapper.println("Kilépés a játékból...");
				break;
			}
			numberOfMoves++;
			LOGGER.debug("Pálya kirajzolása");
			levelPrinter.printLevel(level.toLevelVO());
			if (messageFromCommandProcessing != null) {
				printWrapper.println(messageFromCommandProcessing);
			}
			LOGGER.debug("HUD kirajzolása");
			levelPrinter.printHeroBar(hero);
			LOGGER.debug("Parancs kérése");
			var command = consoleInputWrapper.requestUserInput().toLowerCase();
			try {
				inputHandler.handleInput(command);
				messageFromCommandProcessing = null;
			}
			catch (NullPointerException e) {
				e.printStackTrace();
			}
			catch (RuntimeException e) {
				messageFromCommandProcessing = e.getMessage();
			}
//			EntityController.moveEntitesInRandomDirections(entityControllers);
		}
	}
}
