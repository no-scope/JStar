JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Parser.java \
	StarSolver.java \
	Vertex.java \
	Solver2.java \
	Robot.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

