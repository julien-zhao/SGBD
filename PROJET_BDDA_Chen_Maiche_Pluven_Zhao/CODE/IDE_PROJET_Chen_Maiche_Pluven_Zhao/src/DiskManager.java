import java.io.BufferedReader; 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;

public class DiskManager {
	
	private static Map<Integer ,Vector<Integer>> log;
	
	private static DiskManager g_instance = new DiskManager();
	
	private DiskManager(){
		log = new HashMap<Integer,Vector<Integer>>();
	}
	public static DiskManager getSingleton() {
		return g_instance;
	}
	
	public static PageId AllocPage() throws IOException {
		if(!(new File(DBParams.DBPath+"F0.bdda").exists())) {
			Fichier.newFile(0);
			log.put(0,new Vector<Integer>(DBParams.maxPagesPerFiles));
			return new PageId(0,0);
			
		}else {
			int fichierLibre = -1;
			boolean placeLibre = false;
			
			for(int i = 0; i < log.size()&&!placeLibre;i++) { // Pour toute le log et qu'on a pas trouvé de page libre
				if(log.get(i).size()<DBParams.maxPagesPerFiles) {
					fichierLibre = i;
					placeLibre = true;
				}
			}
			
			if(placeLibre) {
				for(int i = 0;i<DBParams.maxPagesPerFiles;i++) {
					if(!log.get(fichierLibre).contains(i)){
						log.get(fichierLibre).add(i);
						return new PageId(fichierLibre,i);
					}
				}
			}else {//Creation new file
				int n = log.size();
				Fichier.newFile(n);
				log.put(n,new Vector<Integer>(DBParams.maxPagesPerFiles));
				return new PageId(n,0);
			}
		}
		
		return null;

	}
	
	public static void ReadPage(PageId unePageId, ByteBuffer buff) throws IOException {
		String FileName="F"+unePageId.fileIdx+".bdda";
		RandomAccessFile r = new RandomAccessFile(DBParams.DBPath+FileName, "r");
		int offset = unePageId.pageIdx*DBParams.pageSize;
		r.readFully(buff.array(),offset,DBParams.pageSize);
		r.close();
	}
	
	public static void WritePage(PageId unePageId, ByteBuffer buff) throws IOException {
		String FileName="F"+unePageId.fileIdx+".bdda";
		RandomAccessFile r = new RandomAccessFile(DBParams.DBPath+FileName, "w");
		int offset = unePageId.pageIdx*DBParams.pageSize;
		r.write(buff.array(),offset,DBParams.pageSize);
		r.close();
	}

	public static void DeallocPage(PageId unePageId) throws NullPointerException{
		log.get(unePageId.fileIdx).removeElement(unePageId.pageIdx);
	}
	
	public static int GetCurrentCountAllocPages() {
		int sum=0;
		for(int i = 0; i < log.size();i++) {
			sum+= log.get(i).size();
		}
		return sum;

	}
}
