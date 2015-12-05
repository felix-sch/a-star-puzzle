class DistanceHeuristic implements IHeuristic {

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

        heuristic += distance;
      }
    }

    return heuristic;
  }

  private int getDistanceToWall(int size, int x, int y) {
    int h = Math.min(x, Math.abs(size - 1 - x));
    int v = Math.min(y, Math.abs(size - 1 - y));
    return Math.min(h, v);
  }

}
