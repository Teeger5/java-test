package hu.nye.pandragon.wumpus.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class CommandUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandUtils.class);

	public static Point getCoordinates (String pointX, String pointY, int levelSize) {
		int x = pointX.toCharArray()[0] - 96;
		if (x < 1 || x > levelSize) {
			LOGGER.error("Nincs ilyen azonosítójú osztlop: {} ({})", x, pointX.toUpperCase());
			throw new RuntimeException("Nincs ilyen azonosítójú oszlop: " + pointX.toUpperCase());
		}

		int y = Integer.parseInt(pointY);
		if (y < 1 || y > levelSize) {
			var error = "Nincs ilyen azonosítójú sor: " + pointY;
			LOGGER.error(error);
			throw new RuntimeException(error);
		}

		if (x == 1 || x == levelSize || y == 1 || y == levelSize) {
			LOGGER.error("koordináta a pálya szélén");
			throw new RuntimeException("Nem lehet módosítani a pálya szélén lévő falakat");
		}
		return new Point(x, y);
	}
}
