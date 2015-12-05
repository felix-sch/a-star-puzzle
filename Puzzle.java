import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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

  public static Puzzle createGoal(int size) {
    int[][] nums = new int[size][size];
    for (int i=0; i<size; i++)
      for (int j=0; j<size; j++)
        nums[i][j] = i*size + j + 1;
    return new Puzzle(nums, 0);
  }

  public static Puzzle fromFile(File file) {
    int[][] nums = null;
    int lastNum = 0;

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      for (int i=0; (line = br.readLine()) != null; i++) {
        String[] split = line.split(";");

        if (nums == null)
          nums = new int[split.length][split.length];

        if (split.length > 1) {
          for (int j=0; j<split.length; j++)
            nums[i][j] = Integer.parseInt(split[j]);
        } else {
          lastNum = Integer.parseInt(split[0]);
        }
      }

    } catch (IOException e) {
      // TODO
      e.printStackTrace();
    }

    return new Puzzle(nums, lastNum);
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

  public boolean isSolved() {
    for (int i=0; i<size; i++)
      for (int j=0; j<size; j++)
        if (numbers[i][j] != i*size + j + 1)
          return false;
    return true;
  }

  public Puzzle shiftLeft(int row) {
    int[][] shifted = copyArray(numbers);
    int shiftedLastNum = lastNum;
    int tmp;
    for (int i=size-1; i>=0; i--) {
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
    for (int i=0; i<size; i++) {
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
    for (int i=size-1; i>=0; i--) {
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
    for (int i=0; i<size; i++) {
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
    for (int i=0; i<size; i++) {
      for (int j=0; j<size; j++)
        sb.append(String.format("%1$2s ", numbers[i][j]));

      if (i == size-1)
        sb.append(String.format("Last: %1$2s", lastNum));
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

    if (size != otherPuzzle.getSize())
      return false;

    for (int i=0; i<size; i++)
      for (int j=0; j<size; j++)
        if (numbers[i][j] != otherPuzzle.getNumbers()[i][j])
          return false;

    return true;
  }
}
