package algorithm;

public class Transport {
	public double cost_per_10km;
	public double average_speed;	// KM/H
	public String name;
	
	public Transport(String name, double cost_per_10km, double average_speed) {
		this.name = name;
		this.cost_per_10km = cost_per_10km;
		this.average_speed = average_speed;
	}
	
	public double cost(double m) {
		return (m/1000)*cost_per_10km/10;
	}
	
	public double timeInHours(double m) {
		return m/(1000*average_speed);
	}
}
