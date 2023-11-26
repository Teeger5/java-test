package hu.nye.pandragon.wumpus.service.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
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
		this(Path.of(".env"));
	}

	public DotenvUtil (Path file) {
		this.variables = loadDotenv(readFile(file));
	}

	public DotenvUtil (String text) {
		this.variables = loadDotenv(List.of(text.split("\n")));
	}

	private List<String> readFile (Path file) {
		try {
			return Files.readAllLines(file);
		} catch (IOException e) {
			log.error("Hiba a .env fájl olvasásakor ({}): {}", file, e.getMessage());
			throw new RuntimeException(e);
		}
	}

	private Map<String, String> loadDotenv (List<String> lines) {
		return lines.stream()
				.map(s -> s.contains("=") ?
						new String[] { s.substring(0, s.indexOf('=')), s.substring(s.indexOf('=') + 1) }
						: new String[] { s, "" })
				.collect(Collectors.toMap(x -> x[0], x -> x[1]));
	}
}
