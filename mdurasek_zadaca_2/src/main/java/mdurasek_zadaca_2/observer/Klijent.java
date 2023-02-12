package mdurasek_zadaca_2.observer;

import mdurasek_zadaca_2.builder.Brod;

public interface Klijent {
	
	public void obavijesti(Brod brod,String poruka);
	public void spoji(Brod brod);
	public void odspoji(Brod brod);
	
}
