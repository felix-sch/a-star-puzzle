import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;

class AStar {
  private static ArrayList<Puzzle> open = new ArrayList<Puzzle>();
  private static ArrayList<Puzzle> closed = new ArrayList<Puzzle>();

  public static Puzzle calcShortestPath(Puzzle start, Puzzle goal) {

    open.clear();
    closed.clear();

    open.add(start);

    while (open.size() != 0) {

      Puzzle current = open.get(0);

      if (current.equals(goal)) {
        return current;
      }

      open.remove(current);
      closed.add(current);

      for (Puzzle neighbor: getNeighbors(current)) {

        if (closed.contains(neighbor))
          continue;

        neighbor.setHeuristic(calcHeuristic(neighbor, goal));

        if (open.contains(neighbor)) {
          open.set(open.indexOf(neighbor), neighbor);
        } else {
          open.add(neighbor);
        }
      }

      Collections.sort(open);
    }

    return null;
  }

  public static int calcHeuristic(Puzzle current, Puzzle goal) {
    int heuristic = 0;
    // calculate inner field distance

    for (int i=0; i<goal.getNumbers().length; i++) {
      for (int j=0; j<goal.getNumbers().length; j++) {
        int currentNumber = goal.getNumbers()[i][j];
        int[] currentPosition = new int[] {i, j};
        int[] desiredPosition = new int[] {(currentNumber %
          goal.getNumbers().length + (goal.getNumbers().length - 1)) %
          goal.getNumbers().length, currentNumber / goal.getNumbers().length};

        int innerDistance = Math.abs(currentPosition[0] - desiredPosition[0]) +
          Math.abs(currentPosition[1] - desiredPosition[1]);

        int outerDistance;
        int smallerX = Math.min(currentPosition[0], Math.abs(4-currentPosition[0]));
        int smallerY = Math.min(currentPosition[1], Math.abs(4-currentPosition[1]));
        int shortestWallCurrent = Math.min(smallerX, smallerY);

        smallerX = Math.min(desiredPosition[0], Math.abs(4-desiredPosition[0]));
        smallerY = Math.min(desiredPosition[1], Math.abs(4-desiredPosition[1]));
        int shortestWallDesired = Math.min(smallerX, smallerY);

        outerDistance = Math.min(shortestWallCurrent, shortestWallDesired) + 2;
        heuristic += Math.min(outerDistance, innerDistance);
        System.out.println(heuristic);
      }
    }
    return heuristic;
  }

  private static Puzzle[] getNeighbors(Puzzle current) {
    ArrayList<Puzzle> neighbors = new ArrayList<Puzzle>();
    for (int i=0; i<5; i++) {
      neighbors.add(current.shiftRight(i));
      neighbors.add(current.shiftLeft(i));
      neighbors.add(current.shiftUp(i));
      neighbors.add(current.shiftDown(i));
    }
    return neighbors.toArray(new Puzzle[neighbors.size()]);
  }
}
