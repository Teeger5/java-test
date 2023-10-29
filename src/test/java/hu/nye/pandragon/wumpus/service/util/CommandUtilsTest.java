package hu.nye.pandragon.wumpus.service.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

class CommandUtilsTest {

	@Test
	public void shouldConvertCorrectly () {
		var result = CommandUtils.getCoordinates("d", "3", 6);
		var expected = new Point(4, 3);

		Assertions.assertEquals(result, expected);
	}

	@Test
	public void shouldThrowNoSuchRow () {
		Assertions.assertThrows(RuntimeException.class, () -> CommandUtils.getCoordinates("d", "8", 6));
	}

	@Test
	public void shouldThrowNoSuchColumn () {
		Assertions.assertThrows(RuntimeException.class, () -> CommandUtils.getCoordinates("D", "3", 6));
	}

	@Test
	public void shouldThrowPlaceOnBorder () {
		Assertions.assertThrows(RuntimeException.class, () -> CommandUtils.getCoordinates("d", "6", 6));
	}
}