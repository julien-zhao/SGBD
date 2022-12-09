import java.io.IOException;

public class TestCreateTableCommand {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		DBParams.DBPath = ".//..//..//DB//";
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DBParams.frameCount = 2;	
		
		String command = "CREATE TABLE R (X:INTEGER,G2:REAL,BLA:VARCHAR(10))";
		System.out.println("commande : "+command);
		
		/*
		 * Probleme avec le PageID
		 * La relation n'est pas enregistre dans le Catalog.sv
		 */
		
		CreateTableCommand createtab = new CreateTableCommand(command);
		try {
			createtab.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getStackTrace());
		}
		
		
		
		System.out.println("On recherche si la relation 'R' est dans le 'Catalog.sv'");
		if(Catalog.getSingleton().getRelationInfo("R") != null) {
			System.out.println(Catalog.getSingleton().getRelationInfo("R"));
		}
	}
}
