<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:dc="http://purl.org/dc/elements/1.1/"
  xmlns:codeml="http://www.mathweb.org/codeml" 
  xmlns:date="http://exslt.org/dates-and-times"
  extension-element-prefixes="date"
  exclude-result-prefixes="dc codeml date"
  version="1.0">

<xsl:include href="codeml-utils.xsl"/>

<xsl:strip-space elements="*"/>
<xsl:output method="html"/>

<xsl:param name="IndentStep" select="2"/>
<xsl:param name="TargetFormat"/>
<xsl:param name="TargetLanguage" select="'en'"/>
<xsl:param name="css" select="'codeml.css'"/>

<xsl:template match="*">
 <xsl:message>Cannot deal with element of type <xsl:value-of select="local-name()"/>!</xsl:message>
</xsl:template>

<xsl:template match="/">
  <xsl:call-template name="comment-lines">
    <xsl:with-param name="text" select="'This file is automatically generated, from an CodeML document &#xA;by an XSL style sheet (codeml2raw.xsl)  do not edit&#xA;for information about CodeML, see http://www.mathweb.org/omdoc'"/>
  </xsl:call-template>
  <xsl:text>&#xA;&#xA;</xsl:text>
 <html>
  <head><link rel="stylesheet" type="text/css" href="{$css}"/></head>
  <body bgcolor="#FFFFFF">
    <xsl:apply-templates select="/code/metadata" mode="toplevel"/>
    <xsl:apply-templates/>
  </body>
 </html>
</xsl:template>

<xsl:template match="code">
  <pre>
    <xsl:call-template name="do-group">
      <xsl:with-param name="indent" select="0"/>
    </xsl:call-template>
  </pre>
</xsl:template>

<xsl:template match="semantics">
 <!-- we need to decide which of the representations we use -->
 <xsl:choose>
  <!-- if there is presented code in one of the targt formats, then we take that -->
  <xsl:when test="pcode[contains($TargetFormat,@format)]">
   <xsl:apply-templates select="pcode"/>
  </xsl:when>
  <!-- if there is raw code in one of the targt formats, then we take that instead -->
  <xsl:when test="rawcode[contains($TargetFormat,@format)]">
   <xsl:message>There is no presented Code for the targets <xsl:value-of select="TargetFormat"/>
Reverting to raw code!</xsl:message>
   <xsl:apply-templates select="rawcode[contains($TargetFormat,@format)]"/>
  </xsl:when>
  <!-- if all fails, we try to generate something from the content description -->
  <xsl:otherwise>
   <xsl:apply-templates select="*[1]"/>
  </xsl:otherwise>
 </xsl:choose>
</xsl:template>

<xsl:template match="rawcode">
 <xsl:if test="codeml:preferred-format()"><xsl:apply-templates/></xsl:if>
</xsl:template>

<xsl:template match="pcode">
 <xsl:if test="codeml:preferred-format()">
  <xsl:call-template name="do-group">
   <xsl:with-param name="indent" select="0"/>
  </xsl:call-template>
 </xsl:if>
</xsl:template>

<xsl:template match="cpg">
 <xsl:param name="indent"/>
 <xsl:call-template name="do-group">
  <xsl:with-param name="indent" select="$indent"/>
 </xsl:call-template>
</xsl:template>

<xsl:template name="do-group">
 <xsl:param name="indent"/>
 <!-- we compute the effective step increment -->
 <xsl:variable name="step">
  <xsl:choose>
   <xsl:when test="@indent"><xsl:value-of select="$IndentStep * @indent"/> </xsl:when>
   <xsl:otherwise><xsl:value-of select="$IndentStep"/></xsl:otherwise>
  </xsl:choose>
 </xsl:variable>
 <xsl:variable name="sep" select="@separator"/>
 <!-- if the break parameter says so, we break and indent -->
 <!-- **** we do not care about the value yet, every break is hard **** -->
 <xsl:if test="@breakO">
  <xsl:call-template name="do-indented-break">
   <xsl:with-param name="indent" select="$indent"/>
  </xsl:call-template>
 </xsl:if>
 <!-- then comes the opening -->
 <xsl:if test="@open"><xsl:value-of select="@open"/></xsl:if>
 <!-- and we break again, if necessary -->
 <xsl:if test="@Obreak">
  <xsl:call-template name="do-indented-break">
   <xsl:with-param name="indent" select="$indent + $step"/>
  </xsl:call-template>
 </xsl:if>
 <!-- for all the children of the group,-->
 <xsl:for-each select="*">
   <!-- we process the child recursively with updated indent -->
  <xsl:apply-templates select=".">
   <xsl:with-param name="indent" select="$indent+$step"/>
  </xsl:apply-templates>
  <!-- if necessary we treat the separator with its breaks -->
  <xsl:if test="position()!=last()">
    <!-- we break  if specified -->
    <xsl:if test="../@breakS">
      <xsl:call-template name="do-indented-break">
        <xsl:with-param name="indent" select="$indent + $step"/>
      </xsl:call-template>
    </xsl:if>
    <!-- print the separator -->
    <xsl:value-of select="$sep"/>
    <!-- we break  again if specified -->
    <xsl:if test="../@Sbreak">
      <xsl:call-template name="do-indented-break">
        <xsl:with-param name="indent" select="$indent + $step"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:if>
 </xsl:for-each>
 <!-- we break, if specified -->
 <xsl:if test="@breakC">
  <xsl:call-template name="do-indented-break">
   <xsl:with-param name="indent" select="$indent + $step"/>
  </xsl:call-template>
 </xsl:if>
 <!-- then comes the closing -->
 <xsl:if test="@close"><xsl:value-of select="@close"/></xsl:if>
 <!--  and we break again  if specified -->
 <xsl:if test="@Cbreak">
  <xsl:call-template name="do-indented-break">
   <xsl:with-param name="indent" select="$indent"/>
  </xsl:call-template>
 </xsl:if>
</xsl:template>

<xsl:template match="cpbr">
 <xsl:param name="indent"/>
  <xsl:call-template name="do-indented-break">
   <xsl:with-param name="indent" select="$indent"/>
  </xsl:call-template>
</xsl:template>

<xsl:template match="cpb|cpo|cpi|cptype|cpc|cpd">
 <span class="{local-name()}"><xsl:apply-templates/></span>
</xsl:template>

<xsl:template match="cpc">
 <xsl:text>(</xsl:text>
 <span class="{local-name()}"><xsl:apply-templates/></span>
 <xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="cpt">
 <xsl:if test="codeml:preferred-language()"><xsl:apply-templates/></xsl:if>
</xsl:template>


<!-- normally do not output metadata -->
<xsl:template match="metadata"/>

<!-- except at the top level -->
<xsl:template match="metadata" mode="toplevel">
  <xsl:variable name="id">
    <xsl:choose>
      <xsl:when test="dc:Identifier">
        <xsl:for-each select="dc:Identifier">
          <xsl:value-of select="normalize-space(.)"/>
          <xsl:if test="dc:Identifier/@scheme"><xsl:value-of select="concat(' (',@scheme,')')"/></xsl:if>
          <xsl:if test="position()!=last()"><xsl:value-of select="', '"/></xsl:if>
        </xsl:for-each>
      </xsl:when>
      <xsl:otherwise><xsl:value-of select="../@id"/></xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:if test="*">
    <xsl:if test="dc:Title">
      <h1><xsl:value-of select="normalize-space(dc:Title)"/></h1>
      <xsl:value-of select="'&#xA;'"/>
    </xsl:if>
    <xsl:if test="dc:Creator">
      <b>
        <xsl:for-each select="dc:Creator">
          <xsl:variable name="role"><xsl:call-template name="get-role"/></xsl:variable>
          <xsl:value-of select="concat(normalize-space(.),' (',$role,')')"/>
          <xsl:if test="position()!=last()"><xsl:value-of select="', '"/></xsl:if>
        </xsl:for-each>
      </b><br />
      <xsl:value-of select="'&#xA;'"/>
      </xsl:if>
      <xsl:if test="dc:Contributor">
        <em>
          <xsl:value-of select="'with contributions of: '"/>
          <xsl:for-each select="dc:Contributor">
            <xsl:variable name="role"><xsl:call-template name="get-role"/></xsl:variable>
            <xsl:value-of select="concat(normalize-space(.),' (',$role,')')"/>
            <xsl:if test="position()!=last()"><xsl:value-of select="', '"/></xsl:if>
          </xsl:for-each>
        </em><br />
        <xsl:value-of select="'&#xA;'"/>
      </xsl:if>
      <xsl:if test="dc:Date">
        <xsl:for-each select="dc:Date">
          <xsl:variable name="date" select="."/>
          <xsl:value-of select="concat(date:month-name($date),' ',date:day-in-month($date),'. ',date:year($date),' (',@action,')')"/>
          <xsl:if test="position()!=last()"><xsl:value-of select="', '"/></xsl:if>
        </xsl:for-each>
        <br /><hr />
        <xsl:value-of select="'&#xA;'"/>
      </xsl:if>
      <xsl:if test="dc:Rights">
        <xsl:value-of select="concat(normalize-space(dc:Rights),'&#xA;')"/>
      </xsl:if>
      <br /><hr />
      <xsl:text>&#xA;</xsl:text>
    </xsl:if>
</xsl:template>
<!-- to be updated  by importing stylesheet -->

<xsl:template name="commentline">
  <xsl:param name="comment"/>
  <xsl:comment><xsl:value-of select="$comment"/></xsl:comment>
</xsl:template>


</xsl:stylesheet>
