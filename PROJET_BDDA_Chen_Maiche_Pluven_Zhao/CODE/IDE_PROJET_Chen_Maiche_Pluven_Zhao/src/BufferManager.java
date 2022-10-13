import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Vector;

public class BufferManager {
	private static BufferManager g_instance = new BufferManager();

	private Vector<Frame> pool;
	private File file; // File doublement chainee des id de cases pour l'ordonnacement de type LRU

	private BufferManager() {
		this.file = new File();
		
		pool = new Vector<Frame>(DBParams.frameCount);
		
		for (int i = 0; i < DBParams.frameCount; i++) {
			pool.add(new Frame());
			pool.elementAt(i).setCaseId(i);
			pool.elementAt(i).setPosFile(file.add(pool.elementAt(i).getCaseId())); 
		}
		
	}

	public static BufferManager getSingleton() {
		return g_instance;
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
			f.setpId(pageId);
			return f.getBb();
			
		} else {// Aucune Frame Libre
			Frame caseAR= pool.elementAt(file.pop()); //case A Remplacer

			if(!(caseAR.getPinCount()>0)) {

				freePage(caseAR.getpId(),caseAR.isDirty());
				caseAR.incPinCount();
				caseAR.setpId(pageId);
				
				return caseAR.getBb();
			}else {
				try {
					throw new Exception("AUCUNE FRAME DISPO");
			
				}catch(Exception e){
					System.out.println(e.getMessage()); 
					return null;
				}
		
			}
		}
	}

	public void freePage(PageId pageId, boolean dirty) {

		boolean trouve = false;
		Frame caseAFree = null;
		
		for (int i = 0; i < DBParams.frameCount&&!trouve; i++) { 
			if(pageId.equals(pool.elementAt(i).getpId())) {
				trouve = true;
				caseAFree = pool.elementAt(i);
			}
		}
		
		if(dirty) {
			caseAFree.setDirty(true);
		}
		
		caseAFree.decPinCount();
		
		if(caseAFree.getPinCount()==0) {
			caseAFree.setPosFile(file.add(caseAFree.getCaseId())); //J'ajoute dans la file la case et je met la cellule correspondatne dans la var PosFile
		}
	}

	public void flushAll() throws IOException {
		for (int i = 0; i < DBParams.frameCount; i++) { 
			Frame c = pool.elementAt(i);
			if(c.isDirty()) {
				DiskManager.writePage(c.getpId(), c.getBb());
			}
			c.resetBb();
			c.setpId(null);
			c.setPinCount(0);
			c.setDirty(false);
			c.setPosFile(null);	
		}
		
	}
}
