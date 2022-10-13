import java.io.IOException;
import java.nio.ByteBuffer;

public class FileManager {
	private static FileManager g_instance = new FileManager();
	private PageId pIdHeader;
	private FileManager() {
		try {
		pIdHeader = createNewHeaderPage();
	
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public FileManager getSingleton() {
		return g_instance;
	}
	

	public PageId createNewHeaderPage() throws IOException {
		PageId p=DiskManager.allocPage();
		
		BufferManager bm = BufferManager.getSingleton();
		ByteBuffer bb = bm.getPage(p).putInt(0, 0);
		

		
		bm.freePage(p, true);
		return p;
	}
	public PageId addDataPage (RelationInfo relInfo) throws IOException {
		PageId p=DiskManager.allocPage();
		
		BufferManager bm = BufferManager.getSingleton();
		
		byte[] zerobin = ByteBuffer.allocate(8).putInt(0).array();
		byte[] bb = bm.getPage(p);
		
		for(int i = 0;i<64;i++) {
			bb[DBParams.pageSize-64+i] = zerobin[i%32];
		}
		
		bm.freePage(p, true);
		return p;
	}
}


