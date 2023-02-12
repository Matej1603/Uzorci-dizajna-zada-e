package mdurasek_zadaca_3.sustav;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import mdurasek_zadaca_3.singleton.Greska;
import mdurasek_zadaca_3.prototype.*;

public class VezCsvCitac {

public static List<Vez> vez = new ArrayList<Vez>();
public static List<PutnickiVez> putnickiVez = new ArrayList<PutnickiVez>();
public static List<PoslovniVez> poslovniVez = new ArrayList<PoslovniVez>();
public static List<OstaliVez> ostaliVez = new ArrayList<OstaliVez>();
	
	public static void vezCsvCitac(String naziv) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(naziv));
			try {
				String line = br.readLine();
				while ((line = br.readLine()) != null) {
					if (!line.isEmpty()) {
						String[] data = line.split(";");
						if(data.length != 7) {
							Greska greska = Greska.getInstanca();
							greska.setGreska();
							System.out.println("Broj kolona se ne poklapa sa modelom, u potpunosti se preskace red! " + "Redni broj greske: " + greska.brojac);
							continue;
						}
						Boolean ispravnost = validirajCsvVez(data);
						
						if(ispravnost == false) {
							continue;
						}
						switch(data[2]) {
						case "PU":  kreirajPutnickiVez(data);
									break;
						case "PO": kreirajPoslovniVez(data);
									break;
						case "OS": kreirajOstaliVez(data);
									break;
						}
					}
				}
					
			} catch (IOException e) {
				
			}
		} catch (FileNotFoundException e) {
		}
	}
	
	public static void kreirajPutnickiVez(String [] data) {
		PutnickiVez pu = new PutnickiVez();
		pu.setId(Integer.parseInt(data[0]));
		pu.setOznaka_veza(data[1]);
		pu.setVrsta(data[2]);
		pu.setCijena_veza_po_satu(Integer.parseInt(data[3]));
		pu.setMaksimalna_duljina(Integer.parseInt(data[4]));
		pu.setMaksimalna_sirina(Integer.parseInt(data[5]));
		pu.setMaksimalna_dubina(Integer.parseInt(data[6]));
		putnickiVez.add(pu);
		vez.add(pu.clone());
		
	}
	public static void kreirajPoslovniVez(String [] data) {
		PoslovniVez po = new PoslovniVez();
		po.setId(Integer.parseInt(data[0]));
		po.setOznaka_veza(data[1]);
		po.setVrsta(data[2]);
		po.setCijena_veza_po_satu(Integer.parseInt(data[3]));
		po.setMaksimalna_duljina(Integer.parseInt(data[4]));
		po.setMaksimalna_sirina(Integer.parseInt(data[5]));
		po.setMaksimalna_dubina(Integer.parseInt(data[6]));
		poslovniVez.add(po);
		vez.add(po.clone());
	}
	public static void kreirajOstaliVez(String [] data) {
		OstaliVez os = new OstaliVez();
		os.setId(Integer.parseInt(data[0]));
		os.setOznaka_veza(data[1]);
		os.setVrsta(data[2]);
		os.setCijena_veza_po_satu(Integer.parseInt(data[3]));
		os.setMaksimalna_duljina(Integer.parseInt(data[4]));
		os.setMaksimalna_sirina(Integer.parseInt(data[5]));
		os.setMaksimalna_dubina(Integer.parseInt(data[6]));
		ostaliVez.add(os);
		vez.add(os.clone());
	}
	public static Boolean validirajCsvVez(String[]data) {
		Boolean postojiId = false;
		Greska greska = Greska.getInstanca();
		Integer id = probajParsiratBroj(data[0]);
		if(id == null) {
			greska.setGreska();
			System.out.println("Greska id se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		if(id != null) {
			for(Vez v: vez) {
				if(v.getId() == id) {
					postojiId = true;
					break;
				}
			}
			if(postojiId == true) {
				greska.setGreska();
				System.out.println("Greska postoji vec identicna vrijednost id-a! " + "Redni broj greske: " + greska.brojac);
			}
			
		}
		
		data[1] = data[1].trim();
		if(data[1].isEmpty()) {
			greska.setGreska();
			System.out.println("Oznaka veza ne postoji! " + "Redni broj greske: " + greska.brojac);
		}
		data[2] = data[2].trim();
		if(data[2].isEmpty()) {
			greska.setGreska();
			System.out.println("Vrsta veza ne postoji! " + "Redni broj greske: " + greska.brojac);
		}
		Integer cijenaVeza = probajParsiratBroj(data[3]);
		if(cijenaVeza == null) {
			greska.setGreska();
			System.out.println("Greska cijena veza po satu se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		Integer maksimalnaDuljina = probajParsiratBroj(data[4]);
		if(maksimalnaDuljina == null) {
			greska.setGreska();
			System.out.println("Greska maksimalna duljina se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		Integer maksimalnaSirina = probajParsiratBroj(data[5]);
		if(maksimalnaSirina == null) {
			greska.setGreska();
			System.out.println("Greska maksimalna sirina se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		Integer maksimalnaDubina = probajParsiratBroj(data[6]);
		if(maksimalnaDubina == null) {
			greska.setGreska();
			System.out.println("Greska maksimalna dubina se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		if(postojiId == true ||id == null || data[1].isEmpty() || data[2].isEmpty() || cijenaVeza == null || maksimalnaDuljina == null ||  maksimalnaSirina == null || maksimalnaDubina == null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public static Integer probajParsiratBroj (String ulaz) {
		  try {
		    return Integer.parseInt(ulaz);
		  } catch (NumberFormatException e) {
		    return null;
		  }
		}
}
