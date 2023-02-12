package mdurasek_zadaca_3.prototype;

import mdurasek_zadaca_3.visitor.VisitElement;
import mdurasek_zadaca_3.visitor.Visitor;

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
	@Override
	public void ispis() {
		System.out.println("ID: " + this.getId());
	}
	
}
