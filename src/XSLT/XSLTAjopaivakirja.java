// MUUTA ALLA OLEVAA RIVIÄ TARVITTAESSA
package XSLT;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class XSLTAjopaivakirja {

	public static void main(String[] args)
			throws javax.xml.transform.TransformerException, IOException {

		try {

			String xslt = "<?xml version='1.0' encoding='UTF-8'?> <xsl:stylesheet version='1.0' 	xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>  	<xsl:output method='html' indent='yes' /> 	<xsl:template match='/'> 	    <html> 	        <head> 	             <title>Ajopaivakirja</title> 	        </head> 	        <body> 	             <table border='1' cellpadding='5' cellspacing='0'> 	                    <tr> 	                        <th>Lähtöaika</th> 	                        <th>Lähtöpaikka</th> 	                        <th>Tuloaika</th> 	                        <th>Tulopaikka</th> 	                        <th>Matka ja korvaus</th> 	                    </tr> 	                        <xsl:apply-templates select='ajopaivakirja/ajo'> 	                        </xsl:apply-templates> 	             </table> 	        </body> 	    </html> 	</xsl:template> 	<xsl:template match='ajo'> 	     <tr> 	        <xsl:apply-templates select='alku' /> 	        <xsl:apply-templates select='loppu' /> 	        <td> 	            <xsl:value-of select='loppu/lukema - alku/lukema'></xsl:value-of> 	            kilometriä, 	            <xsl:value-of select='(loppu/lukema - alku/lukema)*0.41'></xsl:value-of> 	            euroa 	        </td> 	     </tr> 	</xsl:template> 	<xsl:template match='alku'> 	     <td><xsl:apply-templates select='aika'/></td> 	     <td><xsl:value-of select='paikka'></xsl:value-of></td> 	</xsl:template> 	<xsl:template match='loppu'> 	     <td><xsl:apply-templates select='aika' /></td> 	     <td><xsl:value-of select='paikka'></xsl:value-of></td> 	</xsl:template> 	<xsl:template match='alku/aika'> 	     <xsl:value-of select='@paiva' />&#160;<xsl:value-of select='@kello' /> 	</xsl:template> 	<xsl:template match='loppu/aika'> 	     <xsl:value-of select='@paiva' />&#160;<xsl:value-of select='@kello' /> 	</xsl:template> </xsl:stylesheet> ";
			
			StringReader readerXSLT = new StringReader(xslt);
			final File xmlFile = new File("ajopaivakirja2.xml");
			final File result = new File("ajopaivakirja2.html");

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
					.println("Muunnoksen tulos on tiedostossa ajopaivakirja2.html");

		} catch (javax.xml.transform.TransformerException ex) {
			System.out.println("Muunnos ei onnistu, koska ");
			System.out.println(ex.getMessage());
		} catch (Exception ex) {
			System.out.println("Ongelmia, koska ");
			System.out.println(ex.getMessage());
		}
	}

}
