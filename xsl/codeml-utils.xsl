<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:func="http://exslt.org/functions" 
  xmlns:codeml="http://www.mathweb.org/codeml" 
  xmlns:omdoc="http://www.mathweb.org/omdoc"
  xmlns:str="http://exslt.org/strings" 
  extension-element-prefixes="func omdoc str"
  version="1.0">

  <xsl:include href="../../../xsl/util/compat.xsl"/>


<xsl:template name="do-indented-break">
  <xsl:param name="indent"/>
  <xsl:text>&#xA;</xsl:text>
  <xsl:call-template name="ntimes">
    <xsl:with-param name="n" select="$indent"/>
    <xsl:with-param name="what" select="' '"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="ntimes">
 <xsl:param name="n"/>
 <xsl:param name="what"/>
 <xsl:if test="$n &gt; 0">
  <xsl:copy-of select="$what"/>
  <xsl:call-template name="ntimes">
   <xsl:with-param name="n" select="$n - 1"/>
   <xsl:with-param name="what" select="$what"/>
  </xsl:call-template>
 </xsl:if>
</xsl:template>

<func:function name="codeml:preferred-language">
  <xsl:variable name="nodename"><xsl:value-of  select="local-name()"/></xsl:variable>
  <xsl:variable name="language"><xsl:value-of  select="@xml:lang"/></xsl:variable>
  <xsl:variable name="siblings-nodeset" select="preceding-sibling::node()[local-name()=$nodename]|following-sibling::node()[local-name()=$nodename]"/>
  <!-- Test, whether this node is among the wanted ones (in terms of language). -->
  <xsl:choose>
   <xsl:when test="contains($TargetLanguage,$language)">
    <!-- Test, whether other nodes don't have higher priority language-values -->
    <xsl:if test="not($siblings-nodeset[contains(substring-before($TargetLanguage,$language),@xml:lang)])">
     <!-- Test, whether this node is the only valid one (in terms 
          of language). If not, it is nevertheless a valid one and will 
          be written to the result-tree-->
     <xsl:if test="$language=$siblings-nodeset/@xml:lang">
      <xsl:message>
       Error: There are two siblings with the same xml:lang attribute in <xsl:value-of select="../@id"/>!
     </xsl:message>
    </xsl:if>
    <func:result select="true()"/>
   </xsl:if> 
  </xsl:when>
  <xsl:otherwise><func:result select="false()"/></xsl:otherwise>
 </xsl:choose>
</func:function>

<func:function name="codeml:preferred-format">
  <xsl:variable name="nodename"><xsl:value-of  select="local-name()"/></xsl:variable>
  <xsl:variable name="format"><xsl:value-of  select="@format"/></xsl:variable>
  <xsl:variable 
   name="siblings-nodeset" 
   select="preceding-sibling::node()[local-name()=$nodename]
          |following-sibling::node()[local-name()=$nodename]"/>
  <!-- Test, whether this node is among the wanted ones (in terms of language). -->
  <xsl:choose>
   <xsl:when test="contains($TargetFormat,$format)">
    <!-- Test, whether other nodes don't have higher priority format-values -->
    <xsl:if test="not($siblings-nodeset[contains(substring-before($TargetFormat,$format),@format)])">
     <!-- Test, whether this node is the only valid one (in terms 
          of language). If not, it is nevertheless a valid one and will 
          be written to the result-tree-->
     <xsl:if test="$format=$siblings-nodeset/@format">
      <xsl:message>
       Error: There are two siblings with the same format attribute in <xsl:value-of select="../@id"/>!
     </xsl:message>
    </xsl:if>
    <func:result select="true()"/>
   </xsl:if> 
  </xsl:when>
  <xsl:otherwise><func:result select="false()"/></xsl:otherwise>
 </xsl:choose>
</func:function>

<xsl:template name="comment-lines">
  <xsl:param name="text"/>
  <xsl:for-each select="str:tokenize($text,'&#x09;&#x0A;&#x0D;')">
    <xsl:call-template name="commentline">
      <xsl:with-param name="comment" select="."/>
    </xsl:call-template>
  </xsl:for-each>
</xsl:template>

<xsl:template name="get-role">
  <xsl:choose>
    <xsl:when test="@role='aut'">author</xsl:when>
    <xsl:when test="@role='aqt'">quoted</xsl:when>
    <xsl:when test="@role='aft'">afterword</xsl:when>
    <xsl:when test="@role='aui'">introduction</xsl:when>
    <xsl:when test="@role='clb'">collaborator</xsl:when>
    <xsl:when test="@role='edt'">editor</xsl:when>
    <xsl:when test="@role='ths'">advisor</xsl:when>
    <xsl:when test="@role='trc'">transcriber</xsl:when>
    <xsl:when test="@role='trl'">translator</xsl:when>
  </xsl:choose>
</xsl:template>


</xsl:stylesheet>
