XMLPARSE 	= rxp -V -s -x
PREFIX		= ../..
LIBDIR          = $(PREFIX)/lib
XSLDIR          = $(PREFIX)/xsl
BINDIR          = $(PREFIX)/../../bin

XSLT		= xsltproc

CMLFILES 	= find.cml
RAWCODE		= $(patsubst %.cml, %.raw, $(CMLFILES))  
JAVAHTML	= $(patsubst %.cml, %-java.html, $(CMLFILES))  
PROLOGHTML	= $(patsubst %.cml, %-prolog.html, $(CMLFILES))  
PSEUDOHTML	= $(patsubst %.cml, %-pseudo.html, $(CMLFILES))  
DTD_TESTED	= $(patsubst %.cml,.%.dtd-tested, $(CMLFILES))
UNDEF_TESTED	= $(patsubst %.cml,.%.undef-tested, $(CMLFILES))

all:		$(JAVAHTML) $(RAWCODE) $(PSEUDOHTML)

# the generic targets
test-result:
html:	
ps:	
pdf:
dvi:
mml:


$(RAWCODE): %.raw: %.cml $(XSLDIR)/codeml2raw.xsl 
		$(XSLT)  -o $@ --stringparam TargetFormat prolog $(XSLDIR)/codeml2raw.xsl $(firstword $<) 

$(JAVAHTML): %-java.html: %.cml $(XSLDIR)/codeml2html.xsl codeml.css
		$(XSLT)  -o $@ --stringparam TargetFormat java $(XSLDIR)/codeml2html.xsl $(firstword $<) 

$(PROLOGHTML): %-prolog.html: %.cml $(XSLDIR)/codeml2html.xsl codeml.css
		$(XSLT)  -o $@ --stringparam TargetFormat prolog $(XSLDIR)/codeml2html.xsl $(firstword $<) 

$(PSEUDOHTML): %-pseudo.html: %.cml $(XSLDIR)/codeml2html.xsl codeml.css
		$(XSLT)  -o $@ --stringparam TargetFormat pseudo-code $(XSLDIR)/codeml2html.xsl $(firstword $<) 

codeml.css:    
		ln -s $(LIBDIR)/codeml.css .

test:		$(DTD_TESTED) #$(UNDEF_TESTED) 

.%.dtd-tested:	%.cml
		$(XMLPARSE) $< >! $@ && touch $@

test-clean:	
		rm -f .*.*-tested .*.tested 

clean:
		rm -f *.log *.blg *.ilg *~ ! .#*

distclean:      clean test-clean
		rm -f *.html 
