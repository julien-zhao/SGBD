import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Vector;

public class FileManager {
	private static FileManager g_instance = new FileManager();
	private PageId pIdHeader;

	//Vector<PageId> ListeDePageIds;
	private FileManager() {
		try {
		pIdHeader = createNewHeaderPage();
	
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static FileManager getSingleton() {
		return g_instance;
	}
	

	public PageId createNewHeaderPage() throws IOException {
		PageId p=DiskManager.allocPage();
		
		BufferManager bm = BufferManager.getSingleton();
		bm.getPage(p).putInt(0,0);
		bm.freePage(p, true);
		return p;
	}

	public PageId addDataPage (RelationInfo relInfo) throws IOException {
		PageId p=DiskManager.allocPage();
		
		BufferManager bm = BufferManager.getSingleton();
	
		ByteBuffer bb = bm.getPage(p);
		bb.putInt(DBParams.pageSize-64, 0);
		bb.putInt(DBParams.pageSize-32, 0);
		ByteBuffer header = bm.getPage(pIdHeader);
		int nbDataPage=header.getInt(0);
		header.putInt(4+nbDataPage*12, p.fileIdx);
		header.putInt(4+nbDataPage*12+4, p.pageIdx);
		header.putInt(4+nbDataPage*12+8, DBParams.pageSize);
		header.putInt(nbDataPage+1, 0);
		bm.freePage(p, true);
		return p;
	}
	
	public PageId getFreeDataPageId (RelationInfo relInfo,int sizeRecord) throws IOException {
		BufferManager bm = BufferManager.getSingleton();
		PageId hpId = null;//relInfo.getHeaderPageId(); 
		ByteBuffer header = bm.getPage(hpId);
		int nb = header.get(0);
		for(int i=12;i*12<nb*12;i+=12) {
			if(header.getInt(i)>=sizeRecord) {
				PageId pIdS = new PageId(header.getInt(i-8),header.getInt(i-4));
				return pIdS;
			}
		}
		return null;
	}
	
	public RecordId writeRecordToDataPage(Record record, PageId pageId) throws IOException {
		BufferManager bm = BufferManager.getSingleton();
		ByteBuffer p = bm.getPage(pageId);
		int offset = p.getInt(DBParams.pageSize-4);
		record.writeToBuffer(p,offset);
		p.putInt(DBParams.pageSize-4, offset+record.getWrittenSize());
		int m = p.getInt(DBParams.pageSize-8);
		p.putInt(DBParams.pageSize-8, m+1);
		p.putInt(DBParams.pageSize-8-((m+1)*8), offset);
		p.putInt(DBParams.pageSize-8-(((m+1)*8)+4), record.getWrittenSize());
		
		bm.freePage(pageId, true);
		return new RecordId(pageId, m+1);
	}


	public Vector<Record> getRecordsInDataPage(RelationInfo relInfo,PageId pageId) throws IOException{
		BufferManager bm = BufferManager.getSingleton();
		Vector<Record> r = new Vector<Record>();
		ByteBuffer p = bm.getPage(pageId);
		int m = p.getInt(DBParams.pageSize-8);
		for(int i = 1;i<=m;i++) {
			int pos = p.getInt(DBParams.pageSize-(8-(4*i)));
			if(pos!=-1) {
				Record rec =new Record(relInfo);
				rec.readFromBuffer2(p,pos);
				r.add(rec);
			}
		}
		bm.freePage(pageId, false);
		return r;
	}

	/*public void deleteRecordInDataPage(PageId pageId,RecordId recordId) throws IOException {
		BufferManager bm = BufferManager.getSingleton();
		ByteBuffer p = bm.getPage(pageId);
		int m = p.getInt(DBParams.pageSize-8);
		p.putInt(DBParams.pageSize-(8-(4*recordId.slotIdx)), -1);
		bm.freePage(pageId, true);
	}*/
	

	public Vector<PageId> getAllDataPages(RelationInfo relInfo) throws IOException{
		BufferManager bm = BufferManager.getSingleton();
		ByteBuffer p = bm.getPage(relInfo.getHeaderPageId());
		int nb = p.getInt(0);
		Vector<PageId> L = new Vector<PageId>();
		for(int i=0;i<nb;i++) {
			PageId pId = new PageId(p.getInt(4+i*12),p.getInt(4+i*12+4));
			L.add(pId);
		}
		bm.freePage(relInfo.getHeaderPageId(), false);
		return L;
	}

	public RecordId InsertRecordIntoRelation (Record record) throws IOException {
		PageId pId = getFreeDataPageId(record.getRelInfo(),record.getWrittenSize());
		if(pId==null) {
			pId = addDataPage(record.getRelInfo());
		}
		return writeRecordToDataPage(record,pId);
	}

	public Vector<Record> getRecordsInRelation(RelationInfo relInfo) throws IOException{
		Vector<PageId> L = getAllDataPages(relInfo);
		Vector<Record> R = new Vector<Record>();
		for(PageId p : L) {
			R.addAll(getRecordsInDataPage(relInfo,p));
		}
		return R;
	}

	public Vector<Record> getAllRecords(RelationInfo relInfo) throws IOException{
		Vector<PageId> l = getAllDataPages(relInfo);
		Vector<Record> r = new Vector<Record>();
		for(PageId p : l) {
			r.addAll(getRecordsInDataPage(relInfo,p));
		}
		return r;
	}

	/*public void deleteRecordInRelation(RelationInfo relInfo,RecordId recordId) throws IOException {
		BufferManager bm = BufferManager.getSingleton();
		ByteBuffer p = bm.getPage(recordId.pageId);
		int m = p.getInt(DBParams.pageSize-8);
		p.putInt(DBParams.pageSize-(8-(4*recordId.slotIdx)), -1);
		bm.freePage(recordId.pageId, true);
	}*/

}


