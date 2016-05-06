package parsing;

import graph.RoadGraph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
 

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
 
public class OSMParser {
	
	public static RoadGraph parseOSM(String filepath) throws FileNotFoundException, IOException, XmlPullParserException {
		return parseOSM(filepath, true);
	}
	
	public static RoadGraph parseOSM(String filepath, boolean print) throws FileNotFoundException, IOException, XmlPullParserException {
		if(print)
			System.out.println("Parsing started at " + LocalDateTime.now());
		
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();
		xpp.setInput (new FileReader (filepath));
		
		RoadGraph g = new RoadGraph();
		g.osmGraphParser(xpp);
		
		if(print)
			System.out.println("Parsing ended at "+ LocalDateTime.now() + " (" + g.nodes.size() + " nodes, " + g.edges.size() + " edges)");
		
		return g;
	}
	
}