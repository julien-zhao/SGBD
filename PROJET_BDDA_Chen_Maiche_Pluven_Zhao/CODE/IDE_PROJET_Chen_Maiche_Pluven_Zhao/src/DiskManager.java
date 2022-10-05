
import java.io.File;
import java.io.FileInputStream;
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
	
	public static void readPage(PageId unePageId, byte[] buff) throws IOException {
		String FileName="F"+unePageId.fileIdx+".bdda";
		RandomAccessFile r = new RandomAccessFile(DBParams.DBPath+FileName, "r");
		byte[]res = new byte[buff.length];
		//int offset = unePageId.pageIdx*DBParams.pageSize;
		r.readFully(res);
		String srt1 = new String(res);
		System.out.println(srt1);
		//r.readFully(buff,DBParams.pageSize,offset-1);
		r.seek(0);
		r.close();
	}
	public static void writePage(PageId unePageId, byte[] buff) throws IOException {
		String FileName="F"+unePageId.fileIdx+".bdda";
		RandomAccessFile r = new RandomAccessFile(DBParams.DBPath+FileName, "rw");
		int offset = unePageId.pageIdx*DBParams.pageSize;
		r.seek(0);
		r.write(buff);
		//r.write(buff,DBParams.pageSize,offset-1);
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
	    oos.writeObject(log);
	    
	    fos.close();
	    oos.close();
	}
	
	
	
	@SuppressWarnings({ "unchecked" })
	public void getSaveLog() throws IOException {
		//source https://attacomsian.com/blog/java-read-object-from-file
		
		FileInputStream fis = null;

		fis = new FileInputStream(DBParams.DBPath+"saveLog.bdda");
				
		ObjectInputStream ois = null;

		ois = new ObjectInputStream(fis);


		try {
			log = (Map<Integer, Vector<Integer>>) ois.readObject();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		fis.close();
		ois.close();
	}
	
	public HashMap<Integer,Vector<Integer>> getLog(){
		return  (HashMap<Integer, Vector<Integer>>) log;
	}
	
}
