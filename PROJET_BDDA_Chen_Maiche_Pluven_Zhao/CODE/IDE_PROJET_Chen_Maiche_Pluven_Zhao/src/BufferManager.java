import java.util.Vector;

public class BufferManager {
	private static BufferManager g_instanceBM = new BufferManager();

	private Vector<Frame> pool;
	private File file;

	private BufferManager() {
		pool = new Vector<Frame>(DBParams.frameCount);
		for (int i = 0; i < DBParams.frameCount; i++) {
			pool.add(new Frame());
			pool.elementAt(i).setCaseId(i);
		}
		
		this.file = new File();
	}

	public BufferManager getSingleton() {
		return g_instanceBM;
	}

	public buff getPage(PageId pageId) {
		
		boolean libre = false;
		int iFrameLibre = -1;

		for (int i = 0; i < DBParams.frameCount && !libre; i++) { // Parcours
			if (pool.elementAt(i).getpId() == null || pool.elementAt(i).getpId().equals(pageId)) {
				libre = true;
				iFrameLibre = i;
			}

		}

		if (libre) { //Frame Libre ou Frame deja charché dans la case i
				return pool.elementAt(iFrameLibre).getBb();
			
		} else {// Aucune Frame Libre
			
		}

		return null;
	}

	public void freePage(PageId pageId, boolean dirty) {
		//TODO
		//TODO quand le pinCount de la Frame passe a 0 faire file.add()
	}

	public void flushAll() {
		//TODO
	}
}
