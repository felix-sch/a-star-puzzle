# a-star-puzzle

The A* (A star) search algorithm is commonly used in shortest path findings.

# Compile and Run

<b>Compile</b> ```javac TestAStar.java```

<b>Run</b> ```java TestAStar 3x3.csv```

# Challenge

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

The goal puzzle after n steps by pushing the free field into any row or column:

```
1 2 3
4 5 6
7 8 9  Last number: 0
```

# FAQ

How fast does the algorithm deliver a solution?
- For a 3x3 puzzle it is incredibly fast.

Why is the solving of a 5x5 puzzle so slow?
- This is common because the problem difficulty rises drastically for bigger puzzle sizes. Just imaging you have <b>26!</b> possible states to arrange the numbers in a 5x5 puzzle.

# Class Outline

![](http://i.imgur.com/Tx4llYT.png)
