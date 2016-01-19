JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Parser.java \
	StarSolver.java \
	Vertex.java \
	Robot.java \
	Solver.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

