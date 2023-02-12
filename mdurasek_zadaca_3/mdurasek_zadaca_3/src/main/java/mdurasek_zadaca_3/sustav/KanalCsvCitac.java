package mdurasek_zadaca_3.sustav;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mdurasek_zadaca_3.podaci.Kanal;
import mdurasek_zadaca_3.singleton.Greska;

public class KanalCsvCitac {
public static List<Kanal> listaKanali = new ArrayList<Kanal>();
	
	public static void kanalCsvCitac(String naziv) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(naziv));
			try {
				String line = br.readLine();
				while ((line = br.readLine()) != null) {
					if (!line.isEmpty()) {
						String[] data = line.split(";");
						if(data.length != 3) {
							Greska greska = Greska.getInstanca();
							greska.setGreska();
							System.out.println("Broj kolona se ne poklapa sa modelom, u potpunosti se preskace red! " + "Redni broj greske: " + greska.brojac);
							continue;
						}
						Boolean ispravnost = validirajCsvKanal(data);
						if(ispravnost == false) {
							continue;
						}
						Kanal novi = new Kanal(Integer.parseInt(data[0]), Integer.parseInt(data[1]),Integer.parseInt(data[2]));
						listaKanali.add(novi);
					}
				}
					
			} catch (IOException e) {
				
			}
		} catch (FileNotFoundException e) {
		}
	}
	public static Boolean validirajCsvKanal(String[]data) {
		Greska greska = Greska.getInstanca();
		Integer idKanal = probajParsiratBroj(data[0]);
		boolean postoji = false;
		boolean postojiFrekvencija = false;
		if(idKanal == null) {
			greska.setGreska();
			System.out.println("Greska id kanala se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		if(idKanal != null) {
			for(Kanal k: listaKanali) {
				if(idKanal.equals(k.getId())) {
					postoji = true;
					greska.setGreska();
					System.out.println("Greska id kanala vec postoji u listi! " + "Redni broj greske: " + greska.brojac);
					break;
				}
			}
		}
		Integer frekvencija = probajParsiratBroj(data[1]);
		if(frekvencija == null) {
			greska.setGreska();
			System.out.println("Greska frekvencija se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		if(frekvencija != null) {
			for(Kanal k: listaKanali) {
				if(frekvencija.equals(k.getFrekvencija())) {
					postojiFrekvencija = true;
					greska.setGreska();
					System.out.println("Greska takva frekvencija vec postoji u listi! " + "Redni broj greske: " + greska.brojac);
					break;
					
				}
			}
		}
		Integer maksimalniBroj = probajParsiratBroj(data[2]);
		if(maksimalniBroj == null) {
			greska.setGreska();
			System.out.println("Greska maksimalni broj se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		if(idKanal == null || frekvencija == null|| maksimalniBroj == null || postoji == true || postojiFrekvencija == true) {
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
