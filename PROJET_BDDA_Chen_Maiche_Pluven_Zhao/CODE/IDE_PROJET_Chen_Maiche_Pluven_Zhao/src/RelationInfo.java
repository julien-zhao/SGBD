import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class RelationInfo {
	private String nomRelation; // le nom de la relation
	private PageId headerPageId;
	//private int nbColonnes;		// le nombre de colonnes
	private List<ColInfo> tabInfo; //Une liste qui contient le nom et le type
	
	
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
	
	
	
	
	public PageId getHeaderPageId() {
		return headerPageId;
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
