package mdurasek_zadaca_2.provjera;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; 
import mdurasek_zadaca_2.builder.Raspored;
import mdurasek_zadaca_2.prototype.Vez;
import mdurasek_zadaca_2.singleton.OpcionalniCsv;
import mdurasek_zadaca_2.singleton.VirtualnoVrijeme;
import mdurasek_zadaca_2.state.Kontekst;
import mdurasek_zadaca_2.sustav.RasporedCsvCitac;
import mdurasek_zadaca_2.sustav.VezCsvCitac;

public class RasporedCsvPostojanjeProvjera implements RasporedCsvPostojanje{

	@Override
	public void provjeraPostojanja(String naredba,Kontekst kontekst) throws Exception {
		OpcionalniCsv opcionalniCsv = OpcionalniCsv.getInstanca();
		if(opcionalniCsv.postoji) {
			if(naredba.equals("I")) {
				naredbaIIspis(kontekst.getStanje().toString());
			}
			if(naredba.startsWith("V")) {
				naredbaVIspis(kontekst.getStanje().toString(),naredba);
			}
		}
		else {
			throw new Exception("Zabranjen pristup, za ovu naredbu je potreban ucitan raspored.csv");
		}
	}
	
	
	private static int vezUIntervalu(String naredba) {
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
		int i = 0;
		String [] naredbe = naredba.split(" ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
		String zeljeniStatus = naredbe[2];
		LocalDateTime vrijemeOd = LocalDateTime.parse(naredbe[3] + " " + naredbe[4], formatter);
		LocalDateTime vrijemeDo = LocalDateTime.parse(naredbe[5] + " " + naredbe[6], formatter);
		int brojDanaOd = DayOfWeek.valueOf(vrijemeOd.getDayOfWeek().toString()).getValue()-1;
		int brojDanaDo = DayOfWeek.valueOf(vrijemeDo.getDayOfWeek().toString()).getValue()-1;
		int minuteDanaOd = vrijemeOd.getHour() * 60 + vrijemeOd.getMinute();
		int minuteDanaDo = vrijemeDo.getHour() * 60 + vrijemeDo.getMinute();
		for(Vez v : VezCsvCitac.vez) {
			if(v.getVrsta().equals(naredbe[1])) {
				String rjesenje = pregledajRasporedUInteravlu(v,brojDanaOd,brojDanaDo,minuteDanaOd,minuteDanaDo);
				if(rjesenje.equals(zeljeniStatus)) {
					String str = "";
					if(zeljeniStatus.equals("Z")) {
						str = "Zauzet";
					}
					else {
						str = "Slobodan";
					}
					System.out.format("%1s %10s %22d %15d %15d %15d %13s %15d", 
							v.getOznaka_veza() 
							, v.getVrsta() 
							,v.getCijena_veza_po_satu()
							,v.getMaksimalna_duljina()
							,v.getMaksimalna_sirina()
							,v.getMaksimalna_dubina() 
							, str,v.getId());
							System.out.println();
					i++;
				}
			}
		}
		return i;
	}
	
	private static int vezUIntervaluRb(String naredba) {
		int i = 0;
		int rb = 0;
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
		String [] naredbe = naredba.split(" ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
		String zeljeniStatus = naredbe[2];
		LocalDateTime vrijemeOd = LocalDateTime.parse(naredbe[3] + " " + naredbe[4], formatter);
		LocalDateTime vrijemeDo = LocalDateTime.parse(naredbe[5] + " " + naredbe[6], formatter);
		int brojDanaOd = DayOfWeek.valueOf(vrijemeOd.getDayOfWeek().toString()).getValue()-1;
		int brojDanaDo = DayOfWeek.valueOf(vrijemeDo.getDayOfWeek().toString()).getValue()-1;
		int minuteDanaOd = vrijemeOd.getHour() * 60 + vrijemeOd.getMinute();
		int minuteDanaDo = vrijemeDo.getHour() * 60 + vrijemeDo.getMinute();
		for(Vez v : VezCsvCitac.vez) {
			if(v.getVrsta().equals(naredbe[1])) {
				String rjesenje = pregledajRasporedUInteravlu(v,brojDanaOd,brojDanaDo,minuteDanaOd,minuteDanaDo);
				if(rjesenje.equals(zeljeniStatus)) {
					String str = "";
					if(zeljeniStatus.equals("Z")) {
						str = "Zauzet";
						rb++;
						i++;
					}
					else {
						str = "Slobodan";
						rb++;
						i++;
					}
					System.out.format("%1s %10s %22d %15d %15d %15d %13s %18s %15d", 
							v.getOznaka_veza() 
							, v.getVrsta() 
							,v.getCijena_veza_po_satu()
							,v.getMaksimalna_duljina()
							,v.getMaksimalna_sirina()
							,v.getMaksimalna_dubina() 
							, str,rb + ".",v.getId());
							System.out.println();
				}
			}
		}
		return i;
	}
	
	
	
	
	private static void ispisiPodnozjeV(int i) {
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("Broj redaka: " + i);
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
	}


	private static String pregledajRasporedUInteravlu(Vez v, int brojDanaOd, int brojDanaDo,
			int minuteDanaOd, int minuteDanaDo) {
		boolean postojiDanOd = false;
		boolean postojiDanDo = false;
		String status = "";
		for (Raspored r : RasporedCsvCitac.listaRaspored) {
			if (r.getId_vez() == v.getId()) {
				String[] listaDana = r.getRasporedTermin().getDani_u_tjednu().split(",");
				for (int i = 0; i < listaDana.length; i++) {
					if(Integer.parseInt(listaDana[i]) == brojDanaOd){
						postojiDanOd = true;
						break;
					}
				}
				for (int i = 0; i < listaDana.length; i++) {
					if(Integer.parseInt(listaDana[i]) == brojDanaDo){
						postojiDanDo = true;
						break;
					}
				}
				if(postojiDanOd == postojiDanDo) {
					String[] vrijemeOd = r.getRasporedTermin().getVrijeme_od().split(":");
					String[] vrijemeDo = r.getRasporedTermin().getVrijeme_do().split(":");
					int rasporedMinuteOd = Integer.parseInt(vrijemeOd[0]) * 60 + Integer.parseInt(vrijemeOd[1]);
					int rasporedMinuteDo = Integer.parseInt(vrijemeDo[0]) * 60 + Integer.parseInt(vrijemeDo[1]);
					
					if (((minuteDanaOd > rasporedMinuteDo && minuteDanaDo > rasporedMinuteDo )|| (minuteDanaDo < rasporedMinuteOd && minuteDanaOd < rasporedMinuteOd))) {
						return status = "S";
					}
					else {
						return status = "Z";
					}
				}
				else {
					return status = "Z";
				}
			}
		}
		return status;
	}

	
	private static void naredbaIIspis(String odabir) {
		switch(odabir) {
		case "Pocetno": ispisiStatusVezova(); 
						break;
		case "Z": ispisiZaglavlje();
				  ispisiStatusVezova();
				  break;
		case "P": ispisiStatusVezova();
				  ispisiPodnozje();
				  break;
		case "R": ispisiStatusVezovaRedniBroj();
		  		  break;
		case "PR": ispisiStatusVezovaRedniBroj();
				   ispisiPodnozje();
				   break;
		case "ZP": ispisiZaglavlje();
				   ispisiStatusVezova();
				   ispisiPodnozje();
				   break;
		case "ZR": ispisiZaglavljeRedniBroj();
				   ispisiStatusVezovaRedniBroj();
				   break;
		case "ZRP": ispisiZaglavljeRedniBroj();
		   			ispisiStatusVezovaRedniBroj();
		   			ispisiPodnozje();
		   			break;
		}
	}
	
	
	private static void naredbaVIspis(String odabir,String naredba) {
		switch(odabir) {
		case "Pocetno": vezUIntervalu(naredba);
						break;
		case "Z": ispisiZaglavlje();
				  vezUIntervalu(naredba);
				  break;
		case "P": int i = vezUIntervalu(naredba);
				  ispisiPodnozjeV(i);
				  break;
		case "R": vezUIntervaluRb(naredba);
		  		  break;
		case "PR": int j = vezUIntervaluRb(naredba);
				   ispisiPodnozjeV(j);
				   break;
		case "ZP": ispisiZaglavlje();
		  		   int k = vezUIntervalu(naredba);
		  		   ispisiPodnozjeV(k);
				   break;
		case "ZR": ispisiZaglavljeRedniBroj();
				   vezUIntervaluRb(naredba);
				   break;
		case "ZRP": ispisiZaglavljeRedniBroj();
					int l = vezUIntervaluRb(naredba);
					ispisiPodnozjeV(l);
		   			break;
		}
	}
	
	
	public static void naredbaZaIspis(Kontekst kontekst,int putnicki,int poslovni,int ostali) {
		switch(kontekst.getStanje().toString()) {
		case "Pocetno": ispisiNaredbuZa(putnicki,poslovni,ostali);break;
		case "Z": ispisiZaglavljeZa();
				  ispisiNaredbuZa(putnicki,poslovni,ostali);
				  break;
		case "P": ispisiNaredbuZa(putnicki,poslovni,ostali);
				  ispisiPodnozjeZa();
				  break;
		case "R": ispisiNaredbuZaSaRB(putnicki, poslovni, ostali);
				  break;
		case "PR": ispisiNaredbuZaSaRB(putnicki, poslovni, ostali);
				   ispisiPodnozjeZa();
				   break;
		case "ZP": ispisiZaglavljeZa();
				   ispisiNaredbuZa(putnicki,poslovni,ostali);
				   ispisiPodnozjeZa();
				   break;
		case "ZR": ispisiZaglavljeZaSaRB();
				   ispisiNaredbuZaSaRB(putnicki, poslovni, ostali);
				   break;
		case "ZRP": ispisiZaglavljeZaSaRB();
					ispisiNaredbuZaSaRB(putnicki, poslovni, ostali);
					ispisiPodnozjeZa();
					break;
		}
	}
	private static void ispisiNaredbuZa(int putnicki,int poslovni,int ostali) {
		System.out.format("%10s %15d","Putnicki vezovi",putnicki);
		System.out.println(); 
		System.out.format("%10s %15d","Poslovni vezovi",poslovni);
		System.out.println(); 
		System.out.format("%10s %15d","Ostali vezovi  ",ostali);
		System.out.println(); 
	}
	private static void ispisiZaglavljeZa() {
		System.out.println("------------------------------------------------");
		System.out.format("%1s %25s","Vrsta","Zauzeto");
		System.out.println();
		System.out.println("------------------------------------------------");
	}
	private static void ispisiZaglavljeZaSaRB() {
		System.out.println("------------------------------------------------");
		System.out.format("%1s %25s %15s","Vrsta","Zauzeto","Redni broj");
		System.out.println();
		System.out.println("------------------------------------------------");
	}
	private static void ispisiPodnozjeZa() {
		System.out.println("------------------------------------------------");
		System.out.println("Broj redaka: " + 3);
		System.out.println("------------------------------------------------");
	}
	
	private static void ispisiNaredbuZaSaRB(int putnicki,int poslovni,int ostali) {
		System.out.format("%10s %15d %15s","Putnicki vezovi",putnicki,"1.");
		System.out.println(); 
		System.out.format("%10s %15d %15s","Poslovni vezovi",poslovni,"2.");
		System.out.println(); 
		System.out.format("%10s %15d %15s","Ostali vezovi  ",ostali,"3.");
		System.out.println(); 
	}
	
	private static void ispisiStatusVezova() {
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
		for (Vez v: VezCsvCitac.vez) {
			String status = dohvatiStatus(v);
			if(!status.equals("")) {
				System.out.format("%1s %10s %22d %15d %15d %15d %13s %15d", 
				v.getOznaka_veza() 
				, v.getVrsta() 
				,v.getCijena_veza_po_satu()
				,v.getMaksimalna_duljina()
				,v.getMaksimalna_sirina()
				,v.getMaksimalna_dubina() 
				, status,v.getId());
				System.out.println();  
			}
		}
	}
	private static void ispisiStatusVezovaRedniBroj() {
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
		int i = 1;
		for (Vez v: VezCsvCitac.vez) {
			String status = dohvatiStatus(v);
			if(!status.equals("")) {
				String redniBroj = Integer.toString(i);
				System.out.format("%1s %10s %22d %15d %15d %15d %13s %18s %15d", 
				v.getOznaka_veza() 
				, v.getVrsta() 
				,v.getCijena_veza_po_satu()
				,v.getMaksimalna_duljina()
				,v.getMaksimalna_sirina()
				,v.getMaksimalna_dubina() 
				, status,redniBroj + ".",v.getId());
				System.out.println();
				i++;
			}
		}
	}
	private static void ispisiZaglavlje() {
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.format("%10s %10s %15s %16s %15s %15s %15s %11s",
				"Oznaka veza ","Vrsta veza ","Cijena veza ","Duljina veza ","Sirina veza ","Dubina veza ","Status veza ","Id");
		System.out.println();
	}
	private static void ispisiZaglavljeRedniBroj() {
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.format("%10s %10s %15s %16s %15s %15s %15s %15s %14s",
				"Oznaka veza ","Vrsta veza ","Cijena veza ","Duljina veza ","Sirina veza ","Dubina veza ","Status veza ","Redni Broj","Id");
		System.out.println();
	}
	private static void ispisiPodnozje() {
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("Broj redaka: " + VezCsvCitac.vez.size());
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
	}
	private static String dohvatiStatus(Vez vez) {
		String status = "";
		VirtualnoVrijeme vrijeme = VirtualnoVrijeme.getInstanca();
		int minuteDana = vrijeme.dajMinuteTrenutnogDana();
		int dan = vrijeme.danUTjednu();
		Boolean postojiDan = false;
		for (Raspored r : RasporedCsvCitac.listaRaspored) {
			if (r.getId_vez() == vez.getId()) {
				String[] listaDana = r.getRasporedTermin().getDani_u_tjednu().split(",");
				for (int i = 0; i < listaDana.length; i++) {
					if(Integer.parseInt(listaDana[i]) == dan){
						postojiDan = true;
						break;
					}
				}
				if(postojiDan) {
					String[] vrijemeOd = r.getRasporedTermin().getVrijeme_od().split(":");
					String[] vrijemeDo = r.getRasporedTermin().getVrijeme_do().split(":");
					int rasporedMinuteOd = Integer.parseInt(vrijemeOd[0]) * 60 + Integer.parseInt(vrijemeOd[1]);
					int rasporedMinuteDo = Integer.parseInt(vrijemeDo[0]) * 60 + Integer.parseInt(vrijemeDo[1]);
					if (rasporedMinuteOd <= minuteDana && minuteDana <= rasporedMinuteDo) {
						return status = "Zauzet";
					}
					else {
						status = "Slobodan";
					}
				}
			}
			else {
				status = "Slobodan";
			}
		}
		return status;
	}

}
