
all: Java2CodeML.class

grammar: java.g java.tree.g
	java antlr.Tool java.g
	java antlr.Tool java.tree.g

Java2CodeML.class: Java2CodeML.java Presentation.class RawCode.class
	javac Java2CodeML.java

Presentation.class: Presentation.java
	javac Presentation.java

RawCode.class: RawCode.java
	javac RawCode.java

clean:
	-rm -f *.class
	-rm -f JavaLexer.java JavaRecognizer.java JavaTokenTypes* JavaTreeParser* 
