<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" indent="yes" />
	<xsl:template match="/">
	    <html>
	         <head>
	              <title>Elokuvat</title>
	         </head>
	         <body>
	               <h3>Elokuvat</h3>
	               <xsl:apply-templates select="Events/Event">
	               </xsl:apply-templates>
	         </body>
	         
	    </html>
	</xsl:template>
	<xsl:template match="Event">
	     <p>
	        Nimi: <xsl:value-of select="Title" /> (<xsl:value-of select="OriginalTitle" />)<br />
	        Kuvaus: <xsl:value-of select="ShortSynopsis" /><br />
	        Vuosi: <xsl:value-of select="ProductionYear" /><br />
	        Esitykseen: <xsl:value-of select="substring(dtLocalRelease,1,10)" /><br />
	        Ohjaajat: <xsl:apply-templates select="Directors/Director" /><br />
	        Näyttelijät: <xsl:apply-templates select="Cast/Actor" /> <br />
	     </p>
	</xsl:template>
	<xsl:template match="Director">
	         <xsl:value-of select="FirstName" />&#160;<xsl:value-of select="LastName" />
	</xsl:template>
	<xsl:template match="Actor">
	         <xsl:value-of select="FirstName" />&#160;<xsl:value-of select="LastName" />
	         <xsl:if test="position() != last()">
	              <xsl:text>,&#160;</xsl:text>
	         </xsl:if>
	</xsl:template>
</xsl:stylesheet>