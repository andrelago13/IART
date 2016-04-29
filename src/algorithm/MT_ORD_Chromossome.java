package algorithm;

import java.util.ArrayList;
import java.util.Random;

import utils.BinaryUtils;

public class MT_ORD_Chromossome implements Cloneable {

	private int number_transports;
	private int number_monuments;
	private int number_days;
	
	private String content;
	
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
		
		return generate(transports_base, monuments_base);		
	}
	
	public ArrayList<Integer> generate(String transports_base, String monuments_base) {
		this.content = "";
		int transports_base_size = transports_base.length();
		int monuments_base_size = monuments_base.length();
		
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
			int monument = r.nextInt(number_monuments);
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
		return new MT_ORD_Chromossome(number_transports, number_monuments, number_days, new String(content));
	}
}
