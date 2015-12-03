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

        neighbor.setHeuristic(calcHeuristic(neighbor));

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

  public static int calcHeuristic(Puzzle puzzle) {
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

          System.out.format("Free field at (%d,%d) and %d should be at: (%d,%d)\n",
            currentPosition[0], currentPosition[1],
            puzzle.getLastNum(), desiredPosition[0], desiredPosition[1]);

          int smallerX = Math.min(currentPosition[0], Math.abs(puzzle.getNumbers().length-currentPosition[0]-1));
          int smallerY = Math.min(currentPosition[1], Math.abs(puzzle.getNumbers().length-currentPosition[1]-1));
          int shortestWallCurrent = Math.min(smallerX, smallerY);

          smallerX = Math.min(desiredPosition[0], Math.abs(puzzle.getNumbers().length-desiredPosition[0]-1));
          smallerY = Math.min(desiredPosition[1], Math.abs(puzzle.getNumbers().length-desiredPosition[1]-1));
          int shortestWallDesired = Math.min(smallerX, smallerY);


          System.out.format("Move out %d and move in %d\n",
            shortestWallCurrent, shortestWallDesired);

          heuristic += shortestWallCurrent + shortestWallDesired + 2;
          System.out.println("Heuristic (current): " + heuristic);
          continue;
        }

        int[] desiredPosition = new int[] {
          (currentNumber-1) / puzzle.getNumbers().length,
          (currentNumber %
            puzzle.getNumbers().length + (puzzle.getNumbers().length - 1)) %
            puzzle.getNumbers().length};

        System.out.format("Number: %d at (%d,%d) should be at: (%d,%d)\n",
          currentNumber, currentPosition[0], currentPosition[1],
          desiredPosition[0], desiredPosition[1]);

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

        System.out.format("Inner: %d Outer %d\n", innerDistance, outerDistance);

        System.out.println("Heuristic (current): " + heuristic);
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
