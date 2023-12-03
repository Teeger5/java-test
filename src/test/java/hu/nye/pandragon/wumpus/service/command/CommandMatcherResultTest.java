package hu.nye.pandragon.wumpus.service.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommandMatcherResultTest {

	@Test
	public void shouldContainMessage () {
		var result = CommandMatcherResult.ofInproperSyntax("Doragon wa tsuyoi desu");
		Assertions.assertFalse(result.canBeProcessed());
	}

	@Test
	public void shouldBeProcessed () {
		var result = CommandMatcherResult.ofCorrectMatchingCommand();
		Assertions.assertTrue(result.canBeProcessed());
	}

	@Test
	public void shouldNotBeProcessed () {
		var result = CommandMatcherResult.ofNotMatchingCommand();
		Assertions.assertFalse(result.canBeProcessed());
	}
}