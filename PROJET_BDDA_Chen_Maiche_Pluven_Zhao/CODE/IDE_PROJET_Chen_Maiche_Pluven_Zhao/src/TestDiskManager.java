import java.io.IOException;

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
		System.out.println("Test DeallocPage : "); 
		PageId p1= dm.AllocPage();
		dm.DeallocPage(p1);
		System.out.println("Fin de Test DeallocPage : "); 
		
		
		/**
		 * Test de getCurrentCountAllocPages
		 * 
		 * return: le nombre courant de page allouée
		 */
		System.out.println("Test getCurrentCountAllocPages : "); 
		System.out.println("Le nombre de page alloue est :" + dm.GetCurrentCountAllocPages());
		PageId p2= dm.AllocPage();
		PageId p3= dm.AllocPage();
		PageId p4= dm.AllocPage();
		PageId p5= dm.AllocPage();
		PageId p6= dm.AllocPage();
		PageId p7= dm.AllocPage();
		PageId p8= dm.AllocPage();
		dm.DeallocPage(p2);
		
		System.out.println("Le nombre de page alloue� la fin est :" + dm.GetCurrentCountAllocPages());
		System.out.println("Fin de test getCurrentCountAllocPages : "); 
	}
}
