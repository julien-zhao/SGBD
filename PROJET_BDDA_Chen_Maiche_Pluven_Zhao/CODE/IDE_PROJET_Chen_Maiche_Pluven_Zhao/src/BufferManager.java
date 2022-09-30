import java.util.Vector;

public class BufferManager {
	private static BufferManager g_instanceBM = new BufferManager();
	
	private Vector<Frame> pool; 
	
	private BufferManager() {
		pool = new Vector<Frame>(DBParams.frameCount);
		for(int i = 0; i<DBParams.frameCount;i++) {
			pool.add(new Frame());
		}
	}
	
	public BufferManager getSingleton() {
		return g_instanceBM;
	}
	
	public buff getPage(PageId pageId ) {
		
		boolean libre = false;
		int iFrameLibre;
		
		for(int i = 0; i<DBParams.frameCount&&!libre;i++) { //Parcours
			if(pool.elementAt(i).getpId()==null||pool.elementAt(i).getpId().equals(pageId)) {
				libre = true;
				iFrameLibre = i;
			}
		}
		if(libre) {//Frame Libre
			
		}else {//Aucune
			
		}
		
		
		
		
		return null;
	}
	
	public void freePage(PageId pageId, boolean dirty) {
		
	}
	
	public void flushAll() {
		
	}
}
