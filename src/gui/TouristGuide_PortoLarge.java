package gui;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;


public class TouristGuide_PortoLarge extends TouristGuide_Choice {

	public TouristGuide_PortoLarge() throws FileNotFoundException, IOException, XmlPullParserException {
		super();
		this.frame_title = "Porto (Large)";
		this.graph_path = "data/porto-large.osm";
		this.monuments_path = "data/porto-monuments.txt";
		this.background_path = "data/porto-large.png";
		this.bckgrd_x_pos = 74;
		this.bckgrd_y_pos = 113;
		this.bckgrd_x_scale = 0.8;
		this.bckgrd_y_scale = 1.02;
		
		initialize();
	}
	
	public static void ScreenStart() {
		try {
			TouristGuide_PortoLarge window = new TouristGuide_PortoLarge();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
