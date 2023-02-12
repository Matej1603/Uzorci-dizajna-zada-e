package mdurasek_zadaca_3.sustav;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mdurasek_zadaca_3.builder.Raspored;
import mdurasek_zadaca_3.builder.RasporedTermin;
import mdurasek_zadaca_3.singleton.Greska;


public class RasporedCsvCitac {
	
public static List<Raspored> listaRaspored = new ArrayList<Raspored>();
	
	public static void rasporedCsvCitac(String naziv) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(naziv));
			try {
				String line = br.readLine();
				while ((line = br.readLine()) != null) {
					if (!line.isEmpty()) {
						String[] data = line.split(";");
						if(data.length != 5) {
							Greska greska = Greska.getInstanca();
							greska.setGreska();
							System.out.println("Broj kolona se ne poklapa sa modelom, u potpunosti se preskace red! " + "Redni broj greske: " + greska.brojac);
							continue;
						}
						Boolean ispravnost = validirajCsvRaspored(data);
						if(ispravnost == false) {
							continue;
						}
						
						RasporedTermin rasporedTermin = new RasporedTermin.TerminBuilder()
								.dani_u_tjednu(data[2])
								.vrijeme_od(data[3])
								.vrijeme_do(data[4])
								.build();
						Raspored raspored = new Raspored.RasporedBuilder()
								.id_vez(Integer.parseInt(data[0]))
								.id_brod(Integer.parseInt(data[1]))
								.rasporedTermin(rasporedTermin)
								.build();
						listaRaspored.add(raspored);
					}
				}
					
			} catch (IOException e) {
				
			}
		} catch (FileNotFoundException e) {
		}
		
	}
	
	public static Boolean validirajCsvRaspored(String[]data) {
		Greska greska = Greska.getInstanca();
		Integer idVez = probajParsiratBroj(data[0]);
		if(idVez == null) {
			greska.setGreska();
			System.out.println("Greska id veza se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		Integer idBrod = probajParsiratBroj(data[1]);
		if(idBrod == null) {
			greska.setGreska();
			System.out.println("Greska id broda se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		String regexDani = "^[0-6](,[0-6])*$";
		Pattern uzorakDani = Pattern.compile(regexDani);
		Matcher matcherDani = uzorakDani.matcher(data[2]);
		Boolean ispravniDani = matcherDani.find();
		if(ispravniDani == false) {
			greska.setGreska();
			System.out.println("Neispravan format dana u tjednu! " + "Redni broj greske: " + greska.brojac);
		}
		String regexVrijeme = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
		Pattern uzorakVrijeme = Pattern.compile(regexVrijeme);
		Matcher matcherVrijemeOd = uzorakVrijeme.matcher(data[3]);
		Matcher matcherVrijemeDo = uzorakVrijeme.matcher(data[4]);
		Boolean ispravnoVrijemeOd = matcherVrijemeOd.find();
		Boolean ispravnoVrijemeDo = matcherVrijemeDo.find();
		if(ispravnoVrijemeOd == false) {
			greska.setGreska();
			System.out.println("Neispravan format vrijeme_od! " + "Redni broj greske: " + greska.brojac);
		}
		if(ispravnoVrijemeDo == false) {
			greska.setGreska();
			System.out.println("Neispravan format vrijeme_do! " + "Redni broj greske: " + greska.brojac);
		}
		if(ispravnoVrijemeOd == true && ispravnoVrijemeDo) {
			String[] vrijemeOd = data[3].split(":");
			String[] vrijemeDo = data[4].split(":");
			int rasporedMinuteOd = Integer.parseInt(vrijemeOd[0]) * 60 + Integer.parseInt(vrijemeOd[1]);
			int rasporedMinuteDo = Integer.parseInt(vrijemeDo[0]) * 60 + Integer.parseInt(vrijemeDo[1]);
			if(rasporedMinuteOd > rasporedMinuteDo) {
				greska.setGreska();
				System.out.println("Neispravan format, vrijeme_od je vece od vrijeme_do!" + "Redni broj greske: " + greska.brojac);
				return false;
			}
		}
		
		if(idVez == null || idBrod == null || ispravniDani == false || ispravniDani == false || ispravnoVrijemeOd == false|| ispravnoVrijemeDo == false) {
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
