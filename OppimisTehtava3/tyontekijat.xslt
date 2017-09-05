<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" indent="yes" />

	<xsl:template match="/">
		<html>
			<head>
				<title>Työntekijät</title>
			</head>
			<body>
				 <h2>Työntekijät</h2>
				 <xsl:apply-templates select="tyontekijat/tyontekija">
				     <xsl:sort select="palkka" type="number" order="descending"/>
				 </xsl:apply-templates>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="tyontekija">
        <p>
           Id: <xsl:value-of select="@id" /><br/>
           Nimi: <xsl:value-of select="sukunimi" />&#160;<xsl:apply-templates select="etunimi" /><br/>
           <xsl:if test="palkka">
                Palkka: <xsl:value-of select="palkka * 0.99"></xsl:value-of><br />
           </xsl:if>
           <xsl:if test="osasto">
               Osasto: <xsl:value-of select="osasto/@nimi" />
           </xsl:if>
        </p>
	</xsl:template>
	<!-- <xsl:template match="etunimi">
	   <xsl:value-of select="." />&#160;
	</xsl:template> -->
    <xsl:template match="etunimi">
        <xsl:choose>
            <xsl:when test="@kutsuma ='kyllä'"><b><xsl:value-of select="." /></b>&#160;</xsl:when>
            <xsl:otherwise><xsl:value-of select="." />&#160;</xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>