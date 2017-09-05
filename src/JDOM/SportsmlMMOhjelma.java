// Muuta alla olevaa riviä tarvittaessa
package JDOM;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.SAXException;

import java.util.Iterator; 

public class SportsmlMMOhjelma {
	private Document doc;
	final private String xmlFile = "sportsmlMM.xml";

	public void toJDOM() throws JDOMException, IOException, SAXException {
		SAXBuilder builder = new SAXBuilder();
		doc = builder.build(xmlFile);
	}

		public static void main(String[] args) {
			try {
				SportsmlMMOhjelma sovellus = new SportsmlMMOhjelma();

				sovellus.toJDOM();
				// TEE METODI haeTulokset
				sovellus.haeTulokset();

			} catch (JDOMException e) {
				e.printStackTrace();
				System.out
						.println("XMl-dokumentti ei ole oikein muodostettu, koska "
								+ e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}

	}
	


	private void haeTulokset(){
		List<Element> urheilut = doc.getRootElement().getChildren("tournament");
        //List<Element> tournamentDivision = doc.getRootElement().getChild("tournament").getChildren("tournament-division");

		for(int i=0; i < urheilut.size(); i++){
			Element tuornament = urheilut.get(i);
			List<Element> tournamentDivision = tuornament.getChildren("tournament-division");

			System.out.print(tuornament.getChild("tournament-metadata").getChild("site").getChild("site-metadata").getChild("home-location").getAttributeValue("city")+" ");
			System.out.print(tuornament.getChild("tournament-metadata").getAttributeValue("tournament-name")+" ");
			System.out.print(tuornament.getChild("tournament-metadata").getAttributeValue("start-date-time")+" - ");
            System.out.println(tuornament.getChild("tournament-metadata").getAttributeValue("end-date-time")+"\n");
            for(int j=0; j < tournamentDivision.size(); j++) {
            	   String codeKey = tournamentDivision.get(j).getChild("tournament-division-metadata").getChild("sports-content-codes").getChild("sports-content-code").getAttributeValue("code-key");
            	   String eventName = tournamentDivision.get(j).getChild("tournament-round").getChild("sports-event").getChild("event-metadata").getAttributeValue("event-name");
            	   List<Element> players = tournamentDivision.get(j).getChild("tournament-round").getChild("sports-event").getChildren("player");
            	   System.out.println(codeKey+" "+ eventName);
            	   for(int k=0; k < players.size(); k++){
            		   String fullName= players.get(k).getChild("player-metadata").getChild("name").getAttributeValue("full");
            		   String score= players.get(k).getChild("player-stats").getAttributeValue("score");
            		   String country = players.get(k).getChild("player-metadata").getChild("home-location").getAttributeValue("country");
            		   String rank = players.get(k).getChild("player-stats").getChild("rank").getAttributeValue("value");
            		   if(players.get(k).getChild("player-stats").getChild("award") != null) {
            			   String awardName = players.get(k).getChild("player-stats").getChild("award").getAttributeValue("name");
            		       System.out.println(rank+". "+fullName+" "+country+" "+score+" "+awardName);
            		   } else
            			   System.out.println(k+1+". "+fullName+" "+country+" "+score);             		   }
               System.out.print("\n");
                 }
                               
         }
	}



}