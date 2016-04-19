package algorithm;

import java.util.ArrayList;

public class Generation {
	
	private int size;
	private int number;
	private ArrayList<Chromossome> population;
	
	public Generation(int number, int size) {
		this.size = size;
		this.number = number;
		this.population = new ArrayList<Chromossome>();
		
		for(int i = 0; i < size; ++i) {
			Chromossome t = new Chromossome(20);
			t.generate();
			population.add(t);
			System.out.println(t);
		}
	}
	
	public Generation(int number, int size, ArrayList<Chromossome> initial_population) {
		// TODO
		this.size = size;
		this.number = number;
	}

}
