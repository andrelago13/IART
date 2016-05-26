package gui;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

public class TouristGuide_TestCase extends TouristGuide_Choice {

	public TouristGuide_TestCase() throws FileNotFoundException, IOException, XmlPullParserException {
		super();
		this.frame_title = "Test Case";
		this.graph_path = "data/teste.osm";
		this.monuments_path = "data/test-monuments.txt";
		this.background_path = "";
		this.bckgrd_x_pos = 145;
		this.bckgrd_y_pos = 213;
		this.bckgrd_x_scale = 1.38;
		this.bckgrd_y_scale = 1.62;
		
		initialize();
	}
	
	public static void ScreenStart() {
		try {
			TouristGuide_TestCase window = new TouristGuide_TestCase();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

