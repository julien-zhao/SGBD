import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Vector;

public class BufferManager {
	private static BufferManager g_instanceBM = new BufferManager();

	private Vector<Frame> pool;
	private File file; // File doublement chainee des id de cases pour l'ordonnacement de type LRU

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

	public ByteBuffer getPage(PageId pageId) throws IOException {
		
		boolean libre = false;
		int iFrameLibre = -1;

		for (int i = 0; i < DBParams.frameCount && !libre; i++) { // Parcours
			if (pool.elementAt(i).getpId() == null || pool.elementAt(i).getpId().equals(pageId)) {
				libre = true;
				iFrameLibre = i;
			}

		}

		if (libre) { //Frame Libre ou Frame deja chargee dans la case i
			Frame f = pool.elementAt(iFrameLibre);
			
			if(f.getPinCount()==0 && !file.isVoid()) { //Si la case est dans la liste
				file.del(f.getPosFile());
			}
			
			f.incPinCount();
			return f.getBb();
			
		} else {// Aucune Frame Libre
			
			Frame caseAR= pool.elementAt(file.pop()); //case A Remplacer
			freePage(caseAR.getpId(),caseAR.isDirty());
			caseAR.incPinCount();
			return caseAR.getBb();
			
		}
	}

	public void freePage(PageId pageId, boolean dirty) {
		//TODO Decrementer le pincount
		//TODO quand le pinCount de la Frame passe a 0 faire  /////  pool.elementAt(caseIdACtuelle).setPosFile(file.add(caseIdActuelle));
	}

	public void flushAll() {
		//TODO
	}
}
