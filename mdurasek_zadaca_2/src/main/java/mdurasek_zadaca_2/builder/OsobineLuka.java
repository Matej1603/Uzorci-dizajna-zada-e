package mdurasek_zadaca_2.builder;

public class OsobineLuka {
	
	private final int dubina_luke;
	private final int ukupni_broj_putnickih_vezova;
	private final int ukupni_broj_poslovnih_vezova;
	private final int ukupni_broj_ostalih_vezova;
	
	private OsobineLuka(OsobineLukaBuilder osobineLukaBuilder) {
		this.dubina_luke = osobineLukaBuilder.dubina_luke;
		this.ukupni_broj_putnickih_vezova = osobineLukaBuilder.ukupni_broj_putnickih_vezova;
		this.ukupni_broj_poslovnih_vezova = osobineLukaBuilder.ukupni_broj_poslovnih_vezova;
		this.ukupni_broj_ostalih_vezova = osobineLukaBuilder.ukupni_broj_ostalih_vezova;
	}
	
	public int getDubina_luke() {
		return dubina_luke;
	}
	public int getUkupni_broj_putnickih_vezova() {
		return ukupni_broj_putnickih_vezova;
	}
	public int getUkupni_broj_poslovnih_vezova() {
		return ukupni_broj_poslovnih_vezova;
	}
	public int getUkupni_broj_ostalih_vezova() {
		return ukupni_broj_ostalih_vezova;
	}

	public static class OsobineLukaBuilder{
		private int dubina_luke;
		private int ukupni_broj_putnickih_vezova;
		private int ukupni_broj_poslovnih_vezova;
		private int ukupni_broj_ostalih_vezova;
		
		public OsobineLukaBuilder dubina_luke (final int dubina_luke) {
			this.dubina_luke  = dubina_luke;
			return this;
		}
		public OsobineLukaBuilder ukupni_broj_putnickih_vezova (final int ukupni_broj_putnickih_vezova) {
			this.ukupni_broj_putnickih_vezova  = ukupni_broj_putnickih_vezova;
			return this;
		}
		public OsobineLukaBuilder ukupni_broj_poslovnih_vezova (final int ukupni_broj_poslovnih_vezova) {
			this.ukupni_broj_poslovnih_vezova  = ukupni_broj_poslovnih_vezova;
			return this;
		}
		public OsobineLukaBuilder ukupni_broj_ostalih_vezova (final int ukupni_broj_ostalih_vezova) {
			this.ukupni_broj_ostalih_vezova  = ukupni_broj_ostalih_vezova;
			return this;
		}
		public OsobineLuka build() {
			return new OsobineLuka(this);
		}
	}
}
