
public class PageId {
	/**
	 * Numerp du fichier
	 */
	public int fileIdx;
	
	/**
	 * Numero de la page
	 */
	public int pageIdx;
	
	public PageId(int FileIdx, int PageIdx) {
		this.fileIdx = FileIdx;
		this.pageIdx = PageIdx;
	}
	
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
}