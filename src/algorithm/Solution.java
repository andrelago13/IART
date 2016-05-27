package algorithm;

import java.util.ArrayList;

public class Solution {
	
	private ArrayList<SolutionDay> days;
	
	public Solution() {
		days = new ArrayList<SolutionDay>();
	}
	
	public String toString() {
		String result = "";
		int totalValue = 0;
		
		for(int i = 0; i < days.size(); ++i) {
			result += "Day " + i + '\n' + days.get(i).toString();
			totalValue += days.get(i).totalValue;
		}
		result += "Total trip value: " + totalValue + "." + '\n';
		
		return result;
	}
	
	public void addDay(SolutionDay day) {
		days.add(day);
	}
	
	public void removeDay(SolutionDay day) {
		if(days.contains(day)) {
			days.remove(day);
		}
	}

	public ArrayList<SolutionDay> getDays() {
		return days;
	}
}
