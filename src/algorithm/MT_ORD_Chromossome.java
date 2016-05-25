package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

import edu.uci.ics.jung.graph.Graph;
import graph.Dijkstra;
import graph.DirectedEdge;
import graph.GraphNode;
import graph.Monument;
import utils.BinaryUtils;

public class MT_ORD_Chromossome implements Cloneable {

	private int number_transports;
	private int number_monuments;
	private int number_days;
	
	private String content;
	
	public double adaptation = 0;
	public double slice = 0;
	
	public static Comparator<MT_ORD_Chromossome> adaptationComparator = new Comparator<MT_ORD_Chromossome>() {
        @Override
        public int compare(MT_ORD_Chromossome c1, MT_ORD_Chromossome c2) {
        	if(c1.adaptation < c2.adaptation)
        		return -1;
        	if(c1.adaptation > c2.adaptation)
        		return 1;
        	return 0;
        }
	};
	
	public MT_ORD_Chromossome(int number_transports, int number_monuments, int number_days) {
		this(number_transports, number_monuments, number_days, "");
	}
	
	public MT_ORD_Chromossome(int number_transports, int number_monuments, int number_days, String content) {
		this.number_transports = number_transports;
		this.number_monuments = number_monuments;
		this.number_days = number_days;
		this.content = content;
	}
	
	public ArrayList<Integer> generate() {
		String transports_base = BinaryUtils.zeroBaseForNNumbers(number_transports);
		String monuments_base = BinaryUtils.zeroBaseForNNumbers(number_monuments);
		
		return generate(transports_base, monuments_base, number_monuments);		
	}
	
	public ArrayList<Integer> generate(String transports_base, String monuments_base, int number_monuments) {
		this.content =  "";
		int transports_base_size = transports_base.length();
		int monuments_base_size = monuments_base.length();
		
		ArrayList<Integer> monuments = new ArrayList<Integer>();
		for(int i = 0; i < number_monuments; ++i) {
			monuments.add(i);
		}
		
		ArrayList<Integer> possible_crossovers = new ArrayList<Integer>();
		
		// GENERATE FIRST TRANSPORTS
		Random r = new Random();
		for(int i = 0; i < this.number_days; ++i) {
			int transport = r.nextInt(number_transports);
			String trans_string_temp = Integer.toBinaryString(transport);
			content += transports_base.substring(0, transports_base_size - trans_string_temp.length()) + trans_string_temp;
			possible_crossovers.add(content.length());
		}

		// GENERATE ORDERS AND TRANSPORTS

		for(int i = 0; i < this.number_monuments; ++i) {
			int monuments_size = monuments.size();
			int monument;
			if(monuments_size > 1) {
				int pos = r.nextInt(monuments_size);
				monument = monuments.get(pos);
				monuments.remove(pos);
			} else {
				monument = monuments.get(0);
			}
			String mon_string_temp = Integer.toBinaryString(monument);
			content += monuments_base.substring(0, monuments_base_size - mon_string_temp.length()) + mon_string_temp;
			possible_crossovers.add(content.length());

			int transport = r.nextInt(number_transports);
			String trans_string_temp = Integer.toBinaryString(transport);
			content += transports_base.substring(0, transports_base_size - trans_string_temp.length()) + trans_string_temp;
			if(i < this.number_monuments - 1)
				possible_crossovers.add(content.length());
		}
		
		return possible_crossovers;
	}
	
	public static ArrayList<Integer> getCrossovers(int number_transports, int number_monuments, int number_days) {
		String transports_base = BinaryUtils.zeroBaseForNNumbers(number_transports);
		String monuments_base = BinaryUtils.zeroBaseForNNumbers(number_monuments);
		int transports_base_size = transports_base.length();
		int monuments_base_size = monuments_base.length();
		String content = "";
		
		ArrayList<Integer> possible_crossovers = new ArrayList<Integer>();
		
		// GENERATE FIRST TRANSPORTS
		Random r = new Random();
		for(int i = 0; i < number_days; ++i) {
			int transport = r.nextInt(number_transports);
			String trans_string_temp = Integer.toBinaryString(transport);
			content += transports_base.substring(0, transports_base_size - trans_string_temp.length()) + trans_string_temp;
			possible_crossovers.add(content.length());
		}

		// GENERATE ORDERS AND TRANSPORTS

		for(int i = 0; i < number_monuments; ++i) {
			int monument = r.nextInt(number_monuments);
			String mon_string_temp = Integer.toBinaryString(monument);
			content += monuments_base.substring(0, monuments_base_size - mon_string_temp.length()) + mon_string_temp;
			possible_crossovers.add(content.length());

			int transport = r.nextInt(number_transports);
			String trans_string_temp = Integer.toBinaryString(transport);
			content += transports_base.substring(0, transports_base_size - trans_string_temp.length()) + trans_string_temp;
			if(i < number_monuments - 1)
				possible_crossovers.add(content.length());
		}
		
		return possible_crossovers;
	}
	
	public ArrayList<Integer> getCrossovers() {
		return getCrossovers(number_transports, number_monuments, number_days);
	}
	
 	public String getContent() {
		return content;
	}
 	
 	public void setContent(String content) {
 		this.content = content;
 	}
	
	public String toString() {
		return content;
	}

	public static MT_ORD_Chromossome[] crossover(MT_ORD_Chromossome c1, MT_ORD_Chromossome c2, int index) {
		MT_ORD_Chromossome[] result = new MT_ORD_Chromossome[2];
		
		String[] new_contents = Chromossome.crossover(c1.content, c2.content, index);
		if(new_contents == null)
			return null;
		
		result[0] = new MT_ORD_Chromossome(c1.number_transports, c1.number_monuments, c1.number_days, new_contents[0]);
		result[1] = new MT_ORD_Chromossome(c1.number_transports, c1.number_monuments, c1.number_days, new_contents[1]);
		
		return result;
	}

	public int getNumTransports() {
		return number_transports;
	}

	public int getNumMonuments() {
		return number_monuments;
	}

	public int getNumDays() {
		return number_days;
	}

	public MT_ORD_Chromossome clone() {
		MT_ORD_Chromossome c = new MT_ORD_Chromossome(number_transports, number_monuments, number_days, new String(content));
		c.adaptation = adaptation;
		c.slice = slice;
		return c;
	}

	public static int isValidContent(String content, ArrayList<Integer> possible_crossovers, int number_transports, int number_monuments, int number_days) {
		String transports_base = BinaryUtils.zeroBaseForNNumbers(number_transports);
		String monuments_base = BinaryUtils.zeroBaseForNNumbers(number_monuments);
		if(content.length() != transports_base.length()*number_days + number_monuments*(transports_base.length() + monuments_base.length()))
			return 1;
		
		int prev_stop = 0;
		for(int i = 0; i < possible_crossovers.size(); ++i) {
			String part = content.substring(prev_stop, possible_crossovers.get(i));
			prev_stop = possible_crossovers.get(i);
			int part_num = Integer.parseInt(part, 2);
			if(i < number_days) {					// MT_MT_MT_MT_...
				if(part_num >= number_transports)
					return 2;
			} else {								// ORD_MT_ORD_MT_...
				if((i - number_days) % 2 == 0) {			// ORD
					if(part_num >= number_monuments) {
						return 3;
					}
				} else {									// MT
					if(part_num >= number_transports) {
						return 4;
					}
				}
			}
		}
		
		return 0;
	}

	public int isValidContent(String content) {
		return isValidContent(content, getCrossovers());
	}
	
	public int isValidContent(String content, ArrayList<Integer> possible_crossovers) {
		return isValidContent(content, possible_crossovers, number_transports, number_monuments, number_days);
	}
	
	private ArrayList<ArrayList<Integer> > adaptationStart(ArrayList<Integer> split_points) {
		ArrayList<Integer> initial_transports = new ArrayList<Integer>();
		ArrayList<Integer> monuments = new ArrayList<Integer>();
		ArrayList<Integer> exit_transports = new ArrayList<Integer>();
		
		int curr_index = 0;
		int prev_index = 0;
		
		for(; curr_index < number_days; ++curr_index) {
			String temp = content.substring(prev_index, split_points.get(curr_index));
			initial_transports.add(Integer.parseInt(temp, 2));
			prev_index = split_points.get(curr_index);
		}
		
		for(int i = 0; i < number_monuments; ++curr_index, ++i) {
			String temp = content.substring(prev_index, split_points.get(curr_index));
			monuments.add(Integer.parseInt(temp, 2));
			
			prev_index = split_points.get(curr_index++);

			if(i < number_monuments - 1) {
				temp = content.substring(prev_index, split_points.get(curr_index));
				exit_transports.add(Integer.parseInt(temp, 2));

				prev_index = split_points.get(curr_index);				
			}
		}
		String temp = content.substring(prev_index, content.length());
		exit_transports.add(Integer.parseInt(temp, 2));
		
		ArrayList<ArrayList<Integer> > result = new ArrayList<ArrayList<Integer> >();
		result.add(initial_transports);
		result.add(monuments);
		result.add(exit_transports);
		
		return result;
	}
	
	public double adaptation(Graph<GraphNode, DirectedEdge> graph, ArrayList<Monument> monument_ids, double hours_per_day, double financial_limit, ArrayList<Transport> transports, ArrayList<Integer> split_points, GraphNode hotel_node) {
		int penalty = 0; // 1 - slight, 2 - severe
		
		// TODO verificar se penalizações se adequam
		
		ArrayList<Integer> initial_transports;
		ArrayList<Integer> monuments;
		ArrayList<Integer> exit_transports;
		{
			ArrayList<ArrayList<Integer> > ret = adaptationStart(split_points);
			initial_transports = ret.get(0);
			monuments = ret.get(1);
			exit_transports = ret.get(2);
		}
		
		ArrayList<Monument> sorted = new ArrayList<Monument>(monument_ids);
		for(int i = 0; i < sorted.size(); ++i) {
			Monument m = sorted.get(i);
			m.position = monuments.get(i);
			m.transport = exit_transports.get(i);
		}
		Collections.sort(sorted, Monument.postitionComparator);
		
		int current_day = 0;
		double value_sum = 0;
		double financial_cost = 0;
		double current_day_time = 0;
		
		int number_days = initial_transports.size();
		
		int num_monuments = sorted.size();
		for(int i = 0; i < num_monuments; ++i) {
			if(current_day >= number_days) {
				break;
			}
			
			if(current_day_time == 0) {	// first journey of the day
				
				LinkedList<DirectedEdge> path = Dijkstra.shortestPath(graph, hotel_node, sorted.get(i).graphnode);
				double dist = 0;
				for(int j = 0; j < path.size(); ++j) {
					dist += path.get(j).getWeight();
				}
				
				Transport curr_transport = transports.get(initial_transports.get(current_day));
				double travel_time = curr_transport.timeInHours(dist);
				double monetary_cost = curr_transport.cost(dist);
				
				financial_cost += monetary_cost;
				current_day_time += travel_time;
				--i;	// To stay in the same monument
			} else {
				Monument m = sorted.get(i);
				if(i == num_monuments - 1) {	// last monument
					LinkedList<DirectedEdge> path = Dijkstra.shortestPath(graph, m.graphnode, hotel_node);
					double dist = 0;
					for(int j = 0; j < path.size(); ++j) {
						dist += path.get(j).getWeight();
					}
					
					Transport curr_transport = transports.get(m.transport);
					double travel_time = curr_transport.timeInHours(dist);
					double monetary_cost = curr_transport.cost(dist);
			    	
					if(travel_time + m.visit_time_hours + current_day_time > hours_per_day || monetary_cost + financial_cost > financial_limit) {
						if(penalty == 0)
							penalty = 1;
					}
			    	
					current_day_time += travel_time + m.visit_time_hours;
					financial_cost += monetary_cost;
					value_sum += m.value;
				} else {
					Monument next_m = sorted.get(i+1);
					
					LinkedList<DirectedEdge> path = Dijkstra.shortestPath(graph, m.graphnode, next_m.graphnode);
					double dist = 0;
					for(int j = 0; j < path.size(); ++j) {
						dist += path.get(j).getWeight();
					}
					
					Transport curr_transport = transports.get(m.transport);
					double travel_time = curr_transport.timeInHours(dist);
					double monetary_cost = curr_transport.cost(dist);
					
					if(monetary_cost + financial_cost > financial_limit) {
						penalty = 2;
					}
					
					if(current_day_time + travel_time + m.visit_time_hours > hours_per_day) {
						path = Dijkstra.shortestPath(graph, m.graphnode, hotel_node);
						dist = 0;
						for(int j = 0; j < path.size(); ++j) {
							dist += path.get(j).getWeight();
						}
						
						curr_transport = transports.get(m.transport);
						travel_time = curr_transport.timeInHours(dist);
						monetary_cost = curr_transport.cost(dist);
						
						if(monetary_cost + financial_cost > financial_limit) {
							penalty = 2;
						}
						
						if(current_day_time + travel_time + m.visit_time_hours > hours_per_day) {
							if(penalty == 0)
								penalty = 1;
						}
						
						financial_cost += monetary_cost;
						current_day++;
						current_day_time = 0;
					} else {
						financial_cost += monetary_cost;
						current_day_time += travel_time + m.visit_time_hours;
					}
						
					value_sum += m.value;
				}
			}
		}
		
		if(value_sum < 0) {
			value_sum = 0;
		} else if (value_sum < 4) {
			if(penalty != 0) {
				value_sum = value_sum/2;
			}
		} else {
			if(penalty == 1) {
				value_sum = value_sum/2;
			} else {
				value_sum = Math.sqrt(value_sum);
			}
		}
		
		adaptation = value_sum;
		return adaptation;
	}
}
