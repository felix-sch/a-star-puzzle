import java.io.IOException;

public class TestAStar {

  public static void main(String[] args) {

    PuzzleFileReader csvFileReaderStart = new PuzzleFileReader("S_A031_Daten.csv");
    PuzzleFileReader csvFileReaderEnd = new PuzzleFileReader("S_A031_Daten_End.csv");
    Puzzle start3 = csvFileReaderStart.generatePuzzleFromCSV();
    Puzzle goal3 = csvFileReaderEnd.generatePuzzleFromCSV();

    long start = System.currentTimeMillis();
    Puzzle last = AStar.calcShortestPath(start3, goal3);
    long elapsedTimeMillis = System.currentTimeMillis() - start;

    if (last != null) {
      System.out.println("Steps:");
      printPath(last);
    }

    System.out.format("Elapsed time: %1$2s min %1$2s secs\n",
      elapsedTimeMillis/(60*1000F),
      elapsedTimeMillis/1000F);

  }

  private static void printPath(Puzzle p) {
    if (p.getParent() != null)
      printPath(p.getParent());
    System.out.println(p);
  }
}
