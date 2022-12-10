import java.io.IOException;

public class TestDB2 {
    public static void main(String[] args) throws ClassNotFoundException, IOException{
        DBParams.DBPath = "PROJET_BDDA_Chen_Maiche_Pluven_Zhao/DB/"; //.//..//..//DB//
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DBParams.frameCount = 2;
		DBManager dbm = DBManager.getSingleton();
		dbm.init();

        System.out.println("\n****************************************");
		System.out.println("INSERT INTO R VALUES (1,aab,2):");
		dbm.processCommand("INSERT INTO R VALUES (1,aab,2)");
		
		System.out.println("\n****************************************");
		System.out.println("INSERT INTO R VALUES (2,ab,2):");
		dbm.processCommand("INSERT INTO R VALUES (2,ab,2)");

        System.out.println("\n****************************************");
		System.out.println("Le SELECT * FROM R:");
		dbm.processCommand("SELECT * FROM R");
        
        
        dbm.finish();
    }
}
