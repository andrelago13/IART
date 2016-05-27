package algorithm;

import graph.Monument;

import java.util.ArrayList;

public class SolutionDay {
	
	private ArrayList<Transport> transports;
	private ArrayList<Monument> monuments;
	
	public int totalValue = -1;
	public double totalTime = 0;
	public double totalMoney = 0;
	
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
		totalValue = 0;
		
		for(int i = 0; i < monuments.size(); ++i) {
			Monument m = monuments.get(i);
			Transport t = transports.get(i);
			totalValue += m.value;
			result += "" + '\t' + t.name + '\n';
			result += "" + '\t' + '\t' + m.getName() + " (" + m.value + ")" + '\n';
		}
		result += "" + '\t' + transports.get(transports.size()-1).name + '\n';
		result += "Total value: " + totalValue + "; Total cost: " + totalMoney + "; Total time: " + totalTime + '\n';
		
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
