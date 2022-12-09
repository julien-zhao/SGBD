import java.io.IOException;
import java.nio.ByteBuffer;

public class TestBufferManager {
	public static void main(String []args ) throws IOException {
		DBParams.DBPath = "PROJET_BDDA_Chen_Maiche_Pluven_Zhao/DB/";//".//..//..//DB//"
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DBParams.frameCount = 2;
		DiskManager dm = DiskManager.getSingleton();
		BufferManager bm = BufferManager.getSingleton();
		System.out.println(DBParams.DBPath);
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		
		
 
		PageId p1= dm.allocPage();
		PageId p2= dm.allocPage();
		PageId p3= dm.allocPage();
		PageId p4= dm.allocPage();
		PageId p5= dm.allocPage();

		System.out.println("\n=============================");
		System.out.println("Test de GetPage OverFlow");
		System.out.println("=============================");
		try{
			bm.getPage(p1);
			bm.getPage(p2);
			bm.getPage(p3);
			bm.getPage(p4);
			bm.getPage(p5);
		}
		catch(RuntimeException e){
			System.out.println("Pool Pleine, c'est bon");
		}finally{
			bm.flushAll();
		}


		
		System.out.println("\n=============================");
		System.out.println("Test de GetPage");
		
		ByteBuffer bb1 = bm.getPage(p1);
		bb1.putInt(1);
		bb1.putInt(2);
		dm.writePage(p1, bb1);
		bm.freePage(p1, true);

		ByteBuffer bb2 = bm.getPage(p2);
		bb2.putInt(3);
		bb2.putInt(4);
		dm.writePage(p2, bb2);
		bm.freePage(p2, true);

		ByteBuffer bb3 = bm.getPage(p3);
		bb3.putInt(5);
		bb3.putInt(6);
		dm.writePage(p3, bb3);
		bm.freePage(p3, true);

		ByteBuffer bb4 = bm.getPage(p4);
		bb4.putInt(7);
		bb4.putInt(8);
		dm.writePage(p4, bb4);
		bm.freePage(p4, true);

		ByteBuffer bb5 = bm.getPage(p5);
		bb5.putInt(9);
		bb5.putInt(10);
		dm.writePage(p5, bb5);
		bm.freePage(p5, true);

		System.out.println();
		System.out.println("===========!!!!!==================");
		
		dm.readPage(p5, bb5);
		System.out.println(bb5.getInt(0));
		System.out.println(bb5.getInt(4));
		


		
		
		System.out.println("\n=============================");
		System.out.println("Test de FreePage");
		System.out.println("=============================");
		
		
		
		
		System.out.println("\n=============================");
		System.out.println("Test de FlushAll");
		bm.flushAll();
		System.out.println();
		System.out.println("=============================");
		
	}
}

