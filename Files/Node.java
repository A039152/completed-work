package files;

/**
 * The File type encapsulates name, size, and mod date,
 * along with display logic.
 */
public abstract class Node {
	protected String name;
	
	public Node(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract int getSize();
	public abstract String asHTML();
	public void add(Node child) {}
}
