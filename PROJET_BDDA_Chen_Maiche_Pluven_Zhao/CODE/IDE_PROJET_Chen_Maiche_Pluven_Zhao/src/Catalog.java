import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Catalog {
	private ArrayList<RelationInfo> RelationInfos;
	private int nbRelation; // le compteur
	
	public Catalog(){
		RelationInfos = new ArrayList<RelationInfo>();
	}
	
	public ArrayList<RelationInfo> getCatalog(){
		return RelationInfos;
	}
	
	public void Finish() throws IOException{
		FileOutputStream fos = new FileOutputStream(DBParams.DBPath+"Catalog.sv");
	    ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.flush();
	    oos.writeObject(RelationInfos);
		    
	    fos.close();
		oos.close();		
	}
	
	@SuppressWarnings("unchecked")
	public void Init() throws IOException{
		FileInputStream fis = null;
		fis = new FileInputStream(DBParams.DBPath+"Catalog.sv");
			
		ObjectInputStream ois = null;
		ois = new ObjectInputStream(fis);

		try {
			RelationInfos = (ArrayList<RelationInfo>)ois.readObject();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fis.close();
		ois.close();
	}
	
	
	
	public void addRelationInfo(RelationInfo uneRelation) {
		RelationInfos.add(uneRelation);
		nbRelation++;
	}
	
	public int getNbRelation() {
		return nbRelation;
	}
	
	public String getRelationInfo(String nomRelation) {
		for(RelationInfo uneRelation : RelationInfos) {
			if(uneRelation.getNomRelation().equals(nomRelation)) {
				return uneRelation.afficheRelationInfo(); 
			}
		}
		System.out.println("La relation n'existe pas");
		return null; 
	}
	
	
}
