# SETUP

bin_d=bin
source_d=src
test_d=test
lib_d=lib

JCC=javac
JFLAGS=-g -d $(bin_d) -sourcepath $(source_d)
JFLAGSTEST=-g -cp

JUNIT=$(lib_d)/junit-4.12.jar
HAMCREST=$(lib_d)/hamcrest-core-1.3.jar

.PHONY: clean test
default: SolvePuzzle clean

SolvePuzzle: $(source_d)/*.java
	$(JCC) $(JFLAGS) $(source_d)/SolvePuzzle.java
	jar cmf $(src_d)/MANIFEST.MF $(bin_d)/SolvePuzzle.jar $(bin_d)/*.class

clean:
	-rm	 $(bin_d)/*.class

test: $(test_d)/AStarTest.java
	$(JCC) -d $(class_d) $(JFLAGSTEST) $(JUNIT):. \
		-sourcepath $(source_d) $(test_d)/AStarTest.java
	jar cmf $(test_d)/MANIFEST.MF $(bin_d)/AStarTest.jar $(bin_d)/*.class
	java -cp  $(JUNIT):$(HAMCREST):$(bin_d)/AStarTest.jar \
		org.junit.runner.JUnitCore AStarTest
