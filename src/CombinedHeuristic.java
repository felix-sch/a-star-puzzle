/** Class that combines heuristics of pairs and distance.
  *
  * @author Felix Schaumann, Dominic Pfeil
  * @version 1.0
  */
class CombinedHeuristic implements IHeuristic {

  private IHeuristic[] heuristics;
  private int[] weightings;

  /** Constructs combined heuristics with an arbitrary number of heuristics.
    *
    * @param heuristics heuristics to be combined
    */
  public CombinedHeuristic(IHeuristic... heuristics) {
    this.heuristics = heuristics;
    this.weightings = new int[heuristics.length];
    for (int i=0; i<heuristics.length; i++)
      weightings[i] = 1;
  }

  /** Sets weightings for heurisitics
    *
    * @param weightings array of weightings
    */
  public void setWeightings(int... weightings) {
    this.weightings = weightings;
  }

  /** Calculates heuristic for a given puzzle.
    *
    * @param p puzzle to calculate heuristic for
    * @return heuristic for given puzzle
    */
  public int calcHeuristic(Puzzle p) {
    int heuristic = 0;

    for (int i=0; i<heuristics.length; i++)
      heuristic += heuristics[i].calcHeuristic(p) * weightings[i];

    return heuristic;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("CombinedHeuristic ( ");
    for (int i=0; i<heuristics.length; i++)
      sb.append(weightings[i] + ": " + heuristics[i] + " ");
    sb.append(")");
    return sb.toString();
  }

}
