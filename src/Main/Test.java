package Main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;

import algorithm.Generation;
import algorithm.MT_ORD_Chromossome;
import algorithm.MT_ORD_Factory;
import algorithm.MT_ORD_Generation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import parsing.DirectedEdge;
import parsing.GraphNode;
import parsing.ParseOSM;
import parsing.RoadGraph;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;

public class Test {

	public Test() throws Exception {
		System.out.println("Run started at"+ LocalDateTime.now() );
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();
		xpp.setInput (new FileReader ("C:\\Users\\André\\git\\IART\\data\\map.osm"));
		
		RoadGraph roadgraph = new RoadGraph();
		roadgraph.osmGraphParser(xpp);
		LinkedList<GraphNode> nodes = roadgraph.nodes;
		LinkedList<DirectedEdge> edges = roadgraph.edges;

		System.out.println("Parsing ended at"+ LocalDateTime.now() );
		System.out.println("Edges = "+edges.size());
		System.out.println("Nodes = "+nodes.size());
		
		Graph<GraphNode, DirectedEdge> osm_graph = new SparseGraph<GraphNode, DirectedEdge>();
		for(int i = 0; i < nodes.size(); ++i) {
			osm_graph.addVertex(nodes.get(i));
		}
		for(int i = 0; i < edges.size(); ++i) {
			osm_graph.addEdge(edges.get(i), edges.get(i).from(), edges.get(i).to());
		}
		
        // Create a graph with Integer vertices and String edges
        /*Graph<Integer, String> g = new SparseGraph<Integer, String>();
        for(int i = 0; i < 5; i++) g.addVertex(i);
        g.addEdge("Edge", 1, 2);
        g.addEdge("Another Edge", 1, 4);*/

        // Layout implements the graph drawing logic
        Layout<GraphNode, DirectedEdge> layout = new CircleLayout<GraphNode, DirectedEdge>(osm_graph);
        layout.setSize(new Dimension(300,300));

        // VisualizationServer actually displays the graph
        VisualizationViewer<GraphNode,DirectedEdge> vv = new VisualizationViewer<GraphNode,DirectedEdge>(layout);
        vv.setPreferredSize(new Dimension(350,350)); //Sets the viewing area size

        // Transformer maps the vertex number to a vertex property
        Transformer<GraphNode,Paint> vertexColor = new Transformer<GraphNode,Paint>() {
            public Paint transform(GraphNode i) {
                if(i.getId() == 1) return Color.GREEN;
                return Color.RED;
            }
        };
        Transformer<GraphNode,Shape> vertexSize = new Transformer<GraphNode,Shape>(){
            public Shape transform(GraphNode i){
                Ellipse2D circle = new Ellipse2D.Double(-15, -15, 30, 30);
                // in this case, the vertex is twice as large
                if(i.getId() == 2) return AffineTransform.getScaleInstance(2, 2).createTransformedShape(circle);
                else return circle;
            }
        };
        final AbstractModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        vv.setGraphMouse(graphMouse);
        vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);
        vv.getRenderContext().setVertexShapeTransformer(vertexSize);

        JFrame frame = new JFrame("Simple Graph View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv); 
        frame.pack();
        frame.setVisible(true);    
    }

    public static void main(String[] args) {
        try {
			new Test();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	/*ublic static void main(String[] args) {

		MT_ORD_Generation g = new MT_ORD_Generation(0, 1, 1, 4, 4, 3);
		System.out.println(g);
		System.out.println("");
		g = g.evolve();
		System.out.println(g);
	}*/
	
}
