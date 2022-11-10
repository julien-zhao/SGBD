import java.util.ArrayList;
import java.nio.ByteBuffer;

public class Record {

	RelationInfo relInfo;
	ArrayList<String> values;

	
	public Record(RelationInfo relInfo) {
		this.relInfo = relInfo;
		values = new ArrayList<String>();
	}
	
	public RelationInfo getRelInfo() {
		return relInfo;
	}
	
	
	public void addTuple(ArrayList<String> unTuple) {
<<<<<<< Updated upstream
		
		if(values.size() == 0) {
			for(int i =0; i<unTuple.size();i++) {
				//tester si le type d'entrer est le meme que le type des colonnes
=======

		if(values.size() == 0) {
			for(int i =0; i<unTuple.size();i++) {
				//tester les types d'entrer par rapport aux type colonne
>>>>>>> Stashed changes
				values.add(unTuple.get(i));
			}
		}
		if(unTuple.size() == relInfo.getTabInfo().size()) {
			for(int i =0; i<unTuple.size();i++) {
<<<<<<< Updated upstream
				values.set(i,unTuple.get(i));
=======
				values.set(i, unTuple.get(i));
>>>>>>> Stashed changes
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
				buff.putInt(4); 
			}
			if ( relInfo.getTabInfo().get(i).getType().equals("REAL") ){
				buff.putInt(4);	
			}
			if ( relInfo.getTabInfo().get(i).getType().contains("VARCHAR(") ){
				int sizeString = getValues().get(i).length()*2;
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
	
	
	public void readFromBuffer2(ByteBuffer buff, int pos) {
		buff.position(pos);
		/*
		while(buff.hasRemaining()) {
			System.out.print(buff.getInt() + " ");
		}
		
		*/

		ArrayList<Integer>tabTaille = new ArrayList<>();
		for(int i =0;i <relInfo.getTabInfo().size(); i++) {
			tabTaille.add(buff.getInt());
		}
		
		for(int j = relInfo.getTabInfo().size(); j < buff.capacity(); j++) {
			
			System.out.println( buff.getInt(j) + " ");
		}

		//System.out.println("test : "+ buff.getChar(6));
		
		
		/*
		for (int i=0; i< relInfo.getTabInfo().size();i++) {
			if ( relInfo.getTabInfo().get(i).getType().equals("INTEGER") ){
				int tmpInt = buff.getInt();
				buff.put((byte)tmpInt);
			}
			if ( relInfo.getTabInfo().get(i).getType().equals("REAL") ){
				float tmpFloat = Float.parseFloat(getValues().get(i));
				buff.put((byte)tmpFloat);	
			}
			if ( relInfo.getTabInfo().get(i).getType().contains("VARCHAR(") ){
				String tmpString = getValues().get(i);
				byte[] tmpByte = tmpString.getBytes();
				buff.put(tmpByte);
			}
		}
		*/
	}
	
	
	
	
	
	
	
	
	
	
	
	
	// à revoir
	public void readFromBuffer(ByteBuffer buff, int pos) {
		buff.position(pos);
		int i;
		int longueurChaine;
		
		for (i=0; i< relInfo.getTabInfo().size();i++) {
			if ( relInfo.getTabInfo().get(i).getType().equals("INTEGER") ){
				values.add(Integer.toString(buff.getInt()));
			}
			else if ( relInfo.getTabInfo().get(i).getType().equals("REAL") ){
				values.add(Float.toString(buff.getFloat()));
				
			}
			else if ( relInfo.getTabInfo().get(i).getType().contains("VARCHAR(") ){
				longueurChaine = Integer.parseInt(relInfo.getTabInfo().get(i).getType().substring(7)); 
				String s = "";
				for (int j=0; j<longueurChaine; j++ ) {
					s = s + buff.getChar();
				}
				values.add(s);
			}
		}
	}
	// Tester si c'est correct le code 
	
	
	public int getWrittenSize() {
		byte[] tabByte;
		byte sommeByte = 0;
		for(int i =0; i<values.size();i++) {
			tabByte = values.get(i).getBytes();
			for(Byte b : tabByte) {
				sommeByte += b;
			}
		}
		return ((int)sommeByte);
	}

}
