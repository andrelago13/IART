package graph;

import java.io.Serializable;


/******************************************************************************
 *  Compilation:  javac DirectedEdge.java
 *  Execution:    java DirectedEdge
 *  Dependencies: StdOut.java
 *
 *  Immutable weighted directed edge.
 *
 ******************************************************************************/
/**
 * 
 *  Modified Class at : https://github.com/COMSYS/FootPath
 *  
 */
 
public class DirectedEdge implements Serializable{ 
    /**
	 * 
	 */
	private static final long serialVersionUID = -9074416579376692922L;
	 
	private final GraphNode startNode;
    private final GraphNode endNode;
    private Double length;
    private final int speedMax;
    private boolean isOneway;
    private String type;
    private String name;
    @SuppressWarnings("unused")
	private String other_tags;
    
    private float weight;
    private long way_id; 

	public boolean processed = false;
	public boolean partOfShortestPath = false;
 
    /**
     * Constructor
     */
    public DirectedEdge(GraphNode startNode, GraphNode endNode,
    		double length, int speedMax, boolean isOneway, 
    		String type, String name, float weight, long way_id ) {
        
        this.startNode = startNode;
        this.endNode = endNode;
        this.speedMax = speedMax;
        this.isOneway = isOneway;
        this.length = length;
        this.type = type;
        this.name = name;
        this.weight = weight;
        this.way_id = way_id;
    }
    
    public DirectedEdge(GraphNode startNode, GraphNode endNode,
    		long way_id, float weight, String name,  boolean isOneway ) {
        
        this.startNode = startNode;
        this.endNode = endNode;
        this.speedMax = -1;
        this.isOneway = isOneway;
        this.type = null;
        this.name = name;
        this.weight = weight;
        this.way_id = way_id;
    }
    
	public DirectedEdge() {
		
        this.startNode = null;
        this.endNode = null;
        this.speedMax = -1;
        this.isOneway = false;
        this.length = 0.00;
        this.type = null;
        this.name = null;
        this.weight = 0;
        this.way_id = 0;
	}
	
    public GraphNode from() {
        return startNode;
    }
 
 
    public GraphNode to() {
        return endNode;
    }
 
 
    public int speedMax() {
        return speedMax;
    }
    
    public double getLength() {
        return length;
    }
    
    public boolean isOneway() {
        return isOneway;
    }
    
    public void setType(String type){
		this.type = type;
	}
    public String getType(){
		return type;
	}
    
    public void setWayId(long way_id){
		this.way_id = way_id;
	}
    public long getWayId(){
		return this.way_id;
	}
    
    public void setName(String name) {
		this.name = name;
	}
    
    public void setOtherTags(String other_tags) {
		this.other_tags = other_tags;
	}
    
    public double getWeight(){
    	//return (this.weight*60);
    	return (this.length);
	}
    
    public String toString() {
        return startNode.getId()  + "-&gt;" + endNode.getId() + " " + length;
    }
 
	public String getName() {
		return name;
	}
	public float getWalkWeight(){
		float walk_weight = (float) ((this.length/3)*60);
		return walk_weight;
	}
	
	@Override 
	public int hashCode() { 
	    int hash = 1;
	    hash = hash+startNode.hashCode();
	    hash = hash+endNode.hashCode();
	    hash = hash+ Math.round(weight*1000);
	    return hash;
	  }
 
}