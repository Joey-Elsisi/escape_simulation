import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;

/**
 * @author Joseph Elsisi the map class represents all spots and things in the
 *         simulation
 */
public class Map {
	protected Spot[][] floorplan;
	protected Thing[] things;
	protected int thingsSize;
	protected PrintStream log;
	protected HashMap<Character, Spot> charToSpot = new HashMap<Character, Spot>();
	protected int iterNum = 0;
//	protected int numRows;
//	protected int numColumns;

	/**
	 * @param filename stores the starting Thing/Spot objects and their locations
	 * @param log      a PrintStream object for output
	 * @throws IOException
	 */
	Map(String filename, PrintStream log) throws IOException {
		this.log = log;
		this.things = new Thing[0]; // initialized with length of 0 with other functions to help fill in
		floorplan = fileToArray(filename);
		// this.initialFillUpOfThings();
	}

	/**
	 * @param c   is the char representation of the object
	 * @param loc is the location of the object
	 * @return a new Thing
	 */
	public Thing createNewThing(char c, Coord loc) {
		if (c == 'f') {
			return new Follower(loc, this, this.log);
		} else if (c == 'w') {
			return new Weirdo(loc, this, this.log);
		} else if (c == 's') {
			return new StickyIcky(loc, this, this.log);
		} else if (c == '~') {
			return new Smoke(loc, this, this.log);
		} else {
			return null;
		}
	}

	/**
	 * @param filename to be read
	 * @return an array representing floorplan
	 * @throws IOException
	 */
	public Spot[][] fileToArray(String filename) throws IOException {
		int counter = 0;
		charToSpot.put('.', Spot.Open);
		charToSpot.put('|', Spot.Wall);
		charToSpot.put('e', Spot.Exit);
		charToSpot.put('^', Spot.SignN);
		charToSpot.put('>', Spot.SignE);
		charToSpot.put('v', Spot.SignS);
		charToSpot.put('<', Spot.SignW);
		File readIn = new File(filename);
		Scanner scnr = new Scanner(readIn);
		ArrayList<String> holder = new ArrayList<String>(); // for ease of access, holds each line read in
		while (scnr.hasNext()) {
			holder.add(scnr.nextLine());
			counter++;
		}
		int length = holder.get(0).length(); // determines the number of columns in the map
		floorplan = new Spot[counter][length];
		for (int j = 0; j < counter; j++) {
			for (int i = 0; i < length; i++) {
				Character currSpot = holder.get(j).charAt(i);
				if (charToSpot.containsKey(currSpot)) {
					floorplan[j][i] = charToSpot.get(currSpot); // add a spot to that location
				} else {
					if (currSpot != null) {
						this.addThing(this.createNewThing(currSpot, new Coord(j, i))); // initial
						// applied a fix here, watch
						// this.createNewThing(currSpot, new Coord(i, j)); // out
					}
					floorplan[j][i] = Spot.Open; // assumes that the thing is on an empty space
				}
			}
		}
		scnr.close();
		return floorplan;

	}

	/**
	 * @param c the Coordinates to be checked
	 * @return true/false depending on if the Coord is within floorplan
	 */
	public boolean onMap(Coord c) {
		int x = c.r;
		int y = c.c;
		if (x < floorplan.length && x >= 0) {
			if (y < floorplan[0].length && y >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param c the location to be looked at
	 * @return the Spot at that the specified location
	 */
	public Spot spotAt(Coord c) {
		charToSpot.put('.', Spot.Open);
		charToSpot.put('|', Spot.Wall);
		charToSpot.put('e', Spot.Exit);
		charToSpot.put('^', Spot.SignN);
		charToSpot.put('>', Spot.SignE);
		charToSpot.put('v', Spot.SignS);
		charToSpot.put('<', Spot.SignW);
		if (this.onMap(c)) { // checks to see if in floorplan
			if (charToSpot.containsValue(floorplan[c.r][c.c]))
				;
			return floorplan[c.r][c.c];
		}
		return null;
	}

	/**
	 * @return the number of people still escaping the floorplan
	 */
	public int peopleRemaining() {
		int count = 0;
		for (int i = 0; i < things.length; i++) {
			if (things[i] instanceof Person) {
				Person placeholder = (Person) things[i];
				if (placeholder.status == Status.Escaping) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * a helper function
	 * 
	 * @param a the thing to be added
	 */
	public void addThing(Thing a) {
		Thing[] replacment = new Thing[things.length + 1]; // a new array with one extra spot open
		for (int i = 0; i < things.length; i++) {
			replacment[i] = things[i]; // fill up the new array
		}
		replacment[this.thingsSize] = a; // add the new thing to the last spot in the new array
		this.things = replacment;
		this.thingsSize = things.length;
	}

	/**
	 * @param c the location to be checked
	 * @return an array of things at Coord c
	 */
	public Thing[] thingsAt(Coord c) {
		ArrayList<Thing> thingsAtLoc = new ArrayList<Thing>();// a placeholder to add elements into
		for (int i = 0; i < things.length; i++) {
			if (things[i] == null) {
				continue;
			} else if (things[i].getLoc().equals(c)) {
				thingsAtLoc.add(things[i]);
			}
		}
		Thing[] returning = new Thing[thingsAtLoc.size()]; // the perfect sized array to be returned
		for (int i = 0; i < returning.length; i++) {
			returning[i] = thingsAtLoc.get(i); // fills up the array
		}
		return returning;
	}

	/**
	 * @param the Coordinate location
	 * @return true/false depending on whether the location can be looked through
	 */
	public boolean canLookThroughLocation(Coord c) {
		boolean canLookThrough = true; // if nothing obstructs the view path, true will be returned
		if (!this.onMap(c)) { // first checks to see if on map
			canLookThrough = false;
		}
		Thing[] currList = this.thingsAt(c); // collect all items at that location
		for (int i = 0; i < currList.length; i++) {
			if (currList[i] == null) {
				continue;
			} else if (!currList[i].canLookThrough()) {
				canLookThrough = false;
			}
		}
		if (this.floorplan[c.r][c.c].canLookThrough() == false) {
			canLookThrough = false; // if there is a wall at that location
		}
		return canLookThrough;
	}

	/**
	 * @param The Coordinate to pass through
	 * @return true/false depending on if the person can look through the location
	 */
	public boolean canPassThroughLocation(Coord c) {
		boolean canPassThrough = true;// if nothing obstructs the path, true will be returned
		if (!this.onMap(c)) {// first checks to see if on map
			canPassThrough = false;
		}
		Thing[] currList = this.thingsAt(c);
		for (int i = 0; i < currList.length; i++) {
			if (currList[i] == null) { // prevents errors
				continue;
			} else if (!currList[i].canPassThrough()) { // if an item that can not be passed is present, false is
														// returned
				canPassThrough = false;
			}
		}
		if (this.floorplan[c.r][c.c].canPassThrough() == false) {
			canPassThrough = false;
		}
		return canPassThrough;
	}

	/**
	 * calls on each Thing to doAction(), simulates one turn in the map
	 */
	public int getiterNum() {
		return this.iterNum;
	}
	public void iterate() {
		int iter = things.length;
//		if (this.iterNum == 0) {
//			log.println(this.toString());
//		} else {
//			log.println("iteration " + (this.iterNum - 1));
//		}
		for (int i = 0; i < iter; i++) {
			things[i].doAction(); // any threat that spawns on a person will perform its deeds immediately
		}
//		if (this.iterNum > 0) {
		log.println("map: \n" + this.toString());
//		}
		iterNum++;

	}

	/**
	 * @return a string representation of maps current iteration
	 */
	@Override
	public String toString() {
		// use .repr to distiguish between them
		String returnString = "";
		for (int i = 0; i < this.floorplan.length; i++) {
			for (int j = 0; j < this.floorplan[0].length; j++) {
				Thing[] currentList = this.thingsAt(new Coord(i, j)); // all items in the current location
				boolean thingFound = false; // if true, skip searching through the rest of currentList
				for (int a = 0; a < currentList.length; a++) {
					if (floorplan[i][j].equals(Spot.Wall)) {
						returnString = returnString.concat("|");
						thingFound = true;
						break;
					}
				}
				if (!thingFound) { // if nothing has been found yet, continue
					for (int b = 0; b < currentList.length; b++) {
						if (currentList[b] instanceof Smoke) {
							returnString = returnString.concat("~");
							thingFound = true;
							break;
						}
					}
				}
				if (!thingFound) {
					for (int c = 0; c < currentList.length; c++) {
						if (currentList[c] instanceof StickyIcky) {
							returnString = returnString.concat("s");
							thingFound = true;
							break;
						}
					}
				}
				if (!thingFound) {
					for (int d = currentList.length - 1; d >= 0; d--) {
						if (currentList[d] instanceof Person) {
							if (currentList[d] instanceof Follower) {
								returnString = returnString.concat("f");
								thingFound = true;
								break;
							}
							if (currentList[d] instanceof Weirdo) {
								returnString = returnString.concat("w");
								thingFound = true;
								break;
							}
						}
					}
				}
				if (!thingFound) { // adds remaining spots
					returnString = returnString.concat(floorplan[i][j].toString());
				}
			}
			returnString = returnString.concat("\n"); // in order to format the map
		}
		return returnString;
	}
}
