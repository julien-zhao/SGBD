import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TestDBManager {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		DBParams.DBPath = ".//..//..//DB//";
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DBParams.frameCount = 2;
		DBManager dbm = DBManager.getSingleton();
		dbm.init();
		
		System.out.println("******************************************************");
		System.out.println("Début du DBManager:");
		System.out.println("******************************************************");
		boolean ok = true;
		Scanner sc = new Scanner(System.in);
		/*
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
		}*/
		dbm.processCommand("DROPDB");
		dbm.processCommand("CREATE TABLE R (C1:INTEGER,C2:VARCHAR(3),C3:INTEGER)");
		dbm.processCommand("INSERT INTO R VALUES (1,aab,2)");
		dbm.processCommand("INSERT INTO R VALUES (2,ab,2)");
		dbm.processCommand("INSERT INTO R VALUES (1,agh,1)");
		
		System.out.println("****************************************");
		System.out.println("Le SELECT * FROM R:");
		dbm.processCommand("SELECT * FROM R");
		
		System.out.println("****************************************");
		System.out.println("SELECT * FROM R WHERE C1=1:");
		dbm.processCommand("SELECT * FROM R WHERE C1=1");
		
		System.out.println("****************************************");
		System.out.println("SELECT * FROM R WHERE C3=1:");
		dbm.processCommand("SELECT * FROM R WHERE C3=1");
		
		System.out.println("****************************************");
		System.out.println("SELECT * FROM R WHERE C1=1 AND C3=2:");
		dbm.processCommand("SELECT * FROM R WHERE C1=1 AND C3=2");
		
		System.out.println("****************************************");
		System.out.println("SELECT * FROM R WHERE C1<2:");
		dbm.processCommand("SELECT * FROM R WHERE C1<2");
		
		/*System.out.println("****************************************");
		System.out.println("INSERT INTO S FILECONTENTS(S1.csv):");
		dbm.processCommand("INSERT INTO S FILECONTENTS(S1.csv)");*/
		sc.close();
	}
}
