package graph;

public class Monument {
	
	private long node_id;
	private String name;
	public double value;
	
	public Monument(long id, String name) {
		this(id, name, -1);
	}
	
	public Monument(long id, String name, int value) {
		this.node_id = id;
		this.name = name;
		this.value = value;
	}
	
	public long getNodeID() {
		return node_id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setNodeID(long id) {
		this.node_id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
