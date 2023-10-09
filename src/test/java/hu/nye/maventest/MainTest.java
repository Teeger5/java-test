package hu.nye.maventest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

	@Test
	void testMainWithNoArgs() {
		String[] args = {};
		String expectedOutput = "Ez a program üdvözöl egy opcionálisan megadott nyelven, alapértelmezetten magyarul.\nPéldául: program.jar Csaba -> Szia, Csaba!\n\tprogram.jar Csaba en -> Hello, Csaba!";
		assertEquals(expectedOutput, Main.main(args));
	}

	@Test
	void testMainWithOneArg() {
		String[] args = {"Csaba"};
		String expectedOutput = "Szia, Csaba";
		assertEquals(expectedOutput, Main.main(args));
	}

	@Test
	void testMainWithTwoArgs() {
		String[] args = {"Csaba", "en"};
		String expectedOutput = "Hello, Csaba";
		assertEquals(expectedOutput, Main.main(args));
	}

	@Test
	void testMainWithInvalidLang() {
		String[] args = {"Csaba", "fr"};
		assertThrows(NemkezeltNyelvException.class, () -> Main.main(args));
	}

	@Test
	void testNyelvParse() throws NemkezeltNyelvException {
		assertEquals(Nyelv.Magyar, Nyelv.parse("hu"));
		assertEquals(Nyelv.Angol, Nyelv.parse("en"));
		assertEquals(Nyelv.Japan, Nyelv.parse("ja"));
		assertThrows(NemkezeltNyelvException.class, () -> Nyelv.parse("fr"));
	}

	@Test
	void testNyelvGetGreetingsText() {
		assertEquals("Szia, Csaba", Nyelv.Magyar.getGreetingsText("Csaba"));
		assertEquals("Hello, Csaba", Nyelv.Angol.getGreetingsText("Csaba"));
		assertEquals("Csabaさん、 よおこそ (Csaba-san, yookoso / Üdvözöllek, Csaba)", Nyelv.Japan.getGreetingsText("Csaba"));
	}
}
