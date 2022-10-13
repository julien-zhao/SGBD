
public class TestTP04 {
	public static void main(String []args ) throws Exception {
		DBParams.DBPath = ".//..//..//DB//";
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DiskManager dm = DiskManager.getSingleton();
		System.out.println(DBParams.DBPath);
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		
		System.out.println("\nTest Relation Info et ColInfo");
		System.out.println("***********************************************************");
		RelationInfo Personne = new RelationInfo("Personne");
		Personne.addColonne("Nom", "VARCHAR(10)");
		Personne.addColonne("Prenom", "VARCHAR(12)");
		Personne.addColonne("Age", "INTEGER");
		Personne.addColonne("Taille", "REAL");
		System.out.println("Nom de la relation : "+Personne.getNomRelation()+"\n");
		System.out.println("Affiche toute les relations de Personne: "+Personne.afficheRelationInfo());
		System.out.println("***********************************************************");
		
		
		System.out.println("\n\n\nTest Catalog");
		System.out.println("***********************************************************");
		Catalog uneCatalog = new Catalog();
		RelationInfo Etudiant = new RelationInfo("Etudiant");
		Etudiant.addColonne("Nom", "VARCHAR(10)");
		Etudiant.addColonne("Prenom", "VARCHAR(12)");
		Etudiant.addColonne("Numero_etu", "INTEGER");
		Etudiant.addColonne("Note", "REAL");
		uneCatalog.addRelationInfo(Personne);
		uneCatalog.addRelationInfo(Etudiant);
		
		System.out.println("Nombre de relation dans la catalog : "+ uneCatalog.getNbRelation()+"\n");
		System.out.println("Obtenir la relation Personne : "+ uneCatalog.getRelationInfo(Personne.getNomRelation())+"\n");
		System.out.println("Obtenir la relation Etudiant : "+ uneCatalog.getRelationInfo(Etudiant.getNomRelation()));
		System.out.println("***********************************************************");
		
	}
}
