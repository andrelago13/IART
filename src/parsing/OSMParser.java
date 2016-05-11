package parsing;

import graph.RoadGraph;
import gui.ProgressListener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
 


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
 
public class OSMParser {
	
	public static RoadGraph parseOSM(String filepath, ProgressListener pl) throws FileNotFoundException, IOException, XmlPullParserException {
		return parseOSM(filepath, true, pl);
	}
	
	public static RoadGraph parseOSM(String filepath, boolean print, ProgressListener pl) throws FileNotFoundException, IOException, XmlPullParserException {
		if(print)
			System.out.println("Parsing started at " + LocalDateTime.now());
		
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();
		xpp.setInput (new FileReader (filepath));
		
		RoadGraph g = new RoadGraph();
		g.osmGraphParser(xpp, pl);
		
		if(print)
			System.out.println("Parsing ended at "+ LocalDateTime.now() + " (" + g.nodes.size() + " nodes, " + g.edges.size() + " edges)");
		
		return g;
	}
	
}