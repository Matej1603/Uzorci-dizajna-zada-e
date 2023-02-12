package mdurasek_zadaca_2.chain;

public abstract class Handler{
	
	private Handler sljedeci;
	
	public Handler postaviSljedeciHandler(Handler sljedeci) {
		this.sljedeci = sljedeci;
		return sljedeci;
	}
	
	public abstract boolean handle(String korisnickoIme,String lozinka);

	protected boolean handleSljedeci(String korisnickoIme,String lozinka) {
		if(sljedeci == null) {
			return true;
		}
		return sljedeci.handle(korisnickoIme, lozinka);
	}
}
