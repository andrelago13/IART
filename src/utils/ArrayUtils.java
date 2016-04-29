package utils;

import java.util.ArrayList;

import algorithm.MT_ORD_Chromossome;

public class ArrayUtils {

	public static ArrayList<MT_ORD_Chromossome> clone(ArrayList<MT_ORD_Chromossome> original) {
		ArrayList<MT_ORD_Chromossome> result = new ArrayList<MT_ORD_Chromossome>();
		
		for(int i = 0; i < original.size(); ++i) {
			result.add(original.get(i).clone());
		}
		
		return result;
	}
	
}
