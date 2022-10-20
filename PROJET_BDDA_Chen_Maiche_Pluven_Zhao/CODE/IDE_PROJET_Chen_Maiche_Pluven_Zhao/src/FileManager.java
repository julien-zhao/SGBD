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
		bm.getPage(p).putInt(0,0);
		bm.freePage(p, true);
		return p;
	}
	public PageId addDataPage (RelationInfo relInfo) throws IOException {
		PageId p=DiskManager.allocPage();
		
		BufferManager bm = BufferManager.getSingleton();
	
		ByteBuffer bb = bm.getPage(p);
		bb.putInt(DBParams.pageSize-64, 0);
		bb.putInt(DBParams.pageSize-32, 0);
		bm.getPage(pIdHeader);
		bm.freePage(p, true);
		return p;
	}
}


