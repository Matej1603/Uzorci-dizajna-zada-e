package mdurasek_zadaca_3.visitor;

import mdurasek_zadaca_3.prototype.*;

public interface Visitor {
	public String visitPutnickiVez(PutnickiVez pu,String datum, String dan);
	public String visitPoslovniVez(PoslovniVez po,String datum, String dan);
	public String visitOstaliVez(OstaliVez ov,String datum, String dan);
}
