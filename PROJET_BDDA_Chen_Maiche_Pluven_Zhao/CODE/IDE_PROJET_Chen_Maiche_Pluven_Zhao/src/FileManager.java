import java.io.IOException;
import java.nio.ByteBuffer;

public class FileManager {
	private static FileManager g_instance = new FileManager();
	private PageId pIdHeader;
	private FileManager() {
		try {
		pIdHeader = createNewHeaderPage();
	
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public FileManager getSingleton() {
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
			if(header.get(i)>=sizeRecord) {
				PageId pIdS = new PageId(header.get(i-8),header.get(i-12));
				return pIdS;
			}
		}
		return null;
	}
}


