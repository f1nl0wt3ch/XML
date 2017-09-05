<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" indent="yes" />
	<xsl:template match="/">
	    <html>
	        <head>
	            <title>Vuosi 2017</title>
	        </head>
	        <body>
	            <xsl:apply-templates select="events/event">
	            </xsl:apply-templates>
	        </body>
	    </html>
	</xsl:template>
	<xsl:template match="event">
	    <p>
	        Päivä: <xsl:value-of select="date" /><br/>
	        Tapahtuma: <xsl:value-of select="name" /><br />
	        Kuvaus: <xsl:value-of select="description" /><br />
	        url: <xsl:value-of select="url" />
	    </p>
	</xsl:template>
</xsl:stylesheet>