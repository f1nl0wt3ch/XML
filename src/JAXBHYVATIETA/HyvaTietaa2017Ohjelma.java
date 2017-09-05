package JAXBHYVATIETA;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.SAXException;

import JAXBHYVATIETA.Events.Event;

class HyvaTietaa2017Ohjelma {

	final private File xml = new File("Hyvatietaa/hyvaTietaa2017.xml");
	final private File xsd = new File("Hyvatietaa/hyvaTietaa2017.xsd");

	private Events events;
	

	public void toObject() throws JAXBException, SAXException {
		final JAXBContext jaxbContext = JAXBContext.newInstance(Events.class);
		final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		events = (Events) jaxbUnmarshaller.unmarshal(xml);
	}

	public void toXML() throws JAXBException, SAXException {

		final JAXBContext jaxbContext = JAXBContext.newInstance(Events.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		/*
		 * jaxbMarshaller.setProperty(
		 * Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "hyvaTietaa2017.xsd");
		 * 
		 * SchemaFactory schemaFactory = SchemaFactory
		 * .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); Schema schema =
		 * schemaFactory.newSchema(xsd); jaxbMarshaller.setSchema(schema);
		 */
		jaxbMarshaller.marshal(events, xml);
	}

	// LAITA TÄSTÄ ALASPÄIN OLEVA KOODI Viopeen
	public void tulostaaEvent(Events.Event e) {
		java.text.SimpleDateFormat fm = new java.text.SimpleDateFormat("d.M.yyyy");
		String dateStr = fm.format(e.getDate().toGregorianCalendar().getTime());
		System.out.print(dateStr);
		System.out.print(" " + e.getName());
		System.out.print(" (" + e.getUrl() + ")");
	}

	public void listaaEvents() {
		List<Events.Event> list = events.getEvent();
		if (list.size() == 0)
			System.out.print("Listassa ei ole elementtiä");
		else {
			for (Events.Event e : list) {
				tulostaaEvent(e);
				System.out.print("\n");
			}
		}
	}

	public void haeEvent() {
		int lkm =0;
		String virhe = "Tapahtumaa ei ole annetulla nimellä";
		List<Events.Event> list = events.getEvent();
		System.out.print("Anna haettavan tapahtuman nimi: ");
		Scanner input = new Scanner(System.in);
		String nimi = input.nextLine();
		for (int i=0; i < list.size(); i++) {
			if (list.get(i).getName().equalsIgnoreCase(nimi)){ 
				if(list.get(i).getFlagDay() != null) {
		    	        tulostaaEvent(list.get(i));
		    	        System.out.print(" Liputuspäivä");
		    	        break;
				} else {
					if(list.get(i).getAlternateNames() != null) {
		    	         tulostaaEvent(list.get(i));
		    	         System.out.print(" "+list.get(i).getAlternateNames());
		    	         break;
		    	       } else {
		    	          tulostaaEvent(list.get(i));
		    	          System.out.print("\n");
		    	       }
				}
					
		    } else {
		    	    lkm++;
		       	if (lkm == list.size()-1)
            	          System.out.print(virhe);
		    }
				
		}
		 
	}

	public void muutaEvent() {
		int lkm = 0;
		Events.Event event;
		Scanner input = new Scanner(System.in);
		List<Events.Event> list = events.getEvent();
		for (Events.Event e : list) {
			lkm++;
			System.out.print(lkm + (". "));
			tulostaaEvent(e);
			System.out.print("\n");
		}
		System.out.print("Anna muutettavan tapahtuman numero: ");
		int valinta = input.nextInt();
		event = (valinta <= list.size() + 1) ? list.get(valinta - 1) : null;
		if (event == null)
			System.out.print("Tapahtumaa ei ole numerolla " + valinta);
		else {
			System.out.print("Anna tapahtuman uusi www: ");
			String url = input.next();
			list.get(valinta - 1).setUrl(url);
		}
	}

	public void poistaEvent() {
		int lkm = 0;
		Events.Event event;
		Scanner input = new Scanner(System.in);
		List<Events.Event> list = events.getEvent();
		for (Events.Event e : list) {
			lkm++;
			System.out.print(lkm + (". "));
			tulostaaEvent(e);
			System.out.print("\n");
		}
		System.out.print("Anna poistettavan tapahtuman numero: ");
		int valinta = input.nextInt();
		event = (valinta <= list.size() + 1) ? list.get(valinta - 1) : null;
		if (event == null)
			System.out.print("Tapahtumaa ei ole numerolla " + valinta);
		else {
			list.remove(valinta - 1);
			System.out.print("Tapahtuma poistettiin numerolla "+valinta);
		}
	}

	public static void main(final String[] args) {
		HyvaTietaa2017Ohjelma sovellus = new HyvaTietaa2017Ohjelma();

		try {

			sovellus.toObject();

			Scanner input = new Scanner(System.in);
			int valinta = -1;

			do {
				System.out.println("\n1 = Listaa tapahtumat");
				System.out.println("2 = Hae tapahtumat nimellä");
				System.out.println("3 = Poista tapahtuma");
				System.out.println("4 = Muuta tapahtuman url");
				System.out.println("0 = Lopeta");
				System.out.print("Anna valintasi: ");
				valinta = input.nextInt();
				input.nextLine();
				switch (valinta) {
				case 1:
					sovellus.listaaEvents();
					break;
				case 2:
					sovellus.haeEvent();
					break;
				case 3:
					sovellus.poistaEvent();
					break;
				case 4:
					sovellus.muutaEvent();
					break;
				}

			} while (valinta != 0);

			sovellus.toXML();

		} catch (final JAXBException e) {
			e.printStackTrace();
		} catch (final SAXException e) {
			System.out.print("Validointi ei onnistunut");
		}
	}

}
