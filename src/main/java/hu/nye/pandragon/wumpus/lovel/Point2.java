package hu.nye.pandragon.wumpus.lovel;

import java.util.Objects;

/**
 * Ez az osztály a beépített Point osztály kiváltására van, ha szükség lenne rá
 */
public class Point2 implements Comparable {
	public int x, y;

	public Point2(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Point2 point = (Point2) o;
		return x == point.x && y == point.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public int compareTo(Object o) {
		var p2 = (Point2) o;
		if (x >  p2.x) {
			return 1;
		}
		if (x < p2.x) {
			return -1;
		}
		if (y > p2.y) {
			return 1;
		}
		if (y < p2.y) {
			return -1;
		}
		return 0;
	}
}
