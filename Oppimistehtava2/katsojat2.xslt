<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:output method="html" indent="yes" />
	<xsl:template match="/">
		<html>
			<head>
				<title>Katsojatilasto</title>
			</head>
			<body>
			      <h2>Katsojatilasto 50/2016</h2>
		          <xsl:apply-templates select="toplista/ohjelmat/ohjelma">
		          </xsl:apply-templates>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="ohjelma">
		<p>
	        Sija: <xsl:value-of select="@jnro" /><br />
	        Nimi: <xsl:value-of select="nimi" /><br />
	        Kanava: <xsl:value-of select="@kanava" /><br />
	        Katsojamäärä: <xsl:value-of select="katsojamaara" />
		</p>
	</xsl:template>
</xsl:stylesheet>      