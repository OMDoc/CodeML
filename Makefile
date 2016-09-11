PREFIX          = ../..
MAKEDIRS 	= doc examples
TESTDIRS	= examples # nothing to test in doc
CLEANDIRS	= $(MAKEDIRS) xsl
include $(PREFIX)/lib/Makefile.subdirs

