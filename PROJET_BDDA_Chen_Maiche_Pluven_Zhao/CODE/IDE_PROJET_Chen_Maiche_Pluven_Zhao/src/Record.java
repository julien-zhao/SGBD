import java.util.ArrayList;
import java.util.List;
import java.nio.ByteBuffer;

public class Record {
	/**
	 * Cette RelationInfo correspond à la relation à laquelle appartient le record
	 */
	private RelationInfo relInfo;
	/**
	 * la liste ou tableau de chaîne de caractères qui correspond aux
	 * valeurs du record.
	 */
	private ArrayList<String> values;
	private int bufferSize = 0;

	public Record(RelationInfo relInfo) {
		this.relInfo = relInfo;
		values = new ArrayList<String>();
	}

	public RelationInfo getRelInfo() {
		return relInfo;
	}

	public void setValues(String... s) {
		values.clear();
		for (String str : s) {
			values.add(str);
		}
	}

	public void addTuple(List<String> unTuple) {
		// pour chaque colone,
		bufferSize = unTuple.size() * 4;

		if (values.size() == 0) {
			for (int i = 0; i < unTuple.size(); i++) {
				// tester si le type d'entrer est le meme que le type des colonnes
				if (relInfo.getTabInfo().get(i).getType().equals("INTEGER")) {
					bufferSize += 4;
				}
				if (relInfo.getTabInfo().get(i).getType().equals("REAL")) {
					bufferSize += 4;
				}
				values.add(unTuple.get(i));
			}
			for (int i = 0; i < relInfo.getTabInfo().size(); i++) {
				if (relInfo.getTabInfo().get(i).getType().contains("VARCHAR(")) {
					bufferSize += getValues().get(i).length() * 2;
				}
			}
		}
		if (unTuple.size() == relInfo.getTabInfo().size()) {
			for (int i = 0; i < unTuple.size(); i++) {
				values.set(i, unTuple.get(i));
			}
		}
	}

	public String afficheValues() {
		return values.toString();
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public ArrayList<String> getNomColonne() {
		ArrayList<String> res = new ArrayList<>();
		for (ColInfo uneCol : relInfo.getTabInfo()) {
			res.add(uneCol.getColonne());
		}
		return res;
	}

	public ArrayList<String> getTypeColonne() {
		ArrayList<String> res = new ArrayList<>();
		for (ColInfo uneCol : relInfo.getTabInfo()) {
			res.add(uneCol.getType());
		}
		return res;
	}

	public void writeToBuffer(ByteBuffer buff, int pos) {

		buff.position(pos);

		// boucle qui met les tailles des valeurs dans le buff
		for (int i = 0; i < relInfo.getTabInfo().size(); i++) {

			if (relInfo.getTabInfo().get(i).getType().equals("INTEGER")) {
				// posList.add(4);
				buff.putInt(4);
			}
			if (relInfo.getTabInfo().get(i).getType().equals("REAL")) {
				// posList.add(4);
				buff.putInt(4);
			}
			if (relInfo.getTabInfo().get(i).getType().contains("VARCHAR(")) {
				int sizeString = getValues().get(i).length() * 2;
				// posList.add(sizeString);
				buff.putInt(sizeString);
			}
		}

		// boucle qui transforme les valeurs en byte
		for (int i = 0; i < relInfo.getTabInfo().size(); i++) {
			if (relInfo.getTabInfo().get(i).getType().equals("INTEGER")) {
				int tmpInt = Integer.parseInt(getValues().get(i));
				buff.putInt(tmpInt);
			}
			if (relInfo.getTabInfo().get(i).getType().equals("REAL")) {
				float tmpFloat = Float.parseFloat(getValues().get(i));
				buff.putFloat(tmpFloat);
			}
			if (relInfo.getTabInfo().get(i).getType().contains("VARCHAR(")) {
				String tmpString = getValues().get(i);

				for (int j = 0; j < tmpString.length(); j++) {
					buff.putChar(tmpString.charAt(j));
				}
			}
		}

	}

	
	/**
	 * Il sert a obtenir le record en format byte
	 * 
	 * @param buff un buffer alloué par l'appelant
	 * @param pos un entier correspondant à une position dans le buffer
	 */
	public void getBufferToByte(ByteBuffer buff, int pos) {
		buff.position(0);
		while (buff.hasRemaining()) {
			bufferSize++;
			System.out.print(buff.get() + " ");
		}
	}

	
	/**
	 * cette méthode va lire les valeurs du Record depuis le buffer à partir
	 * de pos
	 * 
	 * @param buff un buffer alloué par l'appelant
	 * @param pos un entier correspondand à une position dans le buffer
	 */
	public void readFromBuffer(ByteBuffer buff, int pos) {
		buff.position(pos);
		ArrayList<Integer> posList = new ArrayList<>();

		for (int i = 0; i < relInfo.getTabInfo().size(); i++) {
			bufferSize += 4;
			posList.add(buff.getInt());
		}
		ArrayList<String> list = getTypeColonne();
		StringBuilder sb = new StringBuilder("");

		for (int i = 0; i < posList.size(); i++) {
			bufferSize += 4;
			if (list.get(i).equals("INTEGER")) {
				sb.append(buff.getInt());
				values.add(sb.toString());
				sb = new StringBuilder("");
			}
			if (list.get(i).equals("REAL")) {
				sb.append(buff.getFloat());
				values.add(sb.toString());
				sb = new StringBuilder("");

			}
			if (list.get(i).startsWith("VARCHAR")) {
				for (int j = 0; j < posList.get(i) / 2; j++) {
					bufferSize += 2;
					sb.append(buff.getChar());
				}
				values.add(sb.toString());
				sb = new StringBuilder("");
			}
		}
	}

	public String toString() {
		String res = "";
		for (String s : values) {
			res += s + " ";
		}
		return res;

	}

	public int getWrittenSize() {
		return this.bufferSize;
	}

	public String getColValue(String s) {
		int index = getNomColonne().indexOf(s);
		return values.get(index);
	}

}
