import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TestFileManager {

	public static void deleteFolder(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            f.delete();
	        }
	    }
	}
	
	
	public static void main(String[] args) throws IOException {
		DBParams.DBPath = ".//..//..//DB//";
		File f1= new File(DBParams.DBPath);
		deleteFolder(f1);
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DBParams.frameCount = 2;
		System.out.println("******************************************************");
		System.out.println("Début Test File Manager:");
		System.out.println("******************************************************");
		
		FileManager fm = FileManager.getSingleton();
		BufferManager bm = BufferManager.getSingleton();
		
		PageId pidH = fm.createNewHeaderPage();
		
		
		
		
		System.out.println(pidH.fileIdx+"\t"+pidH.pageIdx);
		deleteFolder(f1);
		bm.flushAll();
		RelationInfo ri = new RelationInfo("reougo",pidH);
		ri.addColonne("NOM", "VARCHAR(10)");
		ri.addColonne("PRENOM", "VARCHAR(12)");
		ri.addColonne("AGE", "INTEGER");
		ri.addColonne("TAILLE", "REAL");
		
		System.out.println("******************************************************");
		//Ajout du tuple numéro 1
		Record unRecord = new Record(ri);
		ArrayList<String> tuple1 = new ArrayList<>();
		tuple1.add("Zhao");
		tuple1.add("Julien");
		tuple1.add("21");
		tuple1.add("1.6");
		unRecord.addTuple(tuple1);
		RecordId rid= fm.insertRecordIntoRelation(unRecord);
		
		//Ajout du tuple numéro 2
		Record deuxRecord = new Record(ri);
		ArrayList<String> tuple2 = new ArrayList<>();
		tuple2.add("Maiche");
		tuple2.add("Max");
		tuple2.add("20");
		tuple2.add("1.7");
		deuxRecord.addTuple(tuple2);
		RecordId rid2= fm.insertRecordIntoRelation(deuxRecord);
		
		//Ajout du tuple numéro 3
		Record troisRecord = new Record(ri);
		ArrayList<String> tuple3 = new ArrayList<>();
		tuple3.add("dfgdfh");
		tuple3.add("fgjy");
		tuple3.add("945");
		tuple3.add("5.2");
		troisRecord.addTuple(tuple3);
		RecordId rid3= fm.insertRecordIntoRelation(troisRecord);
		
		//affichage du test FileManager
		System.out.println(rid.pageID+"\t"+rid.slotIdx+"\t"+rid.getPageID()+"\t"+rid.getSlotIdx());
		System.out.println("******************************************************");
		System.out.println("fm.getAllDataPages(ri) " + fm.getAllDataPages(ri));
		System.out.println("******************************************************");
		System.out.println("fm.getAllRecords(ri) " + fm.getAllRecords(ri));
		System.out.println("******************************************************");
		System.out.println("fm.getRecordsInRelation(ri) " + fm.getRecordsInRelation(ri));
		System.out.println("******************************************************");
	}

}
 