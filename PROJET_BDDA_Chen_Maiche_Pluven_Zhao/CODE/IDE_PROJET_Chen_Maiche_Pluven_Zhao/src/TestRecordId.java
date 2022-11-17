
public class TestRecordId {
	public static void main(String[] args) {
		System.out.println("******************************************************");
		System.out.println("Début Test Record :");
		System.out.println("******************************************************");
		RecordId r = new RecordId(null, 23);
		System.out.println(r.pageID+"\t"+r.slotIdx+"\t"+r.getPageID()+"\t"+r.getSlotIdx());
		System.out.println("******************************************************");
		PageId pid = new PageId(15, 20);
		r.setPageID(pid);
		System.out.println(r.pageID+"\t"+r.slotIdx+"\t"+r.getPageID()+"\t"+r.getSlotIdx());
		System.out.println("******************************************************");
		r.setSlotIdx(12);
		System.out.println(r.pageID+"\t"+r.slotIdx+"\t"+r.getPageID()+"\t"+r.getSlotIdx());
		System.out.println("******************************************************");
	}
}
