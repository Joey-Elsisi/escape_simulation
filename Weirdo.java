import java.io.PrintStream;

public class Weirdo extends Person {
	/**
	 * @param c a Coordinate
	 * @param map a Map
	 * @param log a PrintStream
	 */
	public Weirdo(Coord c, Map map, PrintStream log) {
		super(c, "w", map, log);
		//map.addThing(this);
	}
	public boolean isSpotOpen(Coord spot) {
		// checks for visible adjacent stickys and walls and prevLocs
		// TODO see if adding a dead method for sticky is helpful
		boolean goAhead;
		boolean smokeHere = false;
		boolean stickyHere = false;
		Thing[] lurking = map.thingsAt(spot);
		for (int i = 0; i < lurking.length; i++) {
			if (lurking[i] instanceof Smoke) {
				smokeHere = true;
			} else if (lurking[i] instanceof StickyIcky) {
				stickyHere = true;
			}
		}
		if (map.floorplan[spot.r][spot.c] == Spot.Wall) {
			goAhead = false;
		} else if (smokeHere) {
			goAhead = true;
		} else if (stickyHere) {
			goAhead = false;
		} else {
			goAhead = true;
		}
		return goAhead;
	}

	/**
	 *decides the next move for a Weirdo
	 */
	@Override
	public Coord chooseMove() {
		if (this.status == Status.Dead || this.status == Status.Safe) { //no action should be done
			return this.getLoc();
		}
		Coord nextLoc = this.getLoc().step(this.facing);
		if ((this.isSpotOpen(nextLoc)) && !nextLoc.equals(this.getPrevLoc())) {
			//map.log.println(this.toString() + " moving " + this.facing.toString());
			return this.getLoc().step(this.facing);
		}
		this.facing = facing.cycle(); //in order to exhaust every direction
		nextLoc = this.getLoc().step(this.facing); 
		if ((this.isSpotOpen(nextLoc)) && !nextLoc.equals(this.getPrevLoc())) {
			//map.log.println(this.toString() + " moving " + this.facing.toString()); 
			return this.getLoc().step(this.facing); // a step in the right direction
		}
		this.facing = facing.cycle();
		nextLoc = this.getLoc().step(this.facing);
		if ((this.isSpotOpen(nextLoc)) && !nextLoc.equals(this.getPrevLoc())) {
			//map.log.println(this.toString() + " moving " + this.facing.toString());
			return this.getLoc().step(this.facing);
		}
		this.facing = facing.cycle();
		nextLoc = this.getLoc().step(this.facing);
		if ((this.isSpotOpen(nextLoc)) && !nextLoc.equals(this.getPrevLoc())) {
			//map.log.println(this.toString() + " moving " + this.facing.toString());
			return this.getLoc().step(this.facing);
		}
		//map.log.println(this.toString() + " staying here"); // if no action is taken than remain in place
		return this.getLoc();
	}
}
