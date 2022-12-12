import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Vector;

public class BufferManager {
	private static BufferManager g_instance = new BufferManager();

	private Vector<Frame> pool;
	private Queue file; // File doublement chainee des id de cases pour l'ordonnacement de type LRU

	private BufferManager() {
		this.file = new Queue();
		
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
		boolean dejaPresente = false;
		int iFrameLibre = -1;
		for (int i = 0; i < DBParams.frameCount && !libre; i++) { // Parcours
			if (pool.elementAt(i).getpId() == null) {
				libre = true;
				iFrameLibre = i;
			}else{
				if(pool.elementAt(i).getpId().equals(pageId)){
					dejaPresente =true;
					libre = true;
					iFrameLibre = i;
				}
			}
		}
		
		if (libre) { //Frame Libre ou Frame deja chargee dans la case i
			Frame f = pool.elementAt(iFrameLibre);
			
			if(f.getPinCount()==0 && !(f.getPosFile()==null)) { //Si la case est dans la liste
				file.del(f.getPosFile());
			}
			
			f.incPinCount();
			f.setpId(pageId);

			if(!dejaPresente) {
				DiskManager.getSingleton().readPage(pageId,f.getBb());
			}
			return f.getBb();
			
		} else {// Aucune Frame Libre
			Frame caseAR = null;
			try {
				caseAR= pool.elementAt(file.pop()); //case A Remplacer
			}catch(ArrayIndexOutOfBoundsException e) {
				System.out.println(e.getMessage());
			}
			if(!(caseAR.getPinCount()>0)) {

				flush(caseAR);
				caseAR.setpId(pageId);
				DiskManager.getSingleton().readPage(pageId,caseAR.getBb());
				caseAR.incPinCount();
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
		if(caseAFree.getPinCount()>0) {
			caseAFree.decPinCount();
		}
		if(caseAFree.getPinCount()==0) {
			caseAFree.setPosFile(file.add(caseAFree.getCaseId())); //J'ajoute dans la file la case et je met la cellule correspondatne dans la var PosFile
		}
	}

	public void flush(Frame frame) throws IOException {
		if(frame.isDirty()) {
			DiskManager.getSingleton().writePage(frame.getpId(), frame.getBb());
			frame.setDirty(false);
		}
		frame.resetBb();
		frame.setpId(null);
		frame.setPinCount(0);
		frame.setDirty(false);
		frame.setPosFile(null);
	}

	public void flushAll() throws IOException {
		file = new Queue();
		for (int i = 0; i < DBParams.frameCount; i++) { 
			Frame c = pool.elementAt(i);
			if(c.isDirty()) {
				DiskManager.getSingleton().writePage(c.getpId(), c.getBb());
			}
			c.resetBb();
			c.setpId(null);
			c.setPinCount(0);
			c.setDirty(false);
			c.setPosFile(null);	
		}
		
	}
	
	public Queue getQueue() {
		return file;
	}
	public Vector<Frame> getPool(){
		return this.pool;
	}
}
