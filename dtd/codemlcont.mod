<!--
   An XML DTD for presentation and content of program code (CodeML): module Content
      SYSTEM http://www.mathweb.org/src/matheb/omdoc/project/codemldtd/codemlc.dtd
      PUBLIC -//OMDoc//DTD CodeML Content V1.2//EN
   See the documentation and examples at 
   http://www.mathweb.org/src/mathweb/omdoc/projects/codeml
   (c) 2002-2003 Michael Kohlhase, released under the GNU Public License (GPL)
-->

<!ENTITY % codeml.apply.qname "%codeml.pfx;apply">
<!ENTITY % codeml.bind.qname "%codeml.pfx;bind">
<!ENTITY % codeml.bvar.qname "%codeml.pfx;bvar">
<!ENTITY % codeml.ccv.qname "%codeml.pfx;ccv">
<!ENTITY % codeml.ccb.qname "%codeml.pfx;ccb">
<!ENTITY % codeml.ccsym.qname "%codeml.pfx;ccsym">
<!ENTITY % codeml.ccdef.qname "%codeml.pfx;ccdef">
<!ENTITY % codeml.symbol.qname "%codeml.pfx;symbol">
<!ENTITY % codeml.type.qname "%codeml.pfx;type">


<!ENTITY % codeml.cc.class "%codeml.apply.qname;
                     |%codeml.bind.qname;
                     |%codeml.bvar.qname;
                     |%codeml.ccv.qname;
                     |%codeml.ccb.qname;
                     |%codeml.ccsym.qname;">

<!ENTITY % codeml.cc.top.class "%codeml.cc.class;|%codeml.ccdef.qname;">

<!ENTITY % codeml.cc.common.attrib "%codeml.common.attrib;">

<!-- Application-->
<!ELEMENT %codeml.apply.qname; (%codeml.cc.class;)*>
<!ATTLIST %codeml.apply.qname; %codeml.cc.common.attrib;>

<!-- Binding -->
<!ELEMENT %codeml.bind.qname; 
          (%codeml.ccsym.qname;,%codeml.bvar.qname;,(%codeml.cc.class;))>
<!ATTLIST %codeml.bind.qname; %codeml.cc.common.attrib;>

<!-- Bound Variable Declarations -->
<!ELEMENT %codeml.bvar.qname; (%codeml.ccv.qname;)*>
<!ATTLIST %codeml.bvar.qname; %codeml.cc.common.attrib;>

<!-- Variables -->
<!ELEMENT %codeml.ccv.qname; EMPTY>
<!ATTLIST %codeml.ccv.qname; %codeml.cc.common.attrib;
                      name CDATA #REQUIRED>

<!-- basic language objects -->
<!ELEMENT %codeml.ccb.qname; (#PCDATA)>
<!ATTLIST %codeml.ccb.qname; %codeml.cc.common.attrib;
                      type NMTOKEN #IMPLIED>

<!-- Sybmols (Reserved Names) -->
<!ELEMENT %codeml.ccsym.qname; (#PCDATA)>
<!ATTLIST %codeml.ccsym.qname; %codeml.cc.common.attrib;
                        cd CDATA #REQUIRED
                        name CDATA #REQUIRED>

<!-- Definitions -->
<!ELEMENT %codeml.ccdef.qname; (%codeml.ccsym.qname;,(%codeml.cc.class;)*)>
<!ATTLIST %codeml.ccdef.qname; %codeml.cc.common.attrib;
                        export CDATA #REQUIRED>

<!ENTITY % codeml.doc.class "">

<!-- Symbol declarations -->
<!ELEMENT %codeml.symbol.qname; (%codeml.doc.class;%codeml.type.qname;)>
<!ATTLIST %codeml.symbol.qname; %codeml.cc.common.attrib;>

<!-- Types -->
<!ELEMENT %codeml.type.qname; (%codeml.cc.class;)>
<!ATTLIST %codeml.type.qname; %codeml.cc.common.attrib;>
