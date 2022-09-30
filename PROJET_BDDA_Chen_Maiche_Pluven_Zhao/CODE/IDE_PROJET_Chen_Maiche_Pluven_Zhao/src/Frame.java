import java.nio.ByteBuffer;

public class Frame {
	private ByteBuffer bb;
	private PageId pId;
	private int pinCount;
	private boolean dirty;
	private Cellule posFile;
}
