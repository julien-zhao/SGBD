import java.io.IOException;
import java.nio.ByteBuffer;

public class TestDiskManager {
	public static void main(String []args ) throws IOException {
		DBParams.DBPath = ".//..//..//DB//";
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DiskManager dm = DiskManager.getSingleton();
		System.out.println(DBParams.DBPath);
		System.out.println("Working Directory = " + System.getProperty("user.dir"));

		
		/**
		 * 
		 * Test de la methode DeallocPage(pageId) 
		 * 
		 * params: une page 
		 * 
		 * return: type void -> désalloue une page
		 */
		System.out.println("=============================");
		System.out.println("Test DeallocPage : "); 
		PageId p1= dm.allocPage();
		dm.deallocPage(p1);
		System.out.println("Fin de Test DeallocPage : ");
		System.out.println("=============================");
		

		/*
		 * Test de l'écriture de page
		 * p1 l'identifiant de page et bf1 le buffer
		 * 
		 * Ici on a cette méthode writepage qui écrit le contenu de 
		 * l'argument bf1 dans le fichier et à une position indiqués 
		 * par l'argument p1
		 */
		System.out.println("\nTest de WritePage");
		ByteBuffer bf1 =  ByteBuffer.allocate(1000);
		//bf1.wr("Coucou");	
		//dm.writePage(p1, bf1);
		//System.out.println("\nFin test de WritePage");
		
		/*
		 * Test de lecture de page
		 * p1 l'identifiant de page et bf2 le buffer
		 * 
		 * Ici on remplit l'argument bf2 avec le contenu du disque de 
		 * la page identifiée par p1. Il s'agit ici d'une page qui
		 * existe déjà
		 */
		/*
		System.out.println("\nTest de readPage");
		ByteBuffer bf2 = ByteBuffer.allocate(1000);
		dm.readPage(p1, bf2);
		System.out.println("\nFin test de readPage");
		
		// on compare si bf2 est égale à l'argument "Coucou"
		System.out.println("\nTest de comparaion");
		System.out.println(bf2.equals("Coucou"));
		System.out.println("\nFin test de comparaison");
		*/
		
		
		
		/**
		 * Test de getCurrentCountAllocPages
		 * 
		 * return: le nombre courant de page allouée
		 */
		System.out.println("Test getCurrentCountAllocPages : "); 
		System.out.println("Le nombre de page allouee est :" + dm.getCurrentCountAllocPages());
		
		/*
		PageId p2= dm.allocPage();
		PageId p3= dm.allocPage();
		PageId p4= dm.allocPage();
		PageId p5= dm.allocPage();
		PageId p6= dm.allocPage();
		PageId p7= dm.allocPage();
		PageId p8= dm.allocPage();
		*/

		dm.deallocPage(new PageId(0,3));
		
		dm.saveLog();
		System.out.println("Log : "+dm.getLog());

		System.out.println("Le nombre de page allouee la fin est :" + dm.getCurrentCountAllocPages());


		System.out.println("Fin de test getCurrentCountAllocPages : "); 
	}
}
