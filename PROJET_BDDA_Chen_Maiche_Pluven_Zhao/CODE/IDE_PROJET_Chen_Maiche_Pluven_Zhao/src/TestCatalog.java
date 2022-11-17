
public class TestCatalog {

	public static void main(String[] args) {
		DBParams.DBPath = ".//..//..//DB//";
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		
		RelationInfo relInfo1 = new RelationInfo("Personne");
		RelationInfo relaInfo = new RelationInfo("testPerso");
		
		relaInfo.addColonne("Nom","VARCHAR(10)");
		relaInfo.addColonne("Prenom", "VARCHAR(12)");
		relaInfo.addColonne("Age", "INTEGER");
		relaInfo.addColonne("NoteEX", "REAL");
		
		System.out.println("****************************************************************");
		System.out.println("Le nom de la relation info : "+relaInfo.getNomRelation());
		System.out.println("*********************************************");		
		
		System.out.println("Initialisation de la catalog");
		Catalog.getSingleton().Init2();
		// ajout de la relation relaInfo dans catalog
		Catalog.getSingleton().addRelationInfo(relaInfo);
		Catalog.getSingleton().addRelationInfo(relInfo1);
		// fin Init()
		System.out.println("sauvegarde catalog dans le fichier catalog.sv");
		Catalog.getSingleton().Finish();
		
		
		System.out.println("*********************************************************************");
		System.out.println("\nLe nombre de nombre de relation dans catalog : "+Catalog.getSingleton().getNbRelation());
		System.out.println("************************************************************************");
		System.out.println("** Test si le nom de relInfo,'Personne' se trouve bien dans catalog  **");
		System.out.println("retourne la relation nommée 'Personne' si il existe dans catalog");
		if(Catalog.getSingleton().getRelationInfo("Personne") != null) {
			System.out.println("'Personne' existe bien dans le catalog");
			System.out.println("Résultat de la recherche : \n"+ Catalog.getSingleton().getRelationInfo("Personne"));
		}
		
		System.out.println("**************************************************************");
		System.out.println("** Test si le nom de relInfo,'testPerso' se trouve bien dans catalog  **");
		System.out.println("retourne la relation nommée 'testPerso' si il existe dans catalog");
		if(Catalog.getSingleton().getRelationInfo("testPerso") != null) {
			System.out.println("'testPerso' existe bien dans le catalog");
			System.out.println("Résultat de la recherche : \n"+ Catalog.getSingleton().getRelationInfo("testPerso"));
		}
		
		System.out.println("**************************************************************");
		System.out.println("** Test si le nom de relInfo,'Etudiant' se trouve bien dans catalog  **");
		System.out.println("retourne la relation nommée 'Etudiant' si il existe dans catalog");
		if(Catalog.getSingleton().getRelationInfo("Etudiant") != null) {
			System.out.println("'Etudiant' existe bien dans le catalog");
			System.out.println("Résultat de la recherche : \n"+ Catalog.getSingleton().getRelationInfo("Etudiant"));
		}
	}
}
