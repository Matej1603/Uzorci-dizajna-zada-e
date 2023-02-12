package mdurasek_zadaca_1.korisnik;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mdurasek_zadaca_1.proxy.Inicijalizacija;
import mdurasek_zadaca_1.proxy.InicijalizacijaProvjera;
import mdurasek_zadaca_1.proxy.RasporedCsvPostojanje;
import mdurasek_zadaca_1.proxy.RasporedCsvPostojanjeProvjera;
import mdurasek_zadaca_1.proxy.UcitaniPodaci;
import mdurasek_zadaca_1.proxy.UcitaniPodaciProvjera;
import mdurasek_zadaca_1.singleton.Greska;
import mdurasek_zadaca_1.singleton.VirtualnoVrijeme;
import mdurasek_zadaca_1.sustav.RezervacijaCsvCitac;


public class Izbornik {
	public static void main(String args[]) {
		String naredba = "";
		for(int i = 0; i < args.length;i++) {
			naredba += args[i] + " ";
		}
		Inicijalizacija inicijalizacija = new InicijalizacijaProvjera();
		try
        {
            inicijalizacija.provjeraUnosa(naredba);
            Scanner unos = new Scanner(System.in);
            UcitaniPodaci ucitaniPodaci = new UcitaniPodaciProvjera();
    		ucitaniPodaci.provjeraPostojanjaLuke();
    		ucitaniPodaci.provjeraPostojanjaVeza();
    		ucitaniPodaci.provjeraPostojanjaBroda();
    		try {
    			while (true) {
    				Boolean prepoznataNaredba = false;
    				System.out.println("Naredba:");
    				String line = unos.nextLine();
    				if (line.equals("Q"))
    					break;
    				
    				if (line.equals("I")) {
    					prepoznataNaredba = true;
    					RasporedCsvPostojanje raspored = new RasporedCsvPostojanjeProvjera();
    					raspored.provjeraPostojanja(line);
    				}
    				
    				if(line.startsWith("VR")){
    					prepoznataNaredba = true;
    					postaviVirtualnoVrijeme(line);
    				}
    				if(line.equals("UR DZ_1_zahtjev_rezervacije.csv")) {
    					prepoznataNaredba = true;
    					String [] naredbaZaRezervaciju = line.split(" ");
    					RezervacijaCsvCitac.rezervacijaCsvCitac(naredbaZaRezervaciju[1]);
    				}
    				String regex = "^V (PU|PO|OS) (S|Z) (0[1-9]|[12][0-9]|3[01])\\."
    						+ "(0[1-9]|1[012])\\.\\d{4}\\."
    						+ " ([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d) (0[1-9]|[12][0-9]|3[01])\\."
    						+ "(0[1-9]|1[012])\\.\\d{4}\\."
    						+ " ([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$";
    				Pattern uzorak = Pattern.compile(regex);
    				Matcher matcher = uzorak.matcher(line);
    				if(matcher.find()) {
    					prepoznataNaredba = true;
    					RasporedCsvPostojanje raspored = new RasporedCsvPostojanjeProvjera();
    					raspored.provjeraPostojanja(line);
    				}
    				
    				if(prepoznataNaredba == false) {
    					Greska greska =  Greska.getInstanca();
    					greska.setGreska();
    					System.out.println("Nepoznata naredba! " + "Redni broj greske: " + greska.brojac);
    				}
    				
    			}
    		} 
    		catch (IllegalStateException | NoSuchElementException e) {
    			System.out.println("Aplikacija je prisilno ugasena!");
    		}
    		unos.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
	}	
	private static void postaviVirtualnoVrijeme(String naredba) {
		Greska greska = Greska.getInstanca();
		String regex = "^VR (0[1-9]|[12][0-9]|3[01])\\."
				+ "(0[1-9]|1[012])\\.\\d{4}\\. "
				+ "([01]\\d|2[0-3]):([0-5]\\d):"
				+ "([0-5]\\d)$";
		Pattern uzorak = Pattern.compile(regex);
		Matcher matcher = uzorak.matcher(naredba);
		if(matcher.find()) {
			naredba = naredba.replace("VR", "");
			naredba = naredba.trim();
			LocalDateTime datum = probajParsiratiDatum(naredba);
			if(datum == null) {
				greska.setGreska();
				System.out.println("Pogreska prilikom parsiranja datuma! " + "Redni broj greske: " + greska.brojac);
			}
			else {
				VirtualnoVrijeme vrijeme = VirtualnoVrijeme.getInstanca();
				vrijeme.setVrijeme(datum);
				vrijeme.setPostoji(true);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
				String datumString = formatter.format(vrijeme.datum);
				System.out.println("Uspjesno je promjenjeno virtualno vrijeme! "  + "Novo virtualno vrijeme jest: " + datumString);
			}
		}
		else {
			greska.setGreska();
			System.out.println("Pogresna naredba za unos virtualnog vremena! " + "Redni broj greske: " + greska.brojac);
		}
	}
	
	private static LocalDateTime probajParsiratiDatum(String ulaz) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
		try {
			return LocalDateTime.parse(ulaz,formatter);
		} catch (DateTimeParseException e) {
			return null;
		}
	}
}
