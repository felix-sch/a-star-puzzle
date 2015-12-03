
public class TestAStar {

  public static void main(String[] args) {

    Puzzle start = new Puzzle(new int[][] {
      {1,2,3},
      {4,5,6},
      {7,8,9}
    }, 0);

    Puzzle test = new Puzzle(new int[][] {
      {2,3,4},
      {1,5,6},
      {7,8,0}
    }, 9);

    //System.out.println(start);
    System.out.println(test);

    System.out.println("Heuristic: " + AStar.calcHeuristic(test));

    //AStar.calcShortestPath(start, start);
  }
}
