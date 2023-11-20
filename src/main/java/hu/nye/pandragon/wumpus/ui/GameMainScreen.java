package hu.nye.pandragon.wumpus.ui;

import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.model.PlayernameVO;
import hu.nye.pandragon.wumpus.model.Screen;
import hu.nye.pandragon.wumpus.model.Screens;

/**
 * Ez az osztály felel a játék irányításáért. Először a főmenü elemeinek kezelését kell megoldani
 */
public class GameMainScreen extends Screen {
	private PlayernameVO playerName;
	private LevelVO levelVO;

	public GameMainScreen() {
		printWrapper.println("Üdvözöllek Wumpus világában");
	}

	public PlayernameVO getPlayerName() {
		return playerName;
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
		while (true) {
			if (shouldExit) {
				break;
			}
			printWrapper.print("> ");
			var command = consoleInputWrapper.readFromConsole();
			var screen = Screens.parseID(command.trim());
			switch (screen) {
				case LevelEditor -> enterEditor();
				case LoadFromDB -> printWrapper.println("Még nem elérhető");
				case Gameplay -> enterGame();
				case Exit -> shouldExit = true;
				case Unknown -> printWrapper.println("Ismeretlen parancs: " + command.trim());
			}
			if (screen != Screens.Unknown) {
				showMenuOptions();
			}
		}
		exit();
	}

	// Ha esetleg később szükség lenne rá, lehet statikus generikus metódusokat is létrehozni
	static <A, B> String concat (A a, B b) {
		return a.toString() + b.toString();
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
		levelVO = editor.getLevelVO();
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
