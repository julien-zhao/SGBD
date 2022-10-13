
public class RecordId {
	PageId pageID;
	int slotIdx;

	public RecordId(PageId pageID, int slotIdx) {
		this.pageID = pageID;
		this.slotIdx = slotIdx;
	}

	public PageId getPageID() {
		return pageID;
	}

	public void setPageID(PageId pageID) {
		this.pageID = pageID;
	}

	public int getSlotIdx() {
		return slotIdx;
	}

	public void setSlotIdx(int slotIdx) {
		this.slotIdx = slotIdx;
	}

}
