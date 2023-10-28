package hu.nye.pandragon.wumpus.service.command.impl.gameplay;

import hu.nye.pandragon.wumpus.service.game.EntityController;
import hu.nye.pandragon.wumpus.service.game.Level;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.service.command.CommandMatcherResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HeroMoveCommandTest {

	private static final String MOVE_COMMAND = "lép";
	private static final String INVALID_COMMAND = "ugrik";
	private static final Point HERO_POSITION = new Point(1, 1);

	@Mock
	private Level level;

	@Mock
	private Hero hero;

	@Mock
	private EntityController controller;

	private HeroMoveCommand underTest;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		underTest = new HeroMoveCommand(level);
		when(level.getHero()).thenReturn(hero);
		when(hero.getPosition()).thenReturn(HERO_POSITION);
		when(controller.moveForward()).thenReturn(true);
	}

	@Test
	void testMatchShouldReturnCorrectMatchingResultWhenInputIsMoveCommand() {
		//given
		var expected = CommandMatcherResult.ofCorrectMatchingCommand().isCommandMatches();

		//when
		var actual = underTest.match(MOVE_COMMAND).isCommandMatches();
		System.out.printf("actual: %s\n", actual);

		//then
		assertEquals(expected, actual);
	}

	@Test
	void testMatchShouldReturnNotMatchingResultWhenInputIsNotMoveCommand() {
		//given
		CommandMatcherResult expected = CommandMatcherResult.ofNotMatchingCommand();

		//when
		CommandMatcherResult actual = underTest.match(INVALID_COMMAND);

		//then
		assertEquals(expected, actual);
	}

	@Test
	void testProcessShouldMoveHeroForwardWhenControllerCanMoveForward() {
		//given

		//when
		underTest.process(MOVE_COMMAND);

		//then
		verify(controller).moveForward();
	}

	@Test
	void testProcessShouldThrowExceptionWhenControllerCannotMoveForward() {
		//given
		when(controller.moveForward()).thenReturn(false);

		//when
		assertThrows(RuntimeException.class, () -> underTest.process(MOVE_COMMAND));

		//then
		verify(controller).moveForward();
	}
}
