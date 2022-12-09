import java.io.IOException;


public class Main {

	public static void main(String[] args) throws IOException {
		DBParams.DBPath = ".//..//..//DB//";
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DBParams.frameCount = 2;
		
		DBManager db = DBManager.getSingleton();
		db.init();
		boolean done = true;
		while(done){
			System.out.println("Enter a command:");
			String cmd = System.console().readLine();
			
			if(cmd.equals("exit")){
				done = false;
			}
			else{
				db.processCommand(cmd);
			}
		}
		db.finish();
	}

}
