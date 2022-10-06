
public class Cellule {
	Cellule prev;
	Cellule suiv;	
	int caseId;
	
	public Cellule(int val) {
		this.caseId = val;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if(obj instanceof Cellule) {
	    	Cellule equalsSample = (Cellule) obj;
	        if(equalsSample.caseId == this.caseId){
	            return true;
	        }
	    }
	    return false;       
	}
}
