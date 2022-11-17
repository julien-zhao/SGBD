import java.util.ArrayList;
import java.nio.ByteBuffer;

public class Record {

	private RelationInfo relInfo;
	private ArrayList<String> values;
	private int bufferSize =0;


	
	public Record(RelationInfo relInfo) {
		this.relInfo = relInfo;
		values = new ArrayList<String>();
	}
	
	public RelationInfo getRelInfo() {
		return relInfo;
	}
	
	public voids etValues(String... s){
		for(String str : s){
			values.add(str);
		}
	}


	public void addTuple(ArrayList<String> unTuple) {
		
		if(values.size() == 0) {
			for(int i =0; i<unTuple.size();i++) {
				//tester si le type d'entrer est le meme que le type des colonnes
				values.add(unTuple.get(i));
			}
		}
		if(unTuple.size() == relInfo.getTabInfo().size()) {
			for(int i =0; i<unTuple.size();i++) {
				values.set(i,unTuple.get(i));
			}
		}
	}
	
	
	public String afficheValues() {
		return values.toString();
	}
	
	public ArrayList<String> getValues(){
		return values;
	}
	
	public ArrayList<String> getNomColonne() {
		ArrayList<String> res= new ArrayList<>();
		for(ColInfo uneCol: relInfo.getTabInfo()){
			res.add(uneCol.getColonne());
		}
		return res;
	}
	public ArrayList<String> getTypeColonne() {
		ArrayList<String> res= new ArrayList<>();
		for(ColInfo uneCol: relInfo.getTabInfo()){
			res.add(uneCol.getType());
		}
		return res;
	}
	
	
	public int getSizePos() {
		int size = getValues().size();

		for(int i =0; i< getValues().size();i++) {
			//si c'est un real ou integer
			if(relInfo.getTabInfo().get(i).getType().equals("REAL") || relInfo.getTabInfo().get(i).getType().equals("INTEGER")) {
				size+=1;
			}else {
				size+= getValues().get(i).length();
			}
		}
		return size;
	}

	public void writeToBuffer(ByteBuffer buff, int pos){
		// 0100091801100
		//int 4 octet de val 1
		
		//8 pour zhao, 12 pour julien, 4 pour 21, 4 pour 1.6
		//81244|709709709

		buff.position(pos); 

		//boucle qui met les tailles des valeurs dans le buff
		for (int i=0; i< relInfo.getTabInfo().size();i++) {
			if ( relInfo.getTabInfo().get(i).getType().equals("INTEGER") ){
				//posList.add(4);
				buff.putInt(4); 
			}
			if ( relInfo.getTabInfo().get(i).getType().equals("REAL") ){
				//posList.add(4);
				buff.putInt(4);	
			}
			if ( relInfo.getTabInfo().get(i).getType().contains("VARCHAR(") ){
				int sizeString = getValues().get(i).length()*2;
				//posList.add(sizeString);
				buff.putInt(sizeString);
			}
		}
		//boucle qui transforme les valeurs en byte
		for (int i=0; i< relInfo.getTabInfo().size();i++) {
			if ( relInfo.getTabInfo().get(i).getType().equals("INTEGER") ){
				int tmpInt = Integer.parseInt(getValues().get(i));
				buff.putInt(tmpInt);
			}
			if ( relInfo.getTabInfo().get(i).getType().equals("REAL") ){
				float tmpFloat = Float.parseFloat(getValues().get(i));
				buff.putFloat(tmpFloat);	
			}
			if ( relInfo.getTabInfo().get(i).getType().contains("VARCHAR(") ){
				String tmpString = getValues().get(i);
				
				
				for(int j =0; j< tmpString.length();j++) {
					buff.putChar(tmpString.charAt(j));
				}
			}
		}

	}

	public void getBufferToByte(ByteBuffer buff, int pos) {
		buff.position(pos);
		while(buff.hasRemaining()) {
			bufferSize++;
			System.out.print(buff.get() + " ");
		}		
	}
	
	public void readFromBuffer2(ByteBuffer buff, int pos) {
		buff.position(pos);
		System.out.println();
		ArrayList<Integer>posList = new ArrayList<>();
		for(int i =0;i <relInfo.getTabInfo().size(); i++) {
			posList.add(buff.getInt());
		}
		
		
		ArrayList<String> list = getTypeColonne();
		StringBuilder sb = new StringBuilder("");;
		for(int i =0; i<posList.size();i++) {
			if(posList.get(i) > 4) {
				for(int j =0; j< posList.get(i)/2; j++) {
					sb.append(buff.getChar());
				}
				System.out.println(sb.toString());
				sb = new StringBuilder("");
			}
			if(posList.get(i) == 4) {
				if(list.get(i).equals("INTEGER")) {
					sb.append(buff.getInt());
					System.out.println(sb.toString());
					sb = new StringBuilder("");
				}else {
					sb.append(buff.getFloat());
					System.out.println(sb.toString());
					sb = new StringBuilder("");
				}
			}
		}
	}
	
	
	public int getWrittenSize() {
		return this.bufferSize;
	}

}
