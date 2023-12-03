package hu.nye.pandragon.wumpus.service.command.impl.editor;

import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.service.game.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

class EditorTestCommandTest {

	private static String COMMAND = "teszt";

	private EditorTestCommand editorTestCommand;
	private Level level;

	@BeforeEach
	public void setup () {
		level = new Level(6);
		editorTestCommand = new EditorTestCommand(level);
	}

	@Test
	public void shouldMatchCommand () {
		Assertions.assertTrue(editorTestCommand.match(COMMAND).isCommandMatches());
	}

	@Test
	public void shouldNotMatchCommand () {
		Assertions.assertFalse(editorTestCommand.match("Doragon ha ii desu").isCommandMatches());
	}

	@Test
	public void shouldThrowsExceptionWhenNoHeroFound () {
		Assertions.assertThrows(RuntimeException.class, () -> editorTestCommand.process(COMMAND));
	}

	@Test
	public void shouldEnterTesting () {
		System.setIn(new ByteArrayInputStream("felad".getBytes()));
		level.placeEntity(2, 2, new Hero());
		Assertions.assertAll(() -> editorTestCommand.process(COMMAND));
	}
}