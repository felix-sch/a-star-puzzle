import java.io.IOException;

public class TestAStar {

  public static void main(String[] args) {

    Puzzle start = new Puzzle(new int[][] {
      {1,3,4},
      {7,6,5},
      {2,0,9}
    }, 8);

    Puzzle goal = new Puzzle(new int[][] {
      {1,2,3},
      {4,5,6},
      {7,8,9}
    }, 0);

    System.out.println(start);
    Puzzle last = null;
    try {
      last = AStar.calcShortestPath(start, goal);
    } catch(IOException e) { }

    if (last != null) {
      System.out.println("Path:");
      printPath(last);
    }
  }

  private static void printPath(Puzzle p) {
    if (p.getParent() != null) {
      printPath(p.getParent());
    }
    System.out.println(p);
  }
}
