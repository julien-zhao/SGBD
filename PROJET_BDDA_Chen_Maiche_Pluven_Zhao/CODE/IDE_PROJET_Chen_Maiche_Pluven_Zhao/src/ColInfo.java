import java.io.Serializable;

public class ColInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String colonne;
	private String type;
	
	public ColInfo(String colonne,String type)  {
		this.colonne = colonne;
		this.type = type;
	}
	public String getColonne(){
		return colonne;
	}
	public String getType() {
		return type;
	}
	
	public String toString() {
		return "( "+this.getColonne()+","+this.type+")";
	}
}
