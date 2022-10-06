import java.util.ArrayList;

public class Record {
	RelationInfo relInfo = new RelationInfo();
	ArrayList<String> values ;//= new ArrayList<String>();
	
	public Record(RelationInfo relInfo) {
		this.relInfo = relInfo;
		values = new ArrayList<String>();
	}


}
