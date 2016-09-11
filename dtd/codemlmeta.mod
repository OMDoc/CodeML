<!--
   An XML DTD for presentation and content of program code (CodeML): module Presentation
      SYSTEM http://www.mathweb.org/src/matheb/omdoc/project/codeml/dtd/codemlmeta.mod
      PUBLIC -//OMDoc//DTD CodeML META V1.2//EN
   See the documentation and examples at 
   http://www.mathweb.org/src/mathweb/omdoc/projects/codeml
   (c) 2002-2003 Michael Kohlhase, released under the GNU Public License (GPL)
-->

<!-- include the OMDoc version of DC (we need the omdoc entities first) -->
<!ENTITY % css.attrib "style CDATA #IMPLIED
                       class NMTOKEN #IMPLIED">
<!ENTITY % idi.attrib "id ID #IMPLIED %css.attrib;">

<!ENTITY % omdocmtxt.style.extra "">
<!ENTITY % omdoc.xmlns.theory.attrib "">
<!ENTITY % omdoc.mtext.content "#PCDATA">
<!ENTITY % anyURI "CDATA">
<!ENTITY % omdocref "CDATA">

<!ENTITY % omdocdc.ent PUBLIC "-//OMDoc//DTD OMDoc DC V1.2 ENTITIES//EN"  
                              "../../../dtd/omdocdc.ent">
%omdocdc.ent;
<!ENTITY % omdocdc.mod PUBLIC "-//OMDoc//DTD OMDoc DC V1.2 ELEMENTS//EN"  
                              "../../../dtd/omdocdc.mod">
%omdocdc.mod;

<!-- CodeML Metadata comes in two forms: 
     1) Bibliographic Metadata corresponding to the model of the
        Dublin Metadata initiative (http://purl.org/dc/elements/1.1/) 
     2) other, application-specific metadata  -->
<!ENTITY % codeml.metadata.qname "%codeml.pfx;metadata">

<!ENTITY % codeml.meta.opt.content "(%codeml.metadata.qname;)?,">

<!ELEMENT %codeml.metadata.qname; (%omdocdc.class;)*>
<!ATTLIST %codeml.metadata.qname; %idi.attrib; inherits CDATA #IMPLIED>

