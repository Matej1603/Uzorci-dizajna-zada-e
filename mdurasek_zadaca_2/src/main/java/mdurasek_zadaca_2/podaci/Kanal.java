package mdurasek_zadaca_2.podaci;

import java.util.ArrayList;
import java.util.List;

import mdurasek_zadaca_2.builder.Brod;
import mdurasek_zadaca_2.observer.Klijent;
import mdurasek_zadaca_2.singleton.Greska;

public class Kanal implements Klijent{
private int id;
private int frekvencija;
private int maksimalniBroj;
List<Brod> brodovi = new ArrayList<Brod>();
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getFrekvencija() {
	return frekvencija;
}
public void setFrekvencija(int frekvencija) {
	this.frekvencija = frekvencija;
}
public int getMaksimalniBroj() {
	return maksimalniBroj;
}
public void setMaksimalniBroj(int maksimalniBroj) {
	this.maksimalniBroj = maksimalniBroj;
}
public Kanal(int id, int frekvencija, int maksimalniBroj) {
	super();
	this.id = id;
	this.frekvencija = frekvencija;
	this.maksimalniBroj = maksimalniBroj;
}
@Override
public void obavijesti(Brod brod, String poruka) {
	for(Brod b: brodovi) {
		b.update(poruka);
	}
	
}
@Override
public void spoji(Brod brod) {
	
	if(brodovi.contains(brod)) {
		Greska greska =  Greska.getInstanca();
		greska.setGreska();
		System.out.println("Greska, brod je vec spojen na frekvenciji! " + "Redni broj greske: " + greska.brojac);
	}
	if(brodovi.size() >= this.maksimalniBroj) {
		Greska greska =  Greska.getInstanca();
		greska.setGreska();
		System.out.println("Greska, premasen je maksimalan broj brodova na frekvenciji! " + "Redni broj greske: " + greska.brojac);
	}
	if(brodovi.size() < this.maksimalniBroj && !brodovi.contains(brod)) {
		brodovi.add(brod);
		String str = "Brod sa id " + Integer.toString(brod.getId()) + " je uspjesno spojen na frekvenciju " + Integer.toString(this.frekvencija);
		obavijesti(brod,str);
	}
}
@Override
public void odspoji(Brod brod) {
	if(brodovi.contains(brod)) {
		String str = "Brod sa id " + Integer.toString(brod.getId()) + " je uspjesno odspojen sa frekvencije " + Integer.toString(this.frekvencija);
		obavijesti(brod,str);
		brodovi.remove(brod);
	}
	else {
		Greska greska =  Greska.getInstanca();
		greska.setGreska();
		System.out.println("Greska, brod nije bio spojen na frekvenciji! " + "Redni broj greske: " + greska.brojac);
	}
}

}
