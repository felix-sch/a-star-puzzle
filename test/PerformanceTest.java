import java.io.File;

public class PerformanceTest extends SolvePuzzle {

  public static void main(String[] args) {

    if (args.length != 1) {
      System.out.print(usageMsg);
      return;
    }

    Puzzle p = Puzzle.fromFile(new File(args[0]));

    IHeuristic distance = new DistanceHeuristic();
    IHeuristic distancePower2 = new DistanceToPowerHeuristic(2);
    IHeuristic pair = new PairHeuristic(p.getSize());
    CombinedHeuristic combined = new CombinedHeuristic(distancePower2, pair);

    System.out.println("Running performance tests for puzzle: ");
    System.out.println(p);

    solve(p, distance);
    solve(p, distancePower2);
    solve(p, pair);
    solve(p, combined);
    combined.setWeightings(1,2);
    solve(p, combined);
    combined.setWeightings(1,3);
    solve(p, combined);
    combined.setWeightings(2,1);
    solve(p, combined);
    combined.setWeightings(3,1);
    solve(p, combined);

    System.out.println("Performance tests finished.");
  }
}
