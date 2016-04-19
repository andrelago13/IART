package algorithm;

import java.util.Random;

public class Chromossome {
	
	private int length;
	private String content;

	public Chromossome(int length) {
		this.length = length;
		this.content = "";
	};
	
	public void generate() {
		Random r = new Random();
		for(int i = 0; i < length; ++i) {
			content += "" + r.nextInt(2);
		}
	}
	
	public String toString() {
		return content;
	}
}
