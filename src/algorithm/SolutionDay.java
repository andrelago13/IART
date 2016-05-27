package algorithm;

import graph.Monument;

import java.util.ArrayList;

public class SolutionDay {
	
	private ArrayList<Transport> transports;
	private ArrayList<Monument> monuments;
	
	public SolutionDay() {
		transports = new ArrayList<Transport>();
		monuments = new ArrayList<Monument>();
	}
	
	public void add(Transport t) {
		transports.add(t);
	}
	
	public void add(Monument m) {
		monuments.add(m);
	}
	
	public void remove(Monument m) {
		if(monuments.contains(m)) {
			monuments.remove(m);
		}
	}
	
	public void remove(Transport t) {
		if(transports.contains(t)) {
			transports.remove(t);
		}
	}
	
	public String toString() {
		String result = "";
		int total_value = 0;
		
		for(int i = 0; i < monuments.size(); ++i) {
			Monument m = monuments.get(i);
			Transport t = transports.get(i);
			total_value += m.value;
			result += "" + '\t' + t.name + '\n';
			result += "" + '\t' + '\t' + m.getName() + " (" + m.value + ")" + '\n';
		}
		result += "" + '\t' + transports.get(transports.size()-1).name + '\n';
		result += "Total value: " + total_value + '\n';
		
		return result;
	}
	
	public int totalValue() {
		int value = 0;
		
		for(Monument m : monuments) {
			value += m.value;
		}
		
		return value;
	}
}
