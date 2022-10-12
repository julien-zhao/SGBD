import java.util.ArrayList;

public class RelationInfo {

	private String nomRelation; // le nom de la relation
	private int nbColonnes;		// le nombre de colonnes
	private ArrayList <String> nomColonnes;	// les noms des colonnes
	private ArrayList<Object> typesColonnes;	// les types des colonnes
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
	
	
	public RelationInfo(String nomRelation,int nbColonnes) {
		this.nomRelation = nomRelation;
		this.nbColonnes = nbColonnes;
		nomColonnes = new ArrayList<String>();
		typesColonnes = new ArrayList<Object>();
	}
	
	public RelationInfo() {
		this("",0);
	}
	
	public String getNomRelation() {
		return nomRelation;
	}
	
	public void setNomRelation(String nomRelation) {
		this.nomRelation = nomRelation;
	}
	
	public int getNbColonnes(){
		return nbColonnes;
	}
	
	public void setNbColonnes(int nbColonnes) {
		this.nbColonnes = nbColonnes;
	}
	
	public String afficheNomColonne() {
		StringBuilder sb = new StringBuilder();
		for(int i =0; i<nbColonnes; i++) {
			sb.append("Nom "+i+" :");
			sb.append(nomColonnes.get(i));
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public String afficheTypesColonne() {
		StringBuilder sb = new StringBuilder();
		for(int i =0; i<nbColonnes; i++) {
			sb.append("Type "+i+" :");
			sb.append(typesColonnes.get(i));
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public String toString() {
		return "Le nomRelation: "+this.getNomRelation()+"\nNombre de Colonnes:"+this.getNbColonnes()
		+"Nom Colonne"+ afficheNomColonne() +"\n" + "Type colonne"+ afficheTypesColonne();
	}

}
