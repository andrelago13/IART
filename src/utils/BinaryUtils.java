package utils;

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
	
}
