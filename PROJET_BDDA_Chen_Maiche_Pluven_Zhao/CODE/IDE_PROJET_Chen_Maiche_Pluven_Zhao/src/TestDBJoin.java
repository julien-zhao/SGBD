import java.io.IOException;


public class TestDBJoin {
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
		System.out.println("INSERT INTO R VALUES (2,ac,2):");
		dbm.processCommand("INSERT INTO R VALUES (2,ac,2)");

		System.out.println("\n****************************************");
		System.out.println("INSERT INTO R VALUES (3,ac,1):");
		dbm.processCommand("INSERT INTO R VALUES (3,ac,1)");

		System.out.println("\n****************************************");
		System.out.println("CREATE TABLE S (AA:INTEGER,BB:INTEGER):");
		dbm.processCommand("CREATE TABLE S (AA:INTEGER,BB:INTEGER)");

        System.out.println("\n****************************************");
		System.out.println("INSERT INTO S VALUES (1,2):");
		dbm.processCommand("INSERT INTO S VALUES (1,2)");

        System.out.println("\n****************************************");
		System.out.println("INSERT INTO S VALUES (3,2):");
		dbm.processCommand("INSERT INTO S VALUES (3,2)");

        System.out.println("\n****************************************");
		System.out.println("INSERT INTO S VALUES (4,5):");
		dbm.processCommand("INSERT INTO S VALUES (4,5)");

        System.out.println("\n****************************************");
		System.out.println("SELECT * FROM R,S WHERE R.C1=S.AA AND R.C3=S.BB:");
		dbm.processCommand("SELECT * FROM R,S WHERE R.C1=S.AA AND R.C3=S.BB");


        System.out.println("\n****************************************");
		System.out.println("SELECT * FROM R,S WHERE R.C3=S.BB AND R.C1<S.BB:");
		dbm.processCommand("SELECT * FROM R,S WHERE R.C3=S.BB AND R.C1<S.BB ");

        dbm.finish();

		
    }
}
