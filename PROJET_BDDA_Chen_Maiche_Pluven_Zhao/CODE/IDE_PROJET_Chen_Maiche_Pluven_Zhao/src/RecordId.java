
public class RecordId {
	PageId pageId;
	int slotIdx;

	public RecordId(PageId pageID, int slotIdx) {
		this.pageId = pageID;
		this.slotIdx = slotIdx;
	}

	public PageId getPageId() {
		return pageId;
	}

	public void setPageId(PageId pageID) {
		this.pageId = pageID;
	}

	public int getSlotIdx() {
		return slotIdx;
	}

	public void setSlotIdx(int slotIdx) {
		this.slotIdx = slotIdx;
	}

}
