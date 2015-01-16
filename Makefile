JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Parser.java \
	StarSolver.java \
	Vertex.java \
	Sol.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

