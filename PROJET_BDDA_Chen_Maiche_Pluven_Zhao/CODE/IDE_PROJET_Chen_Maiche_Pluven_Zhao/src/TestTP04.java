import java.nio.ByteBuffer;
import java.util.ArrayList;


public class TestTP04 {
	public static void main(String []args ) throws Exception {
		DBParams.DBPath = ".//..//..//DB//";
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DBParams.frameCount = 2;
		//DiskManager dm = DiskManager.getSingleton();
		
		//mettre une limite pour un record
		System.out.println(DBParams.DBPath);
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		

		
		System.out.println("\nTest Relation Info et ColInfo");
		System.out.println("***********************************************************");
		RelationInfo Personne = new RelationInfo("Personne");
		Personne.addColonne("NOM", "VARCHAR(10)");
		Personne.addColonne("PRENOM", "VARCHAR(12)");
		Personne.addColonne("AGE", "INTEGER");
		Personne.addColonne("TAILLE", "REAL");
	
		

		
		System.out.println("Nom de la relation : "+Personne.getNomRelation()+"\n");
		System.out.println("Affiche toute les relations de Personne: "+Personne.afficheRelationInfo());
		System.out.println("***********************************************************");
		
		
		
		
		
		System.out.println("\n\n\nTest Catalog");
		System.out.println("***********************************************************");
		Catalog uneCatalog = Catalog.getSingleton();
		RelationInfo Etudiant = new RelationInfo("Etudiant");
		Etudiant.addColonne("Nom", "VARCHAR(10)");
		Etudiant.addColonne("Prenom", "VARCHAR(12)");
		Etudiant.addColonne("Coefficient", "INTEGER");
		Etudiant.addColonne("Note", "REAL");
		uneCatalog.addRelationInfo(Personne);
		uneCatalog.addRelationInfo(Etudiant);


		
		System.out.println("Nombre de relation dans la catalog : "+ uneCatalog.getNbRelation()+"\n");
		System.out.println("Obtenir la relation Personne : "+ uneCatalog.getRelationInfo(Personne.getNomRelation())+"\n");
		System.out.println("Obtenir la relation Etudiant : "+ uneCatalog.getRelationInfo(Etudiant.getNomRelation()));
		System.out.println("***********************************************************");
		
		
		System.out.println("\n\n\nTest Record et de bufferSize");
		System.out.println("***********************************************************");
		
		Record unRecord = new Record(Personne);
		ArrayList<String> tuple1 = new ArrayList<>();
		tuple1.add("Zhao");
		tuple1.add("Julien");
		tuple1.add("21");
		tuple1.add("1.6");
		unRecord.addTuple(tuple1);
		System.out.println("INIT 1 : " + unRecord);
		System.out.println("Le bufferSize de ce record est : " + unRecord.getWrittenSize() );
		ArrayList<String> tuple2 = new ArrayList<>();
		tuple2.add("Maiche");
		tuple2.add("Max");
		tuple2.add("20");
		tuple2.add("1.75");
		Record deuxRecord = new Record(Personne);
		deuxRecord.addTuple(tuple2);
		System.out.println("INIT 2 : " + deuxRecord);
		System.out.println("Le bufferSize de ce record est : " + deuxRecord.getWrittenSize() );
		
		
		System.out.println("\n\n\nTest de writeToBuffer et readFromBuffer : ");
		System.out.println("***********************************************************");
		//capacite d'un buff
		ByteBuffer unBuffer = ByteBuffer.allocate(10000);
       
        
        
        unRecord.writeToBuffer(unBuffer, 20);
        deuxRecord.writeToBuffer(unBuffer, 500);
        
		Record readUnRecord = new Record(Personne);
		Record readDeuxRecord = new Record(Personne);
		
		readUnRecord.readFromBuffer(unBuffer, 20);
        readDeuxRecord.readFromBuffer(unBuffer, 500);
        
		System.out.println("INIT 3 : " + readUnRecord);
		System.out.println("INIT 4 : " + readDeuxRecord);
        
        System.out.println("\nTest de getWrittenSize 1: " + unRecord.getWrittenSize());
        System.out.println("\nTest de getWrittenSize 2: " + deuxRecord.getWrittenSize());
        System.out.println("\nTest de getWrittenSize 3: " + readUnRecord.getWrittenSize());
        System.out.println("\nTest de getWrittenSize 4: " + readDeuxRecord.getWrittenSize());
        

	}
}
