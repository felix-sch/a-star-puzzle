# a-star-puzzle

The A* (A star) search algorithm is commonly used in shortest path findings.

## Run

```bash
java -jar bin/SolvePuzzle.jar sample-puzzles/3x3.csv
```

## Compile

**Windows**

```bash
javac -d . -sourcepath src src/SolvePuzzle.java
jar cfe bin/SolvePuzzle.jar SolvePuzzle *.class
```

**Linux**

```bash
make
```

## Challenge

Given a puzzle with random numbers, you have to sort those in an <b>ascending order</b> by pushing a free field into a row or column of the puzzle. This will result in a new free field (because it fell out of the puzzle), which you can use again to push it into the puzzle.

Example 3x3 puzzle:

```
8 4 3
1 9 7
2 6 5  Last number: 0
```

After pushing the <b>0</b> from right into the first row:

```
4 3 0 <
1 9 7
2 6 5  Last number: 8
```

The goal puzzle after `n` steps by pushing the free field into any row or column:

```
1 2 3
4 5 6
7 8 9  Last number: 0
```

## FAQ

**How fast does the algorithm deliver a solution?**
- For a 3x3 puzzle it is incredibly fast.

**Why is the solving of a 5x5 puzzle so slow?**
- This is common because the problem difficulty rises drastically for bigger puzzle sizes. Just imaging you have <b>26!</b> possible states to arrange the numbers in a 5x5 puzzle - BTW this would be `403.291.461.126.605.635.584.000.000` different states.

**What are heuristics?**
- Heuristics are used by the A* algorithm to determine, how good a single state is. In the case of solving a puzzle, a low heuristic shows that the puzzle is nearly solved. If a state `X` has a lower heuristic than state `Y`, then the algorithm will choose `X` as the next step.

**Which heuristic do you use?**
- This implementation uses two heuristics: distance- and pair-related heuristics. The distance heuristic calculates the distance between the current and goal position of each number within the puzzle. The pair heuristic compares the current pairs in each row and column with the goal pairs, e.g. `(1,2)` and `(2,3)` in the first row.

## Class Outline

![](http://i.imgur.com/cM7K7mk.png)

## Unit Tests

**Windows**

```bash
javac -d . -cp lib/junit-4.12.jar;. -sourcepath src test/AStarTest.java
jar cf bin/AStarTest.jar *.class
java -cp lib/junit-4.12.jar;lib/hamcrest-core-1.3.jar;bin/AStarTest.jar org.junit.runner.JUnitCore AStarTest
```

**Linux**

```bash
make test
```

## Performance Tests

### Run

```bash
java -jar bin/PerformanceTest.jar sample-puzzles/3x3.csv
```

### Compile

**Windows**

```bash
javac -d . -sourcepath src test/PerformanceTest.java
jar cfe bin/PerformanceTest.jar PerformanceTest *.class
```

**Linux**

```bash
make performance
```
