package mdurasek_zadaca_2.sustav;

import java.util.ArrayList;
import java.util.List;
import mdurasek_zadaca_2.podaci.Korisnik;

public class KorisnikPodaci {
	public static List<Korisnik> korisnici = new ArrayList<Korisnik>();
	public String uloga = "";
	public KorisnikPodaci() {
		String[] korisnickaImena = {"pero123","ivo123","ante123","marko123"};
		String[] lozinke = {"1234","123456","123","12345678"};
		String[] uloge = {"korisnik","admin","admin","korisnik"};
		for(int i = 0;i<korisnickaImena.length;i++) {
			Korisnik novi = new Korisnik(korisnickaImena[i],lozinke[i],uloge[i]);
			korisnici.add(novi);
		}
	}
	
	public boolean postojiKorisnickoIme(String korisnickoIme) {
		for(Korisnik k:korisnici) {
			if(korisnickoIme.equals(k.getKorisnickoIme())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean podudaranjeKorisnikaILozinke(String korisnickoIme,String lozinka) {
		for(Korisnik k: korisnici) {
			if(korisnickoIme.equals(k.getKorisnickoIme()) && lozinka.equals(k.getLozinka())) {
				uloga = k.getUloga();
				return true;
			}
		}
		return false;
	}
	
	public boolean ulogaKorisnika() {
		if(uloga.equals("admin")) {
			return true;
		}
		return false;
	}
	
}
