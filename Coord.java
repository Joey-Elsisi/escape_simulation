
/**
 * @author Joseph Elsisi
 * class Coord stores row/column values for a coordinate plain
 */
public class Coord {
	public final int r, c;
	
	/**
	 * @param r takes int row
	 * @param c takes int column
	 */
	public Coord(int r, int c) {
		this.r = r;
		this.c = c;
	}

	/**
	 * @param d a Direction
	 * @return a new Coordinate one step foward in the given direction
	 */
	public Coord step(Direction d) {
		if (d == Direction.N) {
			return new Coord(this.r - 1, this.c);
		} else if (d == Direction.E) {
			return new Coord(this.r, this.c + 1);
		} else if (d == Direction.S) {
			return new Coord(this.r + 1, this.c);
		} else if (d == Direction.W) {
			return new Coord(this.r, this.c - 1);
		}
		return new Coord(this.r, this.c);
	}

	/**
	 * @return a Coord of the same location
	 */
	public Coord copy() {
		return new Coord(this.r, this.c);
	}

	/**
	 * @param an Object to be compared
	 * @return true/false depending on both row and column being equal
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Coord) {
			Coord a = (Coord) o;
			if(this.r == a.r) {
				if(this.c == a.c) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param other a location to be compared
	 * @return true/false depending on the Coords being adjacent to each other
	 */
	public boolean adjacent(Coord other) {
		int difference;
		difference = Math.abs(this.r - other.r);
		difference += Math.abs(this.c - other.c);
		if (difference <= 1) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 *@return "@(row, column)"
	 */
	public String toString() {
		return "@(" + this.r + "," + this.c + ")";
	}
}
