package mdurasek_zadaca_1.builder;

public class KarakteristikeBroda {
	private final String vrsta;
	private final double duljina;
	private final double sirina;
	private final double gaz;
	private final double maksimalna_brzina;
	
	private KarakteristikeBroda(KarakteristikeBrodaBuilder karakteristikeBrodaBuilder) {
		this.vrsta = karakteristikeBrodaBuilder.vrsta;
		this.duljina = karakteristikeBrodaBuilder.duljina;
		this.sirina = karakteristikeBrodaBuilder.sirina;
		this.gaz = karakteristikeBrodaBuilder.gaz;
		this.maksimalna_brzina = karakteristikeBrodaBuilder.maksimalna_brzina;
	}
	
	public String getVrsta() {
		return vrsta;
	}
	public double getDuljina() {
		return duljina;
	}
	public double getSirina() {
		return sirina;
	}
	public double getGaz() {
		return gaz;
	}
	public double getMaksimalna_brzina() {
		return maksimalna_brzina;
	}

	public static class KarakteristikeBrodaBuilder  {
		private String vrsta;
		private double duljina;
		private double sirina;
		private double gaz;
		private double maksimalna_brzina;
		
		public KarakteristikeBrodaBuilder vrsta(final String vrsta) {
			this.vrsta = vrsta;
			return this;
		}
		public KarakteristikeBrodaBuilder duljina(final double duljina) {
			this.duljina = duljina;
			return this;
		}
		public KarakteristikeBrodaBuilder sirina(final double sirina) {
			this.sirina = sirina;
			return this;
		}
		public KarakteristikeBrodaBuilder gaz(final double gaz) {
			this.gaz = gaz;
			return this;
		}
		public KarakteristikeBrodaBuilder maksimalna_brzina(final double maksimalna_brzina) {
			this.maksimalna_brzina = maksimalna_brzina;
			return this;
		}
		
		public KarakteristikeBroda build() {
			return new KarakteristikeBroda(this);
		}
	}
}
