package mdurasek_zadaca_2.proxy;
import mdurasek_zadaca_2.singleton.OpcionalniCsv;

public class InicijalizacijaProxy implements Inicijalizacija {
	private Inicijalizacija inicijalizacija;
	public void provjeraUnosa(String argumenti) throws Exception {
		String naredba = argumenti;
		naredba = naredba.trim();
		naredba = provjeriDodatanCsv(naredba);
		Boolean validniArgumenti = provjeriValidnostArgumenata(naredba);
		if(validniArgumenti == true) {
			inicijalizacija = new InicijalizacijaRealSubject();
			inicijalizacija.provjeraUnosa(naredba);
		}
		else {
			 throw new Exception("Zabranjen pristup, pogresni argumenti!");
		}
	}
	
	public static String provjeriDodatanCsv(String naredba) {
		if(naredba.contains("-r DZ_2_raspored.csv")){
            naredba = naredba.replace("-r DZ_2_raspored.csv","");
            naredba = naredba.trim().replaceAll("\\s{2,}", " ");
            OpcionalniCsv opcionalniCsv = OpcionalniCsv.getInstanca();
            opcionalniCsv.setPostoji();
        }
		return naredba;
	}
	
	public static Boolean provjeriValidnostArgumenata(String naredba) {
		if(naredba.contains("-l DZ_2_luka.csv")
		  && naredba.contains("-v DZ_2_vez.csv")
		  && naredba.contains("-b DZ_2_brod.csv")
		  && naredba.contains("-m DZ_2_mol.csv")
		  && naredba.contains("-mv DZ_2_mol_vez.csv")
		  && naredba.contains("-k DZ_2_kanal.csv")) {
			return true;
		}
		else {
			return false;
		}
	}
}
