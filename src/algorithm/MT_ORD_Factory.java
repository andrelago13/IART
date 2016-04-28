package algorithm;

import java.util.ArrayList;

import utils.BinaryUtils;

public class MT_ORD_Factory {
	
	public static ArrayList<MT_ORD_Chromossome> generateChromossomes(int number_chromossomes, int number_transports, int number_monuments, int number_days) {
		ArrayList<MT_ORD_Chromossome> result = new ArrayList<MT_ORD_Chromossome>();
		
		String transports_base = BinaryUtils.zeroBaseForNNumbers(number_transports);
		String monuments_base = BinaryUtils.zeroBaseForNNumbers(number_monuments);
		
		for(int i = 0; i < number_chromossomes; ++i) {
			MT_ORD_Chromossome c = new MT_ORD_Chromossome(number_transports, number_monuments, number_days);
			c.generate(transports_base, monuments_base);
			result.add(c);
		}
		
		return result;
	}
	
}
