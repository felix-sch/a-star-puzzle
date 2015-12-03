import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;
import java.io.IOException;
import java.util.Arrays;

class AStar {
  private static ArrayList<Puzzle> open = new ArrayList<Puzzle>();
  private static ArrayList<Puzzle> closed = new ArrayList<Puzzle>();

  public static Puzzle calcShortestPath(Puzzle start, Puzzle goal) throws IOException {

    int[][][] goalRowPairs = getRowPairs(goal);
    open.clear();
    closed.clear();
    open.add(start);

    while (open.size() != 0) {
      System.out.println("===================================================");
      System.out.format("Open list: %d elements\n", open.size());

      Puzzle current = open.get(0);
      System.out.println(current);

      if (current.equals(goal)) {
        System.out.println("Goal reached, returning!");
        return current;
      }

      open.remove(current);
      closed.add(current);

      System.out.format("Open list: %d Closed list: %d\n",
        open.size(), closed.size());

      System.out.println("Looping over neighbors");
      for (Puzzle neighbor: getNeighbors(current)) {

        if (closed.contains(neighbor)) {
          System.out.println("Neighbor already in closed list, continuing");
          continue;
        }

        //System.out.println(neighbor);

        neighbor.setHeuristic(calcDistanceHeuristic(neighbor)
          + calcPairsHeuristic(neighbor, goalRowPairs));
        //System.out.println("Heuristic of neighbor: " + neighbor.getHeuristic());

        if (open.contains(neighbor)) {
          //System.in.read();
          System.out.println("Neighbor is in open list:");
          Puzzle existing = open.get(open.indexOf(neighbor));
          System.out.format("%d (existing) > %d (new) ?",
            existing.getHeuristic() + existing.getSteps(),
            neighbor.getHeuristic() + neighbor.getSteps());

          if (existing.getHeuristic() + existing.getSteps()
            > neighbor.getHeuristic() + neighbor.getSteps()) {
            open.set(open.indexOf(neighbor), neighbor);
            System.out.println("> Updated neighbor in open list");
          } else {
            System.out.println("> No action");
          }
        } else {
          open.add(neighbor);
          //System.out.println("Neighbor is not in open list -> added");
        }
      }

      //System.out.println("Sorting open list");
      for (Puzzle p: open) {
        //System.out.format("%d,", p.getSteps() + p.getHeuristic());
      }
      //System.out.println();

      Collections.sort(open);

      for (Puzzle p: open) {
        //System.out.format("%d,", p.getSteps() + p.getHeuristic());
      }
      //System.out.println();
    }

    return null;
  }

  public static int calcDistanceHeuristic(Puzzle puzzle) {
    int heuristic = 0;

    for (int i=0; i<puzzle.getNumbers().length; i++) {
      for (int j=0; j<puzzle.getNumbers()[i].length; j++) {
        int currentNumber = puzzle.getNumbers()[i][j];
        int[] currentPosition = new int[] {i, j};

        if (currentNumber == 0) {

          int[] desiredPosition = new int[] {
            (puzzle.getLastNum()-1) / puzzle.getNumbers().length,
            (puzzle.getLastNum() %
              puzzle.getNumbers().length + (puzzle.getNumbers().length - 1)) %
              puzzle.getNumbers().length};

          int smallerX = Math.min(currentPosition[0], Math.abs(puzzle.getNumbers().length-currentPosition[0]-1));
          int smallerY = Math.min(currentPosition[1], Math.abs(puzzle.getNumbers().length-currentPosition[1]-1));
          int shortestWallCurrent = Math.min(smallerX, smallerY);

          smallerX = Math.min(desiredPosition[0], Math.abs(puzzle.getNumbers().length-desiredPosition[0]-1));
          smallerY = Math.min(desiredPosition[1], Math.abs(puzzle.getNumbers().length-desiredPosition[1]-1));
          int shortestWallDesired = Math.min(smallerX, smallerY);

          heuristic += shortestWallCurrent + shortestWallDesired + 2;
          continue;
        }

        int[] desiredPosition = new int[] {
          (currentNumber-1) / puzzle.getNumbers().length,
          (currentNumber %
            puzzle.getNumbers().length + (puzzle.getNumbers().length - 1)) %
            puzzle.getNumbers().length};

        int innerDistance = Math.abs(currentPosition[0] - desiredPosition[0]) +
          Math.abs(currentPosition[1] - desiredPosition[1]);

        int smallerX = Math.min(currentPosition[0], Math.abs(puzzle.getNumbers().length-currentPosition[0]-1));
        int smallerY = Math.min(currentPosition[1], Math.abs(puzzle.getNumbers().length-currentPosition[1]-1));
        int shortestWallCurrent = Math.min(smallerX, smallerY);

        smallerX = Math.min(desiredPosition[0], Math.abs(puzzle.getNumbers().length-desiredPosition[0]-1));
        smallerY = Math.min(desiredPosition[1], Math.abs(puzzle.getNumbers().length-desiredPosition[1]-1));
        int shortestWallDesired = Math.min(smallerX, smallerY);

        int outerDistance = Math.min(shortestWallCurrent, shortestWallDesired) + 2;
        heuristic += Math.min(outerDistance, innerDistance);
      }
    }

    return heuristic;
  }

  public static int calcPairsHeuristic(Puzzle puzzle, int[][][] goalRowPairs) {
    int[][][] rowPairs = getRowPairs(puzzle);
    int heuristic = 0;

    for (int i=0; i<goalRowPairs.length; i++) {
      int correct = 0;
      for (int j=0; j<goalRowPairs[i].length; j++) {
        for (int k=0; k<rowPairs[i].length; k++) {
          if (Arrays.equals(goalRowPairs[i][j], rowPairs[i][k]))
            correct++;
        }
      }
      heuristic += goalRowPairs[i].length - correct;
    }

    return heuristic;
  }

  public static int[][][] getRowPairs(Puzzle goal) {
    int[][] nums = goal.getNumbers();
    int[][][] rowPairs = new int[nums.length][nums.length-1][2];
    //int[][][] colPairs = new int[nums.length][nums.length-1][2];

    for (int i=0; i<nums.length; i++) {
      for (int j=0; j<nums.length-1; j++) {
        rowPairs[i][j][0] = nums[i][j];
        rowPairs[i][j][1] = nums[i][j+1];

        //System.out.format("(%d,%d) ", rowPairs[i][j][0], rowPairs[i][j][1]);
      }
      //System.out.println();
    }

    return rowPairs;
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
