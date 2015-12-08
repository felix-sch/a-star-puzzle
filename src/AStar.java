import java.lang.Math;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/** This class perfoms all the relevant steps for a successul a*-star routine.
  *
  * @author Felix Schaumann, Dominic Pfeil
  * @version 1.0
  */
class AStar {

  private static PriorityQueue<Puzzle> open = new PriorityQueue<Puzzle>(10,
    new Comparator<Puzzle>() {
      @Override
      public int compare(Puzzle p1, Puzzle p2) {
        return p1.getSteps() + p1.getHeuristic()
          - (p2.getSteps() + p2.getHeuristic());
      }
    });
  private static ArrayList<Puzzle> closed = new ArrayList<Puzzle>();

  /** Performs the a*-star algorithm itself.
    *
    * @param start start-puzzle (node)
    * @param heuristic heuristic that defines the weightings of nodes
    *
    * @return solved puzzle
    */
  public static Puzzle calcShortestPath(Puzzle start, IHeuristic heuristic) {

    open.clear();
    closed.clear();
    open.add(start);

    while (open.size() != 0) {
      Puzzle current = open.poll();
      closed.add(current);

      if (current.isSolved()) {
        System.out.format("Open=%d Closed=%d\n", open.size(), closed.size());
        return current;
      }
      
      if (closed.size() % 1000 == 0) {
        System.out.println(current);
        System.out.format("Open=%d Closed=%d\n", open.size(), closed.size());
      }

      for (Puzzle neighbor: getNeighbors(current)) {
        if (closed.contains(neighbor))
          continue;

        neighbor.setHeuristic(heuristic.calcHeuristic(neighbor));

        Puzzle existing = null;
        for (Iterator<Puzzle> it = open.iterator(); it.hasNext();) {
          Puzzle tmp = it.next();
          if (tmp.equals(neighbor)) {
            existing = tmp;
            break;
          }
        }

        if (existing == null) {
          open.add(neighbor);
        } else {
          if (existing.getHeuristic() + existing.getSteps()
            > neighbor.getHeuristic() + neighbor.getSteps()) {
            open.remove(existing);
            open.add(neighbor);
          }
        }
      }
    }

    return null;
  }

  /** Determines all possible neighbors of a given node.
    *
    * @param current current node of the puzzle
    * @return array of all possible neighbors
    */
  private static Puzzle[] getNeighbors(Puzzle current) {
    ArrayList<Puzzle> neighbors = new ArrayList<Puzzle>();
    for (int i=0; i<current.getNumbers().length; i++) {
      neighbors.add(current.shiftRight(i));
      neighbors.add(current.shiftLeft(i));
      neighbors.add(current.shiftUp(i));
      neighbors.add(current.shiftDown(i));
    }
    return neighbors.toArray(new Puzzle[neighbors.size()]);
  }
}
