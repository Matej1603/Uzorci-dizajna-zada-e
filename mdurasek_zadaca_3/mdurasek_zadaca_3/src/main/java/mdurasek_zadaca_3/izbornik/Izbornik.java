package mdurasek_zadaca_3.izbornik;
import java.io.Console;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mdurasek_zadaca_3.builder.Brod;
import mdurasek_zadaca_3.builder.Luka;
import mdurasek_zadaca_3.chain.Handler;
import mdurasek_zadaca_3.chain.HandlerKorisnickoIme;
import mdurasek_zadaca_3.chain.HandlerLozinka;
import mdurasek_zadaca_3.chain.HandlerUloga;
import mdurasek_zadaca_3.chain.Prijava;
import mdurasek_zadaca_3.podaci.Kanal;
import mdurasek_zadaca_3.prototype.Vez;
import mdurasek_zadaca_3.provjera.RasporedCsvPostojanje;
import mdurasek_zadaca_3.provjera.RasporedCsvPostojanjeProvjera;

import mdurasek_zadaca_3.proxy.Inicijalizacija;
import mdurasek_zadaca_3.proxy.InicijalizacijaProxy;

import mdurasek_zadaca_3.singleton.Greska;
import mdurasek_zadaca_3.singleton.KorisnikUloga;
import mdurasek_zadaca_3.singleton.VirtualnoVrijeme;

import mdurasek_zadaca_3.state.Kontekst;
import mdurasek_zadaca_3.state.PocetnoStanje;
import mdurasek_zadaca_3.state.Podnozje;
import mdurasek_zadaca_3.state.PodnozjeRedni;
import mdurasek_zadaca_3.state.RedniBroj;
import mdurasek_zadaca_3.state.Zaglavlje;
import mdurasek_zadaca_3.state.ZaglavljePodnozje;
import mdurasek_zadaca_3.state.ZaglavljeRedni;
import mdurasek_zadaca_3.state.ZaglavljeRedniPodnozje;
import mdurasek_zadaca_3.sustav.BrodCsvCitac;
import mdurasek_zadaca_3.sustav.KanalCsvCitac;
import mdurasek_zadaca_3.sustav.KorisnikPodaci;
import mdurasek_zadaca_3.sustav.LukaCsvCitac;
import mdurasek_zadaca_3.sustav.RezervacijaCsvCitac;
import mdurasek_zadaca_3.sustav.VezCsvCitac;
import mdurasek_zadaca_3.visitor.Visitor;
import mdurasek_zadaca_3.visitor.ZauzetiVezoviVisitor;



public class Izbornik {
	public static List<Luka> listaLuka = new ArrayList<Luka>();
	public static List<String>listaNaredbi = new ArrayList<String>();
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
    				
    				if(line.equals("COMPOSITE")) {
    					prepoznataNaredba = true;
    					for(Luka l: LukaCsvCitac.listaLuka) {
    						l.ispis();
    					}
    				}
    				
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
    				
    				String regexSPS = "^SPS( +\\w+)* (([0-9]|1[0-9]|2[0-3]):[0-5][0-9])$";
    				Pattern uzorakSPS = Pattern.compile(regexSPS);
    				Matcher matcherSPS = uzorakSPS.matcher(line);
    				if(matcherSPS.find()) {
    					prepoznataNaredba = true;
    					line = line.substring(3);
    					line = line.trim();
    					naredbaSps(line);
    				}
    				
    				String regexVPS = "^VPS( +\\w+)* (([0-9]|1[0-9]|2[0-3]):[0-5][0-9])$";
    				Pattern uzorakVPS = Pattern.compile(regexVPS);
    				Matcher matcherVPS = uzorakVPS.matcher(line);
    				if(matcherVPS.find()) {
    					prepoznataNaredba = true;
    					line = line.substring(3);
    					line = line.trim();
    					naredbaVps(line);
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
	
	public static void naredbaSps(String naredba) {
		boolean postoji = false;
		for(String nar : listaNaredbi) {
			if(nar.equals(naredba)) {
				postoji = true;
				break;
			}
		}
		if(postoji == false) {
			listaNaredbi.add(naredba);
		}
	}
	
	public static void naredbaVps(String naredba) {
		Greska greska = Greska.getInstanca();
		boolean postoji = false;
		for(String nar : listaNaredbi) {
			if(nar.equals(naredba)){
				String vrijeme = "";
				String polje[] = naredba.split(" ");
				for(int i = 0;i<polje.length;i++) {
					vrijeme = polje[i];
				}
				mjenjanjeVirtualnogVps(vrijeme);
				postoji = true;
				break;
			}
		}
		if(postoji == false) {
			greska.setGreska();
			System.out.println("Ne postoji takvo spremljeno stanje! " + "Redni broj greske: " + greska.brojac);
		}
	}
	
	public static void mjenjanjeVirtualnogVps(String vrijeme) {
		Greska greska = Greska.getInstanca();
		String [] satMinute = vrijeme.split(":");
		String sat = satMinute[0];
		String minute = satMinute[1];
		String sekunde = ":00";
		int satInt = Integer.parseInt(sat);
		if(satInt < 10) {
			sat = "0"+sat;
		}
		String vrijemeZaObradu = sat+":"+minute+sekunde;
		System.out.println(vrijemeZaObradu);
		Date date = new Date();
		SimpleDateFormat form = new SimpleDateFormat("dd.MM.yyyy.");
		String format = form.format(date);
		System.out.println(format);
		String obradenaNaredba = format + " " + vrijemeZaObradu;
		LocalDateTime datum = probajParsiratiDatum(obradenaNaredba);
		if(datum == null) {
			greska.setGreska();
			System.out.println("Pogreska prilikom parsiranja datuma! " + "Redni broj greske: " + greska.brojac);
		}
		else {
			VirtualnoVrijeme v = VirtualnoVrijeme.getInstanca();
			v.setVrijeme(datum);
			v.setPostoji(true);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
			String datumString = formatter.format(v.datum);
			System.out.println( "Novo virtualno vrijeme jest: " + datumString);
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
