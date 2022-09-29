
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
}
