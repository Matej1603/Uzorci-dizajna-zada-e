package mdurasek_zadaca_3.singleton;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class VirtualnoVrijeme {
	private static VirtualnoVrijeme virutalnoVrijeme = new VirtualnoVrijeme();
    public LocalDateTime datum;
    public boolean postoji = false;
    
    private VirtualnoVrijeme(){}
     
    public static VirtualnoVrijeme getInstanca()
    {
        if (virutalnoVrijeme == null)
        	virutalnoVrijeme = new VirtualnoVrijeme();
        return virutalnoVrijeme;
    }
    public int danUTjednu() {
    	return DayOfWeek.valueOf(this.datum.getDayOfWeek().toString()).getValue() - 1;
    	
    }
    public int dajMinuteTrenutnogDana() {
    	return this.datum.getHour() * 60 + this.datum.getMinute();
    	
    }
    public void setVrijeme(LocalDateTime datum){
        this.datum = datum;
    }
    public void setPostoji(Boolean postoji) {
    	this.postoji = postoji;
    }
}
