
public class RelationInfo {
<<<<<<< Updated upstream
	private String nomRelation; // le nom de la relation
	private int nbColonnes;		// le nombre de colonnes
	//private String[] nomColonnes;	// les noms des colonnes
	//private int[] typesColonnes;	// les types des colonnes
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
	
	public String toString() {
		return "Le nomRelation: "+this.getNomRelation()+"\nNombre de Colonnes:"+this.getNbColonnes();
	}
=======
	String relation;
	int col;
	String colName;
>>>>>>> Stashed changes
}
