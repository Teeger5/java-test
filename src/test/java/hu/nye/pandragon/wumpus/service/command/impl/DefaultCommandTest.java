package hu.nye.pandragon.wumpus.service.command.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class DefaultCommandTest {
	DefaultCommand defaultCommand;
	static String INPUT_STRING = "konshuu konsaato ni ikimasu";

	@BeforeEach
	public void setup () {
		defaultCommand = new DefaultCommand();
	}

	@Test
	public void shouldMatch () {
		Assertions.assertTrue(defaultCommand.match(INPUT_STRING).isCommandMatches());
	}

	/**
	 * A log bejegyzés miatt más módszert
	 * kell keresni nnek a teszteléséhez
	 */
	@Test
	public void shouldPrintMessage () {
		var outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));
		defaultCommand.process(INPUT_STRING);
		var result = outputStreamCaptor.toString().trim();
		// az 1. sor log
		result = result.substring(result.indexOf('\n') + 1);
		Assertions.assertEquals("Ismeretlen parancs: " + INPUT_STRING, result);
	}
}