package hu.nye.pandragon.wumpus.ui;

import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.model.PlayernameVO;
import hu.nye.pandragon.wumpus.model.Screen;
import hu.nye.pandragon.wumpus.model.Screens;
import hu.nye.pandragon.wumpus.persistence.impl.JdbcGameStateRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

/**
 * Ez az osztály felel a játék irányításáért. Először a főmenü elemeinek kezelését kell megoldani
 */
@Slf4j
public class GameMainScreen extends Screen {
	@Getter
	private PlayernameVO playerName;
	private LevelVO levelVO;

	public GameMainScreen() {
		printWrapper.println("Üdvözöllek Wumpus világában");
	}

	public void start () {
		if (playerName == null) {
			requestPlayerName();
		}
		printWrapper.printf("Köszöntelek, %s! Kezdjünk hozzű!\n", playerName);
		readCommands();
	}

	public void init () {
		requestPlayerName();
	}

	/**
	 * A játékos nevének bekérése és tárolása
	 */
	private void requestPlayerName () {
		printWrapper.print("Add meg a játékosneved: ");
		var input = consoleInputWrapper.readFromConsole();
		playerName = new PlayernameVO(input);
	}

	/**
	 * Belépés a főmenübe
	 * Minden meüopciót egy szám jelöl, aminek megfeleőképernyőre visz a program
	 *   Amikor innen átlépünk valamelyik képernyőre, akkor a vezérlés átkerül oda
	 *   A vezérlés akkor tér vissza ebbe a metődusba,
	 *   amikor a belőle elindított bármelyik folyamat véget ér (pl. kilépünk a pályaszerkesztésből)
	 *   Kilépéskor viszont a programból lépünk ki
	 */
	protected void readCommands () {
		showMenuOptions();
		while (!shouldExit) {
			printWrapper.print("> ");
			var command = consoleInputWrapper.readFromConsole();
			var screen = Screens.parseID(command.trim());
			switch (screen) {
				case LevelEditor -> enterEditor();
				case LoadFromDB -> loadGameFromDB();
				case Gameplay -> enterGame();
				case Highscores -> showHighscores();
				case Exit -> shouldExit = true;
				case Unknown -> printWrapper.println("Ismeretlen parancs: " + command.trim());
			}
			if (screen != Screens.Unknown) {
				showMenuOptions();
			}
		}
		exit();
	}

	/**
	 * Ez a metódus visszaad egy szöveges listát a főmenüben elérhető menüpontokkal
	 */
	private void showMenuOptions () {
		printWrapper.println(Screens.getMenuText());
	}

	/**
	 * Belépés a pályaszerkesztőbe
	 */
	public void enterEditor () {
		var editor = new LevelEditorScreen();
		editor.start();
		var levelVO = editor.getLevelVO();
		if (levelVO != null && levelVO.getSize() > 0) {
			this.levelVO = levelVO;
		}
	}

	/**
	 * Belépés a játékba
	 */
	public void enterGame () {
		if (levelVO != null) {
			try {
				var game = new GameplayScreen(levelVO, playerName);
				game.start();
			}
			catch (RuntimeException e) {
				printWrapper.println(e.getMessage());
			}
		}
		else {
			printWrapper.println("Nincs pálya. Először használd a pályaszerkesztőt, hogy készíts egyet.");
		}

	}

	public void showHighscores () {
		try {
			var database = new JdbcGameStateRepository();
			var highscoresMap = database.getScoreboard();
			var highscores = highscoresMap.entrySet().stream()
//					.sorted(Map.Entry.comparingByValue())
					.sorted((o1, o2) -> -o1.getValue().compareTo(o2.getValue()))
					.map(e -> String.format("%4d - %s", e.getValue(), e.getKey()))
					.collect(Collectors.joining("\n"));
			printWrapper.println("Toplista\n" + highscores);
		} catch (Exception e) {
//			log.error("showHighscores hiba: " + e.getMessage());
//			printWrapper.println("A lista nem elérhető");
			printWrapper.println(e.getMessage());
		}
	}

	public void loadGameFromDB () {
		try {
			var repository = new JdbcGameStateRepository();
			var level = repository.load(playerName);
			this.levelVO = level;
			enterGame();
		} catch (Exception e) {
			printWrapper.println(e.getMessage());
		}
	}

	/**
	 * Kilépés a programból
	 */
	public void exit () {
		printWrapper.printf("Viszontlátásra, %s!\n", playerName);
		System.exit(0);
	}

	/*
	A mentést és a betöltést meg kell kérdeznem, nem egyértelmű, mire vonatkozik
	 */
}
