import java.nio.Buffer;
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
	
	
	public String getValues() {
		return values.toString();
	}
	
	public void writeToBuffer(Buffer buff, int pos) {
		buff.position(pos);
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
