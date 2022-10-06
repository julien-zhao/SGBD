
public class FileTest {

	public static void main(String[] args) {
		File f = new File();
		f.add(1);
		Cellule a = f.add(2);
		System.out.println(f.pop());
		f.add(3);
		f.add(4);
		f.del(a);
		f.afficheFile();

	}

}
