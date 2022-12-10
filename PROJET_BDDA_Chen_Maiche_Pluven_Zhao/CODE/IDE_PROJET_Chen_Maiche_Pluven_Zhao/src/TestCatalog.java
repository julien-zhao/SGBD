import java.io.IOException;

public class TestCatalog {

	public static void main(String[] args) throws IOException, ClassNotFoundException  {
		DBParams.DBPath = ".//..//..//DB//";
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DBParams.frameCount = 2;
		
		
		// Pour tester la méthode finish(), il faut cacher la méthode init()
		/*
		RelationInfo relaInfo;
		RelationInfo relaInfo2;
		
		relaInfo = new RelationInfo("RelationNonVide");
		relaInfo2 = new RelationInfo("RelationQuiEstVide");
			
		
		
		relaInfo.addColonne("Nom","VARCHAR(10)");
		relaInfo.addColonne("Prenom", "VARCHAR(12)");
		relaInfo.addColonne("Age", "INTEGER");
		relaInfo.addColonne("NoteEX", "REAL");
			
		System.out.println("****************************************************************");
		System.out.println("Le nom de la relation info : "+relaInfo.getNomRelation());
		System.out.println("*********************************************");		
			
		System.out.println("Initialisation de la catalog");
			
		// ajout de la relation relaInfo dans catalog
		System.out.println("Nous allons enregistré la relation info suivante dans le fichier catalog.sv");
		Catalog.getSingleton().addRelationInfo(relaInfo);
		Catalog.getSingleton().addRelationInfo(relaInfo2);
			
		System.out.println(Catalog.getSingleton().getCatalog());
		Catalog.getSingleton().Finish();
		*/
		
		
		
		
		
		// Pour tester la méthode init(), il faut exécuter au moins une fois avec finish() et ensuite cacher la méthode finish()
		///*
		Catalog.getSingleton().Init();
		
		System.out.println("*********************************************************************");
		System.out.println("Le nombre de nombre de relation dans catalog : "+Catalog.getSingleton().getNbRelation());
		System.out.println("************************************************************************\n");
		
		System.out.println("** Test si le nom de relInfo,'RelationQuiEstVide' se trouve bien dans catalog  **");
		System.out.println("retourne la relation nommée 'RelationQuiEstVide' si il existe dans catalog");
		if(Catalog.getSingleton().getRelationInfo("RelationQuiEstVide") != null) {
			System.out.println("'RelationQuiEstVide' existe bien dans le catalog");
			System.out.println("Résultat de la recherche : \n\n"+ Catalog.getSingleton().getRelationInfo("RelationQuiEstVide"));
		}
		
		System.out.println("\n**************************************************************");
		System.out.println("** Test si le nom de relInfo,'RelationNonVide' se trouve bien dans catalog  **");
		System.out.println("retourne la relation nommée 'RelationNonVide' si il existe dans catalog");
		if(Catalog.getSingleton().getRelationInfo("RelationNonVide") != null) {
			System.out.println("'RelationNonVide' existe bien dans le catalog");
			System.out.println("Résultat de la recherche : \n\n"+ Catalog.getSingleton().getRelationInfo("RelationNonVide"));
		}
		
		System.out.println("\n**************************************************************");
		System.out.println("** Test si le nom de relInfo,'RelationQuiExistePas' se trouve bien dans catalog  **");
		System.out.println("retourne la relation nommée 'RelationQuiExistePas' si il existe dans catalog");
		if(Catalog.getSingleton().getRelationInfo("RelationQuiExistePas") != null) {
			System.out.println("'RelationQuiExistePas' existe bien dans le catalog");
			System.out.println("Résultat de la recherche : \n\n"+ Catalog.getSingleton().getRelationInfo("RelationQuiExistePas"));
		}
		//*/
		
	}
}
