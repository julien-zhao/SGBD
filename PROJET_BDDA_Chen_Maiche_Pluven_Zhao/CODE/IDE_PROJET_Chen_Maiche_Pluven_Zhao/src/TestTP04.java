import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class TestTP04 {
	public static void main(String []args ) throws Exception {
		DBParams.DBPath = ".//..//..//DB//";
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DiskManager dm = DiskManager.getSingleton();
		
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
		
		
		
		
		
		System.out.println("\n\n\nTest Record");
		System.out.println("***********************************************************");

		Record unRecord = new Record(Personne);
		ArrayList<String> tuple1 = new ArrayList<>();
		tuple1.add("Zhao");
		tuple1.add("Julien");
		tuple1.add("21");
		tuple1.add("1.6");


		unRecord.addTuple(tuple1);
		
		ArrayList<String> tuple2 = new ArrayList<>();
		tuple2.add("Maiche");
		tuple2.add("Max");
		tuple2.add("20");
		tuple2.add("1.75");

		unRecord.addTuple(tuple2);		
		
		System.out.println("Voici le nom du record : " + unRecord.getRelInfo().getNomRelation());
		System.out.println("Voici les differents colonnes de ce record  : "+unRecord.getNomColonne());
		System.out.println("Voici les differents colonnes de ce record  : "+unRecord.getTypeColonne());
		System.out.println("Voici les valeurs de chaque colonne : "+ unRecord.afficheValues());
		System.out.println("***********************************************************");
		
		
		//ByteBuffer - new Byfeter()
		//record.writeToBuffer(reco)
		
		// New record sur Personne vide
		// Tu lui fais lire le buffer dans lequel tu viens d'ecrire
		
		

		
		//8 pour zhao
		//81244|709709709
		
		// 0100091801100
		//int 4 octet de val 1
		
		//8 pour zhao, 12 pour julien, 4 pour 21, 4 pour 1.6
		//81244|709709709
		
		System.out.println("\n\n\nTest lecture et ecriture d'un record dans un buffer");
		System.out.println("***********************************************************");
		System.out.println("Nous allons ecrire le record suivant dans un buff ");
		System.out.println((unRecord.afficheValues() +"\n"));
		
		//capacite d'un buff
		int capacite = unRecord.getSizePos();
        ByteBuffer unBuffer = ByteBuffer.allocate(capacite*10);
        
        unBuffer.limit(capacite*5);
        
        System.out.println("La capacité du record Etudiant : " + unBuffer.capacity());

        
        System.out.println("Voici une lecture de buff à partir de la position 0 : ");
        unRecord.writeToBuffer(unBuffer, 0);
        System.out.println("Voici le record en byte : ");
        unRecord.getBufferToByte(unBuffer, 0);
        System.out.println("\nVoici le record avec readFromBuffer : ");
        unRecord.readFromBuffer2(unBuffer, 0);
        
        System.out.println("\n\nVoici une lecture de buff à partir de la position 2: ");
        //unRecord.readFromBuffer2(unBuffer, 2);
        
        
        
        System.out.println("\nTest de getWrittenSize : " + unRecord.getWrittenSize());


	}
}
