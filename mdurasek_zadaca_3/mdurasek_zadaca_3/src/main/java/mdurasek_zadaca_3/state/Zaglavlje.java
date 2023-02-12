package mdurasek_zadaca_3.state;

public class Zaglavlje implements Tablica{

	@Override
	public void postaviStatus(Kontekst kontekst) {
		kontekst.setStanje(this);
	}
	public String toString() {
	      return "Z";
	}

}
