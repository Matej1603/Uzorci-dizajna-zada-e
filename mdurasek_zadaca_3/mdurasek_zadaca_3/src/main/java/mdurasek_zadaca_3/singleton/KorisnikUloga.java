package mdurasek_zadaca_3.singleton;

public class KorisnikUloga {
	private static KorisnikUloga korisnikUloga = new KorisnikUloga();
	public boolean admin;
	private KorisnikUloga() {
	}

	public static KorisnikUloga getInstanca() {
		if (korisnikUloga == null)
			korisnikUloga = new KorisnikUloga();
		return korisnikUloga;
	}

	public void setAdmin() {
		this.admin = true;
	}

	public void setKorisnik() {
		this.admin = false;
	}

}
