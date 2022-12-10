import java.io.IOException;


public class TestDB1 {
    public static void main(String[] args) throws ClassNotFoundException, IOException{
        DBParams.DBPath = "PROJET_BDDA_Chen_Maiche_Pluven_Zhao/DB/"; //.//..//..//DB//
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DBParams.frameCount = 2;
		DBManager dbm = DBManager.getSingleton();

		dbm.init();

        System.out.println("DROPDB:");
		dbm.processCommand("DROPDB");
		
		System.out.println("\n****************************************");
		System.out.println("CREATE TABLE R (C1:INTEGER,C2:VARCHAR(3),C3:INTEGER):");
		dbm.processCommand("CREATE TABLE R (C1:INTEGER,C2:VARCHAR(3),C3:INTEGER)");


        System.out.println("\n****************************************");
		System.out.println("INSERT INTO R VALUES (1,aab,2):");
		dbm.processCommand("INSERT INTO R VALUES (1,aab,2)");
		
		System.out.println("\n****************************************");
		System.out.println("INSERT INTO R VALUES (2,ab,2):");
		dbm.processCommand("INSERT INTO R VALUES (2,ab,2)");

		/*
		System.out.println(Catalog.getSingleton().getRelationInfo("R").getHeaderPageId());
		int m = BufferManager.getSingleton().getPage(Catalog.getSingleton().getRelationInfo("R").getHeaderPageId()).getInt(0);
        System.out.println(m);
		*/
        dbm.finish();

		
    }
}
