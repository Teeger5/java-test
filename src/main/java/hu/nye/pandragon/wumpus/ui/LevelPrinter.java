package hu.nye.pandragon.wumpus.ui;

import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.model.WallShape;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.model.entities.Wall;
import hu.nye.pandragon.wumpus.service.game.Level;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.stream.Collectors;

public class LevelPrinter {

	private static final Logger LOGGER = LoggerFactory.getLogger(LevelPrinter.class);

	private final PrintWrapper printWrapper;
	@Setter
	private Level level;

	public LevelPrinter(PrintWrapper printWrapper) {
		this.printWrapper = printWrapper;
	}

	public LevelPrinter (PrintWrapper printWrapper, Level level) {
		this.level = level;
		this.printWrapper = printWrapper;
	}

	/**
	 * Pálya kirajzolása és kiírása szerkesztést segítő módon,
	 * azaz az üres helyeken egy pont lesz,
	 * hogy könnyebben azonosítható legyen egy pont a pályán
	 */
	public void printEditorLevel () {
		printWrapper.println(drawLevel2(level.toLevelVO(), true));
	}

	/**
	 * Pálya kirajzolása és kiírása
	 */
	public void printLevel () {
		printWrapper.println(drawLevel2(level.toLevelVO(), false));
	}

	/**
	 * Kiírja a hős adatait a következő formában:
	 * Hős: ikon | oszlop_betűje sor_száma |x nyíl | Tárgyak: ...
	 * A tárgyakat csak akkor írja, ha vannak a hősnek
	 */
	public void printHeroBar () {
		printWrapper.println(drawHeroBar(level.getHero()));
	}

	/**
	 * Eldönti, hogy kell-e meghosszabbítani a falat a jobb oldalán
	 * Erre akkor van szükség, ha a formája ezt indokolja,
	 * azaz meg lehet hosszabbítani
	 * Ez akkor történhet meg, amikor,
	 * amikor nem formáz sarkot jobbra
	 * @param wall a fal, aminek a hosszabbításáról döntünk
	 * @return az eredmény, ami lehet '━' vagy ' '
	 */
	private char getWallRightExtensionSymbol (Wall wall) {
		return switch (wall.getShape()) {
			case Middle, Horizontal, HorizontalBottom, HorizontalTop, BottomLeft,
					VerticalRight, TopLeft, Single -> WallShape.Horizontal.getSymbol();
			default -> ' ';
		};
	}

	/**
	 * Eldönti, hogy kell-e meghosszabbítani a falat a bal oldalán
	 * Erre akkor van szükség, ha a formája ezt indokolja,
	 * azaz meg lehet hosszabbítani
	 * Ez akkor történhet meg, amikor,
	 * amikor nem formáz sarkot balra
	 * @param wall a fal, aminek a hosszabbításáról döntünk
	 * @return az eredmény, ami lehet '━' vagy ' '
	 */
	private char getWallLeftExtensionSymbol (Wall wall) {
		return switch (wall.getShape()) {
			case Middle, Horizontal, HorizontalBottom, HorizontalTop, BottomRight,
					VerticalLeft, TopRight, Single -> WallShape.Horizontal.getSymbol();
			default -> ' ';
		};
	}

	/**
	 * Kiírja a hős adatait
	 * - a nézési irányát
	 * - a pozícióját
	 * - a nyilainak számát
	 * - a tárgyait, ha vannak neki
	 * Tárgy most csak az arany lehet, abból is csak 1 db
	 * Ez a sor a pálya alatt jelenik meg a játékban
	 * @param hero a hős
	 * @return az adatai egy sorban összefoglalva
	 */
	private String drawHeroBar (Hero hero) {
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

	/**
	 * Kirajzolja a pályát, és visszaadja a rajzot
	 * @param levelVO a pálya
	 * @param isEditing szerkesztés közben van-e
	 * @return a pályáról készült rajz
	 */
	private String drawLevel2 (LevelVO levelVO, boolean isEditing) {
		var drawing = new StringBuilder();
		var size = levelVO.getSize();
		var staticEntities = levelVO.getStaticEntities();
		var livingEntities = levelVO.getLivingEntities();
		var startpoint = levelVO.getStartpoint();
		drawing.append("    ");
		for (int i = 0; i < size; i++) {
			drawing.append(' ').append((char) (65 + i)).append(' ');
		}
		drawing.setLength(drawing.length() - 1);
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
				char left = ' ', middle = ' ', right = ' ';
				if (staticEntity == null && livingEntity == null) {
					middle = isEditing ? '•' : ' ';
					if (!isEditing && gettingpoint.equals(startpoint)) {
						left = '>';
						right = '<';
					}
				}
				else if (staticEntity != null) {
					if (staticEntity instanceof Wall wall) {
						left = getWallLeftExtensionSymbol(wall);
						middle = wall.getDisplaySymbol();
						right = getWallRightExtensionSymbol(wall);
					}
					else if (staticEntity.shouldExtendInCell()) {
						left = staticEntity.getDisplaySymbol();
						middle = left;
						if (livingEntity != null) {
							middle = livingEntity.getDisplaySymbol();
						}
						right = left;
					}
					else if (livingEntity != null) {
						left = livingEntity.getDisplaySymbol();
						middle = staticEntity.getDisplaySymbol();
					}
					else {
						middle = staticEntity.getDisplaySymbol();
					}
				}
				else if (livingEntity != null) {
					middle = livingEntity.getDisplaySymbol();
					if (!isEditing && gettingpoint.equals(startpoint)) {
						left = '>';
						right = '<';
					}
				}
				else {
					LOGGER.debug("else: point: {}", gettingpoint);
				}
				drawing.append(left).append(middle).append(right);
			}
			drawing.setLength(drawing.length() - 1);
			drawing.append('\n');
		}
		return drawing.toString();
	}

}
