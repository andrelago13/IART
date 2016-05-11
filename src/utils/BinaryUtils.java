package utils;

import java.util.Random;

public class BinaryUtils {

	public static String zeroBaseForNNumbers(int number_possibilities) {
		String temp = Integer.toBinaryString(number_possibilities-1);
		int base_size = temp.length();
		String base = "";
		for(int i = 0; i < base_size; ++i) {
			base += "0";
		}
		return base;
	}
	
	public static String probabilityNegation(Random rand, double prob, String content) {
		int content_length = content.length();
		
		for(int j = 0; j < content_length; ++j) {
			double mutate = rand.nextDouble();
			if(mutate <= prob) {
				StringBuilder sb = new StringBuilder(content);
				char new_char = (content.charAt(j) == '0') ? '1' : '0';
				sb.setCharAt(j, new_char);
				content = sb.toString();
			}
		}
		
		return content;
	}
	
	public static String probabilityNegation(double prob, String content) {
		return probabilityNegation(new Random(), prob, content);
	}
	
}
