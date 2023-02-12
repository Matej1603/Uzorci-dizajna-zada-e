package mdurasek_zadaca_2.chain;

public class Prijava {
	
	private Handler handler;
	public Prijava (Handler handler) {
		this.handler = handler;
	}
	public boolean prijava(String korisnickoIme,String lozinka) {
		if(handler.handle(korisnickoIme, lozinka)) {
			return true;
		}
		else {
			return false;
		}
	}
}
