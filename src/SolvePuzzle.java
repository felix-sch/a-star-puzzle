import java.io.File;

/** This is the main class of the A*-Puzzle-Solver.
  *
  * @author Felix Schaumann, Dominic Pfeil
  * @version 1.0
  */
public class SolvePuzzle {

  protected final static String usageMsg =
    "Usage: program input.csv\n\nSample input.csv:\n8;4;3\n1;9;7\n2;6;5\n0;;\n";

  /** Main method of the program. Gets puzzle from file.
    *
    * @param args command line arguments (csv-file)
    */
  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.print(usageMsg);
      return;
    }

    Puzzle last = solve(
      Puzzle.fromFile(new File(args[0])), new DistanceToPowerHeuristic(2));

  	printPath(last);
  }

  /** Solves puzzle using given heuristic and prints results.
    *
    * @param p Puzzle-node
    * @param h Heuristic
    *
    * @return solved puzzle (last state)
    */
  protected static Puzzle solve(Puzzle p, IHeuristic h) {
    long start = System.currentTimeMillis();
    Puzzle last = AStar.calcShortestPath(p, h);
    long elapsedTimeMillis = System.currentTimeMillis() - start;

    if (last == null) {
      System.out.println("A* was not smart enough to deliver a valid solution. "
        + "Might be the input puzzle broken?");
    } else {
      System.out.println(h + ": Steps=" + getSteps(last));
    }

    System.out.format("Elapsed time: %f min (%f secs)\n",
      elapsedTimeMillis / (60*1000F),
      elapsedTimeMillis / 1000F);
    System.out.println("----------------------------------------");

    return last;
  }

  /** Prints path from start to a given end node.
    *
    * @param p Puzzle-node
    */
  protected static void printPath(Puzzle p) {
    if (p.getParent() != null)
      printPath(p.getParent());
    System.out.println(p);
  }

  /** Counts number of steps from start to destination puzzle.
    *
    * @param p Puzzle-node
    *
    * @return steps
    */
  protected static int getSteps(Puzzle p) {
    if (p.getParent() != null)
      return getSteps(p.getParent()) + 1;
    return 0;
  }
}
