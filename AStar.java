import java.util.ArrayList;
import java.util.Collections;

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

  private static int calcHeuristic(Puzzle current, Puzzle goal) {
    return 0;
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
