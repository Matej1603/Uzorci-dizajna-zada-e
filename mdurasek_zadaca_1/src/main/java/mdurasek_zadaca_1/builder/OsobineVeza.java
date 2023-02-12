package mdurasek_zadaca_1.builder;

public class OsobineVeza {
	private final int cijena_veza_po_satu;
	private final int maksimalna_duljina;
	private final int maksimalna_sirina;
	private final int maksimalna_dubina;
	
	private OsobineVeza(OsobineBuilder osobineBuilder) {
		this.cijena_veza_po_satu = osobineBuilder.cijena_veza_po_satu;
		this.maksimalna_duljina = osobineBuilder.maksimalna_duljina;
		this.maksimalna_sirina = osobineBuilder.maksimalna_sirina;
		this.maksimalna_dubina = osobineBuilder.maksimalna_dubina;
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
	
	public static class OsobineBuilder{
		private int cijena_veza_po_satu;
		private int maksimalna_duljina;
		private int maksimalna_sirina;
		private int maksimalna_dubina;
		
		public OsobineBuilder cijena_veza_po_satu (final int cijena_veza_po_satu) {
			this.cijena_veza_po_satu = cijena_veza_po_satu;
			return this;
		}
		
		public OsobineBuilder maksimalna_duljina (final int maksimalna_duljina) {
			this.maksimalna_duljina = maksimalna_duljina;
			return this;
		}
		
		public OsobineBuilder maksimalna_sirina (final int maksimalna_sirina) {
			this.maksimalna_sirina = maksimalna_sirina;
			return this;
		}
		
		public OsobineBuilder maksimalna_dubina (final int maksimalna_dubina) {
			this.maksimalna_dubina = maksimalna_dubina;
			return this;
		}
		
		public OsobineVeza build() {
			return new OsobineVeza(this);
		}
	}
}
