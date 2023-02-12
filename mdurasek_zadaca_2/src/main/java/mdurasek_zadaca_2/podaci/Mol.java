package mdurasek_zadaca_2.podaci;

public class Mol {
private int id;
private String naziv;

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

}
