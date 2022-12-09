import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;

public class TestDiskManager {
	public static void main(String []args ) throws Exception {
		DBParams.DBPath = "PROJET_BDDA_Chen_Maiche_Pluven_Zhao/DB/"; //".//..//..//DB//"
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DBParams.frameCount = 2;
		DiskManager dm = DiskManager.getSingleton();
		System.out.println(DBParams.DBPath);
		System.out.println("Working Directory = " + System.getProperty("user.dir"));


		

		/**
		 * 
		 * Test de la methode AllocPage() 
		 * 
		 * params: aucun 
		 * 
		 * return: type PageId -> alloue une page
		 */

		 dm.reset();

		System.out.println("\n===============================");
		System.out.println("Test AllocPage : ");
		PageId p1= dm.allocPage();
		System.out.println("p1 = " + p1);
		PageId p2= dm.allocPage();
		System.out.println("p2 = " + p2);
		PageId p3= dm.allocPage();
		System.out.println("p3 = " + p3);
		PageId p4= dm.allocPage();
		System.out.println("p4 = " + p4);
		PageId p5= dm.allocPage();
		System.out.println("p5 = " + p5);
		PageId p6= dm.allocPage();
		System.out.println("p6 = " + p6);
		PageId p7= dm.allocPage();
		System.out.println("p7 = " + p7);


		/**
		 * 
		 * Test de la methode DeallocPage(pageId) 
		 * 
		 * params: une page 
		 * 
		 * return: type void -> désalloue une page
		 */
		dm.reset();
		System.out.println("\n===============================");
		System.out.println("Test DeallocPage : "); 
		p1= dm.allocPage();
		System.out.println("Le nombre de page allouee est :" + dm.getCurrentCountAllocPages());
		dm.deallocPage(p1);
		System.out.println("Le nombre de page allouee après dealloc est :" + dm.getCurrentCountAllocPages());
		System.out.println("Fin de Test DeallocPage : ");
		System.out.println("================================");
		

		/*
		 * Test de l'écriture de page
		 * p1 l'identifiant de page et bf1 le buffer
		 * 
		 * Ici on a cette méthode writepage qui écrit le contenu de 
		 * l'argument bf1 dans le fichier et à une position indiqués 
		 * par l'argument p1
		 */
		//source : https://fr.acervolima.com/classe-java-nio-bytebuffer-en-java/
		dm.reset();
		System.out.println("\n===============================");
		System.out.println("Test de WritePage");
		p2= dm.allocPage(); //aloue une page
		
		try {			
			ByteBuffer bb = ByteBuffer.allocate(DBParams.pageSize);
			ByteBuffer bb2 = ByteBuffer.allocate(DBParams.pageSize);
			bb.putInt(2002);
			dm.writePage(p2, bb); // ecris "Hello its me" dans p2
			dm.readPage(p2, bb2); 
			System.out.println(bb2.getInt());
			bb.position(0);
			bb.putInt(1985);
			dm.writePage(p2, bb); // ecris "Hello its me" dans p2
			dm.readPage(p2, bb2);
			bb2.position(0);
			System.out.println(bb2.getInt());
		}
		catch (IllegalArgumentException e) {
  
            System.out.println("IllegalArgumentException catched");
        }
  
        catch (ReadOnlyBufferException e) {
  
            System.out.println("ReadOnlyBufferException catched");
        }
		System.out.println("Fin test de WritePage");
		System.out.println("===============================");
			
		
		/**
		 * Test de getCurrentCountAllocPages
		 * 
		 * return: le nombre courant de page allouée
		 */
		System.out.println("\n==============================");
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
	
		System.out.println("Log : "+dm.getLog());
		System.out.println("Le nombre de page allouee la fin est :" + dm.getCurrentCountAllocPages());
		System.out.println("Fin de test getCurrentCountAllocPages : "); 
		System.out.println("==============================");
	}
}
