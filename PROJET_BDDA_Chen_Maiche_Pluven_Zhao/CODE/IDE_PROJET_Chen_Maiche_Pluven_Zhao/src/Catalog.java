import java.io.File; 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Catalog implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<RelationInfo> RelationInfos;
	//private int nbRelation; // le compteur
	private static Catalog g_instance = new Catalog();

	private Catalog(){
		//nbRelation = 0;
		RelationInfos = new ArrayList<RelationInfo>();
	}
	
	public static Catalog getSingleton() {
		return g_instance;
	}

	public ArrayList<RelationInfo> getCatalog(){
		return RelationInfos;
	}
	
	public void Finish() /*throws IOException*/{
		try {
			String path = DBParams.DBPath+"Catalog.sv";
			
			File f = new File(path);
			FileOutputStream fos = new FileOutputStream(f);
		    ObjectOutputStream oos = new ObjectOutputStream(fos);
			//System.out.println("fichier : " + fos);
		    //oos.flush();

		    oos.writeObject(this.RelationInfos);// On doit ajouter aussi le nombre de relation info
		    //fos.close();
			oos.close();
		}
		catch(IOException e){
			System.out.println("finish non valide :"+e.getMessage());
		}
		finally {
			System.out.println("La relation est sauvegardé");
		}
		
	}
	
	
	 
	@SuppressWarnings("unchecked")
	public void Init() {
		try {
			String path = DBParams.DBPath+"Catalog.sv";
			File f = new File(path);
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			RelationInfos = (ArrayList<RelationInfo>) ois.readObject();
			
			ois.close();
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void addRelationInfo(RelationInfo uneRelation) {
		RelationInfos.add(uneRelation);
		//nbRelation++;
	}
	
	public int getNbRelation() {
		return RelationInfos.size();
	}
	
	public RelationInfo getRelationInfo(String nomRelation) {
		for(RelationInfo uneRelation : RelationInfos) {
			if(uneRelation.getNomRelation().equals(nomRelation)) {
				return uneRelation; 
			}
		}
		System.out.println("La relation '"+nomRelation+"' n'existe pas");
		return null; 
	}
	
	
}
