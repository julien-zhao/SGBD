
public class CatalogTest {

	public static void main(String[] args) {
		DBParams.DBPath = ".//..//..//DB//";
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		
		RelationInfo relaInfo = new RelationInfo("testPerso");
		Catalog catalog = new Catalog();
		
		relaInfo.addColonne("Froid","VARCHAR(10)");
		relaInfo.addColonne("Paul", "VARCHAR(12");
		relaInfo.addColonne("123456", "INTEGER");
		relaInfo.addColonne("10.0", "REAL");
		
		System.out.println("Initialisation de la catalog");
		catalog.Init2();
		// ajout de la relation relaInfo dans catalog
		catalog.addRelationInfo(relaInfo);
		// fin Init()
		System.out.println("sauvegarde catalog dans le fichier catalog.sv");
		catalog.Finish();
	}

}
