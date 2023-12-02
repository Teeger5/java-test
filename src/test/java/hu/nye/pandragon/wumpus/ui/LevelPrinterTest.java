package hu.nye.pandragon.wumpus.ui;

import hu.nye.pandragon.wumpus.model.Directions;
import hu.nye.pandragon.wumpus.model.Items;
import hu.nye.pandragon.wumpus.model.entities.Gold;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.model.entities.Pit;
import hu.nye.pandragon.wumpus.model.entities.Wumpus;
import hu.nye.pandragon.wumpus.service.game.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class LevelPrinterTest {

	Level level;
	LevelPrinter levelPrinter;
	ByteArrayOutputStream outputStreamCaptor;

	@BeforeEach
	public void setup () {
		outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));
		level = new Level(6);
		levelPrinter = new LevelPrinter(new PrintWrapper(), level);
	}

	@Test
	public void shouldPrintGameplayCorrectly () {
		var expected =
				"     A  B  C  D  E  F\n" +
				"  1  ┏━━━━━━━━━━━━━━┓\n" +
				"  2  ┃       > <    ┃\n" +
				"  3  ┃       ▲G     ┃\n" +
				"  4  ┃    ░U░       ┃\n" +
				"  5  ┃  U    ░░░    ┃\n" +
				"  6  ┗━━━━━━━━━━━━━━┛";

		level.placeEntity(4, 3, new Hero());
		level.placeEntity(3, 4, new Pit());
		level.placeEntity(3, 4, new Wumpus());
		level.placeEntity(4, 3, new Gold());
		level.placeEntity(2, 5, new Wumpus());
		level.placeEntity(4, 5, new Pit());
		level.setStartpoint(4, 2);

		levelPrinter.printLevel();
		var result = outputStreamCaptor.toString().stripTrailing();
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void shouldPrintGameplayCorrectly2 () {
		var expected =
				"     A  B  C  D  E  F\n" +
				"  1  ┏━━━━━━━━━━━━━━┓\n" +
				"  2  ┃              ┃\n" +
				"  3  ┃    >▲< G     ┃\n" +
				"  4  ┃              ┃\n" +
				"  5  ┃              ┃\n" +
				"  6  ┗━━━━━━━━━━━━━━┛";

		level.placeEntity(3, 3, new Hero());
		level.placeEntity(4, 3, new Gold());

		levelPrinter.printLevel();
		var result = outputStreamCaptor.toString().stripTrailing();
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void shoulddrawingitorCorrectly () {
		var expected =
				"     A  B  C  D  E  F\n" +
				"  1  ┏━━━━━━━━━━━━━━┓\n" +
				"  2  ┃  •  •  •  •  ┃\n" +
				"  3  ┃  •  •  ▲  •  ┃\n" +
				"  4  ┃  •  •  •  •  ┃\n" +
				"  5  ┃  •  •  •  •  ┃\n" +
				"  6  ┗━━━━━━━━━━━━━━┛";

		level.placeEntity(4, 3, new Hero());

		levelPrinter.printEditorLevel();
		var result = outputStreamCaptor.toString().stripTrailing();
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void shouldPrintHeroBarCorrectlyHasGold () {
		var hero = new Hero();
		var expected = "Hős: ▲ | D 3 | 0 nyíl | Tárgyak: 1 x Arany";

		hero.addItem(Items.Gold);

		level.placeEntity(4, 3, hero);
		levelPrinter.printHeroBar();
		var result = outputStreamCaptor.toString().trim();
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void shouldPrintHeroBarCorrectly3Arrows () {
		var hero = new Hero();
		var expected = "Hős: ▲ | D 3 | 3 nyíl";

		hero.setAmmoAmount(3);

		level.placeEntity(4, 3, hero);

		levelPrinter.printHeroBar();
		var result = outputStreamCaptor.toString().trim();
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void shouldPrintHeroBarCorrectly3ArrowsRightDirectionHasGold () {
		var hero = new Hero();
		var expected = "Hős: ▶ | D 3 | 3 nyíl | Tárgyak: 1 x Arany";

		hero.setAmmoAmount(3);
		hero.setDirection(Directions.East);
		hero.addItem(Items.Gold);

		level.placeEntity(4, 3, hero);

		levelPrinter.printHeroBar();
		var result = outputStreamCaptor.toString().trim();
		Assertions.assertEquals(expected, result);
	}
}
