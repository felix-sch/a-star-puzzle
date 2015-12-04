import java.lang.Math;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

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

  private static class Pair {
    private int one, two;
    public Pair(int one, int two) {
      this.one = one;
      this.two = two;
    }
    public int get1() { return one; }
    public int get2() { return two; }
    @Override
    public boolean equals(Object other) {
        if (other == null)
          return false;
        if (other == this)
          return true;
        if (!(other instanceof Pair))
          return false;

        Pair otherPair = (Pair) other;
        return one == otherPair.get1() && two == otherPair.get2();
    }
    @Override
    public String toString() { return "(" + one + "," + two + ")"; }
  }

  public static Puzzle calcShortestPath(Puzzle start, Puzzle goal) {

    Pair[][] goalRowPairs = getRowPairs(goal);
    Pair[][] goalColPairs = getColPairs(goal);
    open.clear();
    closed.clear();
    open.add(start);

    while (open.size() != 0) {
      Puzzle current = open.poll();
      closed.add(current);

      if (current.equals(goal))
        return current;

      if (closed.size() % 1000 == 0) {
        System.out.println(current);
        System.out.format("Open=%d Closed=%d\n", open.size(), closed.size());
      }

      for (Puzzle neighbor: getNeighbors(current)) {
        if (closed.contains(neighbor))
          continue;

        neighbor.setHeuristic(calcDistanceHeuristic(neighbor)
          + calcPairHeuristic(neighbor, goalRowPairs, goalColPairs));

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

  private static int getDistanceToWall(int size, Pair pos) {
    int h = Math.min(pos.get1(), Math.abs(size - 1 - pos.get1()));
    int v = Math.min(pos.get2(), Math.abs(size - 1 - pos.get2()));
    return Math.min(h, v);
  }

  private static int calcDistanceHeuristic(Puzzle puzzle) {
    int heuristic = 0, size = puzzle.getSize();
    int[][] nums = puzzle.getNumbers();

    for (int x=0; x<size; x++) {
      for (int y=0; y<size; y++) {
        Pair pos = new Pair(x, y);
        int num = nums[x][y];

        if (num == 0)
          num = puzzle.getLastNum();

        Pair des = new Pair((num-1) / size, (size - 1 + num % size) % size);

        int distance =
          getDistanceToWall(size, pos) + getDistanceToWall(size, des) + 2;

        if (num != puzzle.getLastNum()) {
          distance = Math.min(distance, Math.abs(pos.get1() - des.get1())
            + Math.abs(pos.get2() - des.get2()));
        }

        heuristic += distance;
      }
    }

    return heuristic;
  }

  private static Pair[][] getRowPairs(Puzzle goal) {
    int size = goal.getSize();
    int[][] nums = goal.getNumbers();
    Pair[][] pairs = new Pair[size][size-1];

    for (int x=0; x<size; x++)
      for (int y=0; y<size-1; y++)
        pairs[x][y] = new Pair(nums[x][y], nums[x][y+1]);

    return pairs;
  }

  private static Pair[][] getColPairs(Puzzle goal) {
    int size = goal.getSize();
    int[][] nums = goal.getNumbers();
    Pair[][] pairs = new Pair[size][size-1];

    for (int x=0; x<size; x++)
      for (int y=0; y<size-1; y++)
        pairs[x][y] = new Pair(nums[y][x], nums[y+1][x]);

    return pairs;
  }

  private static int calcPairHeuristic(Puzzle puzzle,
    Pair[][] goalRowPairs, Pair[][] goalColPairs) {
    Pair[][] rowPairs = getRowPairs(puzzle);
    Pair[][] colPairs = getColPairs(puzzle);
    int heuristic = 0;

    for (int i=0; i<goalRowPairs.length; i++) {
      int correctRowPairs = 0, correctColPairs = 0;
      for (int j=0; j<goalRowPairs[i].length; j++) {
        for (int k=0; k<rowPairs[i].length; k++) {
          if (goalRowPairs[i][j].equals(rowPairs[i][k]))
            correctRowPairs++;
          if (goalColPairs[i][j].equals(colPairs[i][k]))
            correctColPairs++;
        }
      }
      heuristic += goalRowPairs[i].length - correctRowPairs;
      heuristic += goalColPairs[i].length - correctColPairs;
    }

    return heuristic;
  }

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
