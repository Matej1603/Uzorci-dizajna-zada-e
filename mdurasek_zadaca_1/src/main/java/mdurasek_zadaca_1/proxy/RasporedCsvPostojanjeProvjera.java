package mdurasek_zadaca_1.proxy;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import mdurasek_zadaca_1.builder.Raspored;
import mdurasek_zadaca_1.builder.Vez;
import mdurasek_zadaca_1.singleton.OpcionalniCsv;
import mdurasek_zadaca_1.singleton.VirtualnoVrijeme;
import mdurasek_zadaca_1.sustav.RasporedCsvCitac;
import mdurasek_zadaca_1.sustav.VezCsvCitac;

public class RasporedCsvPostojanjeProvjera implements RasporedCsvPostojanje{

	@Override
	public void provjeraPostojanja(String naredba) throws Exception {
		OpcionalniCsv opcionalniCsv = OpcionalniCsv.getInstanca();
		if(opcionalniCsv.postoji) {
			if(naredba.equals("I")) {
				ispisiStatusVezova();
			}
			if(naredba.startsWith("V")) {
				vezUIntervalu(naredba);
			}
		}
		else {
			throw new Exception("Zabranjen pristup, za ovu naredbu je potreban ucitan raspored.csv");
		}
	}
	
	
	private static void vezUIntervalu(String naredba) {
		String [] naredbe = naredba.split(" ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
		String zeljeniStatus = naredbe[2];
		LocalDateTime vrijemeOd = LocalDateTime.parse(naredbe[3] + " " + naredbe[4], formatter);
		LocalDateTime vrijemeDo = LocalDateTime.parse(naredbe[5] + " " + naredbe[6], formatter);
		int brojDanaOd = DayOfWeek.valueOf(vrijemeOd.getDayOfWeek().toString()).getValue();
		int brojDanaDo = DayOfWeek.valueOf(vrijemeDo.getDayOfWeek().toString()).getValue();
		int minuteDanaOd = vrijemeOd.getHour() * 60 + vrijemeOd.getMinute();
		int minuteDanaDo = vrijemeDo.getHour() * 60 + vrijemeDo.getMinute();
		for(Vez v : VezCsvCitac.listaVez) {
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
					System.out.println("Id: " + v.getId() + " Oznaka veza: " + v.getOznaka_veza()
					+ " Vrsta veza: " + v.getVrsta() + " Cijena veza po satu: "+v.getOsobineVeza().getCijena_veza_po_satu()
					+ " Maksimalna duljina: "+v.getOsobineVeza().getMaksimalna_duljina()
					+" Maksimalna sirina: "+v.getOsobineVeza().getMaksimalna_sirina()
					+" Maksimalna dubina: "+v.getOsobineVeza().getMaksimalna_dubina()+" Status: " + str);
				}
			}
		}
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


	private static void ispisiStatusVezova() {
		for (Vez v: VezCsvCitac.listaVez) {
			String status = dohvatiStatus(v);
			if(!status.equals("")) {
				System.out.println("Id: " + v.getId() + " Oznaka veza: " + v.getOznaka_veza()
				+ " Vrsta veza: " + v.getVrsta() + " Cijena veza po satu: "+v.getOsobineVeza().getCijena_veza_po_satu()
				+ " Maksimalna duljina: "+v.getOsobineVeza().getMaksimalna_duljina()
				+" Maksimalna sirina: "+v.getOsobineVeza().getMaksimalna_sirina()
				+" Maksimalna dubina: "+v.getOsobineVeza().getMaksimalna_dubina()+" Status: " + status);
			}
		}
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
		}
		return status;
	}

}
