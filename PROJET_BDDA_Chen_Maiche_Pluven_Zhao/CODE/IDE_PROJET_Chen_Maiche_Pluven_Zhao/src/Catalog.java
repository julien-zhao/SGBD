import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Catalog {
	private ArrayList<RelationInfo> RelationInfos;
	private int nbRelation; // le compteur
	private static Catalog g_instance = new Catalog();

	private Catalog(){
		nbRelation = 0;
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
		    oos.writeObject(RelationInfos);
		    //fos.close();
			oos.close();
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
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
	
	// voir si le code Init2() marche 
	@SuppressWarnings("unchecked")
	public void Init2() {
		try {
			String path = DBParams.DBPath+"Catalog.sv";
			File f = new File(path);
			FileInputStream FIS = new FileInputStream(f);
			ObjectInputStream OIS = new ObjectInputStream(FIS);
			
			RelationInfos = (ArrayList<RelationInfo>) OIS.readObject();
			
			OIS.close();
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// à tester
	
	
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
