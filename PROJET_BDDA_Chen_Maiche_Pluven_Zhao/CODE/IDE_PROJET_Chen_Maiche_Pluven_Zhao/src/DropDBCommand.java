import java.io.File;
import java.io.IOException;

public class DropDBCommand extends XCommand{
    public DropDBCommand(String command) {
        
    }

    public void execute() {
    	File f =new File(DBParams.DBPath); 
        //deleteFolder(f);
        try {
            BufferManager.getSingleton().flushAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DiskManager.getSingleton().reset();
        Catalog.getSingleton().reset();
        deleteFolder(f);
        System.out.println("Database dropped");
    }

    public static void deleteFolder(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            f.delete();
	        }
	    }
	}
} 
