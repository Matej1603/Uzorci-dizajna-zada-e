package mdurasek_zadaca_3.sustav;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mdurasek_zadaca_3.builder.Luka;
import mdurasek_zadaca_3.podaci.Mol;
import mdurasek_zadaca_3.singleton.Greska;

public class MolCsvCitac {
public static List<Mol> listaMolovi = new ArrayList<Mol>();
public static List<Luka> listaLuka = new ArrayList<Luka>();
	
	public static void molCsvCitac(String naziv) {
		Luka luka = vratiLuku();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(naziv));
			try {
				String line = br.readLine();
				while ((line = br.readLine()) != null) {
					if (!line.isEmpty()) {
						String[] data = line.split(";");
						if(data.length != 2) {
							Greska greska = Greska.getInstanca();
							greska.setGreska();
							System.out.println("Broj kolona se ne poklapa sa modelom, u potpunosti se preskace red! " + "Redni broj greske: " + greska.brojac);
							continue;
						}
						Boolean ispravnost = validirajCsvMol(data);
						if(ispravnost == false) {
							continue;
						}
						Mol novi = new Mol(Integer.parseInt(data[0]), data[1]);
						luka.dodajKomponentu(novi);
						listaMolovi.add(novi);
						
					}
				}
					
			} catch (IOException e) {
				
			}
		} catch (FileNotFoundException e) {
		}
	}
	public static Luka vratiLuku() {
		for(Luka l : LukaCsvCitac.listaLuka){
			System.out.println(l.getNaziv());
			return l;
		}
		return null;
	}
	public static Boolean validirajCsvMol(String[]data) {
		Greska greska = Greska.getInstanca();
		Integer idMol = probajParsiratBroj(data[0]);
		boolean postoji = false;
		if(idMol == null) {
			greska.setGreska();
			System.out.println("Greska id mola se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		if(idMol != null) {
			for(Mol m : listaMolovi) {
				if(idMol.equals(m.getId())) {
					postoji = true;
					greska.setGreska();
					System.out.println("Greska id mola vec postoji u listi! " + "Redni broj greske: " + greska.brojac);
					break;
				}
			}
		}
		if(data[1].isEmpty()) {
			greska.setGreska();
			System.out.println("Greska naziv mola ne postoji! " + "Redni broj greske: " + greska.brojac);
		}
		if(idMol == null || data[1].isEmpty() || postoji == true) {
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
