package hu.nye.pandragon.wumpus.service.command.impl.editor;

import hu.nye.pandragon.wumpus.model.Directions;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.service.game.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EditorRotateCommandTest {

	Level level;
	EditorRotateCommand rotateCommand;

	@BeforeEach
	public void setup () {
		level = new Level(6);
		rotateCommand = new EditorRotateCommand(level);
	}

	@Test
	public void shouldMatch () {
		var input = "hős fordul w";

		Assertions.assertTrue(rotateCommand.match(input).isCommandMatches());
	}

	@Test
	public void shouldRotateHero () {
		var input = "hős fordul w";
		var hero = new Hero();
		level.placeEntity(4, 4, hero);

		rotateCommand.process(input);

		Assertions.assertEquals(hero.getDirection(), Directions.West);
	}
}