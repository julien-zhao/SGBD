
public class CatalogTest {

	public static void main(String[] args) {
		DBParams.DBPath = ".//..//..//DB//";
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		
		RelationInfo relaInfo = new RelationInfo("testPerso");
		
		relaInfo.addColonne("Froid","VARCHAR(10)");
		relaInfo.addColonne("Paul", "VARCHAR(12)");
		relaInfo.addColonne("123456", "INTEGER");
		relaInfo.addColonne("10.0", "REAL");
		
		System.out.println("Initialisation de la catalog");
		Catalog.getSingleton().Init2();
		// ajout de la relation relaInfo dans catalog
		Catalog.getSingleton().addRelationInfo(relaInfo);
		// fin Init()
		System.out.println("sauvegarde catalog dans le fichier catalog.sv");
		Catalog.getSingleton().Finish();
		
		System.out.println("\n\n** Test si la relInfo se trouve bien dans catalog **");
		System.out.println("\nLe nombre de nombre de relation dans catalog : "+Catalog.getSingleton().getNbRelation());
		System.out.println("retourne la relation nommée 'testPerso' si il existe dans catalog");
		if(Catalog.getSingleton().getRelationInfo("testPerso") == null)
			System.out.println("'testPerso' n'existe pas dans le catalog");
		else {
			System.out.println("'testPerso' existe bien dans le catalog");
			System.out.println("Résultat de la recherche : \n"+ Catalog.getSingleton().getRelationInfo("testPerso"));
		}
	}

}
