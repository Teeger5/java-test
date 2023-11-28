package hu.nye.pandragon.wumpus.ui;

import hu.nye.pandragon.wumpus.model.*;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.persistence.impl.JdbcGameStateRepository;
import hu.nye.pandragon.wumpus.service.command.InputHandler;
import hu.nye.pandragon.wumpus.service.command.impl.gameplay.*;
import hu.nye.pandragon.wumpus.service.game.EntityController;
import hu.nye.pandragon.wumpus.service.game.Level;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Ez az osztály a játékot, mint a játékmenetet írja le és irányítja
 */
@Slf4j
public class GameplayScreen extends Screen {

//	private static final log log  = logFactory.getlog(GameplayScreen.class);

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
	private final AtomicInteger numberOfMoves;
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
		numberOfMoves = new AtomicInteger(0);
		this.hero = level.getHero();
		if (hero == null) {
			throw new RuntimeException("Nincs hős a pályán. Használd a pályaszerkesztőt, hogy hozzáadd a pályához.");
		}
		log.debug("Hős: " + hero);

		this.entityControllers = level.getEntityControllers();
		inputHandler = new InputHandler(Arrays.asList(
				new HeroMoveCommand(level),
				new HeroTurnCommand(level),
				new HeroShootCommand(level),
				new GameplayExitCommand(this),
				new HeroPickUpCommand(level),
				new GameSaveCommand(playerName, level, numberOfMoves)
		));
		inputHandler.setPrintWrapper(printWrapper);
	}

	public void start () {
		readCommands();
	}

	protected void readCommands () {
		log.info("Játék parancsok olvasásának kezdése");
		log.debug("Pálya start hely: " + level.getStartPoint());
		printWrapper.println("A játék elkezdődött\nJátékos: " + playerName);
		printWrapper.println(GameplayCommands.getMenuText());
		var messageFromCommandProcessing = "A cél eljutni az aranyhoz, felvenni, és visszahozni ugyanide";
		while (true) {
			if (!hero.isAlive()) {
				log.debug("A hős meghalt, megtett lépések száma: " + numberOfMoves);
				printWrapper.printf("Sajnos meghalt a karaktered.\n%d lépést tettél meg.\n", numberOfMoves);
				printWrapper.println("Nyomj meg egy billentyűt a folytatáshoz...");
				consoleInputWrapper.readFromConsole();
				shouldExit = true;
			}
			if (hero.hasItem(Items.Gold) && hero.getPosition().equals(level.getStartPoint())) {
				log.debug("A hős nyert, pozíciója: {}, pálya start hely pozíciója: {}", hero.getPosition(), level.getStartPoint());
				printWrapper.printf("Győztél, sikeresen visszahoztad az aranyat a kiindulási helyre\n Megtettél %d lépést.\n", numberOfMoves.get());
				try {
					log.debug("Eredmény mentése");
					var repository = new JdbcGameStateRepository();
					repository.increaseScore(playerName);
					repository.close();
					log.debug("Az eredmény mentése nem dobott kivételt");
				} catch (Exception e) {
					printWrapper.println(e.getMessage());
				}
				shouldExit = true;
			}
			if (shouldExit) {
				log.debug("shouldExit = " + shouldExit);
				printWrapper.println("Kilépés a játékból...");
				break;
			}
			numberOfMoves.getAndIncrement();
			log.debug("Pálya kirajzolása");
			levelPrinter.printLevel(level.toLevelVO());
			if (messageFromCommandProcessing != null) {
				printWrapper.println(messageFromCommandProcessing);
			}
			log.debug("HUD kirajzolása");
			levelPrinter.printHeroBar(hero);
			log.debug("Parancs kérése");
			var command = consoleInputWrapper.requestUserInput().toLowerCase();
			try {
				inputHandler.handleInput(command);
				messageFromCommandProcessing = null;
			}
			catch (NullPointerException e) {
				log.error("Hiba a parancs feldolgozásakor: {}", e);
			}
			catch (RuntimeException e) {
				messageFromCommandProcessing = e.getMessage();
			}
//			EntityController.moveEntitesInRandomDirections(entityControllers);
		}
	}
}
