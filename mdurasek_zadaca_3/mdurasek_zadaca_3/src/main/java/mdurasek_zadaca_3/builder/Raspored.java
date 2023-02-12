package mdurasek_zadaca_3.builder;


public class Raspored {
	
	private final int id_vez;
	private final int id_brod;
	private final RasporedTermin rasporedTermin;
	
	private Raspored(RasporedBuilder rasporedBuilder) {
		this.id_vez = rasporedBuilder.id_vez;
		this.id_brod = rasporedBuilder.id_brod;
		this.rasporedTermin = rasporedBuilder.rasporedTermin;
	}
	
	public int getId_vez() {
		return id_vez;
	}
	public int getId_brod() {
		return id_brod;
	}
	public RasporedTermin getRasporedTermin() {
		return rasporedTermin;
	}

	public static class RasporedBuilder{
		private int id_vez;
		private int id_brod;
		private RasporedTermin rasporedTermin;
		
		public RasporedBuilder id_vez  (final int id_vez ) {
			this.id_vez  = id_vez ;
			return this;
		}
		public RasporedBuilder id_brod  (final int id_brod ) {
			this.id_brod  = id_brod;
			return this;
		}
		public RasporedBuilder rasporedTermin (final RasporedTermin rasporedTermin ) {
			this.rasporedTermin  = rasporedTermin ;
			return this;
		}
		public Raspored build() {
			return new Raspored(this);
		}
	}
}
