
public class BufferManager {
	private static BufferManager g_instanceBM = new BufferManager();
	
	private BufferManager() {
		
	}
	
	public BufferManager getSingleton() {
		return g_instanceBM;
	}
	
	public buff getPage(PageId pageId ) {
		return null;
	}
	
	public void freePage(PageId pageId, boolean dirty) {
		
	}
	
	public void flushAll() {
		
	}
}
