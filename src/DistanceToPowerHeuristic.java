/** Class to calculate heuristic based on distance of single points.
  *
  * @author Felix Schaumann, Dominic Pfeil
  * @version 1.0
  */
class DistanceToPowerHeuristic implements IHeuristic {

  private int exponent;

  public DistanceToPowerHeuristic(int exponent) {
    this.exponent = exponent;
  }

  /** Calculates heuristic for given puzzle based on the distance of single
    * points of the puzzle to their desired position.
    * Each distance is taken to the power of the chosen exponent.
    *
    * @param p puzzle to calculate heuristic for
    * @return calculated heuristic
    */
public int calcHeuristic(Puzzle p) {
    int heuristic = 0, size = p.getSize();
    int[][] nums = p.getNumbers();

    for (int x=0; x<size; x++) {
      for (int y=0; y<size; y++) {
        int num = nums[x][y];

        if (num == 0)
          num = p.getLastNum();

        int x2 = (num-1) / size, y2 = (size - 1 + num % size) % size;

        int distance =
          getDistanceToWall(size, x, y) + getDistanceToWall(size, x2, y2) + 2;

        if (num != p.getLastNum())
          distance = Math.min(distance, Math.abs(x - x2) + Math.abs(y - y2));

        heuristic += Math.pow(distance, exponent);
      }
    }

    return heuristic;
  }

  /** Gets distance of a single point to closest wall.
    *
    * @param size size of the puzzle
    * @param x x-value of the point
    * @param y y-value of the point
    * @return minimum distance to closest wall
    */
  private int getDistanceToWall(int size, int x, int y) {
    int h = Math.min(x, Math.abs(size - 1 - x));
    int v = Math.min(y, Math.abs(size - 1 - y));
    return Math.min(h, v);
  }

  @Override
  public String toString() {
    return "Distance^" + exponent + "Heuristic";
  }
}
