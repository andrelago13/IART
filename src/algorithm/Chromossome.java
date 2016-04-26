package algorithm;

public abstract class Chromossome {
	
	public abstract String toString();
	
	public static String[] crossover(Chromossome c1, Chromossome c2, int split_index) {
		String[] result = new String[2];
		
		String c1_content = c1.toString();
		String c2_content = c2.toString();
		
		if(c1_content.length() != c2_content.length() || split_index > c1_content.length()-1)
			return null;
		
		int length = c1_content.length();
		String new_c1 = "" + c1_content.substring(0, split_index);
		String new_c2 = "" + c2_content.substring(0, split_index);
		new_c1 += c2_content.substring(split_index, length);
		new_c2 += c1_content.substring(split_index, length);
		
		result[0] = new_c1;
		result[1] = new_c2;
		
		return result;
	}
	
}
