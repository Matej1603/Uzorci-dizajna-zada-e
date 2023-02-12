package mdurasek_zadaca_3.prototype;

import mdurasek_zadaca_3.visitor.VisitElement;
import mdurasek_zadaca_3.visitor.Visitor;

public class OstaliVez extends Vez implements VisitElement{
	public OstaliVez() {

	}

	public OstaliVez(OstaliVez os) {
		super(os);
	}

	@Override
	public Vez clone() {
		return new OstaliVez(this);
	}

	@Override
	public String prihvati(Visitor visitor, String datum, String dan) {
		return visitor.visitOstaliVez(this,datum,dan);
	}
	@Override
	public void ispis() {
		System.out.println("ID: " + this.getId());
		
	}
	
}
