import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class Panic {

	/**
	 * @param args takes in a file name and a Printstream
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Map sim;
		if(args.length == 1) {
			sim = new Map(args[0], System.out);
		}
		else {
			sim = new Map(args[0], new PrintStream(args[1]));
		}
		sim.log.println("begin simulation");
		Thing[] beginners = sim.things;
		for(Thing eachOne : beginners) {
			sim.log.println(eachOne);
		}
		sim.log.println(sim.toString());
		while(sim.peopleRemaining() != 0) {
//			if (sim.getiterNum() == 0) {
//				sim.log.println(sim.toString());
//			} else {
//				sim.log.println("iteration " + (sim.getiterNum() - 1));
//			}
			sim.log.println("iteration " + (sim.getiterNum()));
			sim.iterate();
		}
		sim.log.println("end simulation");
	}
}
