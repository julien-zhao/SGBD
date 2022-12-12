import java.io.IOException;


public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		DBParams.DBPath = args[0];
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DBParams.frameCount = 2;
		
		DBManager db = DBManager.getSingleton();
		db.init();
		boolean done = true;
		System.out.println("EXIT to quit");
		while(done){
			System.out.println("Enter a command:");
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
