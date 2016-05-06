package graph;

import java.util.LinkedList;

/**
 * Used for OSM parsing
 * 
 * Modified Class at : https://github.com/COMSYS/FootPath
 *
 */
public class GraphWay {
	
	// all nodes on this path ( ref0 -&gt; ref1 -&gt; ref2  -&gt; ...)
	private LinkedList refs;
	private long id;
    private String type;
    private String name;
    private String other_tags;
    private int speedMax;
    private boolean isOneway;
    
    public boolean valid = false;
 
	public GraphWay() {
		this.refs = new LinkedList();
		this.id = 0;
		this.type = null;
		this.name = null;
	    this.speedMax = -1;
	    this.isOneway = false;
	    this.other_tags = null;
	}
 
	public GraphWay(LinkedList refs, long id,boolean isOneway,int speedMax, String type, String name) {
		this.refs = refs;
		this.id = id;
		this.speedMax = speedMax;
	    this.isOneway = isOneway;
	    this.name = name;
	    this.type=type;
 
	}
	
	public LinkedList getRefs() {
		return refs;
	}
	
	public long getId() {
		return id;
	}
	
	
	public void setRefs(LinkedList refs) {
		this.refs = refs;
	}
	
	public void setId(long l) {
		this.id = l;
	}
	
	
	public void addRef(long ref){
		this.refs.add(new Long(ref));
	}
    public void setType(String type){
		this.type = type;
	}
    
    public void setName(String name) {
		this.name = name;
	}
    
    public void setSpeedMax(int speedMax) {
        this.speedMax = speedMax;
    }
    
    public int getSpeedMax() {
        return speedMax;
    }
    public boolean getOneway() {
        return isOneway;
    }
    public String getType() {
        return type;
    }
    
    public void setOneway(boolean isOneway) {
        this.isOneway = isOneway;
    }
    public boolean isOneway() {
        return isOneway;
    }
    
    public void setOtherTags(String other_tags) {
		this.other_tags = other_tags;
	}
 
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
 
}