import java.io.File; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Catalog implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * la liste ou table de RelationInfo
	 */
	private ArrayList<RelationInfo> RelationInfos;
	private static Catalog g_instance = new Catalog();

	private Catalog(){
		RelationInfos = new ArrayList<RelationInfo>();
	}
	
	public static Catalog getSingleton() {
		return g_instance;
	}
	
	public ArrayList<RelationInfo> getCatalog(){
		return RelationInfos;
	}

	/**
	 * Cette méthode Finish() enregistre une relation
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void Finish() throws FileNotFoundException, IOException{
		String path = DBParams.DBPath+"Catalog.sv";
			
		File f = new File(path);
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(this.RelationInfos);
		oos.close();

	}
	 
	/**
	 * cette méthode init() est exécuté quand la méthode finish() est exécuté au moins une fois
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public void Init() throws IOException, ClassNotFoundException{
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
			e.printStackTrace();
		}
		
	}
	
	
	public void addRelationInfo(RelationInfo uneRelation) {
		RelationInfos.add(uneRelation);
	}
	
	public int getNbRelation() {
		return RelationInfos.size();
	}
	
	/**
	 * cette méthode prend en argument le nom d'une relation et retourne la RelationInfo associée, 
	 * sinon retourne que la relation n'existe pas.
	 *
	 * @param nomRelation
	 * @return le nom de la relation, le nom de la colonne et le type de la colonne
	 */
	public RelationInfo getRelationInfo(String nomRelation) {
		for(RelationInfo uneRelation : RelationInfos) {
			if(uneRelation.getNomRelation().equals(nomRelation)) {
				return uneRelation; 
			}
		}
		System.out.println("La relation '"+nomRelation+"' n'existe pas");
		return null; 
	}
	
	public void reset() {
		RelationInfos.clear();
	}
}
