package mdurasek_zadaca_1.builder;

public class KapacitetBroda {
	private final int kapacitet_putnika;
	private final int kapacitet_osobnih_vozila;
	private final int kapacitet_tereta;
	
	private KapacitetBroda(KapacitetBrodaBuilder kapacitetBrodaBuilder) {
		this.kapacitet_putnika = kapacitetBrodaBuilder.kapacitet_putnika;
		this.kapacitet_osobnih_vozila = kapacitetBrodaBuilder.kapacitet_osobnih_vozila;
		this.kapacitet_tereta = kapacitetBrodaBuilder.kapacitet_tereta;
	}
	
	public int getKapacitet_putnika() {
		return kapacitet_putnika;
	}
	public int getKapacitet_osobnih_vozila() {
		return kapacitet_osobnih_vozila;
	}
	public int getKapacitet_tereta() {
		return kapacitet_tereta;
	}

	public static class KapacitetBrodaBuilder{
		private int kapacitet_putnika;
		private int kapacitet_osobnih_vozila;
		private int kapacitet_tereta;
		
		public KapacitetBrodaBuilder kapacitet_putnika (final int kapacitet_putnika) {
			this.kapacitet_putnika = kapacitet_putnika;
			return this;
		}
		public KapacitetBrodaBuilder kapacitet_osobnih_vozila (final int kapacitet_osobnih_vozila) {
			this.kapacitet_osobnih_vozila = kapacitet_osobnih_vozila;
			return this;
		}
		public KapacitetBrodaBuilder kapacitet_tereta (final int kapacitet_tereta) {
			this.kapacitet_tereta = kapacitet_tereta;
			return this;
		}
		public KapacitetBroda build () {
			return new KapacitetBroda(this);
		}
	}
}
