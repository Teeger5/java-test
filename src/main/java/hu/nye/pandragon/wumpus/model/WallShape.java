package hu.nye.pandragon.wumpus.model;

import lombok.Getter;

/**
 * Ebben az enum-ban lehetséges fal formák vannak leírva
 * Ezek határozzák meg, milyen karakter lesz kirajzolva
 */
@Getter
public enum WallShape {
	Verticcal('┃'),
	Horizontal('━'),
	TopLeft('┏'),
	TopRight('┓'),
	BottomRight('┛'),
	BottomLeft('┗'),
	VerticalLeft('┫'),
	VerticalRight('┣'),
	HorizontalTop('┻'),
	HorizontalBottom('┳'),
	Middle('╋'),
	Single('█'),
	Startpoint('⚑');

	// ┏━━━━━━━━━━━━━━━━━┓
	// ┃        OK       ┃
	// ┗━━━━━━━━━━━━━━━━━┛

	private final char symbol;
	WallShape(char c) {
		symbol = c;
	}

	/**
	 * Ez a metódus kiválasztja a fal legmegfelelőbb alakját
	 * a körülötte lévő elemek alapján
	 * Például ha 3 fal elem L betű formában van egymás mellett,
	 * és ez az elem a sakron lévő, akkor sarok karaktert fog kiválasztani.
	 * Ez kirajzoláskor számít
	 * @param top van-e fal efelett
	 * @param right van-e fal a jobb oldalán
	 * @param bottom van-e fal alatta
	 * @param left van-e fal a bal oldalán
	 * @return az ezek alapján kiválasztott forma
	 */
	public static WallShape getShape (boolean top, boolean right, boolean bottom, boolean left) {
		var x = 0;
		if (right) { x |= 1; }
		if (bottom) { x |= 1 << 1; }
		if (left) { x |= 1 << 2; }
		if (top) { x |= 1 << 3; }
		return switch (x) {
			case 1, 4, 5 -> WallShape.Horizontal;
			case 2, 8, 10 -> WallShape.Verticcal;
			case 3 -> WallShape.TopLeft;
			case 6 -> WallShape.TopRight;
			case 7 -> WallShape.HorizontalBottom;
			case 9 -> WallShape.BottomLeft;
			case 11 -> WallShape.VerticalRight;
			case 12 -> WallShape.BottomRight;
			case 13 -> WallShape.HorizontalTop;
			case 14 -> WallShape.VerticalLeft;
			case 15 -> WallShape.Middle;
			default -> WallShape.Single;
		};
	}
}
