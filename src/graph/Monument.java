package graph;

import java.util.Comparator;
import java.util.Random;

public class Monument {
	
	private long node_id;
	private String name;
	public double value = 0;
	public double visit_time_hours;
	
	public int position;
	public int transport;
	
	public GraphNode graphnode;
	
	public static Comparator<Monument> postitionComparator = new Comparator<Monument>() {
        @Override
        public int compare(Monument mon1, Monument mon2) {
        	if(mon1.position < mon2.position) {
        		return -1;
        	} else if (mon1.position > mon2.position) {
        		return 1;
        	} else {
        		Random r = new Random();
        		if(r.nextBoolean()) {
        			return -1;
        		} else {
        			return 1;
        		}
        	}
        }
	};
	
	public Monument(long id, String name, double visit_time_hours, GraphNode graphnode) {
		this(id, name, visit_time_hours, graphnode, 1);
	}
	
	public Monument(long id, String name, double visit_time_hours, GraphNode graphnode, double value) {
		this.node_id = id;
		this.name = name;
		this.value = value;
		this.visit_time_hours = visit_time_hours;
		this.graphnode = graphnode;
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

	public double getVisitTime() {
		return this.visit_time_hours;
	}

	public void setVisitTime(double visit_time_hours) {
		this.visit_time_hours = visit_time_hours;
	}
	
	public String toString() {
		return name;
	}
}
