package mdurasek_zadaca_2.izbornik;
import java.io.Console;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mdurasek_zadaca_2.builder.Brod;
import mdurasek_zadaca_2.chain.Handler;
import mdurasek_zadaca_2.chain.HandlerKorisnickoIme;
import mdurasek_zadaca_2.chain.HandlerLozinka;
import mdurasek_zadaca_2.chain.HandlerUloga;
import mdurasek_zadaca_2.chain.Prijava;
import mdurasek_zadaca_2.podaci.Kanal;
import mdurasek_zadaca_2.prototype.Vez;
import mdurasek_zadaca_2.provjera.RasporedCsvPostojanje;
import mdurasek_zadaca_2.provjera.RasporedCsvPostojanjeProvjera;

import mdurasek_zadaca_2.proxy.Inicijalizacija;
import mdurasek_zadaca_2.proxy.InicijalizacijaProxy;

import mdurasek_zadaca_2.singleton.Greska;
import mdurasek_zadaca_2.singleton.KorisnikUloga;
import mdurasek_zadaca_2.singleton.VirtualnoVrijeme;

import mdurasek_zadaca_2.state.Kontekst;
import mdurasek_zadaca_2.state.PocetnoStanje;
import mdurasek_zadaca_2.state.Podnozje;
import mdurasek_zadaca_2.state.PodnozjeRedni;
import mdurasek_zadaca_2.state.RedniBroj;
import mdurasek_zadaca_2.state.Zaglavlje;
import mdurasek_zadaca_2.state.ZaglavljePodnozje;
import mdurasek_zadaca_2.state.ZaglavljeRedni;
import mdurasek_zadaca_2.state.ZaglavljeRedniPodnozje;
import mdurasek_zadaca_2.sustav.BrodCsvCitac;
import mdurasek_zadaca_2.sustav.KanalCsvCitac;
import mdurasek_zadaca_2.sustav.KorisnikPodaci;
import mdurasek_zadaca_2.sustav.RezervacijaCsvCitac;
import mdurasek_zadaca_2.sustav.VezCsvCitac;
import mdurasek_zadaca_2.visitor.Visitor;
import mdurasek_zadaca_2.visitor.ZauzetiVezoviVisitor;



public class Izbornik {
	public static void main(String args[]) {
		String naredba = "";
		for(int i = 0; i < args.length;i++) {
			naredba += args[i] + " ";
		}
		Inicijalizacija inicijalizacija = new InicijalizacijaProxy();
		try
        {
			if(prijavaUSustav() == false) {
				return;
			}
            inicijalizacija.provjeraUnosa(naredba);
            Scanner unos = new Scanner(System.in);
    		Kontekst kontekst = new Kontekst();
    	    PocetnoStanje pocetnoStanje = new PocetnoStanje ();
    	    pocetnoStanje.postaviStatus(kontekst);
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
    					raspored.provjeraPostojanja(line,kontekst);
    				}
    				if(line.startsWith("T")) {
    					prepoznataNaredba = postaviPostavkeTablice(line,kontekst,prepoznataNaredba);
    				}
    				if(line.startsWith("VR")){
    					prepoznataNaredba = true;
    					postaviVirtualnoVrijeme(line);
    				}
    				if(line.equals("UR DZ_3_zahtjev_rezervacije.csv")) {
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
    					raspored.provjeraPostojanja(line,kontekst);
    				}
    				String regexZa = "^ZA (0[1-9]|[12][0-9]|3[01])\\."
    						+ "+(0[1-9]|1[012])\\.\\d{4}\\.+ ([01]\\d|2[0-3])"
    						+ ":([0-5]\\d)$";
    				Pattern uzorakZa = Pattern.compile(regexZa);
    				Matcher matcherZa = uzorakZa.matcher(line);
    				if(matcherZa.find()) {
    					prepoznataNaredba = true;
    					naredbaZa(line,kontekst);
    					
    				}
    				String regexF = "^(F \\d+ \\d+)$|(F \\d+ \\d+ Q)$";
    				Pattern uzorakF = Pattern.compile(regexF);
    				Matcher matcherF = uzorakF.matcher(line);
    				if(matcherF.find()) {
    					prepoznataNaredba = true;
    					naredbaF(line);
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
	
	public static Brod vratiBrod(int id) {
		for(Brod b : BrodCsvCitac.listaBrod) {
			if(id == b.getId()) {
				return b;
			}
		}
		return null;
	}
	public static Kanal vratiKanal(int id) {
		for(Kanal k : KanalCsvCitac.listaKanali) {
			if(k.getFrekvencija() == id) {
				return k;
			}
		}
		return null;
	}
	
	private static void naredbaF(String naredba) {
		String[] polje = naredba.split(" ");
		int idBrod = Integer.parseInt(polje[1]);
		int frekvencija = Integer.parseInt(polje[2]);
		Brod noviBrod = vratiBrod(idBrod);
		Kanal noviKanal = vratiKanal(frekvencija);
		if(noviBrod == null) {
			Greska greska =  Greska.getInstanca();
			greska.setGreska();
			System.out.println("Greska, brod ne postoji! " + "Redni broj greske: " + greska.brojac);
		}
		if(noviKanal == null) {
			Greska greska =  Greska.getInstanca();
			greska.setGreska();
			System.out.println("Greska, kanal ne postoji! " + "Redni broj greske: " + greska.brojac);
		}
		if(noviBrod != null && noviKanal != null) {
			if(!naredba.contains("Q")) {
				noviKanal.spoji(noviBrod);
			}
			else {
				noviKanal.odspoji(noviBrod);
			}
		}
	}
	
	private static void naredbaZa(String naredba,Kontekst kontekst) {
		int rjesenjePu = 0;
		int rjesenjePo = 0;
		int rjesenjeOs = 0;
		String naredbaSplit[] = naredba.split(" ");
		String datumSplit[] = naredbaSplit[1].split("\\.");
		LocalDate ld = LocalDate.of(Integer.parseInt(datumSplit[2]), Integer.parseInt(datumSplit[1]), Integer.parseInt(datumSplit[0]));
		int danUTjednuInt = ld.getDayOfWeek().getValue() - 1;
		String dan = Integer.toString(danUTjednuInt);
		Visitor visitor = new ZauzetiVezoviVisitor();
		for(Vez v: VezCsvCitac.vez) {
			String rjesenje = v.prihvati(visitor, naredbaSplit[2],dan);
			if(rjesenje.equals("PU-Z")) {
				rjesenjePu++;
			}
			if(rjesenje.equals("PO-Z")) {
				rjesenjePo++;
			}
			if(rjesenje.equals("OS-Z")) {
				rjesenjeOs++;
			}
		}
		RasporedCsvPostojanjeProvjera.naredbaZaIspis(kontekst, rjesenjePu, rjesenjePo, rjesenjeOs);
		
	}
	
	
	
	
	
	private static boolean prijavaUSustav() 
	{
		Console console = System.console();
		String korisnik = console.readLine("Korisnicko ime: ");
		char[] lozinkaC = console.readPassword("Lozinka: ");
		String lozinka = new String(lozinkaC);
		KorisnikPodaci korisnikPodaci = new KorisnikPodaci();
		Handler handler = new HandlerKorisnickoIme(korisnikPodaci);
		handler.postaviSljedeciHandler(new HandlerLozinka(korisnikPodaci))
		.postaviSljedeciHandler(new HandlerUloga(korisnikPodaci));
		Prijava login = new Prijava(handler);
		return login.prijava(korisnik, lozinka);
	}
	
	private static boolean postaviPostavkeTablice(String naredba,Kontekst kontekst,Boolean prepoznataNaredba) {
		prepoznataNaredba = osnovnePostavkeTablice(naredba,kontekst,prepoznataNaredba);
		prepoznataNaredba = postavkaZaglavlje(naredba,kontekst,prepoznataNaredba);
		prepoznataNaredba = postavkaRedniBroj(naredba,kontekst,prepoznataNaredba);
		prepoznataNaredba = postavkaPodnozje(naredba,kontekst,prepoznataNaredba);
		prepoznataNaredba = postavkaPodnozjeRedni(naredba,kontekst,prepoznataNaredba);
		prepoznataNaredba = postavkaZaglavljeRedni(naredba,kontekst,prepoznataNaredba);
		prepoznataNaredba = postavkaZaglavljePodnozje(naredba,kontekst,prepoznataNaredba);
		prepoznataNaredba = postavkaSve(naredba,kontekst,prepoznataNaredba);
		return prepoznataNaredba;
	}
	
	private static boolean osnovnePostavkeTablice(String naredba,Kontekst kontekst,Boolean prepoznataNaredba) {
		if(naredba.equals("T") && kontekst.getStanje().toString().equals("Pocetno")) {
			Greska greska =  Greska.getInstanca();
			greska.setGreska();
			System.out.println("Greska, vec je tablica u pocetnom stanju! " + "Redni broj greske: " + greska.brojac);
			prepoznataNaredba = true;
		}
		if(naredba.equals("T") && !kontekst.getStanje().toString().equals("Pocetno")) {
			PocetnoStanje pocetnoStanje = new PocetnoStanje ();
    	    pocetnoStanje.postaviStatus(kontekst);
    	    prepoznataNaredba = true;
		}
		return prepoznataNaredba;
	}
	private static boolean postavkaZaglavlje(String naredba,Kontekst kontekst,Boolean prepoznataNaredba) {
		if(naredba.equals("T Z") && kontekst.getStanje().toString().equals("Z")) {
			Greska greska =  Greska.getInstanca();
			greska.setGreska();
			System.out.println("Greska, tablica vec ima zaglavlje! " + "Redni broj greske: " + greska.brojac);
			prepoznataNaredba = true;
		}
		if(naredba.equals("T Z") && !kontekst.getStanje().toString().equals("Z")) {
			Zaglavlje zaglavljeStanje = new Zaglavlje();
			zaglavljeStanje.postaviStatus(kontekst);
			prepoznataNaredba = true;
		}
		return prepoznataNaredba;
	}
	
	private static boolean postavkaRedniBroj(String naredba,Kontekst kontekst,Boolean prepoznataNaredba) {
		if(naredba.equals("T RB") && kontekst.getStanje().toString().equals("R")) {
			Greska greska =  Greska.getInstanca();
			greska.setGreska();
			System.out.println("Greska, tablica vec ima redni broj! " + "Redni broj greske: " + greska.brojac);
			prepoznataNaredba = true;
		}
		if(naredba.equals("T RB") && !kontekst.getStanje().toString().equals("R")) {
			RedniBroj redniBrojStanje = new RedniBroj();
			redniBrojStanje.postaviStatus(kontekst);
			prepoznataNaredba = true;
		}
		return prepoznataNaredba;
	}
	
	private static boolean postavkaPodnozje(String naredba,Kontekst kontekst,Boolean prepoznataNaredba) {
		if(naredba.equals("T P") && kontekst.getStanje().toString().equals("P")) {
			Greska greska =  Greska.getInstanca();
			greska.setGreska();
			System.out.println("Greska, tablica vec ima podnozje! " + "Redni broj greske: " + greska.brojac);
			prepoznataNaredba = true;
		}
		if(naredba.equals("T P") && !kontekst.getStanje().toString().equals("P")) {
			Podnozje podnozjeStanje = new Podnozje();
			podnozjeStanje.postaviStatus(kontekst);
			prepoznataNaredba = true;
		}
		return prepoznataNaredba;
	}
	
	private static boolean postavkaPodnozjeRedni(String naredba, Kontekst kontekst,Boolean prepoznataNaredba) {
		if((naredba.equals("T RB P") || naredba.equals("T P RB"))&& kontekst.getStanje().toString().equals("PR")) {
			Greska greska =  Greska.getInstanca();
			greska.setGreska();
			System.out.println("Greska, tablica vec ima redni broj i podnozje! " + "Redni broj greske: " + greska.brojac);
			prepoznataNaredba = true;
		}
		if((naredba.equals("T RB P") || naredba.equals("T P RB"))&& !kontekst.getStanje().toString().equals("PR")) {
			PodnozjeRedni podnozjeRedniStanje = new PodnozjeRedni();
			podnozjeRedniStanje.postaviStatus(kontekst);
			prepoznataNaredba = true;
		}
		return prepoznataNaredba;
	}
	private static boolean postavkaZaglavljeRedni(String naredba, Kontekst kontekst,Boolean prepoznataNaredba) {
		if((naredba.equals("T RB Z") || naredba.equals("T Z RB"))&& kontekst.getStanje().toString().equals("ZR")) {
			Greska greska =  Greska.getInstanca();
			greska.setGreska();
			System.out.println("Greska, tablica vec ima redni broj i zaglavlje! " + "Redni broj greske: " + greska.brojac);
			prepoznataNaredba = true;
		}
		if((naredba.equals("T RB Z") || naredba.equals("T Z RB"))&& !kontekst.getStanje().toString().equals("ZR")) {
			ZaglavljeRedni zaglavljeRedniStanje = new ZaglavljeRedni();
			zaglavljeRedniStanje.postaviStatus(kontekst);
			prepoznataNaredba = true;
		}
		return prepoznataNaredba;
	}
	private static boolean postavkaZaglavljePodnozje(String naredba, Kontekst kontekst,Boolean prepoznataNaredba) {
		if((naredba.equals("T P Z") || naredba.equals("T Z P"))&& kontekst.getStanje().toString().equals("ZP")) {
			Greska greska =  Greska.getInstanca();
			greska.setGreska();
			System.out.println("Greska, tablica vec ima zaglavlje i podnozje! " + "Redni broj greske: " + greska.brojac);
			prepoznataNaredba = true;
		}
		if((naredba.equals("T P Z") || naredba.equals("T Z P"))&& !kontekst.getStanje().toString().equals("ZP")) {
			ZaglavljePodnozje zaglavljePodnozjeStanje = new ZaglavljePodnozje();
			zaglavljePodnozjeStanje.postaviStatus(kontekst);
			prepoznataNaredba = true;
		}
		return prepoznataNaredba;
	}
	private static boolean postavkaSve(String naredba, Kontekst kontekst,Boolean prepoznataNaredba) {
		if((naredba.equals("T Z P RB") || naredba.equals("T Z RB P")|| naredba.equals("T RB P Z")|| naredba.equals("T RB Z P")|| naredba.equals("T P Z RB")|| naredba.equals("T P RB Z"))&& kontekst.getStanje().toString().equals("ZRP")) {
			Greska greska =  Greska.getInstanca();
			greska.setGreska();
			System.out.println("Greska, tablica vec ima sve postavke! " + "Redni broj greske: " + greska.brojac);
			prepoznataNaredba = true;
		}
		if((naredba.equals("T Z P RB") || naredba.equals("T Z RB P")|| naredba.equals("T RB P Z")|| naredba.equals("T RB Z P")|| naredba.equals("T P Z RB")|| naredba.equals("T P RB Z"))&& !kontekst.getStanje().toString().equals("ZRP")) {
			ZaglavljeRedniPodnozje zaglavljeRedniPodnozjeStanje = new ZaglavljeRedniPodnozje();
			zaglavljeRedniPodnozjeStanje.postaviStatus(kontekst);
			prepoznataNaredba = true;
		}
		return prepoznataNaredba;
	}
	private static void postaviVirtualnoVrijeme(String naredba) {
		Greska greska = Greska.getInstanca();
		KorisnikUloga korisnikUloga = KorisnikUloga.getInstanca();
		if(korisnikUloga.admin == true) {
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
		else {
			greska.setGreska();
			System.out.println("Nemate ovlasti za promjenu virtualnog vremena! " + "Redni broj greske: " + greska.brojac);
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
