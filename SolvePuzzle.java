import java.io.File;

class SolvePuzzle {

  public static void main(String[] args) {

    if (args.length != 1) {
      System.out.print(
        "Usage: program input.csv\n\nSample input.csv:\n" +
        "8;4;3\n1;9;7\n2;6;5\n0;;\n");
      return;
    }

    Puzzle p = Puzzle.fromFile(new File(args[0]));
    IHeuristic h = new CombinedHeuristic(
      new DistanceHeuristic(), new PairHeuristic(p.getSize()));

    long start = System.currentTimeMillis();
    Puzzle last = AStar.calcShortestPath(p, h);
    long elapsedTimeMillis = System.currentTimeMillis() - start;

    if (last == null) {
      System.out.println("A* was not smart enough to deliver a valid solution. "
        + "Might be " + args[0] + " broken?");
      } else {
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