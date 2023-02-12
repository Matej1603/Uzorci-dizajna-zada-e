package mdurasek_zadaca_2.chain;

import mdurasek_zadaca_2.sustav.KorisnikPodaci;

public class HandlerLozinka extends Handler {
	
	private KorisnikPodaci korisnikPodaci;
	public HandlerLozinka(KorisnikPodaci korisnikPodaci) {
		this.korisnikPodaci = korisnikPodaci;
	}
	@Override
	public boolean handle(String korisnickoIme, String lozinka) {
		if(!korisnikPodaci.podudaranjeKorisnikaILozinke(korisnickoIme,lozinka)) {
			System.out.println("Korisnicko ime se ne podudara sa lozinkom!");
			return false;
		}
		return handleSljedeci(korisnickoIme,lozinka);
	}

}
