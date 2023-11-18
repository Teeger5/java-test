package hu.nye.pandragon.wumpus.service.command.impl.editor;

import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.service.game.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

class EditorPlaceEntityCommandTest {

	Level level;
	EditorPlaceEntityCommand placeEntityCommand;

	@BeforeEach
	public void setup () {
		level = new Level(6);
		placeEntityCommand = new EditorPlaceEntityCommand(level);
	}

	@Test
	void shouldMatch() {
		var input = "legyen hős d 6";
		var result = placeEntityCommand.match(input);
		Assertions.assertTrue(result.isCommandMatches());
	}

	@Test
	void shouldProcess() {
		var input = "legyen hős d 4";
		placeEntityCommand.process(input);

		Assertions.assertTrue(level.toLevelVO().getLivingEntities().get(new Point(4, 4)) instanceof Hero);
	}

	@Test
	void shouldProcessPreventToomanyWumpus() {
		var input = "legyen wumpus d 4";
		placeEntityCommand.process(input);

		final var input2 = "legyen wumpus d 5";

		Assertions.assertThrows(RuntimeException.class, () -> placeEntityCommand.process(input2));
	}
}