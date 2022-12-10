import java.io.IOException;


public class TestDB2 {
    public static void main(String[] args) throws ClassNotFoundException, IOException{
        DBParams.DBPath = "PROJET_BDDA_Chen_Maiche_Pluven_Zhao/DB/"; //.//..//..//DB//
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DBParams.frameCount = 2;
		DBManager dbm = DBManager.getSingleton();

		dbm.init();

		System.out.println(Catalog.getSingleton().getRelationInfo("R").getHeaderPageId());
		int m = BufferManager.getSingleton().getPage(Catalog.getSingleton().getRelationInfo("R").getHeaderPageId()).getInt(0);
        System.out.println(m);

        System.out.println("\n****************************************");
		System.out.println("Le SELECT * FROM R:");
		dbm.processCommand("SELECT * FROM R");
        

        
        dbm.finish();
    }
}
