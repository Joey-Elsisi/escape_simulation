
/**
 * @author Joseph Elsisi
 * 
 */
public interface Passable {
	
	/**
	 * @return True/False depending on whether a human can look beyond this entity
	 */
	boolean canLookThrough();
	
	/**
	 * @return True/False depending on whether a human can walk through this entity
	 */
	boolean canPassThrough();
}
