
class Puzzle implements Comparable<Puzzle> {
  private int[][] numbers;
  private int lastNum, steps, heuristic;
  private Puzzle parent;

  public Puzzle(int[][] numbers, int lastNum) {
    this.numbers = numbers;
    this.lastNum = lastNum;
    this.steps = 0;
    this.parent = null;
  }

  public Puzzle(int[][] numbers, int lastNum, Puzzle parent) {
    this(numbers, lastNum);
    this.parent = parent;
    this.steps = parent.getSteps() + 1;
  }

  public int[][] getNumbers() {
    return numbers;
  }

  public int getLastNum() {
    return lastNum;
  }

  public int getSteps() {
    return steps;
  }

  public int getHeuristic() {
    return heuristic;
  }

  public void setHeuristic(int heuristic) {
    this.heuristic = heuristic;
  }

  public Puzzle shiftLeft(int row) {
    int[][] shifted = numbers;
    int shiftedLastNum = lastNum;
    int tmp;
    for (int i=shifted[row].length-1; i>=0; i--) {
      tmp = shifted[row][i];
      shifted[row][i] = shiftedLastNum;
      shiftedLastNum = tmp;
    }
    return new Puzzle(shifted, shiftedLastNum, this);
  }
  public Puzzle shiftRight(int row) {
    int[][] shifted = numbers;
    int shiftedLastNum = lastNum;
    int tmp;
    for (int i=0; i<shifted[row].length; i++) {
      tmp = shifted[row][i];
      shifted[row][i] = shiftedLastNum;
      shiftedLastNum = tmp;
    }
    return new Puzzle(shifted, shiftedLastNum, this);
  }
  public Puzzle shiftUp(int col) {
    int[][] shifted = numbers;
    int shiftedLastNum = lastNum;
    int tmp;
    for (int i=shifted.length-1; i>=0; i--) {
      tmp = shifted[i][col];
      shifted[i][col] = shiftedLastNum;
      shiftedLastNum = tmp;
    }
    return new Puzzle(shifted, shiftedLastNum, this);
  }
  public Puzzle shiftDown(int col) {
    int[][] shifted = numbers;
    int shiftedLastNum = lastNum;
    int tmp;
    for (int i=0; i<shifted.length; i++) {
      tmp = shifted[i][col];
      shifted[i][col] = shiftedLastNum;
      shiftedLastNum = tmp;
    }
    return new Puzzle(shifted, shiftedLastNum, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i=0; i<numbers.length; i++) {
      for (int j=0; j<numbers[i].length; j++) {
        sb.append(String.format("%1$2s ", numbers[i][j]));
      }
      if (i == numbers.length-1) {
        sb.append(String.format("Last: %1$2s", lastNum));
      }
      sb.append('\n');
    }
    return sb.toString();
  }

  @Override
  public boolean equals(Object other){
    if (other == null)
      return false;
    if (other == this)
      return true;
    if (!(other instanceof Puzzle))
      return false;

    Puzzle otherPuzzle = (Puzzle) other;
    return toString().equals(otherPuzzle.toString());
  }

  public int compareTo(Puzzle other) {
    int sum = steps + heuristic;
    int otherSum = other.getSteps() + other.getHeuristic();

    if (sum < otherSum) {
      return -1;
    } else if (sum > otherSum) {
      return 1;
    } else {
      return 0;
    }
  }
}
