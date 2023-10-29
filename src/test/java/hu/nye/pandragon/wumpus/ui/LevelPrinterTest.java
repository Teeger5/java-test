package hu.nye.pandragon.wumpus.ui;

import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.service.game.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LevelPrinterTest {

	@Test
	public void shouldPrintGameplayCorrectly () {
		var level = new Level(6);
		var expected = "     A  B  C  D  E  F \n" +
				"  1  ┏━━━━━━━━━━━━━━┓ \n" +
				"  2  ┃              ┃ \n" +
				"  3  ┃        ▲     ┃ \n" +
				"  4  ┃              ┃ \n" +
				"  5  ┃              ┃ \n" +
				"  6  ┗━━━━━━━━━━━━━━┛ \n";
		level.placeEntity(4, 3, new Hero());

		var levelPrinter = new LevelPrinter(new PrintWrapper());
		var drawing = levelPrinter.drawLevel(level.toLevelVO(), false);

		Assertions.assertEquals(drawing, expected);
	}

	@Test
	public void shoulddrawingitorCorrectly () {
		var level = new Level(6);
		var expected = "     A  B  C  D  E  F \n" +
				"  1  ┏━━━━━━━━━━━━━━┓ \n" +
				"  2  ┃  •  •  •  •  ┃ \n" +
				"  3  ┃  •  •  ▲  •  ┃ \n" +
				"  4  ┃  •  •  •  •  ┃ \n" +
				"  5  ┃  •  •  •  •  ┃ \n" +
				"  6  ┗━━━━━━━━━━━━━━┛ \n";
		level.placeEntity(4, 3, new Hero());

		var levelPrinter = new LevelPrinter(new PrintWrapper());
		var drawing = levelPrinter.drawLevel(level.toLevelVO(), true);

		Assertions.assertEquals(drawing, expected);
	}

	@Test
	public void shouldPrintHeroBarCorrectly () {
		var hero = new Hero();
		var expected = "Hős: ▲ | D 3 | 0 nyíl";

		hero.setPosition(4, 3);
		var levelPrinter = new LevelPrinter(new PrintWrapper());
		var drawing = levelPrinter.drawHeroBar(hero);

		Assertions.assertEquals(drawing, expected);
	}
}
