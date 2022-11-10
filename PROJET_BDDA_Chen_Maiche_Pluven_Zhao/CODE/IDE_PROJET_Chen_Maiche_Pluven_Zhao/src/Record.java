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
		
		if(values.size() == 0) {
			for(int i =0; i<unTuple.size();i++) {
				values.add(unTuple.get(i));
			}
		}
		if(unTuple.size() == relInfo.getTabInfo().size()) {
			for(int i =0; i<unTuple.size();i++) {
				values.set(i,unTuple.get(i));
			}
		}
	}
	
	public String getValues() {
		return values.toString();
	}
	
	public ArrayList<String> getNomColonne() {
		ArrayList<String> res= new ArrayList<>();
		for(ColInfo uneCol: relInfo.getTabInfo()){
			res.add(uneCol.getColonne());
		}
		return res;
	}
	
	public void writeToBuffer(ByteBuffer buff, int pos){
		buff.position(pos); 
		for (int i=0; i< relInfo.getTabInfo().size();i++) {
			if ( relInfo.getTabInfo().get(i).getType().equals("INTEGER") ){
				int tmpInt = Integer.parseInt(relInfo.getTabInfo().get(i).getColonne());
				buff.put((byte)tmpInt);

			}

			if ( relInfo.getTabInfo().get(i).getType().equals("REAL") ){
				
				float tmpFloat = Float.parseFloat(relInfo.getTabInfo().get(i).getColonne());
				buff.put((byte)tmpFloat);	
			}

			if ( relInfo.getTabInfo().get(i).getType().contains("VARCHAR(") ){		
				String tmpString = relInfo.getTabInfo().get(i).getColonne();
				byte[] tmpByte = tmpString.getBytes();
				buff.put(tmpByte);
			}
		}
	}
	
	
	public void readFromBuffer2(ByteBuffer buff, int pos) {
		buff.position(pos);
		while(buff.hasRemaining()) {
			System.out.print(buff.get()+",");
		}
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
