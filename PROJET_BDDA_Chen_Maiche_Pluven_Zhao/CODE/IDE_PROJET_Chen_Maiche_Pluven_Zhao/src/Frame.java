import java.nio.ByteBuffer;

public class Frame {
	
	private int caseId;
	private ByteBuffer bb;
	private PageId pId;
	private int pinCount;
	private boolean dirty;
	private Cellule posFile;
	
	
	public Frame() {
		this.caseId = -1;
		this.bb = null;
		this.pId = null;
		this.pinCount = 0;
		this.dirty = false;
		this.posFile = null;
	}
	
	
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
	
	public void incPinCount() {
		this.pinCount += 1;
	}
	
	public void decPinCount() {
		this.pinCount -= 1;
	}
	
	public PageId getpId() {
		return pId;
	}
	
	public void setpId(PageId pId) {
		this.pId = pId;
	}
	
	public ByteBuffer getBb() {
		return bb;
	}
	
	public void setBb(ByteBuffer bb) {
		this.bb = bb;
	}

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}
	
	
}
