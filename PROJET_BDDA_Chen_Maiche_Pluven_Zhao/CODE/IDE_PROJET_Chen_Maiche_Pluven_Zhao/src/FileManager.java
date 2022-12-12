import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Vector;


public class FileManager {
	private static FileManager g_instance = new FileManager();

	//Vector<PageId> ListeDePageIds;
	private FileManager() {
		
	}
	
	public static FileManager getSingleton() {
		return g_instance;
	}
	

	public PageId createNewHeaderPage() throws IOException {
		DiskManager dm = DiskManager.getSingleton();
		PageId p=dm.allocPage();
		
		BufferManager bm = BufferManager.getSingleton();
		bm.getPage(p).putInt(0,0);
		bm.freePage(p, true);
		return p;
	}

	public PageId addDataPage (RelationInfo relInfo) throws IOException {
		DiskManager dm = DiskManager.getSingleton();
		PageId p=dm.allocPage();
		
		BufferManager bm = BufferManager.getSingleton();
		ByteBuffer bb = bm.getPage(p);
		bb.putInt(DBParams.pageSize-8, 0);
		bb.putInt(DBParams.pageSize-4, 0);
		bm.freePage(p, true);

		ByteBuffer header = bm.getPage(relInfo.getHeaderPageId());
		int nbDataPage=header.getInt(0);
		header.putInt(4+nbDataPage*12, p.fileIdx);
		header.putInt(4+nbDataPage*12+4, p.pageIdx);
		header.putInt(4+nbDataPage*12+8, DBParams.pageSize-8);
		header.putInt(0, nbDataPage+1);
		bm.freePage(relInfo.getHeaderPageId(), true);
		return p;
	}
	public PageId getFreeDataPageId (RelationInfo relInfo,int sizeRecord) throws IOException {
		BufferManager bm = BufferManager.getSingleton();
		PageId hpId = relInfo.getHeaderPageId(); 
		ByteBuffer header = bm.getPage(hpId);
		int nb = header.getInt(0);
		for(int i=12;i<=4+nb*12;i+=12) {
			if(header.getInt(i)>=sizeRecord + 8) {
				PageId pIdS = new PageId(header.getInt(i-8),header.getInt(i-4));
				bm.freePage(hpId, false);
				return pIdS;
			}
		}
		bm.freePage(hpId, false);
		return addDataPage(relInfo);
	}


	public RecordId writeRecordToDataPage(Record record, PageId pageId) throws IOException {
		BufferManager bm = BufferManager.getSingleton();
		ByteBuffer p = bm.getPage(pageId);
		int offset = p.getInt(DBParams.pageSize-4);
		
		record.writeToBuffer(p,offset);
		p.putInt(DBParams.pageSize-4, offset+record.getWrittenSize());
		int m = p.getInt(DBParams.pageSize-8);
		
		p.putInt(DBParams.pageSize-8, m+1);
		p.putInt(DBParams.pageSize-(8+(m+1)*8), offset);
		p.putInt(DBParams.pageSize-(8+((m+1)*8))+4, record.getWrittenSize());
		
		bm.freePage(pageId, true);


		ByteBuffer header = bm.getPage(record.getRelInfo().getHeaderPageId());

		for(int i=12;i<=4+header.getInt(0)*12;i+=12) {
			if(header.getInt(i-8)==pageId.fileIdx && header.getInt(i-4)==pageId.pageIdx) {
				header.putInt(i, header.getInt(i)-(record.getWrittenSize()+8));
				break;
			}
		}
		bm.freePage(record.getRelInfo().getHeaderPageId(), true);

		RecordId rId = new RecordId(pageId, m+1);
		return rId;
	}


	public Vector<Record> getRecordsInDataPage(RelationInfo relInfo,PageId pageId) throws IOException{
		BufferManager bm = BufferManager.getSingleton();
		Vector<Record> r = new Vector<Record>();
		ByteBuffer p = bm.getPage(pageId);
		int m = p.getInt(DBParams.pageSize-8);
		for(int i = 1;i<=m;i++) {
			int pos = p.getInt(DBParams.pageSize-(8+(8*i)));
			if(pos!=-1) {
				Record rec =new Record(relInfo);
				rec.readFromBuffer(p,pos);
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
		PageId hp = relInfo.getHeaderPageId();
		ByteBuffer p = bm.getPage(hp);
		
		int nb = p.getInt(0);
		Vector<PageId> L = new Vector<PageId>();
		for(int i=0;i<nb;i++) {
			PageId pId = new PageId(p.getInt(4+i*12),p.getInt(4+i*12+4));
			L.add(pId);
		}
		bm.freePage(relInfo.getHeaderPageId(), false);
		return L;
	}

	public RecordId insertRecordIntoRelation (Record record) throws IOException {
		
		int nbS = record.getWrittenSize();
		PageId pId = getFreeDataPageId(record.getRelInfo(),nbS);
		RecordId ret = writeRecordToDataPage(record,pId);
		return ret;
	}

	public Vector<Record> getRecordsInRelation(RelationInfo relInfo) throws IOException{
		Vector<PageId> L = getAllDataPages(relInfo);
		Vector<Record> R = new Vector<Record>();
		for(PageId p : L) {
			Vector<Record> vr = getRecordsInDataPage(relInfo,p);
			R.addAll(vr);
		}
		return R;
	}


	public void deleteRecordInRelation(RelationInfo relInfo,RecordId recordId) throws IOException {
		BufferManager bm = BufferManager.getSingleton();
		ByteBuffer p = bm.getPage(recordId.pageId);
		int m = p.getInt(DBParams.pageSize-8);	

		p.putInt(DBParams.pageSize-(8-(4*recordId.slotIdx)), -1);
		p.putInt(DBParams.pageSize-8-((recordId.slotIdx)*8), -1);
		p.putInt(DBParams.pageSize-8, m-1);
		bm.freePage(recordId.pageId, true);
	}

}


