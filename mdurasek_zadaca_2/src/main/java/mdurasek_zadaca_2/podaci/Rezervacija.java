package mdurasek_zadaca_2.podaci;
import java.time.LocalDateTime;

public class Rezervacija {
	private int id_brod;
	private LocalDateTime datum_vrijeme_od;
	private int trajanje_priveza_u_h;
	
	public int getId_brod() {
		return id_brod;
	}
	public void setId_brod(int id_brod) {
		this.id_brod = id_brod;
	}
	public LocalDateTime getDatum_vrijeme_od() {
		return datum_vrijeme_od;
	}
	public void setDatum_vrijeme_od(LocalDateTime datum_vrijeme_od) {
		this.datum_vrijeme_od = datum_vrijeme_od;
	}
	public int getTrajanje_priveza_u_h() {
		return trajanje_priveza_u_h;
	}
	public void setTrajanje_priveza_u_h(int trajanje_priveza_u_h) {
		this.trajanje_priveza_u_h = trajanje_priveza_u_h;
	}
	
	public Rezervacija(int id_brod, LocalDateTime datum_vrijeme_od, int trajanje_priveza_u_h) {
		super();
		this.id_brod = id_brod;
		this.datum_vrijeme_od = datum_vrijeme_od;
		this.trajanje_priveza_u_h = trajanje_priveza_u_h;
	}
	
}
