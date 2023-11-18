package hu.nye.pandragon.wumpus.service.command.impl.editor;

import hu.nye.pandragon.wumpus.model.entities.Gold;
import hu.nye.pandragon.wumpus.service.game.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

class EditorRemoveEntityCommandTest {

	Level level;
	EditorRemoveEntityCommand removeEntityCommand;

	@BeforeEach
	public void setup () {
		level = new Level(6);
		removeEntityCommand = new EditorRemoveEntityCommand(level);
	}

	@Test
	public void shouldMatch () {
		var input = "törlés d 4";
		Assertions.assertTrue(removeEntityCommand.match(input).isCommandMatches());
	}

	@Test
	public void shouldRemoveEntity () {
		var input = "törlés d 4";

		level.placeEntity(4, 4, new Gold());
		removeEntityCommand.process(input);

		Assertions.assertNull(level.toLevelVO().getStaticEntities().get(new Point(4, 4)));
	}

	@Test
	public void shouldThrowExceptionNoEntity () {
		var input = "törlés d 4";

		Assertions.assertThrows(RuntimeException.class, () -> removeEntityCommand.process(input));
	}
}