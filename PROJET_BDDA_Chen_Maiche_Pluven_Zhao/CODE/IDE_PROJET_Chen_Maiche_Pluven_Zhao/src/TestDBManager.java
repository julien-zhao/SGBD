import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TestDBManager {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		DBParams.DBPath = "PROJET_BDDA_Chen_Maiche_Pluven_Zhao/DB/"; //"PROJET_BDDA_Chen_Maiche_Pluven_Zhao/DB/"
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DBParams.frameCount = 2;
		DBManager dbm = DBManager.getSingleton();
		dbm.init();
		
		System.out.println("******************************************************\n");
		System.out.println("Début du DBManager:\n");
		System.out.println("******************************************************");
		
		/*
		boolean ok = true;
		Scanner sc = new Scanner(System.in);
		while(ok) {
			System.out.println("Sortir (Q): ");
			System.out.println("Rentrer la ligne de commande:");
			String a = sc.nextLine();
			System.out.println("******************************************************");
			System.out.println("Commande: "+a);
			System.out.println("******************************************************");
			if(!a.equals("Q")) {
				dbm.processCommand(a);
			}
		}
		sc.close();*/

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
		
		System.out.println("\n****************************************");
		System.out.println("INSERT INTO R VALUES (1,agh,1):");
		dbm.processCommand("INSERT INTO R VALUES (1,agh,1)");
		
		System.out.println("\n****************************************");
		System.out.println("Le SELECT * FROM R:");
		dbm.processCommand("SELECT * FROM R");
		
		System.out.println("\n****************************************");
		System.out.println("SELECT * FROM R WHERE C1=1:");
		dbm.processCommand("SELECT * FROM R WHERE C1=1");
		
		System.out.println("\n****************************************");
		System.out.println("SELECT * FROM R WHERE C3=1:");
		dbm.processCommand("SELECT * FROM R WHERE C3=1");
		
		System.out.println("\n****************************************");
		System.out.println("SELECT * FROM R WHERE C1=1 AND C3=2:");
		dbm.processCommand("SELECT * FROM R WHERE C1=1 AND C3=2");
		
		System.out.println("\n****************************************");
		System.out.println("SELECT * FROM R WHERE C1<2:");
		dbm.processCommand("SELECT * FROM R WHERE C1<2");
		
		
		System.out.println("\n******************************************************");
		System.out.println("Début du DBManager (INSERT par lot et DELETE):");
		System.out.println("******************************************************\n");
		System.out.println("DROPDB:");
		dbm.processCommand("DROPDB");
		
		System.out.println("****************************************");
		System.out.println("CREATE TABLE S (C1:INTEGER,C2:REAL,C3:INTEGER,C4:INTEGER,C5:INTEGER):");
		dbm.processCommand("CREATE TABLE S (C1:INTEGER,C2:REAL,C3:INTEGER,C4:INTEGER,C5:INTEGER)");
		

		System.out.println("****************************************");
		System.out.println("INSERT INTO S FILECONTENTS(S1.csv):");
		dbm.processCommand("INSERT INTO S FILECONTENTS(PROJET_BDDA_Chen_Maiche_Pluven_Zhao/RESSOURCES/S.csv)");

		System.out.println("****************************************");
		System.out.println("AFFICHE LA PAGE 0:3 DE S A LA MAIN:");
		FileManager fm = FileManager.getSingleton();
		RelationInfo rl = Catalog.getSingleton().getRelationInfo("S");
		System.out.println(fm.getRecordsInDataPage(rl, new PageId(0, 3)));
		
		System.out.println("****************************************");
		System.out.println("SELECT * FROM S:");
		dbm.processCommand("SELECT * FROM S");
		
		System.out.println("****************************************");
		System.out.println("SELECT * FROM S WHERE C3=12:");
		dbm.processCommand("SELECT * FROM S WHERE C3=12)");

		
		System.out.println("****************************************");
		System.out.println("DELETE * FROM S WHERE C3=12 AND C1=167:");
		dbm.processCommand("DELETE * FROM S WHERE C3=12 AND C1=167");
		
		
	}
}
