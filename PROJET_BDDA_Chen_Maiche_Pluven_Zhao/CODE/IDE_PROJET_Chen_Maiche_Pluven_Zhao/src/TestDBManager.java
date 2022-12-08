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
		dbm.processCommand("CREATE TABLE R (C1:INTEGER,C2:VARHCAR(3),C3:INTEGER)");
		dbm.processCommand("INSERT INTO R VALUES (1,aab,2)");
		dbm.processCommand("INSERT INTO R VALUES (2,ab,2)");
		dbm.processCommand("INSERT INTO R VALUES (1,agh,1)");
		dbm.processCommand("SELECT * FROM R");
		sc.close();
	}
}
