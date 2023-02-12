package mdurasek_zadaca_3.singleton;

public class OpcionalniCsv {
	private static OpcionalniCsv opcionalniCsv = new OpcionalniCsv();
	public Boolean postoji = false;
	private OpcionalniCsv() {
		
	}
	 public static OpcionalniCsv getInstanca()
	    {
	        if (opcionalniCsv == null)
	        	opcionalniCsv= new OpcionalniCsv();
	        return opcionalniCsv;
	    }
	     
	    public void setPostoji()
	    {
	        this.postoji = true;
	    }
}
