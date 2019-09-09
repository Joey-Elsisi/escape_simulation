
/**
 * @author Joseph Elsisi
 * A Spot represents a feature on any given spot on a map
 */
public enum Spot implements Representable, Passable {
	Open("."), Wall("|"), Exit("e"), SignN("^"), SignE(">"), SignS("v"), SignW("<");
	public final String repr;
	/**
	 * @param representation is a string used to represent a Spot on a map
	 */
	Spot(String representation){
		this.repr = representation;
	}
	/**
	 * @return true if the Spot gives a Direction
	 */
	public boolean isSign() {
		switch(this) {
		case SignN:
		case SignE:
		case SignS:
		case SignW:
			return true;
		default:
			return false;
		}
	}
	/**
	 *@return the String representation
	 */
	@Override public String toString() {
		return repr;
	}
	/**
	 *@return the String representation
	 */
	@Override public String repr() {
		return repr;
	}
	/**
	 *@return true if the Spot does not block a Thing objects view
	 */
	@Override public boolean canLookThrough() {
		if(this == Wall) {
			return false;
		}
		return true;
	}
	/**
	 *@return true if the Spot does not block a Thing object from landing on a space
	 */
	@Override public boolean canPassThrough() {
		if(this == Wall) {
			return false;
		}
		return true;
		}
	}
