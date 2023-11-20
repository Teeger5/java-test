package hu.nye.pandragon.wumpus.ui;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class GameMainScreenTest {

	GameMainScreen gameMainScreen;
	ByteArrayOutputStream outputStreamCaptor;

	@BeforeEach
	public void setup () {
		gameMainScreen = new GameMainScreen();
		outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));
	}

	/**
	 * Nem biztos, hogy ezt lehet tesztelni JUnittal
	 */
	public void shouldShowmenu () {
		var menu = "\n" +
				"Menü\n" +
				" 1 - Pályaszerkesztő indítása\n" +
				" 2 - Betöltés az adatbázisból\n" +
				" 3 - Játék indítása\n" +
				" 4 - Kilépés\n" +
				"\n" +
				"> ";
		System.setIn(new ByteArrayInputStream("Konshuu wa konsaato ni ikimasu ka".getBytes()));
		gameMainScreen.start();

		Assertions.assertEquals(menu, outputStreamCaptor.toString().trim());
	}

	@Test
	public void shouldGetPlayername () {
		var input = "Ima ni ji goro desu";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		gameMainScreen.init();

		var result = gameMainScreen.getPlayerName().toString();
		Assertions.assertEquals(result, input);
	}

	/**
	 * A kilépés tesztelésére még megoldást kell keresni
	 */
	public void shouldExitOnExitCommand () {
		System.setIn(new ByteArrayInputStream("nan ji desu ka".getBytes()));
		gameMainScreen.init();
		System.setIn(new ByteArrayInputStream("4".getBytes()));
		int status = Integer.MAX_VALUE;
		try {
			status = SystemLambda.catchSystemExit(() -> gameMainScreen.start());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Assertions.assertEquals(status, 0);
	}
}