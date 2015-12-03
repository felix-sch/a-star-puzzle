
public class TestAStar {

  public static void main(String[] args) {

    Puzzle start = new Puzzle(new int[][] {
      {1,2,3},
      {4,5,6},
      {7,8,9}
    }, 10);

    Puzzle test1 = new Puzzle(new int[][] {
      {8,5,9},
      {7,2,3},
      {1,4,6}
    }, 10);

    Puzzle test2 = new Puzzle(new int[][] {
      {2,1,3},
      {4,5,6},
      {7,8,9}
    }, 10);

    System.out.println(test1);
    System.out.println(test2);

    System.out.println(AStar.calcHeuristic(test1, start));
    System.out.println(AStar.calcHeuristic(test2, start));

    //AStar.calcShortestPath(start, start);
  }
}
