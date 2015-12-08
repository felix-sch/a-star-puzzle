import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/** Class that represents a puzzle, likewise a state of a puzzle.
  *
  * @author Felix Schaumann, Dominic Pfeil
  * @version 1.0
  */
class Puzzle {
  private int[][] numbers;
  private int lastNum, steps, heuristic, size;
  private Puzzle parent;

  /** Constructs a puzzle.
    *
    * @param numbers 2-dimensional array representing the numbers of a puzzle
    * @param lastNum last number (empty field) of a puzzle
    */
  public Puzzle(int[][] numbers, int lastNum) {
    this.numbers = numbers;
    this.lastNum = lastNum;
    this.steps = 0;
    this.size = numbers.length;
    this.parent = null;
  }

  /** Constructs a puzzle.
    *
    * @param numbers 2-dimensional array representing the numbers of a puzzle
    * @param lastNum last number (empty field) of a puzzle
    * @param parent parent node of this puzzle
    */
  public Puzzle(int[][] numbers, int lastNum, Puzzle parent) {
    this(numbers, lastNum);
    this.parent = parent;
    this.steps = parent.getSteps() + 1;
  }

  /** Creates a goal for a puzzle based on its size.
    *
    * @param size size of the puzzle
    * @return ordered puzzle
    */
  public static Puzzle createGoal(int size) {
    int[][] nums = new int[size][size];
    for (int i=0; i<size; i++)
      for (int j=0; j<size; j++)
        nums[i][j] = i*size + j + 1;
    return new Puzzle(nums, 0);
  }

  /** Reads a puzzle from a file.
    *
    * @param file csv-file that holds all necessary info
    * @return new puzzle that was generated from file
    */
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

  /**
    * @return puzzle's numbers
    */
  public int[][] getNumbers() {
    return numbers;
  }

  /**
    * @return last number of a puzzle (empty field)
    */
  public int getLastNum() {
    return lastNum;
  }

  /**
    * @return steps of a puzzle
    */
  public int getSteps() {
    return steps;
  }

  /**
    * @return heuristic of a puzzle
    */
  public int getHeuristic() {
    return heuristic;
  }

  /**
    * @param heuristic to be set for puzzle
    */
  public void setHeuristic(int heuristic) {
    this.heuristic = heuristic;
  }

  /**
    * @return size of a puzzle
    */
  public int getSize() {
    return size;
  }

  /**
    * @return parent of a puzzle
    */
  public Puzzle getParent() {
    return parent;
  }

  /** Checks if puzzle is solved.
    *
    * @return boolean value if puzzle is solved
    */
  public boolean isSolved() {
    for (int i=0; i<size; i++)
      for (int j=0; j<size; j++)
        if (numbers[i][j] != i*size + j + 1)
          return false;
    return true;
  }

  /** Shifts row left at given position.
    *
    * @param row position of row
    * @return puzzle after the shift
    */
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

  /** Shifts row right at given position.
    *
    * @param row position of row
    * @return puzzle after the shift
    */
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

  /** Shifts column up at given position.
    *
    * @param col position of column
    * @return puzzle after the shift
    */
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

  /** Shifts column down at given position.
    *
    * @param col position of column
    * @return puzzle after the shift
    */
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

  /** Copies an Array to avoid reference errors.
    *
    * @param array array to be copied
    * @return copy of the array
    */
  private int[][] copyArray(int[][] array) {
    int[][] copy = new int[array.length][array.length];
    for(int i=0; i<array.length; i++)
      for(int j=0; j<array[i].length; j++)
        copy[i][j]=array[i][j];
    return copy;
  }

  /** Form a puzzle's string representation.
    *
    * @return string representation of puzzle
    */
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

  /** Check if two puzzles are equal
    *
    * @param other object to be compared
    * @return boolean value representing the equality of two objects.
    */
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
