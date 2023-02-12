package mdurasek_zadaca_3.state;

public class Kontekst {

	private Tablica stanje;

	public Kontekst(){
		      stanje = null;
		   }
	public void setStanje(Tablica stanje) {
		this.stanje = stanje;
	}

	public Tablica getStanje() {
		return stanje;
	}

}
