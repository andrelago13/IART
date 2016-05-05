package algorithm;

import java.util.ArrayList;
import java.util.Random;

import utils.ArrayUtils;
import utils.BinaryUtils;

public class MT_ORD_Generation {
	
	private double crossover_probability;
	private double mutation_probability;
	private ArrayList<MT_ORD_Chromossome> chromossomes;

	public MT_ORD_Generation(double crossover_prob, double mutation_prob, int size, int number_transports, int number_monuments, int number_days) {
		this.crossover_probability = crossover_prob;
		this.mutation_probability = mutation_prob;
		
		chromossomes = MT_ORD_Factory.generateChromossomes(size, number_transports, number_monuments, number_days);
	}
	
	public MT_ORD_Generation(double crossover_prob, double mutation_prob, ArrayList<MT_ORD_Chromossome> chromossomes) {
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
	
	private ArrayList<MT_ORD_Chromossome> evolveChromossomes(Random rand) {
		// FIXME evolução depende da avaliação do cromossoma e não de uma probabilidade de crossover
		
		ArrayList<MT_ORD_Chromossome> evolved_chromossomes = ArrayUtils.clone(chromossomes);
		
		int first_cross_index = -1;
		
		ArrayList<Integer> possible_crossovers = null;
		if(evolved_chromossomes.size() > 0) {
			possible_crossovers = evolved_chromossomes.get(0).getCrossovers();
		} else {
			possible_crossovers = new ArrayList<Integer>();
		}
		int n_possible_crossovers = possible_crossovers.size();
		
		for(int i = 0; i < evolved_chromossomes.size(); ++i) {
			double cross = rand.nextDouble();
			if(cross <= crossover_probability) {
				if(first_cross_index == -1) {
					first_cross_index = i;
				} else {
					int cross_index = possible_crossovers.get(rand.nextInt(n_possible_crossovers));
					MT_ORD_Chromossome[] crossed_chromossomes = MT_ORD_Chromossome.crossover(evolved_chromossomes.get(first_cross_index), evolved_chromossomes.get(i), cross_index);
					
					evolved_chromossomes.set(first_cross_index, crossed_chromossomes[0]);
					evolved_chromossomes.set(i, crossed_chromossomes[1]);
					
					first_cross_index = -1;
				}
			}
		}
		
		return evolved_chromossomes;
	}
	
	private ArrayList<MT_ORD_Chromossome> mutateChromossomes(Random rand, ArrayList<MT_ORD_Chromossome> chromossomes) {
		ArrayList<MT_ORD_Chromossome> result = ArrayUtils.clone(chromossomes);
		int c_size = result.size();
	
		for(int i = 0; i < c_size; ++i) {
			String content = result.get(i).getContent();
			result.get(i).setContent(BinaryUtils.probabilityNegation(rand, mutation_probability, content));
		}
		
		// FIXME se cromossoma passar a ser invalido anula mutação
		
		return result;
	}
	
	public MT_ORD_Generation evolve() {
		// TODO
		Random rand = new Random();
		ArrayList<MT_ORD_Chromossome> evolved_chromossomes = evolveChromossomes(rand);
		evolved_chromossomes = mutateChromossomes(rand, evolved_chromossomes);
		
		return new MT_ORD_Generation(crossover_probability, mutation_probability, evolved_chromossomes);
	}

	public String toString() {
		String result = "";
		int size = chromossomes.size();
		
		for(int i = 0; i < size; ++i) {
			result += chromossomes.get(i);
			if(i < size - 1) {
				result += '\n';
			}
		}
		
		return result;
	}
}
