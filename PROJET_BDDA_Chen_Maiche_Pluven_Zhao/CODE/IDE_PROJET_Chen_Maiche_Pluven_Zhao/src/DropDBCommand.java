import java.io.File;
import java.io.IOException;

public class DropDBCommand extends XCommand{
    public DropDBCommand(String command) {
        
    }

    public void execute() {

        deleteFolder(new File(DBParams.DBPath));
        try {
            BufferManager.getSingleton().flushAll();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        DiskManager.getSingleton().reset();
        Catalog.getSingleton().reset();
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
