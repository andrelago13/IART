package algorithm;

import java.util.ArrayList;
import java.util.Random;

import edu.uci.ics.jung.graph.Graph;
import graph.DirectedEdge;
import graph.GraphNode;
import utils.ArrayUtils;
import utils.BinaryUtils;

public class MT_ORD_Generation {
	
	private double mutation_probability;
	private ArrayList<MT_ORD_Chromossome> chromossomes;
	
	private Graph<GraphNode, DirectedEdge> graph;
	private int hours_per_day;
	private double financial_limit;
	private ArrayList<Transport> transports;

	public MT_ORD_Generation(double mutation_prob, int size, int number_transports, int number_monuments, int number_days) {
		this.mutation_probability = mutation_prob;
		
		chromossomes = MT_ORD_Factory.generateChromossomes(size, number_transports, number_monuments, number_days);
	}
	
	public MT_ORD_Generation(double mutation_prob, ArrayList<MT_ORD_Chromossome> chromossomes) {
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
	
	public ArrayList<MT_ORD_Chromossome> evolveChromossomes(Random rand) {
		int num_chromossomes = chromossomes.size();
		
		ArrayList<Double> adaptation = getAdaptation(chromossomes);
		double total_adaptation = 0;
		for(int i = 0; i < adaptation.size(); ++i) {
			total_adaptation += adaptation.get(i);
		}
		double current_adaptation = 0;
		for(int i = 0; i < adaptation.size(); ++i) {
			current_adaptation = adaptation.get(i)/total_adaptation + current_adaptation;
			adaptation.set(i, current_adaptation);
		}
		
		// Adaptation now holds the top interval for each chromossome to be selected
		
		ArrayList<MT_ORD_Chromossome> selected_chromossomes = new ArrayList<MT_ORD_Chromossome>();
		for(int i = 0; i < num_chromossomes; ++i) {
			double slice = rand.nextDouble();
			
			for(int j = 0; j < adaptation.size(); ++j) {
				if(slice < adaptation.get(j)) {
					selected_chromossomes.add(chromossomes.get(j).clone());
				}
			}
		}
		
		// At this point, selected_chromossomes must have the same size as chromossomes
		
		int first_cross_index = -1;
		
		ArrayList<Integer> possible_crossovers = null;
		if(num_chromossomes > 0) {
			possible_crossovers = selected_chromossomes.get(0).getCrossovers();
		} else {
			possible_crossovers = new ArrayList<Integer>();
		}
		int n_possible_crossovers = possible_crossovers.size();
		
		for(int i = 0; i < num_chromossomes; ++i) {
			double cross = rand.nextDouble();
			if(cross <= adaptation.get(i)) {
				if(first_cross_index == -1) {
					first_cross_index = i;
				} else {
					int cross_index = possible_crossovers.get(rand.nextInt(n_possible_crossovers));
					MT_ORD_Chromossome[] crossed_chromossomes = MT_ORD_Chromossome.crossover(selected_chromossomes.get(first_cross_index), selected_chromossomes.get(i), cross_index);
					
					selected_chromossomes.set(first_cross_index, crossed_chromossomes[0]);
					selected_chromossomes.set(i, crossed_chromossomes[1]);
					
					first_cross_index = -1;
				}
			}
		}
		
		return selected_chromossomes;
	}
	
	public static ArrayList<MT_ORD_Chromossome> mutateChromossomes(Random rand, double mutation_probability, ArrayList<MT_ORD_Chromossome> chromossomes) {
		ArrayList<MT_ORD_Chromossome> result = ArrayUtils.clone(chromossomes);
		int c_size = result.size();
		
		if(c_size > 0) {
			ArrayList<Integer> crossovers = result.get(0).getCrossovers();
			
			for(int i = 0; i < c_size; ++i) {
				String content = result.get(i).getContent();
				String new_content = BinaryUtils.probabilityNegation(rand, mutation_probability, content);
				
				if(result.get(i).isValidContent(new_content, crossovers) == 0) {
					result.get(i).setContent(new_content);	
				}
			}
		}
		
		return result;
	}
	
	public MT_ORD_Generation evolve(Graph<GraphNode, DirectedEdge> graph, int hours_per_day, double financial_limit, ArrayList<Transport> transports) {
		this.graph = graph;
		this.hours_per_day = hours_per_day;
		this.financial_limit = financial_limit;
		this.transports = transports;
		
		Random rand = new Random();
		ArrayList<MT_ORD_Chromossome> evolved_chromossomes = evolveChromossomes(rand);
		evolved_chromossomes = mutateChromossomes(rand, mutation_probability, evolved_chromossomes);
		
		return new MT_ORD_Generation(mutation_probability, evolved_chromossomes);
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

	public ArrayList<Double> getAdaptation(ArrayList<MT_ORD_Chromossome> chromossomes) {
		ArrayList<Double> result = new ArrayList<Double>();
		
		for(int i = 0; i < chromossomes.size(); ++i) {
			result.add(chromossomes.get(i).adaptation(graph, hours_per_day, financial_limit, transports));
		}
		
		return result;
	}
}
