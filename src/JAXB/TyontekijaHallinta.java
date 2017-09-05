package JAXB;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class TyontekijaHallinta { 

	final private File xml = new File("tyontekijat.xml");
	final private File xsd = new File("tyontekijat.xsd");

	private Tyontekijat tyontekijat;

	public void toObject() throws JAXBException, SAXException {
		final JAXBContext jaxbContext = JAXBContext
				.newInstance(Tyontekijat.class);
		final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
/*
		SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(xsd);
		jaxbUnmarshaller.setSchema(schema);
*/
		tyontekijat = (Tyontekijat) jaxbUnmarshaller.unmarshal(xml);
	}

	public void toXML() throws JAXBException, SAXException {

		final JAXBContext jaxbContext = JAXBContext
				.newInstance(Tyontekijat.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		jaxbMarshaller
				.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION,
						"tyontekijat.xsd");
/*
		SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(xsd);
		jaxbMarshaller.setSchema(schema);
*/
		jaxbMarshaller.marshal(tyontekijat, xml);
		jaxbMarshaller.marshal(tyontekijat, System.out);

	}
	public void haeTyontekijaSukunimella(){
		Scanner input = new Scanner(System.in);
		System.out.println("Anna työntekijän sukunimi: ");
		String suku = input.nextLine();
		Boolean onko = false;
		
		List<Tyontekijat.Tyontekija> lista = tyontekijat.getTyontekija();
		for(Tyontekijat.Tyontekija t : lista){
			if(t.getSukunimi().equalsIgnoreCase(suku))
				 onko = true;
			     naytaaTyontekija(t);
			if(onko == false)
				System.out.println("Sukunimellä "+suku + " ei ole työntekijää");
				
		}
		
	}
	
	public void listaaTyontekijat() {
		List<Tyontekijat.Tyontekija> tyontekijaLista = tyontekijat.getTyontekija();
		for(Tyontekijat.Tyontekija t : tyontekijaLista){
			System.out.println("Id: "+t.getId());
			System.out.println("Asema: ");
			if (t.getAsema() != null)
				System.out.print(t.getAsema());
			else
				System.out.println();
			
			System.out.println("Sukunimi: "+t.getSukunimi());
			
			System.out.println("Etunimet: ");
			List<Tyontekijat.Tyontekija.Etunimi> etunimiLista = t.getEtunimi();
			for(Tyontekijat.Tyontekija.Etunimi e : etunimiLista){
				System.out.print(e.getValue()+" ");
			}
			
			System.out.println("Osasto: ");
			if (t.getOsasto() != null)
				System.out.print(t.getOsasto().getNimi());
			else 
				System.out.println();
		}
		/*for(int i=0; i< tyontekijaLista.size(); i++){
			Tyontekijat.Tyontekija tyontekija = tyontekijaLista.get(i);
			System.out.println("Id: "+tyontekija.getId());
			System.out.println("Sukunimi: "+tyontekija.getSukunimi());
		}*/
	}
	
	public void naytaaTyontekija(Tyontekijat.Tyontekija t){
		System.out.println("Id: "+t.getId());
		System.out.println("Asema: ");
		if (t.getAsema() != null)
			System.out.print(t.getAsema());
		else
			System.out.println();
		
		System.out.println("Sukunimi: "+t.getSukunimi());
		
		System.out.println("Etunimet: ");
		List<Tyontekijat.Tyontekija.Etunimi> etunimiLista = t.getEtunimi();
		for(Tyontekijat.Tyontekija.Etunimi e : etunimiLista){
			System.out.print(e.getValue()+" ");
		}
		
		System.out.println("Osasto: ");
		if (t.getOsasto() != null)
			System.out.print(t.getOsasto().getNimi());
		else 
			System.out.println();
	}

	public void lisaaTyontekija() {
		Tyontekijat.Tyontekija tyontekija = new Tyontekijat.Tyontekija();
		
		Scanner input = new Scanner(System.in);
		System.out.print("Anna työntekijän id: ");
		long id = input.nextLong();
	    tyontekija.setId(id);
		System.out.print("Anna työntekijän sukunimi: ");
		input.nextLine();
		String sukunimi = input.nextLine();
		tyontekija.setSukunimi(sukunimi);
		
		System.out.print("Anna työntekijän etunimi: ");
		String etunimi = input.nextLine();
		Tyontekijat.Tyontekija.Etunimi etu = new Tyontekijat.Tyontekija.Etunimi();
		etu.setValue(etunimi);
		tyontekija.getEtunimi().add(etu);
		
		System.out.print("Anna työntekijän palkka: ");
		BigDecimal palkka = input.nextBigDecimal();
		tyontekija.setPalkka(palkka);
		
		System.out.print("Anna työntekijän osasto: ");
		input.nextLine();
		String osastoStr = input.nextLine();
		Tyontekijat.Tyontekija.Osasto osasto = new Tyontekijat.Tyontekija.Osasto();
		osasto.setNimi(osastoStr);
		tyontekija.setOsasto(osasto);
		
		tyontekijat.getTyontekija().add(tyontekija);
	}

	public int haeTyontekija(int id) {
		List<Tyontekijat.Tyontekija> tekijaLista = tyontekijat.getTyontekija();
		int i = 0;
		int index = -1;

		while (index == -1 && i < tekijaLista.size()) {
			if (tekijaLista.get(i).getId() == id) {
				index = i;
			} else {
				i++;
			}
		}
		return index;
	}

	public static void main(final String[] args) {

		TyontekijaHallinta sovellus = new TyontekijaHallinta();

		try {

			sovellus.toObject();

			Scanner input = new Scanner(System.in);
			int valinta = -1;

			do {
				System.out.println("\n1 = Listaa työntekijät");
				System.out.println("2 = Lisää työntekijä");
				System.out.println("3 = Hae työntekijä");
				System.out.println("0 = Lopeta");
				System.out.print("Anna valintasi: ");
				valinta = input.nextInt();
				input.nextLine();
				switch (valinta) {
				case 1:
					sovellus.listaaTyontekijat();
					break;
				case 2:
					sovellus.lisaaTyontekija();
				case 3:
					
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