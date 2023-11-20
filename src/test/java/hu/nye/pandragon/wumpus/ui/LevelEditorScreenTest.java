package hu.nye.pandragon.wumpus.ui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

class LevelEditorScreenTest {

	LevelEditorScreen levelEditorScreen;

	@BeforeEach
	public void setup () {
		levelEditorScreen = new LevelEditorScreen();
	}

	@Test
	public void shouldSetCorrectLevelSize () {
		var input = "20";
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		levelEditorScreen.init();

		var result = levelEditorScreen.getLevelVO().getSize();

		Assertions.assertEquals(result, 20);
	}

	/**
	 * Amikor -1 a pálya mérete, akkor ki kell lépnie
	 */
	@Test
	public void shouldExitOnCancel () {
		var input = "-1";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		levelEditorScreen.init();

		var result = levelEditorScreen.isShouldExit();

		Assertions.assertTrue(result);
	}

	@Test
	public void shouldNotExitOnExitCommand () {
		var input = "20";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		levelEditorScreen.init();
		input = "kész";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		levelEditorScreen.start();

		var result = levelEditorScreen.isShouldExit();
		Assertions.assertTrue(result);
	}
}