package mdurasek_zadaca_3.podaci;
import java.util.ArrayList;
import java.util.List;

import mdurasek_zadaca_3.composite.Component;

public class Mol implements Component {
private int id;
private String naziv;

List<Component> komponenta = new ArrayList<Component>();
public void dodajKomponentu(Component con) {
	komponenta.add(con);
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getNaziv() {
	return naziv;
}
public void setNaziv(String naziv) {
	this.naziv = naziv;
}
public Mol(int id, String naziv) {
	super();
	this.id = id;
	this.naziv = naziv;
}

@Override
public void ispis() {
	System.out.println(this.id + " " + this.naziv);
	for(int i = 0;i<komponenta.size();i++) {
		Component c = (Component) komponenta.get(i);
		c.ispis();
	}
	
}

}
