package mdurasek_zadaca_2.sustav;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mdurasek_zadaca_2.builder.Brod;
import mdurasek_zadaca_2.builder.KapacitetBroda;
import mdurasek_zadaca_2.builder.KarakteristikeBroda;
import mdurasek_zadaca_2.singleton.Greska;

public class BrodCsvCitac {
	public static List<Brod> listaBrod = new ArrayList<Brod>();
	public static void brodCsvCitac(String naziv) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(naziv));
			try {
				String line = br.readLine();
				while ((line = br.readLine()) != null) {
					if (!line.isEmpty()) {
						String[] data = line.split(";");
						if(data.length != 11) {
							Greska greska = Greska.getInstanca();
							greska.setGreska();
							System.out.println("Broj kolona se ne poklapa sa modelom, u potpunosti se preskace red! " + "Redni broj greske: " + greska.brojac);
							continue;
						}
						data = zamjeniDecimale(data);
						Boolean ispravnost = validirajCsvBrod(data);
						if(ispravnost == false) {
							continue;
						}
						KapacitetBroda kapacitetBroda = new KapacitetBroda.KapacitetBrodaBuilder().kapacitet_putnika(Integer.parseInt(data[8]))
								.kapacitet_osobnih_vozila(Integer.parseInt(data[9])).kapacitet_tereta(Integer.parseInt(data[10])).build();

							KarakteristikeBroda karakteristikeBroda = new KarakteristikeBroda.KarakteristikeBrodaBuilder()
								.vrsta(data[3]).duljina(Double.parseDouble(data[4])).sirina(Double.parseDouble(data[5])).gaz(Double.parseDouble(data[6])).maksimalna_brzina(Double.parseDouble(data[7])).build();
							Brod brod = new Brod.BrodBuilder().id(Integer.parseInt(data[0])).oznaka_broda(data[1]).naziv(data[2])
									.karakteristikeBroda(karakteristikeBroda).kapacitetBroda(kapacitetBroda).build();
							listaBrod.add(brod);
					}
				}
					
			} catch (IOException e) {
				
			}
		} catch (FileNotFoundException e) {
			
		}
	}
	
	
	public static Boolean validirajCsvBrod(String[]data) {
		Boolean postojiId = false;
		Greska greska = Greska.getInstanca();
		Integer id = probajParsiratBroj(data[0]);
		data[1] = data[1].trim();
		data[2] = data[2].trim();
		data[3] = data[3].trim();
		if(id == null) {
			greska.setGreska();
			System.out.println("Greska id broda se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		} 
		if(id != null) {
			for(Brod b: listaBrod) {
				if(b.getId() == id) {
					postojiId = true;
					break;
				}
			}
			if(postojiId == true) {
				greska.setGreska();
				System.out.println("Greska postoji vec identicna vrijednost id-a! " + "Redni broj greske: " + greska.brojac);
			}
			
		}
		if(data[1].isEmpty()) {
			greska.setGreska();
			System.out.println("Greska oznaka broda ne postoji! " + "Redni broj greske: " + greska.brojac);
		}
		if(data[2].isEmpty()) {
			greska.setGreska();
			System.out.println("Greska naziv broda ne postoji! " + "Redni broj greske: " + greska.brojac);
		} 
		if(data[3].isEmpty()) {
			greska.setGreska();
			System.out.println("Greska vrsta broda ne postoji! " + "Redni broj greske: " + greska.brojac);
		} 
		Double duljina = probajParsiratDecimalniBroj(data[4]);
		if(duljina == null) {
			greska.setGreska();
			System.out.println("Greska duljina se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		Double sirina = probajParsiratDecimalniBroj(data[5]);
		if(sirina == null) {
			greska.setGreska();
			System.out.println("Greska sirina se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		Double gaz = probajParsiratDecimalniBroj(data[6]);
		if(gaz == null) {
			greska.setGreska();
			System.out.println("Greska gaz se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		Double maksimalnaBrzina = probajParsiratDecimalniBroj(data[7]);
		if(maksimalnaBrzina == null) {
			greska.setGreska();
			System.out.println("Greska maksimalna brzina se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		Integer kapacitetPutnika = probajParsiratBroj(data[8]);
		if(kapacitetPutnika == null) {
			greska.setGreska();
			System.out.println("Greska kapacitet putnika se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		Integer kapacitetVozila = probajParsiratBroj(data[9]);
		if(kapacitetVozila == null) {
			greska.setGreska();
			System.out.println("Greska kapacitet vozila se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		Integer kapacitetTereta = probajParsiratBroj(data[10]);
		if(kapacitetTereta == null) {
			greska.setGreska();
			System.out.println("Greska kapacitet tereta se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		if(postojiId == true || id == null || data[1].isEmpty() || data[2].isEmpty() || data[3].isEmpty() || duljina == null || sirina == null || gaz == null || maksimalnaBrzina == null || kapacitetPutnika == null|| kapacitetVozila == null|| kapacitetTereta == null) {
			return false;
		}
		else {
			return true;
		}
		
	}
	public static String[] zamjeniDecimale(String[] data) {
		data[4] = provjeriDecimale(data[4]);
		data[5] = provjeriDecimale(data[5]);
		data[6] = provjeriDecimale(data[6]);
		data[7] = provjeriDecimale(data[7]);
		return data;
	}

	public static String provjeriDecimale(String ulaz) {
		if (ulaz.contains(",")) {
			ulaz = ulaz.replace(",", ".");
			return ulaz;
		} else {
			ulaz = ulaz.replace(".", ",");
			return ulaz;
		}
	}
	
	
	public static Integer probajParsiratBroj(String ulaz) {
		try {
			return Integer.parseInt(ulaz);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Double probajParsiratDecimalniBroj(String ulaz) {
		try {
			return Double.parseDouble(ulaz);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
