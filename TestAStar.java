import java.io.IOException;

public class TestAStar {

  public static void main(String[] args) {

    Puzzle start3 = new Puzzle(new int[][] {
      {4,2,3},
      {5,9,1},
      {6,7,8}
    }, 0);
    Puzzle goal3 = new Puzzle(new int[][] {
      {1,2,3},
      {4,5,6},
      {7,8,9}
    }, 0);

    Puzzle start5 = new Puzzle(new int[][] {
      {23, 0, 25, 9, 10},
      {22, 1, 2, 8, 11},
      {21, 16, 3, 7, 12},
      {20, 17, 4, 6, 13},
      {18, 19, 15, 5, 14}
    }, 24);
    Puzzle goal5 = new Puzzle(new int[][] {
      {1,2,3,4,5},
      {6,7,8,9,10},
      {11,12,13,14,15},
      {16,17,18,19,20},
      {21,22,23,24,25}
    }, 0);

    long start = System.currentTimeMillis();
    Puzzle last = AStar.calcShortestPath(start5, goal5);
    long elapsedTimeMillis = System.currentTimeMillis() - start;

    if (last != null) {
      System.out.println("Steps:");
      printPath(last);
    }

    System.out.format("Elapsed time: %1$2s min %1$2s secs",
      elapsedTimeMillis/(60*1000F),
      elapsedTimeMillis/1000F);
  }

  private static void printPath(Puzzle p) {
    if (p.getParent() != null)
      printPath(p.getParent());
    System.out.println(p);
  }
}
