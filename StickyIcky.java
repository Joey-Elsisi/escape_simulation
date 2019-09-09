import java.io.PrintStream;

/**
 * @author Joseph Elsisi
 * StickyIcky's are Threats that kill people
 */
public class StickyIcky extends Threat{
	/**
	 * @param loc a Coord
	 * @param map a Map
	 * @param log a PrintStream
	 */
	public StickyIcky(Coord loc, Map map, PrintStream log) {
		super(loc, "s", 4, map, log);
		//map.addThing(this);

	}

	/**
	 *@return true
	 */
	@Override
	public boolean canLookThrough() {
		return true;
	}

	/**
	 *@return true
	 */
	@Override
	public boolean canPassThrough() {
		return true;
	}

	/**
	 *tests the adjacent area and determines if a new StickyIcky will form
	 */
	@Override
	public void spawn(Coord c) {
		Thing[] stuffHere = map.thingsAt(c);
		boolean canSpawnHere = true; //as long as nothing is in the way, a new Sticky will form
		for(int i = 0; i < stuffHere.length; i++) {
			if(stuffHere[i] instanceof StickyIcky  || map.floorplan[c.r][c.c] == Spot.Wall) {
				canSpawnHere = false;
			}
		}
		if(canSpawnHere) {
			map.addThing(map.createNewThing('s', c));
			//map.createNewThing('s', c);
			map.log.println("s"+c.toString() + "spawned");
		}
		for(int i = 0; i < stuffHere.length; i++) {
			if(stuffHere[i] instanceof Person) {
				Person temp = (Person) stuffHere[i];
				temp.die(); //kills all people in the same location
				map.log.println(this.toString() + " killed " + temp.toString());
			}
		}
	}
	@Override public void doAction() {
		super.doAction();
		//FIXME kill person
	}
}
