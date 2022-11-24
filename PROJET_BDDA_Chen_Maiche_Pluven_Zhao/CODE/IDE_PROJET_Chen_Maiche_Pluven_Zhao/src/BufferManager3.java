import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Vector;

public class BufferManager3 {
	private static BufferManager g_instance = new BufferManager();
 map page id a buffer
 	Map<PageId> = new Has

	public static BufferManager getSingleton() {
		return g_instance;
	}

	public ByteBuffer getPage(PageId pageId) throws IOException {
	
		renvoie de la map
		
		# Afficher la stack courante
	}

	public void freePage(PageId pageId, boolean dirty) {
		
		afficher la stack qui m'appelle
	}

	public void flushAll() throws IOException {
	}
	

}
