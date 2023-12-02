package hu.nye.pandragon.wumpus.ui;

import hu.nye.pandragon.wumpus.model.Items;
import hu.nye.pandragon.wumpus.model.PlayernameVO;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.service.game.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

class GameplayScreenTest {

	static PlayernameVO PLAYERNAME = new PlayernameVO("Doragon ga suki desu");
	GameplayScreen gameplayScreen;
	Level level;

	@BeforeEach
	public void setup () {
		level = new Level(20);
		level.placeEntity(4, 4, new Hero());
		gameplayScreen = new GameplayScreen(level.toLevelVO(), new PlayernameVO("San ji desu"));
	}

	@Test
	public void shouldExitOnExitCommand () {
		var input = "felad";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		gameplayScreen.start();

		var result = gameplayScreen.isShouldExit();
		Assertions.assertTrue(result);
	}

	@Test
	public void shouldThrowExceptionOnheroNotFound () {
		var levelVO = new Level(20).toLevelVO();

		Assertions.assertThrows(RuntimeException.class, () -> new GameplayScreen(levelVO, PLAYERNAME));
	}

	@Test
	public void shouldExitOnWhenHeroIsNotAlive () {
		level.getHero().setAlive(false);
		gameplayScreen = new GameplayScreen(level.toLevelVO(), PLAYERNAME);
		System.setIn(new ByteArrayInputStream("Ashita konsaato ni ikimasu".getBytes()));
		gameplayScreen.start();

		var result = gameplayScreen.isShouldExit();
		Assertions.assertTrue(result);
	}

	@Test
	public void shouldExitOnHeroWin () {
		level.getHero().addItem(Items.Gold);
		gameplayScreen = new GameplayScreen(level.toLevelVO(), PLAYERNAME);
		gameplayScreen.start();
		Assertions.assertTrue(gameplayScreen.isShouldExit());
	}
}