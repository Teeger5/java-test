package hu.nye.pandragon.wumpus.ui;

import hu.nye.pandragon.wumpus.model.Directions;
import hu.nye.pandragon.wumpus.model.Items;
import hu.nye.pandragon.wumpus.model.entities.Gold;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.model.entities.Pit;
import hu.nye.pandragon.wumpus.model.entities.Wumpus;
import hu.nye.pandragon.wumpus.service.game.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LevelPrinterTest {

	@Test
	public void shouldPrintGameplayCorrectly () {
		var level = new Level(6);
		var expected =
				"     A  B  C  D  E  F \n" +
				"  1  ┏━━━━━━━━━━━━━━┓ \n" +
				"  2  ┃              ┃ \n" +
				"  3  ┃       ▲G     ┃ \n" +
				"  4  ┃    ░U░       ┃ \n" +
				"  5  ┃  U           ┃ \n" +
				"  6  ┗━━━━━━━━━━━━━━┛ \n";

		level.placeEntity(4, 3, new Hero());
		level.placeEntity(3, 4, new Pit());
		level.placeEntity(3, 4, new Wumpus());
		level.placeEntity(4, 3, new Gold());
		level.placeEntity(2, 5, new Wumpus());

		var levelPrinter = new LevelPrinter(new PrintWrapper());
		var drawing = levelPrinter.drawLevel(level.toLevelVO(), false);

		Assertions.assertEquals(drawing, expected);
	}

	@Test
	public void shouldPrintGameplayCorrectly2 () {
		var level = new Level(6);
		var expected =
				"     A  B  C  D  E  F \n" +
						"  1  ┏━━━━━━━━━━━━━━┓ \n" +
						"  2  ┃              ┃ \n" +
						"  3  ┃        G     ┃ \n" +
						"  4  ┃              ┃ \n" +
						"  5  ┃              ┃ \n" +
						"  6  ┗━━━━━━━━━━━━━━┛ \n";

		level.placeEntity(4, 3, new Gold());

		var levelPrinter = new LevelPrinter(new PrintWrapper());
		var drawing = levelPrinter.drawLevel(level.toLevelVO(), false);

		Assertions.assertEquals(drawing, expected);
	}

	@Test
	public void shoulddrawingitorCorrectly () {
		var level = new Level(6);
		var expected =
				"     A  B  C  D  E  F \n" +
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
	public void shouldPrintHeroBarCorrectlyHasGold () {
		var hero = new Hero();
		var expected = "Hős: ▲ | D 3 | 0 nyíl | Tárgyak: 1 x Arany";

		hero.setPosition(4, 3);
		hero.addItem(Items.Gold);
		var levelPrinter = new LevelPrinter(new PrintWrapper());
		var drawing = levelPrinter.drawHeroBar(hero);

		Assertions.assertEquals(drawing, expected);
	}

	@Test
	public void shouldPrintHeroBarCorrectly3Arrows () {
		var hero = new Hero();
		var expected = "Hős: ▲ | D 3 | 3 nyíl";

		hero.setPosition(4, 3);
		hero.setAmmoAmount(3);
		var levelPrinter = new LevelPrinter(new PrintWrapper());
		var drawing = levelPrinter.drawHeroBar(hero);

		Assertions.assertEquals(drawing, expected);
	}

	@Test
	public void shouldPrintHeroBarCorrectly3ArrowsRightDirection () {
		var hero = new Hero();
		var expected = "Hős: ▶ | D 3 | 3 nyíl";

		hero.setPosition(4, 3);
		hero.setAmmoAmount(3);
		hero.setDirection(Directions.East);
		var levelPrinter = new LevelPrinter(new PrintWrapper());
		var drawing = levelPrinter.drawHeroBar(hero);

		Assertions.assertEquals(drawing, expected);
	}
}
