import java.io.File;

public class TestAStar {

  public static void main(String[] args) {

    if (args.length != 1) {
      System.out.println("Usage: program input.csv");
      return;
    }

    Puzzle p = Puzzle.fromFile(new File(args[0]));
    IHeuristic h = new CombinedHeuristic(
      new DistanceHeuristic(), new PairHeuristic(p.getSize()));

    long start = System.currentTimeMillis();
    Puzzle last = AStar.calcShortestPath(p, h);
    long elapsedTimeMillis = System.currentTimeMillis() - start;

    if (last != null) {
      System.out.println("Steps:");
      printPath(last);
    }

    System.out.format("Elapsed time: %1$2s min %1$2s secs\n",
      elapsedTimeMillis / (60*1000F),
      elapsedTimeMillis / 1000F);
  }

  private static void printPath(Puzzle p) {
    if (p.getParent() != null)
      printPath(p.getParent());
    System.out.println(p);
  }
}
