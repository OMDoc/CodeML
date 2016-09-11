TARGET.src = codeml.rnc #$(shell ls *-*.rnc) 
SRC.rnc	   = $(shell ls *.rnc) 

TARGET.dtd = $(TARGET.src:%.rnc=%.dtd)
TARGET.rng = $(TARGET.src:%.rnc=%.rng)
SRC.rng = $(SRC.rnc:%.rnc=%.rng)
BUT.xsd = $(shell ls mathml*.xsd) 
TARGET.xsd = $(filter-out $(BUT.xsd),$(TARGET.src:%.rnc=%.xsd))

all:		$(TARGET.rng) #$(TARGET.xsd) 

clean:		
		rm -f *~ 
distclean:	clean
		rm -f *.dtd *.xsd *.rng

install:	$(SRC.rnc) $(SRC.rng) #$(TARGET.xsd)
	cp $(SRC.rnc) $(HOME)/vc/svn/mathweb.org/trunk/www/omdoc/rnc
	cp $(SRC.rng) $(HOME)/vc/svn/mathweb.org/trunk/www/omdoc/rnc
#	cp -a $(TARTET.xsd) $(HOME)/vc/svn/mathweb.org/trunk/www/omdoc/xsd

$(TARGET.dtd): %.dtd: %.rnc 
		trang -I rnc -O dtd $< $@

$(SRC.rng): %.rng: %.rnc
#		rnc2rng $< $@
		trang -I rnc -O rng $< $@

$(TARGET.xsd): %.xsd: %.rnc 
		trang -I rnc omdoc.rnc omdoc.xsd
