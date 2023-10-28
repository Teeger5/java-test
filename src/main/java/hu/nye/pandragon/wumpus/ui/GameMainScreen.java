package hu.nye.pandragon.wumpus.ui;

import hu.nye.pandragon.wumpus.Utils;
import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.model.PlayernameVO;

import static hu.nye.pandragon.wumpus.Utils.readFromConsole;

/**
 * Ez az osztály felel a játék irányításáért. Először a főmenü elemeinek kezelését kell megoldani
 */
public class GameMainScreen {
	private PlayernameVO playerName;
	private LevelVO levelVO;

	public GameMainScreen() {
		onStart();
		enterMenu();
	}

	private void onStart () {
		System.out.println("Üdvözöllek Wumpus világában");
		requestPlayerName();
		System.out.printf("GG, %s! Kezdjünk hozzű!\n", playerName);
	}

	/**
	 * A játékos nevének bekérése és tárolása
	 */
	private void requestPlayerName () {
		System.out.print("Add meg a játékosneved: ");
		var input = readFromConsole();
		playerName = new PlayernameVO(input);
	}

	/**
	 * Belépés a főmenübe
	 * Minden meüopciót egy szám jelöl, aminek megfeleőképernyőre visz a program
	 * Jelenleg a következő lehetőségek érhetőek el:
	 *  - 1 -> Pályaszerkesztő
	 *   - 2 -> Kilépés
	 *   Amikor innen átlépünk valamelyik képernyőre, akkor a vezérlés átkerül oda
	 *   A vezérlés akkor tér vissza ebbe a metődusba,
	 *   amikor a belőle elindított bármelyik folyamat véget ér (pl. kilépünk a pályaszerkesztésből)
	 *   Kilépéskor viszont a programból lépünk ki
	 */
	public void enterMenu () {
		showMenuOptions();
		while (true) {
			System.out.print("> ");
			var command = Utils.readFromConsole();
			var screen = Screens.parseID(command.trim());
			switch (screen) {
				case LevelEditor -> enterEditor();
				case LoadFromDB -> System.out.println("Még nem elérhető");
				case Gameplay -> enterGame();
				case Exit -> exit();
				case Unknown -> System.out.println("Ismeretlen parancs: " + command.trim());
			}
			if (screen != Screens.Unknown) {
				showMenuOptions();
			}
/*			if (command.equals("1")) {
				enterEditor();
				showMenuOptions();
			}
			else if (command.equals("2")) {
				enterGame();
			}
			else if (command.equals("3")) {
				exit();
			}
			else {
				System.out.println("Ismeretlen opció: " + command);
			}*/
		}
	}

	// Ha esetleg később szükség lenne rá, lehet statikus generikus metódusokat is létrehozni
	static <A, B> String concat (A a, B b) {
		return a.toString() + b.toString();
	}

	/**
	 * Ez a metódus visszaad egy szöveges listát a főmenüben elérhető menüpontokkal
	 */
	private void showMenuOptions () {
		System.out.println(Screens.getMenuText());
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
			var game = new GameplayScreen(levelVO, playerName);
			game.start();
		}
		else {
			System.out.println("Nincs pálya. Először használd a pályaszerkesztőt, hogy készíts egyet.");
		}

	}

	/**
	 * Kilépés a programból
	 */
	public void exit () {
		System.out.printf("Viszontlátásra, %s!\n", playerName);
		System.exit(0);
	}

	/*
	A mentést és a betöltést meg kell kérdeznem, nem egyértelmű, mire vonatkozik
	 */
}
