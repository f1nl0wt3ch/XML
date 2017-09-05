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

public class ValidateFinnkino {

	public static void main(String[] args) throws SAXException, IOException {
		final String xmlFile = "finnkino.xml";

		try {

			String schemaFile = "<?xml version='1.0' encoding='UTF-8'?> <xs:schema xmlns:xs='http://www.w3.org/2001/XMLSchema'> 	<xs:element name='Events'> 		<xs:complexType> 			<xs:sequence> 				<xs:element name='Event' maxOccurs='unbounded'> 					<xs:complexType> 						<xs:sequence> 							<xs:element name='Title' type='xs:string'></xs:element> 							<xs:element name='OriginalTitle' type='xs:string'></xs:element> 							<xs:element name='ProductionYear' type='xs:gYear'></xs:element> 							<xs:element name='LengthInMinutes' type='xs:integer'></xs:element> 							<xs:element name='dtLocalRelease' type='xs:string'></xs:element> 							<xs:element name='Rating' type='xs:string'></xs:element> 							<xs:element name='EventType' type='xs:string'></xs:element> 							<xs:element name='Genres' type='xs:string'></xs:element> 							<xs:element name='ShortSynopsis' type='xs:string' minOccurs='0'></xs:element> 							<xs:element name='Synopsis' type='xs:string' minOccurs='0'></xs:element> 							<xs:element name='Cast' minOccurs='0'> 								<xs:complexType> 									<xs:sequence> 										<xs:element name='Actor' maxOccurs='unbounded'> 											<xs:complexType> 												<xs:sequence> 													<xs:element name='FirstName' type='xs:string' minOccurs='0'></xs:element> 													<xs:element name='LastName' type='xs:string'></xs:element> 												</xs:sequence> 											</xs:complexType> 										</xs:element> 									</xs:sequence> 								</xs:complexType> 							</xs:element><!-- cast --> 							<xs:element name='Directors'> 								<xs:complexType> 									<xs:sequence> 										<xs:element name='Director'> 											<xs:complexType> 												<xs:sequence> 													<xs:element name='FirstName' type='xs:string'></xs:element> 													<xs:element name='LastName' type='xs:string'></xs:element> 												</xs:sequence> 											</xs:complexType> 										</xs:element> 									</xs:sequence> 								</xs:complexType> 							</xs:element><!-- Directors --> 						</xs:sequence> 					</xs:complexType> 				</xs:element> 			</xs:sequence> 		</xs:complexType> 	</xs:element> </xs:schema> ";
			
			StringReader reader = new StringReader(schemaFile);
			SchemaFactory factory = SchemaFactory
					.newInstance("http://www.w3.org/2001/XMLSchema");

			Schema schema = factory.newSchema(new StreamSource(reader));
			Validator validator = schema.newValidator();

			Source source = new StreamSource(xmlFile);

			validator.validate(source);
			System.out.println("Tekemäsi XML Schema vastaa " + xmlFile
					+ "-dokumenttia.");

			DocumentBuilderFactory factory2 = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory2.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(schemaFile));
			Document doc = builder.parse(is);

			ValidateFinnkino ohjelma = new ValidateFinnkino();
			ohjelma.listNodes(doc.getDocumentElement());
			ohjelma.count();

		} catch (SAXException ex) {
			System.out.println("Tekemäsi XML Schema ei vastaa " + xmlFile
					+ "-dokumenttia, koska");
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
		if (elementCount > 15) {
			System.out.println("XML Schema on tehty kokonaan.");
		} else {
			System.out.println("XML Schema on tehty puutteellisesti, koska xs:element määrä on liian pieni eli " + elementCount + " kappaletta.");
		}
	}
}
