
class Puzzle {
  private int[][] numbers;
  private int lastNum, steps, heuristic, size;
  private Puzzle parent;

  public Puzzle(int[][] numbers, int lastNum) {
    this.numbers = numbers;
    this.lastNum = lastNum;
    this.steps = 0;
    this.size = numbers.length;
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

  public int getSize() {
    return size;
  }

  public Puzzle getParent() {
    return parent;
  }

  public Puzzle shiftLeft(int row) {
    int[][] shifted = copyArray(numbers);
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
    int[][] shifted = copyArray(numbers);
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
    int[][] shifted = copyArray(numbers);
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
    int[][] shifted = copyArray(numbers);
    int shiftedLastNum = lastNum;
    int tmp;
    for (int i=0; i<shifted.length; i++) {
      tmp = shifted[i][col];
      shifted[i][col] = shiftedLastNum;
      shiftedLastNum = tmp;
    }
    return new Puzzle(shifted, shiftedLastNum, this);
  }

  private int[][] copyArray(int[][] array) {
    int[][] copy = new int[array.length][array.length];
    for(int i=0; i<array.length; i++)
      for(int j=0; j<array[i].length; j++)
        copy[i][j]=array[i][j];
    return copy;
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

    for (int i=0; i<numbers.length; i++) 
      for (int j=0; j<numbers[i].length; j++)
        if (numbers[i][j] != otherPuzzle.getNumbers()[i][j])
          return false;

    return true;
  }
}
