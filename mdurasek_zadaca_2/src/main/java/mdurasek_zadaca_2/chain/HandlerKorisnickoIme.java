package mdurasek_zadaca_2.chain;

import mdurasek_zadaca_2.sustav.KorisnikPodaci;

public class HandlerKorisnickoIme extends Handler {
	private KorisnikPodaci korisnikPodaci;
	public HandlerKorisnickoIme(KorisnikPodaci korisnikPodaci) {
		this.korisnikPodaci = korisnikPodaci;
	}
	@Override
	public boolean handle(String korisnickoIme, String lozinka) {
		if(!korisnikPodaci.postojiKorisnickoIme(korisnickoIme)) {
			System.out.println("Korisnicko ime ne postoji!");
			return false;
		}
		return handleSljedeci(korisnickoIme,lozinka);
	
	}

}
