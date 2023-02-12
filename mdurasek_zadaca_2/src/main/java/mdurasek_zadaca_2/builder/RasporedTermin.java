package mdurasek_zadaca_2.builder;


public class RasporedTermin {
	private final String dani_u_tjednu;
	private final String vrijeme_od;
	private final String vrijeme_do;
	
	
	private RasporedTermin(TerminBuilder terminBuilder) {
		this.dani_u_tjednu = terminBuilder.dani_u_tjednu;
		this.vrijeme_od = terminBuilder.vrijeme_od;
		this.vrijeme_do = terminBuilder.vrijeme_do;
	}
	
	public String getDani_u_tjednu() {
		return dani_u_tjednu;
	}
	public String getVrijeme_od() {
		return vrijeme_od;
	}
	public String getVrijeme_do() {
		return vrijeme_do;
	}

	public static class TerminBuilder{
		private String dani_u_tjednu;
		private String vrijeme_od;
		private String vrijeme_do;
		
		public TerminBuilder dani_u_tjednu (final String dani_u_tjednu ) {
			this.dani_u_tjednu  = dani_u_tjednu ;
			return this;
		}
		
		public TerminBuilder vrijeme_od (final String vrijeme_od ) {
			this.vrijeme_od = vrijeme_od ;
			return this;
		}
		
		public TerminBuilder vrijeme_do (final String vrijeme_do) {
			this.vrijeme_do  = vrijeme_do;
			return this;
		}
		public RasporedTermin build() {
			return new RasporedTermin(this);
		}
	}
}
