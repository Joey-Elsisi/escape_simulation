import java.io.PrintStream;

/**
 * @author Joseph Elsisi
 *represents all objects moving on a map
 */
public abstract class Thing implements Representable, Passable {
	private Coord loc, prevLoc;
	public final String repr;
	protected PrintStream log;
	protected Map map;
	/**
	 * @param c a Coordinate 
	 * @param repr a String representation
	 * @param map a Map
	 * @param log a PrintStream
	 */
	public Thing(Coord c, String repr, Map map, PrintStream log) {
		loc = c;
		prevLoc = c;
		this.repr = repr;
		this.map = map;
		this.log = log;
	}

	public abstract void doAction();
	/**
	 * @return the current location
	 */
	public Coord getLoc() {
		return this.loc;
	}
	/**
	 * @return the previous location
	 */
	public Coord getPrevLoc() {
		return this.prevLoc;
	}
	/**
	 * @param a coordinate
	 * sets a new location
	 */
	public void setLoc(Coord c) {
		prevLoc = loc;
		loc = c;
	}
	@Override public String repr() {
		return repr;
	}
	@Override public String toString() {
		return "" + repr + this.getLoc();
	}
}
