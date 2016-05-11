package parsing;

public class OsmConstants {
	 
	//If speed values are not present in data, then assign values according to road type
		public static int roadTypeToSpeed(String type)
		{
			int speed=0;
			switch (type)
			{
			case "primary":
				speed =40;
				break;
			case "secondary":
				speed =40;
				break;
			case "tertiary":
				speed =35;
				break;
			case "primary_link":
				speed =35;
				break;
			case "secondary_link":
				speed =35;
				break;
			case "teritiary_link":
				speed =35;
				break;
			case "residential":
				speed =20;
				break;
			case "unclassified":
				speed =20;
				break;
			case "road":
				speed =20;
				break;
			case "living street":
				speed =20;
				break;
			case "motorway":
				speed =65;
				break;
			case "motorway_link":
				speed =35;
				break;
			case "trunk":
				speed =65;
				break;
			case "trunk_link":
				speed =35;
				break;
			default:
				speed =20;
	 
			}
			return speed;
		}
		
}