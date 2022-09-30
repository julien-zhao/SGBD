
public class File {
	Cellule debut;
	Cellule fin;
	
	public File() {
		debut = new Cellule(-1);
		fin = new Cellule(-2);
		debut.suiv = fin;
		fin.prev = debut;
	}
	
	public void add(int val) {
		Cellule c = new Cellule(val); 
		fin.prev.suiv = c;
		c.prev = fin.prev;
		fin.prev = c;
		c.suiv = fin;
	}
	
	public Cellule pop() {
		Cellule c = debut.suiv;
		debut.suiv = c.suiv;
		c.suiv.prev = debut;
		return c;
	}
	
	public void del(int val) {

		Cellule c = debut;
		while(c.caseId!=val) {
			c=c.suiv;
		}
		
		c.prev.suiv=c.suiv;
		c.suiv.prev=c.prev;
	}
}
