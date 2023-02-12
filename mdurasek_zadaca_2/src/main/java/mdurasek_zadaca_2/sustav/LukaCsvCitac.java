package mdurasek_zadaca_2.sustav;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import mdurasek_zadaca_2.builder.Luka;
import mdurasek_zadaca_2.builder.OsobineLuka;
import mdurasek_zadaca_2.singleton.Greska;
import mdurasek_zadaca_2.singleton.VirtualnoVrijeme;


public class LukaCsvCitac {
	
	public static List<Luka> listaLuka = new ArrayList<Luka>();
	
	public static void lukaCsvCitac(String naziv) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(naziv));
			try {
				String line = br.readLine();
				while ((line = br.readLine()) != null) {
					if (!line.isEmpty()) {
						String[] data = line.split(";");
						if(data.length != 8) {
							Greska greska = Greska.getInstanca();
							greska.setGreska();
							System.out.println("Broj kolona se ne poklapa sa modelom, u potpunosti se preskace red! " + "Redni broj greske: " + greska.brojac);
							continue;
						}
						data = zamjeniDecimale(data);
						Boolean ispravnost = validirajCsvLuka(data);
						if(ispravnost == false) {
							continue;
						}
						LocalDateTime datum = probajParsiratiDatum(data[7]);
						OsobineLuka osobineLuka = new OsobineLuka.OsobineLukaBuilder()
								.dubina_luke(Integer.parseInt(data[3]))
								.ukupni_broj_putnickih_vezova(Integer.parseInt(data[4]))
								.ukupni_broj_poslovnih_vezova(Integer.parseInt(data[5]))
								.ukupni_broj_ostalih_vezova(Integer.parseInt(data[6]))
								.build();
						Luka luka = new Luka.LukaBuilder()
								.naziv(data[0])
								.GPS_sirina(Double.parseDouble(data[1]))
								.GPS_visina(Double.parseDouble(data[2]))
								.osobineLuka(osobineLuka)
								.virutalno_vrijeme(datum)
								.build();
						VirtualnoVrijeme vrijeme = VirtualnoVrijeme.getInstanca();
						vrijeme.setVrijeme(datum);
						vrijeme.setPostoji(true);
						listaLuka.add(luka);
					}
				}
					
			} catch (IOException e) {
				
			}
		} catch (FileNotFoundException e) {
		}
		
	}
	
	public static Boolean validirajCsvLuka(String[]data) {
		Greska greska = Greska.getInstanca();
		data[0] = data[0].trim();
		if(data[0].isEmpty()) {
			greska.setGreska();
			System.out.println("Greska naziv ne postoji! " + "Redni broj greske: " + greska.brojac);
		}
		Double gpsSirina = probajParsiratDecimalniBroj(data[1]);
		if(gpsSirina == null) {
			greska.setGreska();
			System.out.println("Greska gps sirina se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		} 
		Double gpsVisina = probajParsiratDecimalniBroj(data[2]);
		if(gpsVisina == null) {
			greska.setGreska();
			System.out.println("Greska gps visina se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		Integer dubinaLuke = probajParsiratBroj(data[3]);
		if(dubinaLuke == null) {
			greska.setGreska();
			System.out.println("Greska dubina luke se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		} 
		Integer putnickiVez = probajParsiratBroj(data[4]);
		if(putnickiVez == null) {
			greska.setGreska();
			System.out.println("Greska broj putnicki vez se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		} 
		Integer poslovniVez = probajParsiratBroj(data[5]);
		if(poslovniVez == null) {
			greska.setGreska();
			System.out.println("Greska broj poslovnih vez se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		} 
		Integer ostaliVez = probajParsiratBroj(data[6]);
		if(ostaliVez == null) {
			greska.setGreska();
			System.out.println("Greska broj ostalih vezova se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		} 
		LocalDateTime datum = probajParsiratiDatum(data[7]);
		if(datum == null){
			greska.setGreska();
			System.out.println("Greska datum se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		if(data[0].isEmpty()||ostaliVez == null || poslovniVez == null || putnickiVez == null || dubinaLuke == null || gpsVisina == null || gpsSirina == null || datum == null ) {
			return false;
		}
		else {
			return true;
		}
		
	}
	public static String[] zamjeniDecimale(String[] data) {
		data[1] = provjeriDecimale(data[1]);
		data[2] = provjeriDecimale(data[2]);
		return data;
	}

	public static String provjeriDecimale(String ulaz) {
		if (ulaz.contains(",")) {
			ulaz = ulaz.replace(",", ".");
		} 
		return ulaz;
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
	public static Double probajParsiratDecimalniBroj (String ulaz) {
		  try {
		    return Double.parseDouble(ulaz);
		  } catch (NumberFormatException e) {
		    return null;
		  }
		}
}
