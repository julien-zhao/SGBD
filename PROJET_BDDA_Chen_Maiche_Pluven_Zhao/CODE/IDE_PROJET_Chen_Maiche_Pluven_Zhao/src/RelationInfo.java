import java.util.ArrayList;
import java.util.List;

public class RelationInfo {
	private String nomRelation; // le nom de la relation
	//private int nbColonnes;		// le nombre de colonnes
	private List<ColInfo> tabInfo; //Une liste qui contient le nom et le type
	/*
	 *  Stocker les noms des colonnes en tant que liste ou tableau
	 *  de caractères, et faire de même pour les types des colonnes
	 *
	 *
	 * il y a trois types de colonnes possibles dans ce projet:
	 * 	- INTEGER correspond à un type entier sur 4 octets
	 *  - REAL correspond à un type float sur 4 octets
	 *  - VARCHAR(T) correspond à une chaîne de caractères de 
	 *  taille maximale (nombre maximal de caractères) T
	 */
	
	
	//relInfo = new RelationInfo(null, 0);
	//values = new ArrayList();
	
	
	
	public RelationInfo(String nomRelation) {
		this.nomRelation = nomRelation;
		tabInfo = new ArrayList<ColInfo>(); //Initialise tabInfo en taille nbColonnes
	}
	
	public RelationInfo() {
		this("");
	}
	
	public void addColonne(String nomColonne, String type) {
		ColInfo uneColInfo = new ColInfo(nomColonne, type);
		tabInfo.add(uneColInfo);
	}
	
	public List<ColInfo> getTabInfo() {
		return tabInfo;
	}
	

	public String getNomRelation() {
		return nomRelation;
	}
	
	public void setNomRelation(String nomRelation) {
		this.nomRelation = nomRelation;
	}
	/*
	public int getNbColonnes(){
		return nbColonnes;
	}
	
	public void setNbColonnes(int nbColonnes) {
		this.nbColonnes = nbColonnes;
	}
	*/
	public String afficheNomColonne() {
		StringBuilder sb = new StringBuilder();
		for(int i =0; i<tabInfo.size(); i++) {
			sb.append("\n");
			sb.append(tabInfo.get(i).getColonne());
		}
		return sb.toString();
	}

	
	public String afficheTypesColonne() {
		StringBuilder sb = new StringBuilder();
		for(int i =0; i<tabInfo.size(); i++) {
			sb.append("\n");
			sb.append(tabInfo.get(i).getType());
		}
		return sb.toString();
	}
	
	public String afficheRelationInfo() {
		StringBuilder sb = new StringBuilder();
		for(int i =0; i<tabInfo.size(); i++) {
			sb.append("\n");
			sb.append(tabInfo.get(i).getType()+" : ");
			sb.append(tabInfo.get(i).getColonne());
		}
		return sb.toString();
	}
	
	public String toString() {
		return "Le nomRelation: "+this.getNomRelation()+"\nNombre de Colonnes:"+tabInfo.size()
		+"Nom Colonne"+ afficheNomColonne() +"\n" + "Type colonne"+ afficheTypesColonne();
	}


	
	
	/*
	public String getType_col(int i) {
		return typesColonnes.get(i).toString();
	}*/
	

}
