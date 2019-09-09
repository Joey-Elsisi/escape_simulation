import java.io.PrintStream;

public class Follower extends Person {

	public Follower(Coord loc, Map map, PrintStream log) {
		super(loc, "f", map, log);
		super.log = log;
		// map.addThing(this);
	}

	public boolean isSpotOpen(Coord c) {
		boolean allClear = true;// if nothing has yet obstructed the view keep on looping
		if(!map.onMap(c)) {
			return false;
		}
		Thing[] thingsHere = map.thingsAt(c);
		Spot spotHere = this.map.floorplan[c.r][c.c];
		
		if (spotHere == Spot.Wall) {
			return false;
		}
		for (int i = 0; i < thingsHere.length; i++) {
			if (thingsHere[i] instanceof Smoke) {
				return false;
			} else if (thingsHere[i] instanceof StickyIcky) {
				this.die();
				return true;
			}
		}
		if (c == this.getPrevLoc()) {
			return false;
		}
		return allClear;
	}

	/**
	 * Follower finds signs and exits as long as path vision is not blocked and they
	 * are directly north/east/west of the Follower Followers will step into a
	 * Sticky
	 */
	@Override
	public Coord chooseMove() {
		// possibly check if currently on a sticky?
		Spot thisSpot = map.floorplan[this.getLoc().r][this.getLoc().c];
		if (this.status == Status.Dead || this.status == Status.Safe) { // no need to move on in these states
			return this.getLoc();
		}
		// if standing on a direction sign, turn to that direction
		if (thisSpot == Spot.SignE) {
			this.facing = Direction.E;
		}
		if (thisSpot == Spot.SignN) {
			this.facing = Direction.N;
		}
		if (thisSpot == Spot.SignS) {
			this.facing = Direction.S;
		}
		if (thisSpot == Spot.SignW) {
			this.facing = Direction.W;
		}
		boolean spotFound = false;// if no spot is found, remains false
		Direction direction = this.facing;
		for (int i = 0; i < 4; i++) {
			if (spotFound) {
				//map.log.println(this.toString() + " moving " + this.facing);
				return this.getLoc().step(direction);
			}
			Coord nextLoc = this.getLoc().step(this.facing);
			while (this.isSpotOpen(nextLoc)) {
				if (map.floorplan[nextLoc.r][nextLoc.c].isSign()) {
					direction = this.facing;
					spotFound = true;
					break;
				} else if (map.floorplan[nextLoc.r][nextLoc.c] == Spot.Exit) {
					direction = this.facing;
					spotFound = true;
					break;
				}
				nextLoc = nextLoc.step(this.facing);
			}
			this.facing.cycle();
		}
		//map.log.println(this.toString() + " staying here");
		return this.getLoc();
	}
}