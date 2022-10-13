
public class FileManager {
	private static FileManager g_instance = new FileManager();
	
	private FileManager() {
		
	}
	
	public FileManager getSingleton() {
		return g_instance;
	}
	
	
}
