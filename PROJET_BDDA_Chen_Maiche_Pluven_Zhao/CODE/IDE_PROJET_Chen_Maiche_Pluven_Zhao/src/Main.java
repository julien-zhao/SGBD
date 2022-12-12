import java.io.IOException;


public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		DBParams.DBPath = args[0];
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DBParams.frameCount = 2;

		System.out.println("DBPath: " + DBParams.DBPath);
		DBManager db = DBManager.getSingleton();
		db.init();
		boolean done = true;
		System.out.println("EXIT to quit\n");
		while(done){
			System.out.println("\nEnter a command:");
			String cmd = System.console().readLine();
			
			if(cmd.equals("EXIT")){
				done = false;
			}
			else{
				db.processCommand(cmd);
			}
		}
		db.finish();
	}

}
