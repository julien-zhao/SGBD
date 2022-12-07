import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestDBManager {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		DBParams.DBPath = "PROJET_BDDA_Chen_Maiche_Pluven_Zhao/DB/";
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DBParams.frameCount = 2;
		DBManager dbm = DBManager.getSingleton();
		dbm.init();
		File f1= new File(DBParams.DBPath);
		//deleteFolder(f1);
		System.out.println("******************************************************");
		System.out.println("Début du DBManager:");
		System.out.println("******************************************************");
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
		sc.close();
	}
}
