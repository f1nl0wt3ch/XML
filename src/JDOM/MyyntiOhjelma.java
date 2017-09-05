package JDOM;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.SAXException;

public class MyyntiOhjelma {
	
	private Document doc;
	final private String xmlFile = "myynti4.xml";

	public void toJDOM() throws JDOMException, IOException, SAXException {
		SAXBuilder builder = new SAXBuilder();
		doc = builder.build(xmlFile);
	}

	public void toXML() throws IOException, SAXException {
		XMLOutputter format = new XMLOutputter();
		format.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
		FileWriter writer = new FileWriter(xmlFile);
		format.output(doc, writer);
	}
	
	public void naytaTulokset(Element e){
		List<Element> hinnat = e.getChildren("hinta");
		System.out.println("Kohdenumero: "+ e.getAttributeValue("kohdenumero"));
		System.out.print("Sijainti: "+ e.getChildText("osoite")+"\n");
		System.out.print("Rakennusvuosi: "+ e.getAttributeValue("rakennusvuosi")+"\n");
		System.out.print("Kuvaus: "+ e.getChildText("kuvaus")+"\n");
		System.out.print("Pinta-ala: "+ e.getChildText("pinta-ala")+"\n");
		System.out.print("Hinta: ");
		for(Element hinta : hinnat){
			System.out.print(hinta.getValue()+" ("+hinta.getAttributeValue("tyyppi")+") ");
		}
		System.out.print("\n");
	}

	private void listaMyynit() {
        List<Element> myynnit = doc.getRootElement().getChildren("asunto");
        for(Element e : myynnit){
        	   naytaTulokset(e);
        }
	}

	private void haeMyynti() {
		Scanner input = new Scanner(System.in);
		int lkm = 0;
		List<Element> myynnit = doc.getRootElement().getChildren("asunto");
        System.out.print("Anna haettavan myyntikohteen kohdenumero: ");
        int numero = input.nextInt();
        for(Element e : myynnit){
        	   if(Integer.parseInt(e.getAttributeValue("kohdenumero")) == numero){
        		   naytaTulokset(e);
        	   } else {
        		   lkm++;
        		   if(lkm == myynnit.size()) System.out.println("Kohdetta ei ole numerolla "+numero);
        	   }
        }
	}

	private void poistaMyynti() {
		int lkm = 0;
		Scanner input = new Scanner(System.in);
        System.out.print("Anna poistettavan myyntikohteen kohdenumero: ");
        int numero = input.nextInt();
        List<Element> myynnit = doc.getRootElement().getChildren("asunto");
        for(int i=0; i < myynnit.size(); i++){
        	  if(Integer.parseInt(myynnit.get(i).getAttributeValue("kohdenumero")) == numero){
       		   myynnit.remove(i);
       		   System.out.println("Kohde numerolla "+numero+" poistettiin");
       	   } else {
       		   lkm++;
       		   if(lkm == myynnit.size()) System.out.println("Kohdetta ei ole numerolla "+numero);
       	   }
        }
	}

	private void muutaMyynti() {
		int lkm = 0;
		Scanner input = new Scanner(System.in);
        System.out.print("Anna muutettavan myyntikohteen kohdenumero: ");
        int numero = input.nextInt();
        List<Element> myynnit = doc.getRootElement().getChildren("asunto");
        for(int i=0; i < myynnit.size(); i++){
        	  if(Integer.parseInt(myynnit.get(i).getAttributeValue("kohdenumero")) == numero){
        		   Scanner in = new Scanner(System.in);
       		   System.out.println("Entinen kuvaus: "+ myynnit.get(i).getChildText("kuvaus"));
       		   System.out.println("Anna uusi kuvaus: ");
       		   String uusikuvaus = in.nextLine();
       		   myynnit.get(i).getChild("kuvaus").setText(uusikuvaus);
       	   } else {
       		   lkm++;
       		   if(lkm == myynnit.size()) System.out.println("Kohdetta ei ole numerolla "+numero);
       	   }
        }

	}

	public static void main(String[] args) {
		MyyntiOhjelma ohjelma = new MyyntiOhjelma();
		int valinta = 0;
		Scanner input = new Scanner(System.in);
		try {
			ohjelma.toJDOM();
		do {
			System.out.println("1. Listaa myyntikohteet");
			System.out.println("2. Hae myyntikohde");
			System.out.println("3. Poista myyntikohde");
			System.out.println("4. Muuta myyntikohteen kuvausta");
			System.out.println("0. Lopeta");
			System.out.print("Anna valintasi: ");
			valinta = input.nextInt();
			switch (valinta) {
			case 1:
				ohjelma.listaMyynit();
				break;
			case 2:
				ohjelma.haeMyynti();
				break;
			case 3:
				ohjelma.poistaMyynti();
				break;
			case 4:
				ohjelma.muutaMyynti();
				break;
			}
		} while (valinta != 0);
		ohjelma.toXML();
		input.close();
	   } catch(JDOMException e){
		   e.printStackTrace();
	   } catch(SAXException e){
		   e.printStackTrace();
	   } catch (IOException e) {
		e.printStackTrace();
	}
		
		
	}

}
