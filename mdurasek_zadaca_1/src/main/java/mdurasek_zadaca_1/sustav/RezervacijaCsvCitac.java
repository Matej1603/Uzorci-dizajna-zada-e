package mdurasek_zadaca_1.sustav;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import mdurasek_zadaca_1.podaci.Rezervacija;
import mdurasek_zadaca_1.singleton.Greska;


public class RezervacijaCsvCitac {
	
	public static List<Rezervacija> listaRezervacija = new ArrayList<Rezervacija>();
	public static void rezervacijaCsvCitac(String naziv) {
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
						Boolean ispravnost = validirajCsvRezervacija(data);
						if(ispravnost == false) {
							continue;
						}
						Rezervacija rezervacija = new Rezervacija(Integer.parseInt(data[0]), probajParsiratiDatum(data[1]), Integer.parseInt(data[2]));
						listaRezervacija.add(rezervacija);
					}
				}
					
			} catch (IOException e) {
				
			}
		} catch (FileNotFoundException e) {
		}
		
	}
	
	public static Boolean validirajCsvRezervacija(String[]data) {
		Greska greska = Greska.getInstanca();
		Integer idBrod = probajParsiratBroj(data[0]);
		if(idBrod == null) {
			greska.setGreska();
			System.out.println("Greska id broda se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		LocalDateTime vrijemeOd = probajParsiratiDatum(data[1]);
		if(vrijemeOd == null) {
			greska.setGreska();
			System.out.println("Greska vrijeme se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		Integer trajanjePriveza = probajParsiratBroj(data[2]);
		if(trajanjePriveza == null) {
			greska.setGreska();
			System.out.println("Greska trajanje priveza se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		if(idBrod == null || vrijemeOd == null || trajanjePriveza == null) {
			return false;
		}
		else {
			return true;
		}
		
	}	
	public static LocalDateTime probajParsiratiDatum(String ulaz) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
		try {
			return LocalDateTime.parse(ulaz,formatter);
		} catch (DateTimeParseException e) {
			return null;
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
