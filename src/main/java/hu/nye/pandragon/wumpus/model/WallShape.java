package hu.nye.pandragon.wumpus.model;

import lombok.Getter;

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
	Single('█');

	// ┏━━━━━━━━━━━━━━━━━┓
	// ┃        OK       ┃
	// ┗━━━━━━━━━━━━━━━━━┛

	private final char symbol;
	WallShape(char c) {
		symbol = c;
	}

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
