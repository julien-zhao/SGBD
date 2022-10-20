import java.nio.Buffer;
import java.nio.ByteBuffer;

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
		Personne.addColonne("ZHAO", "VARCHAR(10)");
		Personne.addColonne("Julien", "VARCHAR(12)");
		Personne.addColonne("21", "INTEGER");
		Personne.addColonne("1.6", "REAL");
		System.out.println("Nom de la relation : "+Personne.getNomRelation()+"\n");
		System.out.println("Affiche toute les relations de Personne: "+Personne.afficheRelationInfo());
		System.out.println("***********************************************************");
		
		
		
		
		
		System.out.println("\n\n\nTest Catalog");
		System.out.println("***********************************************************");
		Catalog uneCatalog = new Catalog();
		RelationInfo Etudiant = new RelationInfo("Etudiant");
		Etudiant.addColonne("ZHAO", "VARCHAR(10)");
		Etudiant.addColonne("Julien", "VARCHAR(12)");
		Etudiant.addColonne("22000018", "INTEGER");
		Etudiant.addColonne("16.0", "REAL");
		uneCatalog.addRelationInfo(Personne);
		uneCatalog.addRelationInfo(Etudiant);
		
		System.out.println("Nombre de relation dans la catalog : "+ uneCatalog.getNbRelation()+"\n");
		System.out.println("Obtenir la relation Personne : "+ uneCatalog.getRelationInfo(Personne.getNomRelation())+"\n");
		System.out.println("Obtenir la relation Etudiant : "+ uneCatalog.getRelationInfo(Etudiant.getNomRelation()));
		System.out.println("***********************************************************");
		
		
		
		
		
		System.out.println("\n\n\nTest Record");
		System.out.println("***********************************************************");
		Record unRecord = new Record(Etudiant);
		System.out.println("Le record de la relation Etudiant : "+unRecord.getValues());
		System.out.println("***********************************************************");
		
		
		
		
		
		
		System.out.println("\n\n\nTest lecture et ecriture d'un record dans un buffer");
		System.out.println("***********************************************************");
		System.out.println("Nous allons ecrire le record suivant dans un buff ");
		System.out.println((unRecord.getValues().toString() +"\n"));
		
		//capacite d'un buff
		int capacite = unRecord.getValues().size();
        ByteBuffer unBuffer = ByteBuffer.allocate(capacite*10);
        
        unBuffer.limit(capacite*10);
        
        //System.out.println("La capacité du record Etudiant : " + unBuffer.capacity());

        System.out.println("Voici une lecture de buff à partir de la position 0 : ");
        unRecord.writeToBuffer(unBuffer, 0);
        unRecord.readFromBuffer2(unBuffer, 0);
        
        System.out.println("\n\nVoici une lecture de buff à partir de la position 2: ");
        unRecord.readFromBuffer2(unBuffer, 2);
        
        
        
        System.out.println("\n\n\nMaintenant nous allons ecrire un nouveau record pour voir si le dernier record est ecrasé : ");
        
        
        
        
        Record unAutreRecord = new Record(Personne);
        System.out.println("\n\n\nVoici le record dont nous allons convertir en byte et effectuer une lecture");
        System.out.println(unAutreRecord.getValues().toString() +"\n");
        
        System.out.println("Voici une lecture de buff à partir de la position 0 : ");
        unAutreRecord.writeToBuffer(unBuffer, 0);
        unAutreRecord.readFromBuffer2(unBuffer, 0);
        
        System.out.println("\n\nVoici une lecture de buff à partir de la position 2: ");
        unAutreRecord.readFromBuffer2(unBuffer, 2);
		
		System.out.println("\n***********************************************************");
	}
}
