package mdurasek_zadaca_3.builder;

import mdurasek_zadaca_3.observer.Observer;

public class Brod implements Observer{
	
	private final int id;
	private final String oznaka_broda;
	private final String naziv;
	private final KarakteristikeBroda karakteristikeBroda;
	private final KapacitetBroda kapacitetBroda;
	
	
	
	public Brod(BrodBuilder brodBuilder) {
		this.id = brodBuilder.id;
		this.oznaka_broda = brodBuilder.oznaka_broda;
		this.naziv = brodBuilder.naziv;
		this.karakteristikeBroda = brodBuilder.karakteristikeBroda;
		this.kapacitetBroda = brodBuilder.kapacitetBroda;
		
	}
	
	public int getId() {
		return id;
	}
	public String getOznaka_broda() {
		return oznaka_broda;
	}
	public String getNaziv() {
		return naziv;
	}
	public KarakteristikeBroda getkarakteristikeBroda() {
		return karakteristikeBroda;
	}
	
	public KapacitetBroda getKapacitetBroda() {
		return kapacitetBroda;
	}

	public static class BrodBuilder{
		private int id;
		private String oznaka_broda;
		private String naziv;
		private KarakteristikeBroda karakteristikeBroda;
		private KapacitetBroda kapacitetBroda;
		
		public BrodBuilder id (final int id) {
			this.id = id;
			return this;
		}
		
		public BrodBuilder oznaka_broda (final String oznaka_broda) {
			this.oznaka_broda = oznaka_broda;
			return this;
		}
		
		public BrodBuilder naziv (final String naziv) {
			this.naziv = naziv;
			return this;
		}
		public BrodBuilder karakteristikeBroda (final KarakteristikeBroda karakteristikeBroda) {
			this.karakteristikeBroda = karakteristikeBroda;
			return this;
		}
		public BrodBuilder kapacitetBroda (final KapacitetBroda kapacitetBroda) {
			this.kapacitetBroda = kapacitetBroda;
			return this;
		}
		
		public Brod build() {
			return new Brod(this);
		}
	}

	@Override
	public void update(String poruka) {
		System.out.println("Brod " + this.id + " je primio poruku - " + poruka);
	}
}
