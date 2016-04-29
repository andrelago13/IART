package algorithm;

import java.util.ArrayList;

public class MT_ORD_Generation {
	
	private int crossover_probability;
	private int mutation_probability;
	private int size;
	private ArrayList<MT_ORD_Chromossome> chromossomes;

	public MT_ORD_Generation(int crossover_prob, int mutation_prob, int size, int number_transports, int number_monuments, int number_days) {
		this.crossover_probability = crossover_prob;
		this.mutation_probability = mutation_prob;
		
		chromossomes = MT_ORD_Factory.generateChromossomes(size, number_transports, number_monuments, number_days);
	}
	
	public MT_ORD_Generation(int crossover_prob, int mutation_prob, ArrayList<MT_ORD_Chromossome> chromossomes) {
		this.crossover_probability = crossover_prob;
		this.mutation_probability = mutation_prob;
		this.chromossomes = chromossomes;
	}
	
	public int getNumTransports() {
		if(chromossomes == null || chromossomes.size() == 0)
			return -1;
		
		return chromossomes.get(0).getNumTransports();
	}
	
	public int getNumMonuments() {
		if(chromossomes == null || chromossomes.size() == 0)
			return -1;
		
		return chromossomes.get(0).getNumMonuments();
	}
	
	public int getNumDays() {
		if(chromossomes == null || chromossomes.size() == 0)
			return -1;
		
		return chromossomes.get(0).getNumDays();
	}
	
	public MT_ORD_Generation evolve() {
		// TODO
		return null;
	}
}
