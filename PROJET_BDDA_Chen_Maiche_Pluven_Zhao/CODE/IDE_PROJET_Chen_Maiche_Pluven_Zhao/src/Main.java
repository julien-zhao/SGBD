import java.io.IOException;


public class Main {

	public static void main(String[] args) throws IOException {
		DBParams.DBPath = ".//..//..//DB//";
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		
		
		/*
		PageId pageId= DiskManager.AllocPage();
		
		int capacity = 10;
		ByteBuffer bytebuffer = ByteBuffer.allocate(capacity);
		DiskManager.ReadPage(pageId, bytebuffer);
		*/
		//#TODO
	}

}
