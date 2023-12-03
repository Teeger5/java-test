package hu.nye.pandragon.wumpus.service.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

/**
 * A Dotenv tesztelésében fájlbeolvasásra is szükség van,
 * ezért a fájlnak léteznie kell a tesztek futtatásához
 */
class DotenvUtilTest {
	DotenvUtil dotenvUtil;

	@BeforeEach
	public void setup () {
		var text = """
				DBLINK=yama
				DBUSER
				DBPASS=toratoiimasu
				""";
		dotenvUtil = new DotenvUtil(text);
	}

	/**
	 * Ez a teszt a .env fájlból olvasást teszteli,
	 * ezért a fájlnak léteznie kell itt
	 * 2 assert metódus is van benne,
	 * a NullPointerException elkerüléséért
	 */
	@Test
	public void shouldGetVariableFromFile () {
		dotenvUtil = new DotenvUtil();
		var result = dotenvUtil.get("DBLINK");
		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
	}

	@Test
	public void shouldThrowExceptionFileNotFound () {
		Assertions.assertThrows(RuntimeException.class, () -> new DotenvUtil(Path.of("")));
	}

	@Test
	public void shouldGetVariableCorrectly () {
		var result = dotenvUtil.get("DBLINK");
		Assertions.assertEquals("yama", result);
	}

	@Test
	public void shouldGetEmptyValue () {
		var result = dotenvUtil.get("DBUSER");
		Assertions.assertEquals("", result);
	}
}