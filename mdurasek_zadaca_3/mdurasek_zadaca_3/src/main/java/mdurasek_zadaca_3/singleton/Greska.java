package mdurasek_zadaca_3.singleton;

public class Greska {
	
	private static Greska greska = new Greska();
    public int brojac=0;
    
    private Greska(){}
     
    public static Greska getInstanca()
    {
        if (greska == null)
            greska = new Greska();
        return greska;
    }
     
    public void setGreska()
    {
        this.brojac++;
    }

}
