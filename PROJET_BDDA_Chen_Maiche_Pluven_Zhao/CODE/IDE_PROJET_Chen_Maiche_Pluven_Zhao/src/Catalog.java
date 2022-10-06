import java.util.ArrayList;

public class Catalog {
	private ArrayList<RelationInfo> RelationInfos;
	int nbRelation;
	
	public Catalog(){
		RelationInfos = new ArrayList<RelationInfo>();
	}
	
	public void Init() {
		
	}
	
	public void Finish() {
		
	}
	
	public void addRelationInfo(RelationInfo uneRelation) {
		RelationInfos.add(uneRelation);
		nbRelation++;
	}
	
	public int getNbRelation() {
		return nbRelation;
	}
	
	public RelationInfo getRelationInfo(String nomRelation) {
		for(RelationInfo uneRelation : RelationInfos) {
			if(uneRelation.getNomRelation().equals(nomRelation)) {
				return uneRelation; 
			}
		}
		System.out.println("La relation n'existe pas");
		return null; 
	}
	
	
}
