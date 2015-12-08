import static org.junit.Assert.assertEquals;

import org.junit.Test;
public class AStarTest {

  @Test
  public void puzzleMovesShouldWork() {
    Puzzle created = Puzzle.createGoal(3);
    Puzzle goal = new Puzzle(new int[][] {{1,2,3}, {4,5,6}, {7,8,9}}, 0);
    Puzzle l = new Puzzle(new int[][] {{2,3,0}, {4,5,6}, {7,8,9}}, 1);
    Puzzle r = new Puzzle(new int[][] {{0,1,2}, {4,5,6}, {7,8,9}}, 3);
    Puzzle d = new Puzzle(new int[][] {{0,2,3}, {1,5,6}, {4,8,9}}, 7);
    Puzzle u = new Puzzle(new int[][] {{4,2,3}, {7,5,6}, {0,8,9}}, 1);

    assertEquals("Created puzzle must equal goal", goal, created);
    assertEquals("Shift puzzle left", l, goal.shiftLeft(0));
    assertEquals("Shift puzzle right", r, goal.shiftRight(0));
    assertEquals("Shift puzzle down", d, goal.shiftDown(0));
    assertEquals("Shift puzzle up", u, goal.shiftUp(0));
  }

  @Test
  public void heuristicsShouldBeCalculatedCorrectly() {
    Puzzle goal = Puzzle.createGoal(3);
    Puzzle p = new Puzzle(new int[][] {{9,3,4}, {6,5,0}, {7,2,8}}, 1);

    DistanceHeuristic dh = new DistanceHeuristic();
    PairHeuristic ph = new PairHeuristic(p.getSize());
    CombinedHeuristic ch = new CombinedHeuristic(dh, ph);

    assertEquals("Heuristic of goal state should 0", 0, dh.calcHeuristic(goal));
    assertEquals("Heuristic of goal state should 0", 0, ph.calcHeuristic(goal));
    assertEquals("Heuristic of goal state should 0", 0, ch.calcHeuristic(goal));

    assertEquals("Distance heuristic should be 12", 12, dh.calcHeuristic(p));
    assertEquals("Pair heuristic should be 12", 12, ph.calcHeuristic(p));
    assertEquals("Combined heuristic should be 24", 24, ch.calcHeuristic(p));
  }

  @Test
  public void algoShouldSolvePuzzle() {
    Puzzle p = new Puzzle(new int[][] {{8,4,3}, {1,9,7}, {2,6,5}}, 0);
    IHeuristic h = new CombinedHeuristic(
      new DistanceHeuristic(), new PairHeuristic(p.getSize()));

    Puzzle last = AStar.calcShortestPath(p, h);

    assertEquals("Puzzle must be solved", true, last.isSolved());
  }

}
