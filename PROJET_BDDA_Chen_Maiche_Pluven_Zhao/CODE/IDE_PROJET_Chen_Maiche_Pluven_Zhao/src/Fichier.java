import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Fichier {
	
	
	public static void newFile(int i) throws IOException{
		
		File fichier;
		String file= DBParams.DBPath+"F"+i+".bdda";
		fichier = new File(file);
		if(!(fichier.exists())) {
			RandomAccessFile f = new RandomAccessFile(file, "rw");
			f.setLength(DBParams.pageSize*DBParams.maxPagesPerFiles); //Set la taille du fichier 
			f.close();

		}

	}
}
