import java.nio.Buffer;
import java.util.ArrayList;

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
	
	public int getWrittenSize() {
		byte[] tabByte;
		byte sommeByte = 0;
		for(int i =0; i<values.size();i++) {
			tabByte = values.get(i).getBytes();
			for(Byte b : tabByte) {
				sommeByte+= b;
			}
		}
		return (int)sommeByte;
	}

	
}
