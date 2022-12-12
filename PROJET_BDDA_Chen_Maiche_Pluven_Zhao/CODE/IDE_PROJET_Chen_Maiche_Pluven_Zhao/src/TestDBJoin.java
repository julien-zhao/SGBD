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
		System.out.println("CREATE TABLE S (AA:INTEGER,BB:INTEGER,CC:INTEGER,DD:INTEGER,EE:INTEGER,FF:INTEGER,GG:INTEGER,HH:INTEGER):");
		dbm.processCommand("CREATE TABLE S (AA:INTEGER,BB:INTEGER,CC:INTEGER,DD:INTEGER,EE:INTEGER,FF:INTEGER,GG:INTEGER,HH:INTEGER)");

        System.out.println("\n****************************************");
		System.out.println("INSERT INTO S VALUES (1,2,3,4,5,6,7,8):");
		dbm.processCommand("INSERT INTO S VALUES (1,2,3,4,5,6,7,8)");

        System.out.println("\n****************************************");
		System.out.println("INSERT INTO S VALUES (8,7,6,5,4,3,2,1):");
		dbm.processCommand("INSERT INTO S VALUES (8,7,6,5,4,3,2,1)");

        System.out.println("\n****************************************");
		System.out.println("INSERT INTO S VALUES (5,6,8,1,3,2,7,4):");
		dbm.processCommand("INSERT INTO S VALUES (5,6,8,1,3,2,7,4)");

        System.out.println("\n****************************************");
		System.out.println("SELECT * FROM R,S WHERE R.C1<S.CC :");
		dbm.processCommand("SELECT * FROM R,S WHERE R.C1<S.CC");


        System.out.println("\n****************************************");
		System.out.println("SELECT * FROM R,S WHERE R.C3=S.BB AND R.C1<S.BB:");
		dbm.processCommand("SELECT * FROM R,S WHERE R.C3=S.BB AND R.C1<S.BB ");

        dbm.finish();

		
    }
}
