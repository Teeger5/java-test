package hu.nye.pandragon.wumpus.ui;

import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.model.WallShape;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.model.entities.Wall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.stream.Collectors;

public class LevelPrinter {

	private static final Logger LOGGER = LoggerFactory.getLogger(LevelPrinter.class);

	private PrintWrapper printWrapper;

	public LevelPrinter(PrintWrapper printWrapper) {
		this.printWrapper = printWrapper;
	}

	/**
	 * Pálya kirajzolása és kiírása szerkesztést segítő módon,
	 * azaz az üres helyeken egy pont lesz,
	 * hogy könnyebben azonosítható legyen egy pont a pályán
	 * @param levelVO a pálya
	 */
	public void printEditorLevel (LevelVO levelVO) {
		printWrapper.println(drawLevel(levelVO, true));
	}

	/**
	 * Pálya kirajzolása és kiírása
	 * @param levelVO a pálya
	 */
	public void printLevel (LevelVO levelVO) {
		printWrapper.println(drawLevel(levelVO, false));
	}

	/**
	 * Kiírja a hős adatait a következő formában:
	 * Hős: ikon | oszlop_betűje sor_száma |x nyíl | Tárgyak: ...
	 * A tárgyakat csak akkor írja, ha vannak a hősnek
	 * @param hero a hős
	 */
	public void printHeroBar (Hero hero) {
		printWrapper.println(drawHeroBar(hero));
	}

	/**
	 * Kirajzolja a pályát, és visszaadja a rajzot
	 * @param levelVO a pálya
	 * @param isEditing szerkesztés közben van-e
	 * @return a pályáról készült rajz
	 */
	public String drawLevel (LevelVO levelVO, boolean isEditing) {
		var drawing = new StringBuilder();
		var size = levelVO.getSize();
		var staticEntities = levelVO.getStaticEntities();
		var livingEntities = levelVO.getLivingEntities();
		drawing.append("    ");
		for (int i = 0; i < size; i++) {
			drawing.append(' ').append((char) (65 + i)).append(' ');
		}
		drawing.append('\n');
		var gettingpoint = new Point(0, 0);
		for (int y = 1; y <= size; y++) {
			drawing.append(String.format(" %2d ", y));
			for (int x = 1; x <= size; x++) {
				gettingpoint.setLocation(x, y);
				var livingEntity = livingEntities.get(gettingpoint);
//				logger.debug(String.format("  -> %2d %2d %s", j, i, (entity == null ? "null" : entity.getName())));
				var staticEntity = staticEntities.get(gettingpoint);
//				logger.debug(String.format(" ==> %2d %2d %s", j, i, (entity == null ? "null" : entity.getName())));
				if (staticEntity == null && livingEntity == null) {
					var c = isEditing ? '•' : ' ';
					drawing.append(' ').append(c).append(' ');
					continue;
				}
				if (staticEntity != null) {
					if (staticEntity instanceof Wall wall) {
//						var wallExtensionSymbol = getWallExtensionSymbol(wall);
						drawing.append(getWallLeftExtensionSymbol(wall))
								.append(wall.getDisplaySymbol())
								.append(getWallRightExtensionSymbol(wall));
						continue;
					}
					if (staticEntity.shouldExtendInCell()) {
						char c = staticEntity.getDisplaySymbol();
						var middle = c;
						if (livingEntity != null) {
							middle = livingEntity.getDisplaySymbol();
						}
						drawing.append(c).append(middle).append(c);
					}
					else if (livingEntity != null) {
						drawing.append(livingEntity.getDisplaySymbol()).append(staticEntity.getDisplaySymbol()).append(' ');
					}
					else {
						drawing.append(' ').append(staticEntity.getDisplaySymbol()).append(' ');
					}
				}
				else if (livingEntity != null) {
/*					Entity entity = livingEntity;
					if (entity == null) {
						entity = staticEntity;
					}*/
					drawing.append(' ').append(livingEntity.getDisplaySymbol()).append(' ');
				}
				else {
					LOGGER.debug("else: point: {}, livingEntity: {}, staticEntity: {}", gettingpoint, livingEntity, staticEntity);
				}
			}
			drawing.append('\n');
		}
		return drawing.toString();
	}

	private char getWallRightExtensionSymbol (Wall wall) {
		return switch (wall.getShape()) {
			case Middle, Horizontal, HorizontalBottom, HorizontalTop, BottomLeft,
					VerticalRight, TopLeft, Single -> WallShape.Horizontal.getSymbol();
			default -> ' ';
		};
	}

	private char getWallLeftExtensionSymbol (Wall wall) {
		return switch (wall.getShape()) {
			case Middle, Horizontal, HorizontalBottom, HorizontalTop, BottomRight,
					VerticalLeft, TopRight, Single -> WallShape.Horizontal.getSymbol();
			default -> ' ';
		};
	}

	public String drawHeroBar (Hero hero) {
		var barText = String.format(
				"Hős: %c | %s %d | %d nyíl",
				hero.getDisplaySymbol(),
				(char) (hero.getPosition().x + 64),
				hero.getPosition().y,
				hero.getAmmoAmount());
		if (!hero.getInventory().isEmpty()) {
			barText = String.format("%s | Tárgyak: %s", barText,
					hero.getInventory().entrySet().stream()
							.map(x -> String.format("%d x %s", x.getValue(), x.getKey().toString()))
							.collect(Collectors.joining(", ")));
		}
		return barText;
	}
}
