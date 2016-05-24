package algorithm;

public class Transport {
	public double cost_per_10km;
	public double average_speed;
	public String name;
	
	public Transport(String name, double cost_per_10km, double average_speed) {
		this.name = name;
		this.cost_per_10km = cost_per_10km;
		this.average_speed = average_speed;
	}
	
	public double cost(double km) {
		return km*cost_per_10km/10;
	}
}
