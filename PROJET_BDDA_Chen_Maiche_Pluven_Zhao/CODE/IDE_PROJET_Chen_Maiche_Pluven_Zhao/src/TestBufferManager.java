import java.io.IOException;

public class TestBufferManager {
	public static void main(String []args ) throws IOException {
		DBParams.DBPath = ".//..//..//DB//";
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DBParams.frameCount = 2;
		//DiskManager dm = DiskManager.getSingleton();
		BufferManager bm = BufferManager.getSingleton();
		System.out.println(DBParams.DBPath);
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		
		
		System.out.println("\n=============================");
		System.out.println("Test DeallocPage : "); 
		PageId p1= DiskManager.allocPage();
		PageId p2= DiskManager.allocPage();
		PageId p3= DiskManager.allocPage();
		System.out.println("=============================");
		
		
		
		System.out.println("\n=============================");
		System.out.println("Test de GetPage");
		bm.getPage(p1);
		bm.getPage(p2);
		System.out.println();
		System.out.println("=============================");
		
		
		
		
		
		System.out.println("\n=============================");
		System.out.println("Test de FreePage");
		bm.freePage(p2, false);
		bm.getPage(p3);
		System.out.println("=============================");
		
		
		
		
		System.out.println("\n=============================");
		System.out.println("Test de FlushAll");
		bm.flushAll();
		System.out.println();
		System.out.println("=============================");
		
	}
}

