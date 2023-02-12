package mdurasek_zadaca_2.prototype;

import mdurasek_zadaca_2.visitor.VisitElement;
import mdurasek_zadaca_2.visitor.Visitor;

public class PutnickiVez extends Vez implements VisitElement{
	public PutnickiVez() {

	}

	public PutnickiVez(PutnickiVez pv) {
		super(pv);
	}

	@Override
	public Vez clone() {
		return new PutnickiVez(this);
	}

	@Override
	public String prihvati(Visitor visitor,String datum, String dan) {
		return visitor.visitPutnickiVez(this,datum,dan);
		
	}

}
