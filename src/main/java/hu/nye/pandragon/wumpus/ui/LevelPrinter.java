package hu.nye.pandragon.wumpus.ui;

import hu.nye.pandragon.wumpus.lovel.WallShape;
import hu.nye.pandragon.wumpus.lovel.entities.Entity;
import hu.nye.pandragon.wumpus.lovel.entities.Hero;
import hu.nye.pandragon.wumpus.lovel.entities.Wall;
import hu.nye.pandragon.wumpus.model.LevelVO;

import java.awt.*;

public class LevelPrinter {

	public static void printEditorLevel (LevelVO levelVO) {
		System.out.println(drawLevel(levelVO, true));
	}

	public static void printGameLevel (LevelVO levelVO, Hero hero) {
		var drawing = drawLevel(levelVO, false);
		drawing += drawHeroBar(hero);
		System.out.println(drawing);
	}

	public static void printLevel (LevelVO levelVO) {
		printLevel(levelVO, false);
	}

	private static void printLevel (LevelVO levelVO, boolean isEditing) {
		System.out.println(drawLevel(levelVO, isEditing));
	}

	public static void printHeroBar (Hero hero) {
		System.out.println(drawHeroBar(hero));
	}

	private static String drawLevel (LevelVO levelVO) {
		return drawLevel(levelVO, false);
	}

	private static String drawLevel (LevelVO levelVO, boolean isEditing) {
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
				if (staticEntity != null && staticEntity.shouldExtendInCell()) {
					if (staticEntity instanceof Wall wall) {
//						var wallExtensionSymbol = getWallExtensionSymbol(wall);
						drawing.append(getWallLeftExtensionSymbol(wall))
								.append(wall.getDisplaySymbol())
								.append(getWallRightExtensionSymbol(wall));}
					else {
						char c = staticEntity.getDisplaySymbol();
						var middle = c;
						if (livingEntity != null) {
							middle = livingEntity.getDisplaySymbol();
						}
						drawing.append(c).append(middle).append(c);
					}
				}
				else {
					Entity entity = livingEntity;
					if (entity == null) {
						entity = staticEntity;
					}
					drawing.append(' ').append(entity.getDisplaySymbol()).append(' ');
				}
			}
			drawing.append('\n');
		}
		return drawing.toString();
	}

	private static char getWallRightExtensionSymbol (Wall wall) {
		return switch (wall.getShape()) {
			case Middle, Horizontal, HorizontalBottom, HorizontalTop, BottomLeft, VerticalRight, TopLeft, Single -> WallShape.Horizontal.getSymbol();
			default -> ' ';
		};
	}

	private static char getWallLeftExtensionSymbol (Wall wall) {
		return switch (wall.getShape()) {
			case Middle, Horizontal, HorizontalBottom, HorizontalTop, BottomRight, VerticalRight, TopRight, Single -> WallShape.Horizontal.getSymbol();
			default -> ' ';
		};
	}

	private static String drawHeroBar (Hero hero) {
		return String.format(
				"Hős: %c | %d %s | %d nyíl\n",
				hero.getDisplaySymbol(),
				hero.getPosition().y,
				(char) (hero.getPosition().x + 64),
				hero.getAmmoAmount());
	}
}
