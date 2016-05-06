package graph;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Set;
 
 
/**
 * 
 *   Modified Class at : https://github.com/COMSYS/FootPath
 *  
 *
 */
public class GraphNode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -951013750518377697L;
	/**
	 * 
	 */
 
	private double lon;
	private double lat;
	private long id;
	
	private LinkedList<DirectedEdge> from = new LinkedList<DirectedEdge>();
	private LinkedList<DirectedEdge> to = new LinkedList<DirectedEdge>();
	
	public double distance;
	public boolean selected = false;
	public DirectedEdge shortestPath = null;
 
	public GraphNode() {
		this.lon = 0.0;
		this.lat = 0.0;
		this.id = 0;
	}
/*	public KdTree.XYZPoint toXYZPoint(){
		return new KdTree.XYZPoint(""+this.id,this.lat,this.lon,0);
	}*/
	
	public GraphNode(double lat, double lon, long id) {
		this.lon = lon;
		this.lat = lat;
		this.id = id;
	}
	
 
	public double getLon() {
		return lon;
	}
	
	public double getLat() {
		return lat;
	}
	
	
	public void addFrom(DirectedEdge edge) {
		if(!from.contains(edge))
			from.add(edge);
	}
	
	public void addTo(DirectedEdge edge) {
		if(!to.contains(edge))
			to.add(edge);
	}
	
	public LinkedList<DirectedEdge> from() {
		return from;
	}
	
	public LinkedList<DirectedEdge> to() {
		return to;
	}
	
	
	public long getId() {
		return id;
	}
	
	public void setLon(double lon) {
		this.lon = lon;
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}
		
	public void setId(long l){
		this.id = l;
	}
	
	@Override 
	public boolean equals(Object node) {
		if(node == null)
			return false;
        GraphNode node_x = (GraphNode) node;
        return (node_x.id == this.id);
    }
	
	public String toString(){
		String rt_str;
		rt_str = this.id+", ("+this.lat+ "," + this.lon+")";
		return	rt_str;
	}
	
	@Override 
	public int hashCode() { 
	    int hash = 1;
	    hash = hash+((int) Math.round(this.lat*1000) );
	    hash = hash+((int) Math.round(this.lon*1000) );
	    hash = (int) (hash+this.id);
	    return hash;
	  }
}