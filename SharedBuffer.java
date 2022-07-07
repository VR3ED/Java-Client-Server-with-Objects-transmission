
public class SharedBuffer {
	private TheData content;

	SharedBuffer() {
		content=null;
	}
	
	public synchronized TheData getData() {
		return content;
	}
	
	public synchronized void setData(TheData d) {
		content=d;
	}
}
