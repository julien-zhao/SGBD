
public class RecordIdTest {
	public static void main(String[] args) {
		DBParams.DBPath = ".//..//..//DB//";
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DBParams.frameCount = 2;
		
		
		PageId pageid = new PageId(0,0);
		RecordId recdid = new RecordId(pageid,0);
		
		System.out.println("****** Test Record *******");
		
		System.out.println("Le record appartient à la page :"+recdid.getPageID());
		System.out.println("sloxIDx :"+recdid.getSlotIdx());
		System.out.println("******* changement de pageId pour le record *****");
		
		// affectation de la valeur 1 au slotIdx du Record
		recdid.setSlotIdx(1);
		System.out.println("******** Après affectation *********");
		System.err.println();
		
		
	}
}
