

import java.io.Serializable; 

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RelationInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * le nom de la relation
	 */
	private String nomRelation; 
	/**
	 * le header page id
	 */
	private PageId headerPageId;
	/**
	 * la liste contenant le nom et le type de la colonne
	 */
	private List<ColInfo> tabInfo;
	
	public RelationInfo(String nomRelation) throws IOException{

		this.nomRelation = nomRelation;
		this.tabInfo = new ArrayList<ColInfo>(); //Initialise tabInfo en taille nbColonnes
		
		this.headerPageId = FileManager.getSingleton().createNewHeaderPage();
		
		
	}

	public RelationInfo(String nomRelation, PageId headerPageId) {
		this.nomRelation = nomRelation;
		this.headerPageId = headerPageId;
		tabInfo = new ArrayList<ColInfo>(); //Initialise tabInfo en taille nbColonnes
	}
	


	
	public void addColonne(String nomColonne, String type) {
		ColInfo uneColInfo = new ColInfo(nomColonne, type);
		tabInfo.add(uneColInfo);
	}
	
	
	
	
	public PageId getHeaderPageId() {
		return this.headerPageId;
	}
	
	
	public List<ColInfo> getTabInfo() {
		return this.tabInfo;
	}
	

	public String getNomRelation() {
		return nomRelation;
	}
	
	public void setNomRelation(String nomRelation) {
		this.nomRelation = nomRelation;
	}

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
		StringBuilder sb = new StringBuilder(this.getNomRelation());
		for(int i =0; i<tabInfo.size(); i++) {
			sb.append("\n");
			sb.append(tabInfo.get(i).getType()+" : ");
			sb.append(tabInfo.get(i).getColonne());
		}
		return sb.toString();
	}
	
	public String toString() {
		return this.afficheRelationInfo();
	}


	public int getSize() {
		return tabInfo.size();
	}

	public int getColonneIndex(String nomColonne) {
		for(int i =0; i<tabInfo.size(); i++) {
			if(tabInfo.get(i).getColonne().equals(nomColonne)) {
				return i;
			}
		}
		return -1;
	}
}
