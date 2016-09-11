<!--
   An XML DTD for presentation and content of program code (CodeML)
   Module INTER: Interaction of Presentation and Content
      SYSTEM http://www.mathweb.org/src/matheb/omdoc/project/codeml/dtd/codeml.dtd
      PUBLIC -//OMDoc//DTD CodeML INTER V1.2//EN
   See the documentation and examples at 
   http://www.mathweb.org/src/mathweb/omdoc/projects/codeml
   (c) 2002-2003 Michael Kohlhase, released under the GNU Public License (GPL)
-->

<!ENTITY % codeml.semantics.qname "%codeml.pfx;semantics">
<!ENTITY % codeml.pcode.qname "%codeml.pfx;pcode">
<!ENTITY % codeml.rawcode.qname "%codeml.pfx;rawcode">
<!ENTITY % codeml.code.qname "%codeml.pfx;code">

<!ELEMENT %codeml.semantics.qname; 
          ((%codeml.cc.top.class;),(%codeml.pcode.qname;|%codeml.rawcode.qname;)*)>

<!ENTITY % codeml.xcode.attrib "format CDATA #REQUIRED 
                                type CDATA #IMPLIED
                                href CDATA #IMPLIED">

<!ENTITY % codeml.pcode.content "%codeml.meta.opt.content;(%codeml.cp.class;)*">
<!ELEMENT %codeml.pcode.qname; (%codeml.pcode.content;)>
<!ATTLIST %codeml.pcode.qname; %codeml.xcode.attrib;
                               %codeml.cp.group.attrib;>

<!ENTITY % codeml.rawcode.content "#PCDATA">
<!ELEMENT %codeml.rawcode.qname; (%codeml.rawcode.content;)>
<!ATTLIST %codeml.rawcode.qname; %codeml.xcode.attrib;>

<!-- need to declare at least the xlink namespace -->
<!ENTITY % codeml.extra.namespacedecl.attrib "">
<!ENTITY % codeml.namespacedecl.attrib 
           "%codeml.extra.namespacedecl.attrib;
            xmlns:xlink CDATA #FIXED 'http:XXXXXX'">

<!ELEMENT %codeml.code.qname; 
          (%codeml.meta.opt.content;(%codeml.cp.class;|
                                       %codeml.cc.top.class;|
                                       %codeml.semantics.qname;)*)>
<!ATTLIST %codeml.code.qname; %codeml.namespacedecl.attrib;
                       %codeml.common.attrib;
		       %codeml.cp.group.attrib;
                       %codeml.xcode.attrib;
		       %codeml.extra.toplevel.attrib;>
