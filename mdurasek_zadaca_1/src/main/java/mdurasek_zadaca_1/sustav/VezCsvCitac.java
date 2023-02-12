package mdurasek_zadaca_1.sustav;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mdurasek_zadaca_1.builder.OsobineVeza;
import mdurasek_zadaca_1.builder.Vez;
import mdurasek_zadaca_1.singleton.Greska;

public class VezCsvCitac {
public static List<Vez> listaVez = new ArrayList<Vez>();
	
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
						
						OsobineVeza osobineVeza = new OsobineVeza.OsobineBuilder()
								.cijena_veza_po_satu(Integer.parseInt(data[3]))
								.maksimalna_duljina(Integer.parseInt(data[4]))
								.maksimalna_sirina(Integer.parseInt(data[5]))
								.maksimalna_dubina(Integer.parseInt(data[6]))
								.build();
						Vez vez = new Vez.VezBuilder()
								.id(Integer.parseInt(data[0]))
								.oznaka_veza(data[1])
								.vrsta(data[2])
								.osobineVeza(osobineVeza)
								.build();
						listaVez.add(vez);
					}
				}
					
			} catch (IOException e) {
				
			}
		} catch (FileNotFoundException e) {
		}
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
			for(Vez v: listaVez) {
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
