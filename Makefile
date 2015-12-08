# SETUP

class_d=bin
source_d=src
test_d=test
lib_d=lib

JCC=javac
JFLAGS=-g -d $(class_d) -sourcepath $(source_d)
JFLAGSTEST= -g -cp

JUNIT=$(lib_d)/junit-4.12.jar
HAMCREST=$(lib_d)/hamcrest-core-1.3.jar

.PHONY: test

default: SolvePuzzle

SolvePuzzle: $(source_d)/SolvePuzzle.java
	$(JCC) $(JFLAGS) $(source_d)/SolvePuzzle.java

clean:
	$(RM) $(class_d)/*

test: $(JUNIT) $(HAMCREST)
	$(JCC) -d $(class_d) $(JFLAGSTEST) $(JUNIT):. -sourcepath src $(test_d)/AStarTest.java &&\
	jar cf $(test_d)/AStarTest.jar $(class_d)/*.class &&\
	java -cp  $(JUNIT):$(HAMCREST):$(test_d)/AStarTest.jar:$(class_d) org.junit.runner.JUnitCore AStarTest
