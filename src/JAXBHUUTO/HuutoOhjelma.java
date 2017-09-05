package JAXBHUUTO;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.xml.sax.SAXException;

class HuutoOhjelma {

	final private File xml = new File("huuto/huuto3.xml");
	final private File xsd = new File("huuto/huuto3.xsd");

	private Entry entry;

	public void toXML() throws JAXBException, SAXException {

		final JAXBContext jaxbContext = JAXBContext.newInstance(Entry.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		/*
		 * jaxbMarshaller.setProperty(
		 * Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "huuto3.xsd");
		 * 
		 * SchemaFactory schemaFactory = SchemaFactory
		 * .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); Schema schema =
		 * schemaFactory.newSchema(xsd); jaxbMarshaller.setSchema(schema);
		 */

		jaxbMarshaller.marshal(entry, xml);
	}

	// LAITA TÄSTÄ ALASPÄIN OLEVA KOODI Viopeen

	public void lisaaEntry() {
		entry = new Entry();
		XMLGregorianCalendar result;
		java.text.SimpleDateFormat fm = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		GregorianCalendar cal = new GregorianCalendar();
		String url = "http://api.huuto.net/somt/0.9/categories/";
        String toimitus;
		Entry.Category cate = new Entry.Category();
		Entry.Price.StartingPrice startprice = new Entry.Price.StartingPrice();
		Entry.Price price = new Entry.Price();
		Entry.DeliveryMethods deliverymethods = new Entry.DeliveryMethods();
		List<Entry.DeliveryMethods.DeliveryMethod> listMethod = deliverymethods.getDeliveryMethod();

		Scanner input = new Scanner(System.in);
		Scanner input1 = new Scanner(System.in);
		try {

			System.out.print("Anna tapahtuman nimi: ");
			String title = input.nextLine();
			
			System.out.print("Anna tapahtuman kategorian numero: ");
			int numero = input.nextInt();
			cate.setScheme(url + numero);

			System.out.print("Anna tapahtuman yhteenveto: ");
	         String yhteenveto = input1.nextLine();
	        
			System.out.print("Anna huuhdon päättymispäivä (vvvv-kk-pp): ");
			String paivaStr = input.next();
			cal.setTime(fm.parse(paivaStr));
			result = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(cal.get(java.util.Calendar.YEAR), cal.get(java.util.Calendar.MONTH)+1, cal.get(java.util.Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
			//System.out.println(result);
			System.out.print("Anna tapahtuman aloitushinta: ");
			String hinta = input.next();
			startprice.setCurrency("EUR");
			startprice.setValue(new BigDecimal(hinta.replace(",", ".")));
			price.setStartingPrice(startprice);
						do {
				System.out.print("Anna toimitustapa (- lopettaa): ");
				toimitus = input.next();
				if(!toimitus.equals("-")){
					Entry.DeliveryMethods.DeliveryMethod method = new Entry.DeliveryMethods.DeliveryMethod();
					method.setType(toimitus);
					listMethod.add(method);
				} else {
					entry.setTitle(title);
					entry.setCategory(cate);
					entry.setSummary(yhteenveto);
					entry.setExpirationTime(result);
					entry.setPrice(price);
					entry.setDeliveryMethods(deliverymethods);
			       }
			} while (!toimitus.equals("-"));

		} catch (java.text.ParseException e) {
			e.printStackTrace();
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(final String[] args) {
		HuutoOhjelma ohjelma = new HuutoOhjelma();
		try {
			ohjelma.lisaaEntry();
			ohjelma.toXML();
		} catch (final JAXBException e) {
			e.printStackTrace();
		} catch (final SAXException e) {
			System.out.print("Validointi ei onnistunut");
		}
	}

}