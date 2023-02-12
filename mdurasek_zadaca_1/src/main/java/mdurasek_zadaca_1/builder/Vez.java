package mdurasek_zadaca_1.builder;

public class Vez {
	private final int id;
	private final String oznaka_veza;
	private final String vrsta;
	private final OsobineVeza osobineVeza;
	
	private Vez(VezBuilder vezBuilder) {
		this.id = vezBuilder.id;
		this.oznaka_veza = vezBuilder.oznaka_veza;
		this.vrsta = vezBuilder.vrsta;
		this.osobineVeza = vezBuilder.osobineVeza;
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
	public OsobineVeza getOsobineVeza() {
		return osobineVeza;
	}

	public static class VezBuilder {
		private int id;
		private String oznaka_veza;
		private String vrsta;
		private OsobineVeza osobineVeza;
		
		public VezBuilder id (final int id) {
			this.id = id;
			return this;
		}
		public VezBuilder oznaka_veza (final String oznaka_veza) {
			this.oznaka_veza = oznaka_veza;
			return this;
		}
		public VezBuilder vrsta (final String vrsta) {
			this.vrsta = vrsta;
			return this;
		}
		public VezBuilder osobineVeza (final OsobineVeza osobineVeza) {
			this.osobineVeza = osobineVeza;
			return this;
		}
		public Vez build() {
			return new Vez(this);
		}
	}
}
