import java.io.IOException;
import Commande.*;

public class DBManager {
    private static DBManager g_instance = new DBManager();


    private DBManager() {

    }

    public static DBManager getSingleton() {
        return g_instance;
    }

    public void init()   {  // throws IOException
        DiskManager.getSingleton();
        BufferManager.getSingleton();
        //Catalog.getSingleton().init();
    }

    public void finish() throws IOException{
        BufferManager.getSingleton().flushAll();
        DiskManager.getSingleton().saveLog();
        //Catalog.getSingleton().finish();
    }

    public void processCommand(String cmd) throws IOException {
        XCommand c;
        if(cmd.startsWith("CREATE TABLE")){
            c= new CreateTableCommand(cmd);
            c.execute();
        }
        else if(cmd.startsWith("INSERT INTO")){
            c= new InsertIntoCommand(cmd);
            c.execute();
        }
        else if(cmd.startsWith("SELECT")){
            c= new SelectFromCommand(cmd);
            c.execute();
        }
        else if(cmd.startsWith("DROPDB")){
            c= new DropDBCommand(cmd);
            c.execute();
        }
        /*else if(cmd.startsWith("DELETE")){
            c= new DeleteCommand(cmd);
            c.execute();
        }*/
        else{
            System.out.println("Commande non reconnue");
        }
    }
}
