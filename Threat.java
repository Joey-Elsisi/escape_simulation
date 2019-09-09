import java.io.PrintStream;

public abstract class Threat extends Thing{
	protected int charge;
	protected final int fullCharge;
	public Threat(Coord c, String repr, int fullCharge, Map map, PrintStream log) {
		super(c, repr, map, log);
		this.charge = 0;
		this.fullCharge = fullCharge;
	}
	public abstract void spawn(Coord c);
	/**
	 *attempts 4 different spawn locations
	 *increments charge, than if fullCharge is reached, sets charge to zero
	 */
	@Override public void doAction() {
		charge++;
		if(charge >= fullCharge) {
			this.charge = 0;
			spawn(this.getLoc().step(Direction.N));
			spawn(this.getLoc().step(Direction.E));
			spawn(this.getLoc().step(Direction.S));
			spawn(this.getLoc().step(Direction.W));
		charge = 0;
		}
		
	}
}
