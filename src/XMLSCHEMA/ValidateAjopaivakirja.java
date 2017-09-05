// MUUTA ALLA OLEVAA RIVIÄ TARVITTAESSA
package XMLSCHEMA;

import static org.w3c.dom.Node.ELEMENT_NODE;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ValidateAjopaivakirja {
	public static void main(String[] args) throws SAXException, IOException {

		final String xmlFile = "ajopaivakirja.xml";

		try {

			String schemaFile = "<?xml version='1.0' encoding='UTF-8'?> <xs:schema xmlns:xs='http://www.w3.org/2001/XMLSchema'> <xs:element name='ajopaivakirja'>    <xs:complexType>       <xs:sequence>           <xs:element name='ajo' maxOccurs='unbounded'>               <xs:complexType>                   <xs:sequence>                       <xs:element name='alku'>                           <xs:complexType>                               <xs:sequence>                                   <xs:element name='lukema' type='xs:positiveInteger' ></xs:element>                                   <xs:element name='aika'>                                        <xs:complexType>                                             <xs:attribute name='kello' type='xs:string' />                                             <xs:attribute name='paiva' type='xs:date' />                                        </xs:complexType>                                   </xs:element>                                   <xs:element name='paikka' type='xs:string' ></xs:element>                               </xs:sequence>                           </xs:complexType>                       </xs:element>                       <xs:element name='loppu'>                            <xs:complexType>                               <xs:sequence>                                   <xs:element name='lukema' type='xs:positiveInteger' ></xs:element>                                   <xs:element name='aika'>                                        <xs:complexType>                                             <xs:attribute name='kello' type='xs:string' />                                             <xs:attribute name='paiva' type='xs:date' />                                        </xs:complexType>                                   </xs:element>                                   <xs:element name='paikka' type='xs:string' ></xs:element>                                                            </xs:sequence>                           </xs:complexType>                       </xs:element>                   </xs:sequence>               </xs:complexType>           </xs:element>                </xs:sequence>       <xs:attribute name='rekisterinro' type='xs:string' use='required' />       <xs:attribute name='laatija' type='xs:string' use='required' />    </xs:complexType> </xs:element> </xs:schema> ";
			
			StringReader reader = new StringReader(schemaFile);

			SchemaFactory factory = SchemaFactory
					.newInstance("http://www.w3.org/2001/XMLSchema");

			Schema schema = factory.newSchema(new StreamSource(reader));
			Validator validator = schema.newValidator();

			Source source = new StreamSource(xmlFile);

			validator.validate(source);
			System.out
					.println("Tekemäsi XML Schema vastaa " + xmlFile + "-dokumenttia.");
			DocumentBuilderFactory factory2 = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory2.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(schemaFile));
			Document doc = builder.parse(is);

			ValidateAjopaivakirja ohjelma = new ValidateAjopaivakirja();
			ohjelma.listNodes(doc.getDocumentElement());
			ohjelma.count();

		} catch (SAXException ex) {
			System.out
					.println("Tekemäsi XML Schema ei vastaa " + xmlFile + "-dokumenttia, koska");
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			System.out.println("Tiedostonkäsittely ei onnistu, koska");
			System.out.println(ex.getMessage());
		} catch (Exception ex) {
			System.out.println("Käsittely ei onnistu, koska");
			System.out.println(ex.getMessage());
		}

	}

	private int elementCount = 0;

	private void listNodes(Node node) {
		if (node.getNodeType() == ELEMENT_NODE) {
			if (node.getNodeName().equalsIgnoreCase("xs:element")) {
				elementCount++;
			}

			NodeList list = node.getChildNodes();
			if (list.getLength() > 0) {
				for (int i = 0; i < list.getLength(); i++) {
					listNodes(list.item(i));
				}
			}
		}

	}

	private void count() {
		if (elementCount > 6) {
			System.out.println("XML Schema on tehty kokonaan.");
		} else {
			System.out.println("XML Schema on tehty puutteellisesti, koska xs:element määrä on liian pieni eli " + elementCount + " kappaletta.");
		}
	}
}
