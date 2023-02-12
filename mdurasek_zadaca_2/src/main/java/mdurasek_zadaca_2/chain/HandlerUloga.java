package mdurasek_zadaca_2.chain;

import mdurasek_zadaca_2.singleton.KorisnikUloga;
import mdurasek_zadaca_2.sustav.KorisnikPodaci;

public class HandlerUloga extends Handler{
	private KorisnikPodaci korisnikPodaci;
	public HandlerUloga(KorisnikPodaci korisnikPodaci) {
		this.korisnikPodaci = korisnikPodaci;
	}
	@Override
	public boolean handle(String korisnickoIme, String lozinka) {
		KorisnikUloga korisnikUloga = KorisnikUloga.getInstanca();
		if(korisnikPodaci.ulogaKorisnika()) {
			System.out.println("Uspjesno ste prijavljeni, uloga - admin, imate ovlasti nad svim naredbama");
			korisnikUloga.setAdmin();
		}
		else {
			System.out.println("Uspjesno ste prijavljeni, uloga - korisnik, nemate ovlasti mijenjati virtualno vrijeme");
			korisnikUloga.setKorisnik();
		}
		return handleSljedeci(korisnickoIme,lozinka);
	}
	

}
