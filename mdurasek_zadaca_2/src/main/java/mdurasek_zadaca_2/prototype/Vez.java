package mdurasek_zadaca_2.prototype;

import mdurasek_zadaca_2.visitor.Visitor;

public abstract class Vez {
	private int id;
	private String oznaka_veza;
	private String vrsta;
	private int cijena_veza_po_satu;
	private int maksimalna_duljina;
	private int maksimalna_sirina;
	private int maksimalna_dubina;
	
	public Vez() {
		
	}
	
	public Vez(Vez vt) {
		if(vt != null) {
			this.id = vt.id;
			this.oznaka_veza = vt.oznaka_veza;
			this.vrsta = vt.vrsta;
			this.cijena_veza_po_satu = vt.cijena_veza_po_satu;
			this.maksimalna_duljina = vt.maksimalna_duljina;
			this.maksimalna_sirina = vt.maksimalna_sirina;
			this.maksimalna_dubina = vt.maksimalna_dubina;
		}
	}
	public int getId() {
		return id;
	}
	public String getOznaka_veza() {
		return oznaka_veza;
	}
	public String getVrsta() {
		return vrsta;
	}
	public int getCijena_veza_po_satu() {
		return cijena_veza_po_satu;
	}
	public int getMaksimalna_duljina() {
		return maksimalna_duljina;
	}
	public int getMaksimalna_sirina() {
		return maksimalna_sirina;
	}
	public int getMaksimalna_dubina() {
		return maksimalna_dubina;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public void setOznaka_veza(String oznaka_veza) {
		this.oznaka_veza = oznaka_veza;
	}
	public void setVrsta(String vrsta) {
		this.vrsta = vrsta;
	}
	public void setCijena_veza_po_satu(int cijena_veza_po_satu) {
		this.cijena_veza_po_satu = cijena_veza_po_satu;
	}
	public void setMaksimalna_duljina(int maksimalna_duljina) {
		this.maksimalna_duljina = maksimalna_duljina;
	}
	public void setMaksimalna_sirina(int maksimalna_sirina) {
		this.maksimalna_sirina = maksimalna_sirina;
	}
	public void setMaksimalna_dubina(int maksimalna_dubina) {
		this.maksimalna_dubina = maksimalna_dubina;
	}
	
	public abstract Vez clone();
	public abstract String prihvati(Visitor visitor, String datum, String dan);
}
