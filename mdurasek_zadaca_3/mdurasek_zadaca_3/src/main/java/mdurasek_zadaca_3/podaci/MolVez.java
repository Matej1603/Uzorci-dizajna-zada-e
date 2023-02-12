package mdurasek_zadaca_3.podaci;

public class MolVez {
	
private int idMol;
private int[] idVezovi;

public int getIdMol() {
	return idMol;
}
public void setIdMol(int idMol) {
	this.idMol = idMol;
}
public String getIdVezovi() {
	String st = "";
	for(int i = 0;i<idVezovi.length;i++) {
		st += idVezovi[i] + " ";
	}
	return st;
}
public void setIdVezovi(int[] idVezovi) {
	this.idVezovi = idVezovi;
}
public MolVez(int idMol, int[] idVezovi) {
	super();
	this.idMol = idMol;
	this.idVezovi = idVezovi;
}
}
