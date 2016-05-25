package gui;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

public class TouristGuide_PortoSmall extends TouristGuide_Choice {

	public TouristGuide_PortoSmall() throws FileNotFoundException, IOException, XmlPullParserException {
		super();
		this.frame_title = "Porto (Small)";
		this.graph_path = "data/porto-small.osm";
		this.monuments_path = "data/porto-monuments.txt";
		this.background_path = "data/porto-small.png";
		this.bckgrd_x_pos = 145;
		this.bckgrd_y_pos = 213;
		this.bckgrd_x_scale = 1.38;
		this.bckgrd_y_scale = 1.62;
		
		initialize();
	}
	
	public static void ScreenStart() {
		try {
			TouristGuide_PortoSmall window = new TouristGuide_PortoSmall();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
