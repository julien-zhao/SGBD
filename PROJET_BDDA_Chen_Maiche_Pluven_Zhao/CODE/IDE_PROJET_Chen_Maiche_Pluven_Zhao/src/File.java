
public class File {
	Cellule debut;
	Cellule fin;
	
	public File() {
		debut = new Cellule(-1);
		fin = new Cellule(-2);
		debut.suiv = fin;
		fin.prev = debut;
	}
	
	public Cellule add(int val) {
		Cellule c = new Cellule(val); 
		fin.prev.suiv = c;
		c.prev = fin.prev;
		fin.prev = c;
		c.suiv = fin;
		return c;
	}
	
	public int pop() {
		Cellule c = debut.suiv;
		debut.suiv = c.suiv;
		c.suiv.prev = debut;
		 int cId = c.caseId;
		 c = null;
		return cId;
	}
	
	public void del(Cellule c) {
		
		c.prev.suiv=c.suiv;
		c.suiv.prev=c.prev;
		c = null;
	}
	
	public boolean isVoid() {
		if(debut.suiv==fin && fin.prev == debut) {
			return true;
		}
		return false;
	}
}
