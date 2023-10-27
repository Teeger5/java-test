package hu.nye.pandragon.wumpus.ui;

import hu.nye.pandragon.wumpus.Utils;
import hu.nye.pandragon.wumpus.lovel.EntityController;
import hu.nye.pandragon.wumpus.lovel.Items;
import hu.nye.pandragon.wumpus.lovel.Level;
import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.lovel.entities.Hero;
import hu.nye.pandragon.wumpus.model.PlayernameVO;
import hu.nye.pandragon.wumpus.service.command.InputHandler;
import hu.nye.pandragon.wumpus.service.command.impl.gameplay.GameplayExitCommand;
import hu.nye.pandragon.wumpus.service.command.impl.gameplay.HeroMoveCommand;
import hu.nye.pandragon.wumpus.service.command.impl.gameplay.HeroShootCommand;
import hu.nye.pandragon.wumpus.service.command.impl.gameplay.HeroTurnCommand;
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
		LOGGER.debug("Hős: " + hero);
		this.entityControllers = level.getEntityControllers();
		inputHandler = new InputHandler(Arrays.asList(
				new HeroMoveCommand(level),
				new HeroTurnCommand(level),
				new HeroShootCommand(level),
				new GameplayExitCommand(this)
		));
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
			if (shouldExit) {
				System.out.println("Kilépés a játékból...");
				break;
			}
			LevelPrinter.printLevel(level.toLevelVO());
			if (messageFromCommandProcessing != null) {
				System.out.println(messageFromCommandProcessing);
			}
			LevelPrinter.printHeroBar(hero);
			System.out.print("> ");
			var command = Utils.readFromConsole().trim().toLowerCase();
			try {
				inputHandler.handleInput(command);
				messageFromCommandProcessing = null;
			}
			catch (RuntimeException e) {
				messageFromCommandProcessing = e.getMessage();
			}
		}
	}
}
