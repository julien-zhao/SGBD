import java.io.IOException;
import java.nio.ByteBuffer;

public class RecordIterator {
    RecordId recordId;
    RelationInfo relInfo;
    PageId pageId;
    int slotIdx;
    BufferManager bm;
    ByteBuffer p;

    public RecordIterator(RelationInfo relInfo, PageId pageId) throws IOException {
        this.relInfo = relInfo;
        this.pageId = pageId;
        this.slotIdx = 0;
        
        this.bm = BufferManager.getSingleton();
        this.p = bm.getPage(pageId);
    }

    public Record getNextRecord() throws IOException {
        int m = p.getInt(DBParams.pageSize - 8);
        if (slotIdx < m) {
            int pos = p.getInt(DBParams.pageSize - (16 + (8 * slotIdx)));
            if (pos != -1) {
                Record rec = new Record(relInfo);
                rec.readFromBuffer(p, pos);
                slotIdx++;
                return rec;
            }
        }
        return null;
    }

    public boolean hasNext() throws IOException {
        int m = p.getInt(DBParams.pageSize - 8);
        if (slotIdx >= m) {
            return false;
        }
        return true;
    }

    public void close() throws IOException {
        bm.freePage(pageId, false);
    }   

    public void reset(){
        slotIdx = 0;
    }
}
