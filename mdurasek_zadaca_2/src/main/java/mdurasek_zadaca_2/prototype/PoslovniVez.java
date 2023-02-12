package mdurasek_zadaca_2.prototype;

import mdurasek_zadaca_2.visitor.VisitElement;
import mdurasek_zadaca_2.visitor.Visitor;

public class PoslovniVez extends Vez implements VisitElement{
	public PoslovniVez() {

	}

	public PoslovniVez(PoslovniVez po) {
		super(po);
	}

	@Override
	public Vez clone() {
		return new PoslovniVez(this);
	}

	@Override
	public String prihvati(Visitor visitor,String datum, String dan) {
		return visitor.visitPoslovniVez(this, datum,dan);
		
	}
	
}
