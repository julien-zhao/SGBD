
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
		/*try {
			//saveLog(); //RESET FICHIER SAVELOG
			//getSaveLog();
				
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	
	public static DiskManager getSingleton()  {
		return g_instance;
	}
	
	public PageId allocPage() throws IOException {
		if(!(new File(DBParams.DBPath+"F0.bdda").exists())) {
			Fichier.newFile(0);
			log.put(0,new Vector<Integer>(DBParams.maxPagesPerFiles));
			log.get(0).add(0);
			//System.out.println("Fichier 0 créé");
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
						//System.out.println("Page "+i+" du fichier "+fichierLibre+" créé");
						return new PageId(fichierLibre,i);
					}
				}
			}else {//Creation new file
				int n = log.size();
				Fichier.newFile(n);
				log.put(n,new Vector<Integer>(DBParams.maxPagesPerFiles));
				log.get(n).add(0);
				//System.out.println("Page "+0+" du fichier "+n+" créé");
				return new PageId(n,0);
			}
		}
		
		return null;
	}
	
	public void readPage(PageId unePageId, ByteBuffer buff) throws IOException {
		String FileName="F"+unePageId.fileIdx+".bdda";
		RandomAccessFile r = new RandomAccessFile(DBParams.DBPath+FileName, "r");
		r.seek(unePageId.pageIdx*DBParams.pageSize);
		r.readFully(buff.array());
		r.close();
	}
	public void writePage(PageId unePageId, ByteBuffer byteBuffer) throws IOException {
		String FileName="F"+unePageId.fileIdx+".bdda";
		RandomAccessFile r = new RandomAccessFile(DBParams.DBPath+FileName, "rw");
		r.seek(unePageId.pageIdx*DBParams.pageSize);
		r.write(byteBuffer.array());
		r.close();
	}

	public void deallocPage(PageId unePageId) throws Exception{
		log.get(unePageId.fileIdx).removeElement(unePageId.pageIdx);
	}
	
	public int getCurrentCountAllocPages() {
		int sum=0;
		for(int i = 0; i < log.size();i++) {
			sum+= log.get(i).size();
		}
		return sum;
	}
	
	/*
		public void saveLog() throws IOException {
		//Source https://attacomsian.com/blog/java-write-object-to-file
		FileOutputStream fos = new FileOutputStream(DBParams.DBPath+"saveLog.bdda");
	    ObjectOutputStream oos = new ObjectOutputStream(fos);
	    oos.flush();
	    oos.writeObject(log);
	    
	    fos.close();
	    oos.close();
	}
	
	
	
	@SuppressWarnings("unchecked")
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
	*/
	
	public HashMap<Integer,Vector<Integer>> getLog(){
		return  (HashMap<Integer, Vector<Integer>>) log;
	}

	public void reset() {
		log = new HashMap<Integer,Vector<Integer>>();
		deleteFolder();
	}

	public static void deleteFolder() {
		File folder =new File(DBParams.DBPath); 
	    File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            f.delete();
	        }
	    }
	}
	
}
