import java.io.IOException;


public class Main {

	public static void main(String[] args) throws IOException {
		DBParams.DBPath = args[0];
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DBParams.frameCount = 2;
		
		//Test
		/*
		PageId pageId= DiskManager.AllocPage();
		
		int capacity = 10;
		ByteBuffer bytebuffer = ByteBuffer.allocate(capacity);
		DiskManager.ReadPage(pageId, bytebuffer);
		*/
		//#TODO
	}

}
