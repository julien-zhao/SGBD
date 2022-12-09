import java.io.IOException;

public class DropDBCommand extends XCommand{
    public DropDBCommand(String command) {
        
    }
    
    public void execute() {
        try {
            BufferManager.getSingleton().flushAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DiskManager.getSingleton().reset();
        Catalog.getSingleton().reset();
        System.out.println("Database dropped");
    }
} 
