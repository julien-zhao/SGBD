import java.nio.Buffer;
import java.util.ArrayList;

public class Record {
	RelationInfo relInfo = new RelationInfo();
	ArrayList<String> values ;//= new ArrayList<String>();
	
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
}
