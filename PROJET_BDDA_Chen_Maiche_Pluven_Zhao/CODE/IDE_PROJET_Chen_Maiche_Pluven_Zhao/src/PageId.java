
public class PageId {
	/**
	 * Numerp du fichier
	 */
	public int fileIdx;
	
	/**
	 * Numero de la page
	 */
	public int pageIdx=-1;
	
	public PageId(int FileIdx, int PageIdx) {
		this.fileIdx = FileIdx;
		this.pageIdx = PageIdx;
	}
	
	@Override
	public String toString() {
		return "("+fileIdx+","+pageIdx+")";
	}


	@Override
	public boolean equals(Object obj) {
	    if(obj instanceof PageId||obj != null) {
	    	PageId equalsSample = (PageId) obj;
	        if(equalsSample.fileIdx == this.fileIdx && equalsSample.pageIdx == this.pageIdx){
	            return true;
	        }
	    }
	    return false;       
	}
	
	public int getPageIdx() {
		return pageIdx;
	}
	
	public int getFileIdx() {
		return fileIdx;
	}
	
	public void setFileIdx(int fileIdx) {
		this.fileIdx = fileIdx;
	}
	
	public void setPageIdx(int pageIdx) {
		this.pageIdx = pageIdx;
	}
}