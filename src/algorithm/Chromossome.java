package algorithm;

public abstract class Chromossome {
	
	public abstract String toString();
	
	public static String[] crossover(String c1, String c2, int split_index) {
		String[] result = new String[2];
		
		if(c1.length() != c2.length() || split_index > c1.length()-1)
			return null;
		
		int length = c1.length();
		String new_c1 = "" + c1.substring(0, split_index);
		String new_c2 = "" + c2.substring(0, split_index);
		new_c1 += c2.substring(split_index, length);
		new_c2 += c1.substring(split_index, length);
		
		result[0] = new_c1;
		result[1] = new_c2;
		
		return result;
	}
	
}
