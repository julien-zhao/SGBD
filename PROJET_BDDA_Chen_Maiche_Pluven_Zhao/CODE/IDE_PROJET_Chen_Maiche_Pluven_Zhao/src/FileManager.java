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
		
		byte[] zerobin = ByteBuffer.allocate(4).putInt(0).array();
		byte[] bb = bm.getPage(p);
		
		for(int i = 0;i<32;i++) {
			bb[i] = zerobin[i];
		}
		
		bm.freePage(p, true);
		return p;
	}
}


