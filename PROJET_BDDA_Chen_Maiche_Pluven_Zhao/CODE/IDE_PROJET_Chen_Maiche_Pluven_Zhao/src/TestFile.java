
public class TestFile {

	public static void main(String[] args) {
		Queue f = new Queue();
		f.add(1);
		Cellule a = f.add(2);
		System.out.println(f.pop());
		f.add(3);
		f.add(4);
		f.del(a);
		f.afficheFile();
		System.out.println(f.isVoid());
		System.out.println(f.pop());
		System.out.println(f.pop());
		System.out.println(f.isVoid());
	}

}
