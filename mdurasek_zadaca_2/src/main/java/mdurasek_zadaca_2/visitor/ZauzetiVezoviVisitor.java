package mdurasek_zadaca_2.visitor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import mdurasek_zadaca_2.builder.Raspored;
import mdurasek_zadaca_2.prototype.OstaliVez;
import mdurasek_zadaca_2.prototype.PoslovniVez;
import mdurasek_zadaca_2.prototype.PutnickiVez;
import mdurasek_zadaca_2.sustav.RasporedCsvCitac;

public class ZauzetiVezoviVisitor implements Visitor {

	public ZauzetiVezoviVisitor() {
		
	}
	@Override
	public String visitPutnickiVez(PutnickiVez pu,String datum, String dan) {
		String rjesenje = "";
		for(Raspored r: RasporedCsvCitac.listaRaspored) {
			if(r.getId_vez() == pu.getId()) {
				String stringPocetno = r.getRasporedTermin().getVrijeme_od();
				String stringZavrsno = r.getRasporedTermin().getVrijeme_do();
				int vrijemePocetno = parsirajString(stringPocetno);
				int vrijemeZavrsno = parsirajString(stringZavrsno);
				int trazenoVrijeme = parsirajString(datum);
				if(trazenoVrijeme >= vrijemePocetno && trazenoVrijeme <= vrijemeZavrsno && r.getRasporedTermin().getDani_u_tjednu().contains(dan)) {
					rjesenje = "PU-Z";
					break;
				}
			}
		}
		return rjesenje;
	}

	@Override
	public String visitPoslovniVez(PoslovniVez po,String datum, String dan) {
		String rjesenje = "";
		for(Raspored r: RasporedCsvCitac.listaRaspored) {
			if(r.getId_vez() == po.getId()) {
				String stringPocetno = r.getRasporedTermin().getVrijeme_od();
				String stringZavrsno = r.getRasporedTermin().getVrijeme_do();
				int vrijemePocetno = parsirajString(stringPocetno);
				int vrijemeZavrsno = parsirajString(stringZavrsno);
				int trazenoVrijeme = parsirajString(datum);
				if(trazenoVrijeme >= vrijemePocetno && trazenoVrijeme <= vrijemeZavrsno && r.getRasporedTermin().getDani_u_tjednu().contains(dan)) {
					rjesenje = "PO-Z";
					break;
				}
			}
		}
		return rjesenje;
		
	}

	@Override
	public String visitOstaliVez(OstaliVez ov,String datum, String dan) {
		String rjesenje = "";
		for(Raspored r: RasporedCsvCitac.listaRaspored) {
			if(r.getId_vez() == ov.getId()) {
				String stringPocetno = r.getRasporedTermin().getVrijeme_od();
				String stringZavrsno = r.getRasporedTermin().getVrijeme_do();
				int vrijemePocetno = parsirajString(stringPocetno);
				int vrijemeZavrsno = parsirajString(stringZavrsno);
				int trazenoVrijeme = parsirajString(datum);
				if(trazenoVrijeme >= vrijemePocetno && trazenoVrijeme <= vrijemeZavrsno && r.getRasporedTermin().getDani_u_tjednu().contains(dan)) {
					rjesenje = "OS-Z";
					break;
				}
			}
		}
		return rjesenje;
	}
	
	public int parsirajString(String datum) {
	    LocalTime localTime = LocalTime.parse(datum, DateTimeFormatter.ofPattern("HH:mm"));
	    int sati = localTime.getHour();
	    int minute = localTime.getMinute();
	    return (sati * 60) + minute;
	}

}
