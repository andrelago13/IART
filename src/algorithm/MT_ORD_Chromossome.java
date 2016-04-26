package algorithm;

import java.util.ArrayList;
import java.util.Random;

public class MT_ORD_Chromossome {

	private int number_transports;
	private int number_monuments;
	private int number_days;
	
	private String content;
	
	public MT_ORD_Chromossome(int number_transports, int number_monuments, int number_days) {
		this.number_transports = number_transports;
		this.number_monuments = number_monuments;
		this.number_days = number_days;
		this.content = "";
		
		generate();
	}
	
	public void generate() {
		this.content = "";
		
		String temp = Integer.toBinaryString(number_transports-1);
		int transports_base_size = temp.length();
		String transports_base = "";
		for(int i = 0; i < transports_base_size; ++i) {
			transports_base += "0";
		}
		temp = Integer.toBinaryString(number_monuments-1);
		int monuments_base_size = temp.length();
		String monuments_base = "";
		for(int i = 0; i < monuments_base_size; ++i) {
			monuments_base += "0";
		}
		
		// GENERATE FIRST TRANSPORTS

		Random r = new Random();
		for(int i = 0; i < this.number_days; ++i) {
			int transport = r.nextInt(number_transports);
			String trans_string_temp = Integer.toBinaryString(transport);
			content += transports_base.substring(0, transports_base_size - trans_string_temp.length()) + trans_string_temp;
		}
		
		// GENERATE ORDERS AND TRANSPORTS
		for(int i = 0; i < this.number_monuments; ++i) {
			int monument = r.nextInt(number_monuments);
			String mon_string_temp = Integer.toBinaryString(monument);
			content += monuments_base.substring(0, monuments_base_size - mon_string_temp.length()) + mon_string_temp;
			
			int transport = r.nextInt(number_transports);
			String trans_string_temp = Integer.toBinaryString(transport);
			content += transports_base.substring(0, transports_base_size - trans_string_temp.length()) + trans_string_temp;
		}
		
	}
	
	public String getContent() {
		return content;
	}
	
	public String toString() {
		return content;
	}
	
}
