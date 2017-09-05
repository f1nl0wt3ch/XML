// MUUTA ALLA OLEVAA RIVIÄ TARVITTAESSA
package XSLT;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class XSLTKatsojat {

	public static void main(String[] args)
			throws javax.xml.transform.TransformerException, IOException {

		try {
			String xslt = "<?xml version='1.0' encoding='UTF-8'?> <xsl:stylesheet version='1.0' 	xmlns:xsl='http://www.w3.org/1999/XSL/Transform'> 	 	<xsl:output method='html' indent='yes' /> 	<xsl:template match='/'> 		<html> 			<head> 				<title>Katsojatilasto</title> 			</head> 			<body> 			      <h2>Katsojatilasto 50/2016</h2> 		          <xsl:apply-templates select='toplista/ohjelmat/ohjelma'> 		          </xsl:apply-templates> 			</body> 		</html> 	</xsl:template> 	<xsl:template match='ohjelma'> 		<p> 	        Sija: <xsl:value-of select='@jnro' /><br /> 	        Nimi: <xsl:value-of select='nimi' /><br /> 	        Kanava: <xsl:value-of select='@kanava' /><br /> 	        Katsojamäärä: <xsl:value-of select='katsojamaara' /> 		</p> 	</xsl:template> </xsl:stylesheet>       ";
			StringReader readerXSLT = new StringReader(xslt);
			final File xmlFile = new File("katsojat2.xml");
			final File result = new File("katsojat2.html");

			javax.xml.transform.Source xmlSource = new javax.xml.transform.stream.StreamSource(
					xmlFile);
			javax.xml.transform.Source xsltSource = new javax.xml.transform.stream.StreamSource(
					readerXSLT);
			javax.xml.transform.Result htmlResult = new javax.xml.transform.stream.StreamResult(
					result);

			javax.xml.transform.TransformerFactory transFact = javax.xml.transform.TransformerFactory
					.newInstance();

			javax.xml.transform.Transformer trans = transFact
					.newTransformer(xsltSource);

			trans.transform(xmlSource, htmlResult);
			System.out
					.println("Muunnoksen tulos on tiedostossa katsojat2.html");

		} catch (javax.xml.transform.TransformerException ex) {
			System.out.println("Muunnos ei onnistu, koska ");
			System.out.println(ex.getMessage());
		} catch (Exception ex) {
			System.out.println("Ongelmia, koska ");
			System.out.println(ex.getMessage());
		}
	}

}
