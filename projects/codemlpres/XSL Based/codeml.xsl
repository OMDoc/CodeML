<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" doctype-public="-//W3C//DTD HTML 4.01//EN"
	doctype-system="http://www.w3.org/TR/html4/strict.dtd"/>
	<xsl:template match="/">
		<html>
			<head>
				<title>CodeML</title>
				<script type='text/javascript'>
					window.onload = function(){
						if (window.winOnLoad) window.winOnLoad();
					}
				</script>
				<link rel='stylesheet' type='text/css' href='codeml.css'/>
				<script type='text/javascript' src='codeml.js'></script>
			</head>
			<body>
				<xsl:apply-templates/>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="pcode">
		<ul id='pcode1' class='pCode'>
			<li>
			<xsl:for-each select="*">
				<xsl:call-template name="whole" />
			</xsl:for-each>
			</li>
		</ul>
	</xsl:template>
	<xsl:template name="whole">
			<xsl:if test="./cpg[1]">
				<span>
					<xsl:call-template name="break"/>
					<xsl:if test="./cpg[2]/@breakO">
						<xsl:value-of select="./cpg[2]/@open"/>
					</xsl:if>
				</span>
			</xsl:if>
			<xsl:if test="./cpg[2]">
				<ul>
					<xsl:if test="./cpg[2]/@Obreak">
						<li>
							<xsl:value-of select="./cpg[2]/@open"/>
						</li>
					</xsl:if>
						<xsl:for-each select="./cpg[2]/*">
							<xsl:if test="@Cbreak">
							<li>
							<xsl:choose>
								<xsl:when test="self::cpg">
									<xsl:for-each select="*">
										<xsl:if test="@open">
											<xsl:value-of select="@open"/>
										</xsl:if>
										<xsl:call-template name="cpgsecond"/>
										<xsl:if test="@close">
											<xsl:value-of select="@close"/>
										</xsl:if>
									</xsl:for-each>
								</xsl:when>	
							</xsl:choose>
							<xsl:value-of select="@close"/>
							</li>
							</xsl:if>
							
							<xsl:if test="@Pbreak">
							<li>
								<xsl:call-template name="whole"/>
							</li>
							</xsl:if>
							
						</xsl:for-each>
					<li>
						<xsl:value-of select="./cpg[2]/@close"/>	
					</li>	
				</ul>
			</xsl:if>
	</xsl:template>
	<xsl:template name="break">
		<xsl:for-each select="./cpg[1]/*">
			<xsl:if test="@open">
				<xsl:value-of select="@open"/>
			</xsl:if>
			<xsl:call-template name="cpgsecond"/>
			<xsl:if test="@close">
				<xsl:value-of select="@close"/>
			</xsl:if>
			
			
		</xsl:for-each>
	</xsl:template>
	
	<xsl:template name="cpgsecond">
			<xsl:choose>
				<xsl:when test="self::cpo">
					<xsl:choose>
						<xsl:when test="./@type='built-in'">
							<div class="cpobuiltin">
								<xsl:value-of select="."/>
								<xsl:text> </xsl:text>
							</div>
						</xsl:when>
						<xsl:otherwise>
							<div class="cpo">
								<xsl:value-of select="."/>
								<xsl:text> </xsl:text>
							</div>
						</xsl:otherwise> 
					</xsl:choose>				
				</xsl:when>	
				<xsl:when test="self::cpb">
					<div class="cpb"><xsl:value-of select="."/></div>
				</xsl:when>	
				<xsl:when test="self::cpi">
					<div class="cpi"><xsl:value-of select="."/></div>
				</xsl:when>	
				<xsl:when test="self::cptype">
					<div class="cptype">
						<xsl:value-of select="."/>
						<xsl:text> </xsl:text>
					</div>
				</xsl:when>	
				<xsl:when test="self::cpg">
					<xsl:for-each select="*">
						<xsl:if test="@open">
							<xsl:value-of select="@open"/>
						</xsl:if>
						<xsl:call-template name="cpgsecond"/>
						<xsl:if test="@close">
							<xsl:value-of select="@close"/>
						</xsl:if>
					</xsl:for-each>											
				</xsl:when>	
			</xsl:choose>
	</xsl:template>
	<xsl:template match="*">
	</xsl:template>	
</xsl:stylesheet>

  