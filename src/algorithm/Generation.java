package algorithm;

import java.util.ArrayList;

public class Generation {
	
	private int size;
	private int number;
	private ArrayList<Chromossome> population;
	
	public Generation(int number, int size) {
		this.setSize(size);
		this.setNumber(number);
		this.setPopulation(new ArrayList<Chromossome>());
		
		/*for(int i = 0; i < size; ++i) {
			Chromossome t = new Chromossome(20);
			t.generate();
			population.add(t);
			System.out.println(t);
		}*/
	}
	
	public Generation(int number, int size, ArrayList<Chromossome> initial_population) {
		this.setSize(size);
		this.setNumber(number);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ArrayList<Chromossome> getPopulation() {
		return population;
	}

	public void setPopulation(ArrayList<Chromossome> population) {
		this.population = population;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
