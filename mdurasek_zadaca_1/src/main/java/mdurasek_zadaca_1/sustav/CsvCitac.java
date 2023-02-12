package mdurasek_zadaca_1.sustav;

import mdurasek_zadaca_1.singleton.OpcionalniCsv;

public class CsvCitac {
	private static String rasporedCsv = "";
	public static void ucitavanjeCsva(String naredba){
		String [] datotekeZaUcitavanje = naredba.split(" ");
		rasporedNaziva(datotekeZaUcitavanje);
		OpcionalniCsv opcionalniCsv = OpcionalniCsv.getInstanca();
		if(opcionalniCsv.postoji == true) {
			rasporedCsv = "DZ_1_raspored.csv";
			RasporedCsvCitac.rasporedCsvCitac(rasporedCsv);
		}
	}
	private static void rasporedNaziva(String[] datotekeZaUcitavanje) {
		if(datotekeZaUcitavanje[1].equals("DZ_1_luka.csv")) {
			LukaCsvCitac.lukaCsvCitac(datotekeZaUcitavanje[1]);
		}
		if(datotekeZaUcitavanje[1].equals("DZ_1_brod.csv")) {
			BrodCsvCitac.brodCsvCitac(datotekeZaUcitavanje[1]);
		}
		if(datotekeZaUcitavanje[1].equals("DZ_1_vez.csv")) {
			VezCsvCitac.vezCsvCitac(datotekeZaUcitavanje[1]);
		}
		
		if(datotekeZaUcitavanje[3].equals("DZ_1_luka.csv")) {
			LukaCsvCitac.lukaCsvCitac(datotekeZaUcitavanje[3]);
		}
		if(datotekeZaUcitavanje[3].equals("DZ_1_brod.csv")) {
			BrodCsvCitac.brodCsvCitac(datotekeZaUcitavanje[3]);
		}
		if(datotekeZaUcitavanje[3].equals("DZ_1_vez.csv")) {
			VezCsvCitac.vezCsvCitac(datotekeZaUcitavanje[3]);
		}
		if(datotekeZaUcitavanje[5].equals("DZ_1_luka.csv")) {
			LukaCsvCitac.lukaCsvCitac(datotekeZaUcitavanje[5]);
		}
		if(datotekeZaUcitavanje[5].equals("DZ_1_brod.csv")) {
			BrodCsvCitac.brodCsvCitac(datotekeZaUcitavanje[5]);
		}
		if(datotekeZaUcitavanje[5].equals("DZ_1_vez.csv")) {
			VezCsvCitac.vezCsvCitac(datotekeZaUcitavanje[5]);
		}
		
	
	}
}
