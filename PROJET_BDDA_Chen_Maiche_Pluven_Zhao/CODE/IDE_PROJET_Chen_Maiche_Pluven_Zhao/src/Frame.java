
public class Frame {
	
	private int caseId;
	private byte[] bb;
	private PageId pId;
	private int pinCount;
	private boolean dirty;
	private Cellule posFile;
	
	
	public Frame() {
		this.caseId = -1;
		this.bb = new byte[DBParams.pageSize];
		this.pId =  null;
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
		this.pinCount ++;
	}
	
	public void decPinCount() {
		this.pinCount --;
	}
	
	public void setPinCount(int c) {
		this.pinCount = c;
	}
	
	public PageId getpId() {
		return pId;
	}
	
	public void setpId(PageId pId) {
		this.pId = pId;
	}
	
	public byte[] getBb() {
		return bb;
	}
	
	public void setBb(byte[] bb) {
		this.bb = bb;
	}

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}
	
	
}
