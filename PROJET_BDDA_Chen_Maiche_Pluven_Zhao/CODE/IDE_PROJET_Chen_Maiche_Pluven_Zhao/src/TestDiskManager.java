import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;

public class TestDiskManager {
	public static void main(String []args ) throws Exception {
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
		System.out.println("\n===============================");
		System.out.println("Test DeallocPage : "); 
		PageId p1= DiskManager.allocPage();
		System.out.println("Le nombre de page allouee est :" + DiskManager.getCurrentCountAllocPages());
		DiskManager.deallocPage(p1);
		System.out.println("Le nombre de page allouee après dealloc est :" + DiskManager.getCurrentCountAllocPages());
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
		System.out.println("\n===============================");
		System.out.println("Test de WritePage");
		PageId p2= DiskManager.allocPage(); //aloue une page
		
		try {			
			
			ByteBuffer bb = ByteBuffer.allocate(DBParams.pageSize);
			ByteBuffer bb2 = ByteBuffer.allocate(DBParams.pageSize);
			bb.putInt(2002);
			DiskManager.writePage(p2, bb); // ecris "Hello its me" dans p2
			DiskManager.readPage(p2, bb2); 
			System.out.println(bb2.getInt());
			bb.position(0);
			bb.putInt(1985);
			DiskManager.writePage(p2, bb); // ecris "Hello its me" dans p2
			DiskManager.readPage(p2, bb2);
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
		System.out.println("Le nombre de page allouee est :" + DiskManager.getCurrentCountAllocPages());
		/*
		PageId p2= dm.allocPage();
		PageId p3= dm.allocPage();
		PageId p4= dm.allocPage();
		PageId p5= dm.allocPage();
		PageId p6= dm.allocPage();
		PageId p7= dm.allocPage();
		PageId p8= dm.allocPage();
		*/
		DiskManager.deallocPage(new PageId(0,3));
		dm.saveLog();
		System.out.println("Log : "+dm.getLog());
		System.out.println("Le nombre de page allouee la fin est :" + DiskManager.getCurrentCountAllocPages());
		System.out.println("Fin de test getCurrentCountAllocPages : "); 
		System.out.println("==============================");
	}
}
