package algorithm;

import java.util.ArrayList;

public class Solution {
	
	private ArrayList<SolutionDay> days;
	
	public Solution() {
		days = new ArrayList<SolutionDay>();
	}
	
	public String toString() {
		String result = "";
		
		for(int i = 0; i < days.size(); ++i) {
			if(days.get(i) == null) {
				System.out.println("null");
			} else {
				System.out.println("not null");
			}
			result += "Day " + i + '\n' + days.get(i).toString();
		}
		
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
