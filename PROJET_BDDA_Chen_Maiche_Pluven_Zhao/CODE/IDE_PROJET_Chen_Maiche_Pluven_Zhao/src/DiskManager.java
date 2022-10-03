
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;

public class DiskManager {
	
	private static Map<Integer ,Vector<Integer>> log;
	

	private static DiskManager g_instance = new DiskManager();

	
	
	private DiskManager() {
		log = new HashMap<Integer,Vector<Integer>>();
		try {
			//saveLog(); //RESET FICHIER SAVELOG
			getSaveLog();
			System.out.println(log);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static DiskManager getSingleton()  {
		return g_instance;
	}
	
	public static PageId allocPage() throws IOException {
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
	
	public static void readPage(PageId unePageId, ByteBuffer buff) throws IOException {
		String FileName="F"+unePageId.fileIdx+".bdda";
		RandomAccessFile r = new RandomAccessFile(DBParams.DBPath+FileName, "r");
		int offset = unePageId.pageIdx*DBParams.pageSize;
		r.readFully(buff.array(),offset,DBParams.pageSize);
		r.close();
	}
	
	public static void writePage(PageId unePageId, ByteBuffer buff) throws IOException {
		String FileName="F"+unePageId.fileIdx+".bdda";
		RandomAccessFile r = new RandomAccessFile(DBParams.DBPath+FileName, "rw");
		int offset = unePageId.pageIdx*DBParams.pageSize;
		r.write(buff.array(),offset,DBParams.pageSize);
		r.close();
	}

	public static void deallocPage(PageId unePageId) throws NullPointerException{
		log.get(unePageId.fileIdx).removeElement(unePageId.pageIdx);
	}
	
	public static int getCurrentCountAllocPages() {
		int sum=0;
		for(int i = 0; i < log.size();i++) {
			sum+= log.get(i).size();
		}
		return sum;
	}
	
	
	public void saveLog() throws IOException {
		//Source https://attacomsian.com/blog/java-write-object-to-file
		
		

		
		FileOutputStream fos = new FileOutputStream(DBParams.DBPath+"saveLog.bdda");
	    ObjectOutputStream oos = new ObjectOutputStream(fos);
	    oos.flush();
	    
	    for(int i = 0; i < log.size();i++) {
	    		oos.writeObject(log.get(i));
	    }
	    fos.close();
	    oos.close();
	}
	
	
	
	@SuppressWarnings({ "unchecked", "resource" })
	public void getSaveLog() throws IOException {
		//source https://attacomsian.com/blog/java-read-object-from-file
		
		FileInputStream fis = null;

		fis = new FileInputStream(DBParams.DBPath+"saveLog.bdda");
		
			
		ObjectInputStream ois = null;

		ois = new ObjectInputStream(fis);

					    
		Vector<Integer> vec;
	    boolean vide = false;
		for(int i = 0; !vide ; i++) {
			try {
				vec = (Vector<Integer>) ois.readObject();
					log.put(i, vec);
			}catch (Exception e) {
				vide = true;
				e.getStackTrace();
			}
		}
		fis.close();
		ois.close();
	}
	
}
