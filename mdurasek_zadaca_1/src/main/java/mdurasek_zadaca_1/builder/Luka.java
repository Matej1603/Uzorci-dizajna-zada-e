package mdurasek_zadaca_1.builder;

import java.time.LocalDateTime;

public class Luka {

	private final String naziv;
	private final double GPS_sirina;
	private final double GPS_visina;
	private final OsobineLuka osobineLuka;
	private final LocalDateTime virutalno_vrijeme;

	
	private Luka(LukaBuilder lukaBuilder) {
		this.naziv = lukaBuilder.naziv;
		this.GPS_sirina = lukaBuilder.GPS_sirina;
		this.GPS_visina = lukaBuilder.GPS_visina;
		this.osobineLuka = lukaBuilder.osobineLuka;
		this.virutalno_vrijeme = lukaBuilder.virutalno_vrijeme;
	}
	
	public String getNaziv() {
		return naziv;
	}
	public double getGPS_sirina() {
		return GPS_sirina;
	}
	public double getGPS_visina() {
		return GPS_visina;
	}
	public OsobineLuka getOsobineLuka() {
		return osobineLuka;
	}
	public LocalDateTime getVirutalno_vrijeme() {
		return virutalno_vrijeme;
	}
	
	public static class LukaBuilder{
		private String naziv;
		private double GPS_sirina;
		private double GPS_visina;
		private OsobineLuka osobineLuka;
		private LocalDateTime virutalno_vrijeme;
		
		public LukaBuilder naziv(final String naziv) {
			this.naziv  = naziv;
			return this;
		}
		public LukaBuilder GPS_sirina(final double GPS_sirina) {
			this.GPS_sirina  = GPS_sirina;
			return this;
		}
		public LukaBuilder GPS_visina(final double GPS_visina) {
			this.GPS_visina  = GPS_visina;
			return this;
		}
		public LukaBuilder osobineLuka(final OsobineLuka osobineLuka) {
			this.osobineLuka  = osobineLuka;
			return this;
		}
		public LukaBuilder virutalno_vrijeme(final LocalDateTime virutalno_vrijeme) {
			this.virutalno_vrijeme = virutalno_vrijeme;
			return this;
		}
		
		public Luka build() {
			return new Luka(this);
		}
	}
	
}
