
public class TestTP04 {
	public static void main(String []args ) throws Exception {
		DBParams.DBPath = ".//..//..//DB//";
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DiskManager dm = DiskManager.getSingleton();
		System.out.println(DBParams.DBPath);
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		
		System.out.println("Test Relation Info et ColInfo");
		System.out.println("***********************************************************");
		RelationInfo info = new RelationInfo("Personne");
		info.addColonne("Nom", "VARCHAR");
		info.addColonne("Prenom", "VARCHAR");
		info.addColonne("Age", "INTEGER");
		info.addColonne("Taille", "REAL");
		System.out.println("Nom de la relation : "+info.getNomRelation());
		System.out.println("Affiche toute les relations : "+info.afficheRelationInfo());
		System.out.println("***********************************************************");
		
		
	}
}
