package mdurasek_zadaca_1.proxy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mdurasek_zadaca_1.singleton.OpcionalniCsv;
import mdurasek_zadaca_1.sustav.CsvCitac;

public class InicijalizacijaProvjera implements Inicijalizacija {
	
	public void provjeraUnosa(String argumenti) throws Exception {
		String naredba = argumenti;
		naredba = naredba.trim();
		naredba = provjeriDodatanCsv(naredba);
		Boolean validniArgumenti = provjeriValidnostArgumenata(naredba);
		if(validniArgumenti == true) {
			CsvCitac.ucitavanjeCsva(naredba);
		}
		else {
			 throw new Exception("Zabranjen pristup, pogresni argumenti!");
		}
	}
	
	public static String provjeriDodatanCsv(String naredba) {
		if(naredba.contains("-r DZ_1_raspored.csv")){
            naredba = naredba.replace("-r DZ_1_raspored.csv","");
            naredba = naredba.trim().replaceAll("\\s{2,}", " ");
            OpcionalniCsv opcionalniCsv = OpcionalniCsv.getInstanca();
            opcionalniCsv.setPostoji();
        }
		return naredba;
	}
	
	public static Boolean provjeriValidnostArgumenata(String naredba) {
		String regex = "^(?i)(?:-v DZ_1_vez\\.csv -l DZ_1_luka\\.csv -b DZ_1_brod\\.csv"
				+ "|-v DZ_1_vez\\.csv -b DZ_1_brod\\.csv -l DZ_1_luka\\.csv"
				+ "|-l DZ_1_luka\\.csv -v DZ_1_vez\\.csv -b DZ_1_brod\\.csv"
				+ "|-l DZ_1_luka\\.csv -b DZ_1_brod\\.csv -v DZ_1_vez\\.csv"
				+ "|-b DZ_1_brod\\.csv -l DZ_1_luka\\.csv -v DZ_1_vez\\.csv"
				+ "|-b DZ_1_brod\\.csv -v DZ_1_vez\\.csv -l DZ_1_luka\\.csv)$";
		Pattern uzorak = Pattern.compile(regex);
		Matcher matcher = uzorak.matcher(naredba);
		return matcher.find();
	}
}
