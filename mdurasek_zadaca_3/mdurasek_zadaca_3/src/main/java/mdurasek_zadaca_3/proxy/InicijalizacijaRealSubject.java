package mdurasek_zadaca_3.proxy;

import mdurasek_zadaca_3.singleton.OpcionalniCsv;
import mdurasek_zadaca_3.sustav.BrodCsvCitac;
import mdurasek_zadaca_3.sustav.KanalCsvCitac;
import mdurasek_zadaca_3.sustav.LukaCsvCitac;
import mdurasek_zadaca_3.sustav.MolCsvCitac;
import mdurasek_zadaca_3.sustav.MolVezCsvCitac;
import mdurasek_zadaca_3.sustav.RasporedCsvCitac;
import mdurasek_zadaca_3.sustav.VezCsvCitac;

public class InicijalizacijaRealSubject implements Inicijalizacija {
	private static String rasporedCsv = "";
	@Override
	public void provjeraUnosa(String naredba){
		String [] datotekeZaUcitavanje = naredba.split(" ");
		rasporedNaziva(datotekeZaUcitavanje);
		OpcionalniCsv opcionalniCsv = OpcionalniCsv.getInstanca();
		if(opcionalniCsv.postoji == true) {
			rasporedCsv = "DZ_3_raspored.csv";
			System.out.println("-----------------Ucitavanje raspored.csv----------------------");
			RasporedCsvCitac.rasporedCsvCitac(rasporedCsv);
		}
	}
	private static void rasporedNaziva(String[] datotekeZaUcitavanje) {
		System.out.println("-----------------Ucitavanje luka.csv----------------------");
		LukaCsvCitac.lukaCsvCitac("DZ_3_luka.csv");
		for(int i = 1; i < datotekeZaUcitavanje.length; i+=2) {
			if(datotekeZaUcitavanje[i].equals("DZ_3_brod.csv")) {
				System.out.println("-----------------Ucitavanje brod.csv----------------------");
				BrodCsvCitac.brodCsvCitac(datotekeZaUcitavanje[i]);
			}
			if(datotekeZaUcitavanje[i].equals("DZ_3_vez.csv")) {
				System.out.println("-----------------Ucitavanje vez.csv----------------------");
				VezCsvCitac.vezCsvCitac(datotekeZaUcitavanje[i]);
			}
			if(datotekeZaUcitavanje[i].equals("DZ_3_mol.csv")) {
				System.out.println("-----------------Ucitavanje mol.csv----------------------");
				MolCsvCitac.molCsvCitac(datotekeZaUcitavanje[i]);
			}
			if(datotekeZaUcitavanje[i].equals("DZ_3_kanal.csv")) {
				System.out.println("-----------------Ucitavanje kanal.csv----------------------");
				KanalCsvCitac.kanalCsvCitac(datotekeZaUcitavanje[i]);
			}
		}
		System.out.println("-----------------Ucitavanje molVez.csv----------------------");
		MolVezCsvCitac.molVezCsvCitac("DZ_3_mol_vez.csv");
	}
}
