package mdurasek_zadaca_1.proxy;

import mdurasek_zadaca_1.singleton.VirtualnoVrijeme;
import mdurasek_zadaca_1.sustav.BrodCsvCitac;
import mdurasek_zadaca_1.sustav.VezCsvCitac;

public class UcitaniPodaciProvjera implements UcitaniPodaci{

	@Override
	public void provjeraPostojanjaLuke() throws Exception {
		VirtualnoVrijeme virtualnoVrijeme = VirtualnoVrijeme.getInstanca();
		if(!virtualnoVrijeme.postoji) {
			throw new Exception("Nije se uspjela učitati luka, aplikacija nema virtualno vrijeme te se gasi!");
		}
	}

	@Override
	public void provjeraPostojanjaVeza() throws Exception {
		if(VezCsvCitac.listaVez.isEmpty()) {
			throw new Exception("Nije se uspio učitati niti jedan vez, aplikacija se gasi!");
		}
	}

	@Override
	public void provjeraPostojanjaBroda() throws Exception {
		if(BrodCsvCitac.listaBrod.isEmpty()) {
			throw new Exception("Nije se uspio učitati niti jedan brod, aplikacija se gasi!");
		}
		
	}
	
	
}
