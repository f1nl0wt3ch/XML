// MUUTA ALLA OLEVAA RIVIÄ TARVITTAESSA
package XMLSCHEMA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MuunnaViopelle {
	public static void main(String[] args) throws IOException {

		System.out
				.println("Ohjelma muuntaa annetun tiedoston Viope tarkastusta varten olevaan muotoon.");

		System.out.print("\nAnna muunnettavan tiedoston nimi >> ");
		Scanner input = new Scanner(System.in);
		String lukuFile = input.nextLine();
		try {
			BufferedReader luku = new BufferedReader(new FileReader(lukuFile));

			String rivi = "", schemaTeksti = "";

			while ((rivi = luku.readLine()) != null) {
				String riviMuunnettu = rivi.replace("\"", "'");
				schemaTeksti = schemaTeksti + riviMuunnettu + " ";
			}

			String tulosFile = lukuFile  + ".txt";
			FileWriter writer = new FileWriter(tulosFile);

			writer.write(schemaTeksti);
			writer.flush();
			writer.close();
			System.out.println("\nMuunto onnistui. Tulos on tiedostossa " + tulosFile);
			System.out.println("Näet tiedoston Eclipsessä, kun painat projektin päällä F5-painketta.");
			luku.close();
		} catch (Exception ex) {
			System.out.println("Muunto ei onnistunut, koska ");
			System.out.println(ex.getMessage());
		}
	
	}

}
