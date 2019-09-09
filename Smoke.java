import java.io.PrintStream;

/**
 * @author Joseph Elsisi
 *
 */

public class Smoke extends Threat{
	/**
	 * @param c a Coordinate
	 * @param map a Map
	 * @param log a PrintStream
	 */
	public Smoke(Coord c, Map map, PrintStream log) {
		super(c, "~", 2, map, log);
		this.charge = 0;
		//map.addThing(this);
	}
	@Override public void spawn(Coord c) {
		this.charge = 0;
		Thing[] stuffHere = map.thingsAt(c);
		boolean canSpawnHere = true;
		for(int i = 0; i < stuffHere.length; i++) {
			if(stuffHere[i] instanceof Smoke  || map.floorplan[c.r][c.c] == Spot.Wall) {
				canSpawnHere = false;
			}
		}
		if(canSpawnHere) {
			map.addThing(map.createNewThing('~', c));
			//map.createNewThing('~', c);
			this.map.log.println("~"+c.toString() + "spawned");
			//this.log.println("~"+c.toString() + "spawned");
		}
		
	}
	@Override public boolean canLookThrough() {
		return false;
	}
	@Override public boolean canPassThrough() {
		return true;
	}
	
}
