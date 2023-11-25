package hu.nye.pandragon.wumpus.service.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Ez az osztály felel a .env fájlből való olvasásért
 * Itt használom először a Lombok-ot,
 * szükséges a Lombok IntelliJ plugin,
 * hogy ne jelezzen hibát
 */
@Slf4j
public class DotenvUtil {
	private final Map<String, String> variables;
	public String get (String key) {
		return variables.get(key);
	}

	public DotenvUtil() {
		variables = loadDotenv();
	}

	private Map<String, String> loadDotenv () {
		try {
			return Files.readAllLines(Path.of(".env")).stream()
					.map(s -> s.contains("=") ?
							new String[] { s.substring(0, s.indexOf('=')), s.substring(s.indexOf('=') + 1) }
							: new String[] { s, "" })
					.collect(Collectors.toMap(x -> x[0], x -> x[1]));
		} catch (IOException e) {
			log.error("Hiba a .env fájl olvasásakor: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
