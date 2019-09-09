import java.io.PrintStream;

public abstract class Person extends Thing {
	protected Direction facing;
	protected Status status;

	/**
	 * @param loc a Coordinate
	 * @param repr a String representation
	 * @param map a Map
	 * @param log a PrintStream
	 */ 
	public Person(Coord loc, String repr, Map map, PrintStream log) {
		super(loc, repr, map, log);
		//super.log = log;
		facing = Direction.N;
		status = Status.Escaping;
		// map.addThing(this);
	}

	public abstract Coord chooseMove();

	/**
	 *calls on child classes implementation of chooseMove() 
	 */
	@Override
	public void doAction() {
		if (this.status == Status.Escaping) { //only persons still escaping should me called on
			Coord nextLocation = this.chooseMove();
			this.setLoc(nextLocation);
			if(this.getLoc() == this.getPrevLoc()) {
				map.log.println(this.toString() + " staying here");
			}
			else {
				map.log.println(this.toString() + " moving " + this.facing);
			}
		}

	}

	/**
	 * @return true if safe
	 */
	public boolean isSafe() {
		if (status == Status.Safe) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * sets status to Status.Dead
	 */
	public void die() {
		this.status = Status.Dead;
	}

	/**
	 * calls on parent class setLoc, than updates the Status of person if they landed on an exit
	 */
	@Override
	public void setLoc(Coord newLoc) {
		super.setLoc(newLoc);
		if (this.map.onMap(newLoc)) {
			if (map.floorplan[newLoc.r][newLoc.c] == Spot.Exit) {
				status = Status.Safe;
				map.log.println(this.toString() + " safe");
			}
		}
		/*
		 * 
		 */
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
	
	abstract boolean isSpotOpen(Coord spot); //check the spot for availability depending on the persons constrains

}
