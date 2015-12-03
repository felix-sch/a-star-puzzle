
public class TestAStar {

  public static void main(String[] args) {

    Puzzle start = new Puzzle(new int[][] {
      {1,2,3},
      {4,5,6},
      {7,8,9}
    }, 10);

    System.out.println(start);

    AStar.calcShortestPath(start, start);
  }
}
