<!--
   An XML DTD for presentation and content of program code (CodeML): module Presentation
      SYSTEM http://www.mathweb.org/src/matheb/omdoc/project/codemldtd/codemlp.dtd
      PUBLIC -//OMDoc//DTD CodeML Presentation V1.2//EN
   See the documentation and examples at 
   http://www.mathweb.org/src/mathweb/omdoc/projects/codeml
   (c) 2002-2003 Michael Kohlhase, released under the GNU Public License (GPL)
-->

<!ENTITY % codeml.cpg.qname "%codeml.pfx;cpg">
<!ENTITY % codeml.cpb.qname "%codeml.pfx;cpb">
<!ENTITY % codeml.cpo.qname "%codeml.pfx;cpo">
<!ENTITY % codeml.cpi.qname "%codeml.pfx;cpi">
<!ENTITY % codeml.cpbr.qname "%codeml.pfx;cpbr">
<!ENTITY % codeml.cptype.qname "%codeml.pfx;cptype">
<!ENTITY % codeml.cpt.qname "%codeml.pfx;cpt">
<!ENTITY % codeml.cpd.qname "%codeml.pfx;cpd">
<!ENTITY % codeml.cpc.qname "%codeml.pfx;cpc">
<!ENTITY % codeml.cpr.qname "%codeml.pfx;cpr">
<!ENTITY % codeml.cpstyle.qname "%codeml.pfx;cpstyle">

<!ENTITY % codeml.cp.class "%codeml.cpg.qname;
                           |%codeml.cpb.qname;
                           |%codeml.cpo.qname;
                           |%codeml.cpi.qname;
                           |%codeml.cpbr.qname;
                           |%codeml.cptype.qname;
                           |%codeml.cpd.qname;
                           |%codeml.cpc.qname;
                           |%codeml.cpr.qname;
                           |%codeml.cpstyle.qname;">


<!ENTITY % codeml.cp.common.attrib 
           "%codeml.common.attrib;
            xlink:xref IDREF #IMPLIED 
            xlink:type CDATA #FIXED 'simple'
            xlink:role CDATA #FIXED 'corresponds-to'">

<!ENTITY % codeml.cp.group.attrib "open CDATA #IMPLIED
                                   close CDATA #IMPLIED
                                   separator CDATA #IMPLIED
                                   indent CDATA #IMPLIED
                                   Obreak CDATA #IMPLIED
                                   breakO CDATA #IMPLIED
                                   Cbreak CDATA #IMPLIED
                                   breakC CDATA #IMPLIED
                                   Sbreak CDATA #IMPLIED
                                   breakS CDATA #IMPLIED">

<!ENTITY % codeml.cp.style.attrib "variant NMTOKEN #IMPLIED
                                   size CDATA #IMPLIED
                                   color CDATA #IMPLIED
                                   background CDATA #IMPLIED">
<!ENTITY % codeml.cp.token.attrib "%codeml.cp.common.attrib; 
                                   %codeml.cp.style.attrib;">

<!-- groups -->
<!ELEMENT %codeml.cpg.qname; (%codeml.cp.class;)*>
<!ATTLIST %codeml.cpg.qname; %codeml.cp.common.attrib;
                             %codeml.cp.group.attrib;>

<!-- basic language objects -->
<!ELEMENT %codeml.cpb.qname; (#PCDATA)>
<!ATTLIST %codeml.cpb.qname; %codeml.cp.token.attrib; type NMTOKEN #IMPLIED>

<!-- operators -->
<!ENTITY % codeml.cpo.type.attval "(built-in|imported|defined|definiens|recursive-call)">
<!ELEMENT %codeml.cpo.qname; (#PCDATA)>
<!ATTLIST %codeml.cpo.qname; %codeml.cp.token.attrib;
                             type %codeml.cpo.type.attval; #IMPLIED>

<!-- identifiers -->
<!ELEMENT %codeml.cpi.qname; (#PCDATA)>
<!ATTLIST %codeml.cpi.qname; %codeml.cp.token.attrib;>

<!-- linebreaks -->
<!ELEMENT %codeml.cpbr.qname; EMPTY>
<!ATTLIST %codeml.cpbr.qname; %codeml.cp.common.attrib;>

<!-- data types -->
<!ELEMENT %codeml.cptype.qname; (#PCDATA)>
<!ATTLIST %codeml.cptype.qname; %codeml.cp.common.attrib;>

<!-- multilingual text -->
<!ENTITY % codeml.cpt.extra.content "">
<!ELEMENT %codeml.cpt.qname; (#PCDATA%codeml.cpt.extra.content;)>
<!ATTLIST %codeml.cpt.qname;  %codeml.cp.common.attrib;
                              %codeml.cp.style.attrib;
                              %xml.lang.attrib;>

<!-- descriptions -->
<!ELEMENT %codeml.cpd.qname; (%codeml.cpt.qname;)*>
<!ATTLIST %codeml.cpd.qname; %codeml.cp.common.attrib;>

<!-- comments -->
<!ELEMENT %codeml.cpc.qname; (%codeml.cpt.qname;)*>
<!ATTLIST %codeml.cpc.qname; %codeml.cp.common.attrib;>

<!-- raw code -->
<!ELEMENT %codeml.cpr.qname; (#PCDATA)>
<!ATTLIST %codeml.cpr.qname; %codeml.cp.common.attrib;>


<!-- style information -->
<!ELEMENT %codeml.cpstyle.qname; (%codeml.cp.class;)>
<!ATTLIST %codeml.cpstyle.qname; %codeml.cp.common.attrib;
                          %codeml.cp.style.attrib;>

