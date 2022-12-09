import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class TestFileManager {
    
    public static void main(String[] args) throws IOException {

        DBParams.DBPath = ".//..//..//DB//";
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFiles = 4;
		DBParams.frameCount = 2;
		
        FileManager fm = FileManager.getSingleton();
        
        //test every methode of FileManager
        RelationInfo Personne = new RelationInfo("Personne");
		Personne.addColonne("NOM", "VARCHAR(10)");
		Personne.addColonne("PRENOM", "VARCHAR(12)");
		Personne.addColonne("AGE", "INTEGER");
		Personne.addColonne("TAILLE", "REAL");


        Catalog uneCatalog = Catalog.getSingleton();
        uneCatalog.addRelationInfo(Personne);
        Record unRecord = new Record(Personne);

        ArrayList<String> tuple2 = new ArrayList<>();
		tuple2.add("Maiche");
		tuple2.add("Max");
		tuple2.add("20");
		tuple2.add("1.70");
		unRecord.addTuple(tuple2);	

        PageId pId = fm.addDataPage(Personne);
        fm.writeRecordToDataPage(unRecord, pId);


        Vector<Record> records = fm.getRecordsInRelation(Personne);

        for (Record r : records) {
            System.out.println(r);
        }
    }
}
