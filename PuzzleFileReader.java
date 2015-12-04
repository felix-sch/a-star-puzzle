import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PuzzleFileReader {

  private String path;

  public PuzzleFileReader(String path) {
    this.path = path;
  }

  public Puzzle generatePuzzleFromCSV() {
    int lastNum;
    String[] rawData = readCSVFile();
    int[][] field = new int[rawData.length][rawData.length];

    for (int i=0; i<rawData.length; i++) {
      String[] splitted = rawData[i].split(";");
      for (int j=0; j<splitted.length; j++) {
          field[i][j] = Integer.parseInt(splitted[j]);
      }
    }
    lastNum = getLastNum(field);
    Puzzle generatedPuzzle = new Puzzle(field, lastNum);
    return generatedPuzzle;
  }

  private int getLastNum(int[][] field){
    int lastNum = 0;
    ArrayList<Integer> flatArray = new ArrayList<Integer>();
    for (int i=0; i<field.length; i++) {
      for (int j=0; j<field.length; j++) {
        flatArray.add(field[i][j]);
      }
    }
    if (flatArray.contains(0)) {
      for (int i = 1; i<flatArray.size(); i++) {
        if (flatArray.contains(i)) {
          continue;
        } else {
          lastNum = i;
          break;
        }
      }
    }
    return lastNum;
  }

  private String[] readCSVFile() {
    String line;
    BufferedReader csvReader = null;
    ArrayList<String> result = new ArrayList<String>();

    try {
      csvReader = new BufferedReader(new FileReader(this.path));
      while ((line = csvReader.readLine()) != null) {
          result.add(line);
      }
    } catch (FileNotFoundException e) {
  		e.printStackTrace();
  	} catch (IOException e) {
  		e.printStackTrace();
  	} finally {
  		if (csvReader != null) {
  			try {
  				csvReader.close();
  			} catch (IOException e) {
  				e.printStackTrace();
  			}
  		}
  	}

    return result.toArray(new String[result.size()]);
  }

}
