import java.nio.ByteBuffer;

public class Frame {
	
	private int caseId;
	private buff bb;
	private PageId pId;
	private int pinCount;
	private boolean dirty;
	private Cellule posFile;
	
	public Cellule getPosFile() {
		return posFile;
	}
	
	public void setPosFile(Cellule posFile) {
		this.posFile = posFile;
	}
	
	public boolean isDirty() {
		return dirty;
	}
	
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	
	public int getPinCount() {
		return pinCount;
	}
	
	public void setPinCount(int pinCount) {
		this.pinCount = pinCount;
	}
	
	public PageId getpId() {
		return pId;
	}
	
	public void setpId(PageId pId) {
		this.pId = pId;
	}
	
	public buff getBb() {
		return bb;
	}
	
	public void setBb(buff bb) {
		this.bb = bb;
	}

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}
	
	
}
