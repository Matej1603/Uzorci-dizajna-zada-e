package mdurasek_zadaca_2.sustav;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mdurasek_zadaca_2.podaci.Mol;
import mdurasek_zadaca_2.podaci.MolVez;
import mdurasek_zadaca_2.prototype.Vez;
import mdurasek_zadaca_2.singleton.Greska;

public class MolVezCsvCitac {
public static List<MolVez> listaMoloviVezovi = new ArrayList<MolVez>();
public static int[] vezovi = new int[1000]; 
	
	public static void molVezCsvCitac(String naziv) {
		int x = 0;
		for(Vez v: VezCsvCitac.vez) {
			vezovi[x] = v.getId();
			x++;
		}
		
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
						Boolean ispravnost = validirajCsvMolVez(data);
						if(ispravnost == false) {
							continue;
						}
						int[] uredenoPolje = urediPoljeVezova(data[1]);
						MolVez novi = new MolVez(Integer.parseInt(data[0]), uredenoPolje);
						listaMoloviVezovi.add(novi);
					}
				}
					
			} catch (IOException e) {
				
			}
		} catch (FileNotFoundException e) {
		}
	}
	public static int[] urediPoljeVezova(String vez) {
		String[] vezoviPolje = vez.split(",");
		int trenutniBroj;
		int [] vezoviPoljeBrojeva = new int[1000];
		for(int i = 0; i<vezoviPolje.length;i++) {
			trenutniBroj = probajParsiratBroj(vezoviPolje[i]);
			vezoviPoljeBrojeva[i] = trenutniBroj;
		}
		int duljina = micanjeDuplikataIzPolja(vezoviPoljeBrojeva,vezoviPoljeBrojeva.length);
		int [] poljeBezDuplikata = new int[duljina-1];
		for(int i = 0;i<duljina-1;i++) {
			poljeBezDuplikata[i] = vezoviPoljeBrojeva[i];
		}
		ArrayList<Integer> lista = new ArrayList<Integer>();
		for(int i = 0;i<poljeBezDuplikata.length;i++) {
			for(int j = 0; j<vezovi.length;j++) {
				if(vezovi[j] == poljeBezDuplikata[i]) {
					lista.add(poljeBezDuplikata[i]);
				}
			}
		}
		int[] zavrsnoPolje = listaUPolje(lista);
		return zavrsnoPolje;
	}
	public static int[] listaUPolje(List<Integer> lista)
	{
	    int[] polje = new int[lista.size()];
	    for (int i=0; i < polje.length; i++)
	    {
	        polje[i] = lista.get(i).intValue();
	    }
	    return polje;
	}
	
	private static int micanjeDuplikataIzPolja(int[] vezoviPoljeBrojeva, int duljina) {
	     if (duljina ==0 || duljina==1){  
	            return duljina;  
	        }  
	        int[] temp = new int[duljina];  
	        int j = 0;  
	        for (int i=0; i<duljina-1; i++){  
	            if (vezoviPoljeBrojeva[i] != vezoviPoljeBrojeva[i+1]){  
	                temp[j++] = vezoviPoljeBrojeva[i];  
	            }  
	         }  
	        temp[j++] = vezoviPoljeBrojeva[duljina-1];     
	        for (int i=0; i<j; i++){  
	        	vezoviPoljeBrojeva[i] = temp[i];  
	        }  
	        return j;
	}
	
	public static Boolean validirajCsvMolVez(String[]data) {
		Greska greska = Greska.getInstanca();
		Integer idMol = probajParsiratBroj(data[0]);
		boolean postoji = false;
		if(idMol == null) {
			greska.setGreska();
			System.out.println("Greska id mola se ne moze parsirati! " + "Redni broj greske: " + greska.brojac);
		}
		if(idMol != null) {
			for(Mol m : MolCsvCitac.listaMolovi) {
				if(idMol.equals(m.getId())) {
					postoji = true;
					break;
				}
			}
		}
		if(postoji == false) {
			greska.setGreska();
			System.out.println("Greska id mola je nepostojeci! " + "Redni broj greske: " + greska.brojac);
		}
		String regex = "^\\d+(,\\d+)*$";
		Pattern uzorakRegex = Pattern.compile(regex);
		Matcher matcherRegex = uzorakRegex.matcher(data[1]);
		Boolean ispravno = matcherRegex.find();
		if(ispravno == false) {
			greska.setGreska();
			System.out.println("Neispravan format vezova! " + "Redni broj greske: " + greska.brojac);
		}
		if(idMol == null || ispravno == false || postoji == false) {
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
