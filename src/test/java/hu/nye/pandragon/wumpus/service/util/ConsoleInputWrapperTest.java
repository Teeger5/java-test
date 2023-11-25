package hu.nye.pandragon.wumpus.service.util;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleInputWrapperTest {

	@Test
	public void shouldReadFromConsole() {
		var input = "Ima ni ji goro desu";
		var byteArrayInputStream = new ByteArrayInputStream(input.getBytes());
		System.setIn(byteArrayInputStream);
		var consoleInputWrapper = new ConsoleInputWrapper();
		String result = consoleInputWrapper.readFromConsole();
		assertEquals(input, result);
	}

	@Test
	public void shoudRequestUserInput() {
		var input = "Ima ni ji goro desu";
		var byteArrayInputStream = new ByteArrayInputStream(input.getBytes());
		System.setIn(byteArrayInputStream);
		var consoleInputWrapper = new ConsoleInputWrapper();
		String result = consoleInputWrapper.requestUserInput();
		assertEquals(input, result);
	}
}
