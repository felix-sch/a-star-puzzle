# SETUP

bin_d=bin
source_d=src
test_d=test
lib_d=lib

JCC=javac
JFLAGS=-g -d . -sourcepath $(source_d)
JFLAGSTEST=-g -cp

JUNIT=$(lib_d)/junit-4.12.jar
HAMCREST=$(lib_d)/hamcrest-core-1.3.jar

.PHONY: clean test
default: SolvePuzzle clean

SolvePuzzle: $(source_d)/*.java
	$(JCC) $(JFLAGS) $(source_d)/SolvePuzzle.java
	jar cfe $(bin_d)/SolvePuzzle.jar SolvePuzzle *.class

clean:
	-rm	*.class

test: $(test_d)/AStarTest.java
	$(JCC) -d . $(JFLAGSTEST) $(JUNIT):. \
		-sourcepath $(source_d) $(test_d)/AStarTest.java
	jar cf $(bin_d)/AStarTest.jar *.class
	java -cp  $(JUNIT):$(HAMCREST):$(bin_d)/AStarTest.jar \
		org.junit.runner.JUnitCore AStarTest

performance: $(test_d)/PerformanceTest.java
	$(JCC) $(JFLAGS) $(test_d)/PerformanceTest.java
	jar cfe $(bin_d)/PerformanceTest.jar PerformanceTest *.class
