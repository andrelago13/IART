package Main;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import parsing.OSMParser;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.GraphMouseListener;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import graph.DirectedEdge;
import graph.GraphNode;
import graph.RoadGraph;
import gui.GraphPanel;

public class Test {

    public static void main(String[] args) {
    	
    }

	
}

/*
private GraphNode src = null;

	public Test() throws Exception {
				
		RoadGraph roadgraph = OSMParser.parseOSM("data/porto-large.osm");
		LinkedList<GraphNode> nodes = roadgraph.nodes;
		LinkedList<DirectedEdge> edges = roadgraph.edges;
		
		GraphFrame gf = new GraphFrame(roadgraph);
		
		// > 41.13982
		// < -8.59959
		// > -8.63018
		
		Graph<GraphNode, DirectedEdge> osm_graph = new SparseGraph<GraphNode, DirectedEdge>();
		for(int i = 0; i < nodes.size(); ++i) {
			GraphNode n = nodes.get(i);
			//if(n.getLat() > 41.13982 && n.getLon() < -8.59959 && n.getLon() > -8.63018)
			osm_graph.addVertex(n);
		}
		for(int i = 0; i < edges.size(); ++i) {
			GraphNode from = edges.get(i).from();
			GraphNode to = edges.get(i).to();
			
			//if(from.getLat() > 41.13982 && from.getLon() < -8.59959 && from.getLon() > -8.63018 && to.getLat() > 41.13982 && to.getLon() < -8.59959 && to.getLon() > -8.63018)
			osm_graph.addEdge(edges.get(i), from, to);
		}

        // Layout implements the graph drawing logic
        Layout<GraphNode, DirectedEdge> layout = new StaticLayout<GraphNode, DirectedEdge>(osm_graph);
        layout.setSize(new Dimension(977,650));
//976.1869639183353
        double min_lat = 1000000000;
		double max_lat = -1000000000;
		double min_lon = 1000000000;
		double max_lon = -1000000000; 
		
		for(int i = 0; i < nodes.size(); ++i) {
			double lat = nodes.get(i).getLat();
			double lon = nodes.get(i).getLon();
			
			if(lat > max_lat)
				max_lat = lat;
			if(lat < min_lat)
				min_lat = lat;
			
			if(lon > max_lon)
				max_lon = lon;
			if(lon < min_lon)
				min_lon = lon;
		}
		//-8.63018,41.13982,-8.59959,41.15259
		min_lat = 41.13982;
		max_lat = 41.15259;
		min_lon = -8.63018;
		max_lon = -8.59959;
		
		for(int i = 0; i < nodes.size(); ++i) {
			double lat = nodes.get(i).getLat();
			double lon = nodes.get(i).getLon();
			
			lon = 977*(lon-min_lon)/(max_lon-min_lon);
			lat = 650 - (650*(lat-min_lat)/(max_lat-min_lat));
			
			layout.setLocation(nodes.get(i), new Point2D.Double(lon,lat));
		}
		
        // VisualizationServer actually displays the graph
        VisualizationViewer<GraphNode,DirectedEdge> vv = new VisualizationViewer<GraphNode,DirectedEdge>(layout);
        vv.setPreferredSize(new Dimension(977,650)); //Sets the viewing area size

        // Transformer maps the vertex number to a vertex property
        Transformer<GraphNode,Paint> vertexColor = new Transformer<GraphNode,Paint>() {
            public Paint transform(GraphNode i) {
                if(i.processed) return Color.YELLOW;
                return Color.GRAY;
            }
        };
        Transformer<GraphNode,Shape> vertexSize = new Transformer<GraphNode,Shape>(){
            public Shape transform(GraphNode i){
                Ellipse2D circle = new Ellipse2D.Double(-5, -5, 10, 10);
                // in this case, the vertex is twice as large
                if(i.getId() == 2) return AffineTransform.getScaleInstance(2, 2).createTransformedShape(circle);
                else return circle;
                //return AffineTransform.getScaleInstance(0, 0).createTransformedShape(circle);
            }
        };
        
        Transformer<DirectedEdge, Paint> edgePaint = new Transformer<DirectedEdge, Paint>() {
            public Paint transform(DirectedEdge e) {
            	if(e.partOfShortestPath) {
            		return Color.YELLOW;
            	}
                return Color.BLACK;
            }
        };
        
        final AbstractModalGraphMouse graphMouse = new DefaultModalGraphMouse<GraphNode, DirectedEdge>();
        
        vv.setGraphMouse(graphMouse);
        vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);
        vv.getRenderContext().setVertexShapeTransformer(vertexSize);
        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<GraphNode,DirectedEdge>());
        vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
        
        vv.addGraphMouseListener(new GraphMouseListener<GraphNode>() {

			@Override
			public void graphClicked(GraphNode arg0, MouseEvent arg1) {
				
			}

			@Override
			public void graphPressed(GraphNode arg0, MouseEvent arg1) {
				System.out.println(arg0.from().size());
				System.out.println(arg0.to().size());
				
				if(arg0.from().size() > 0) {
					System.out.println(arg0.getLat());
					System.out.println("[ " + arg0.from().get(0).from().getLat() + " , " + arg0.from().get(0).to().getLat() + " ]");
				}
				
				System.out.println();
				
				if(src == null) {
					System.out.println("pressed first");
					src = arg0;
				} else {
					System.out.println("calculating");
					//roadgraph.shortestPathDijkstra(src, arg0);
					
					src = null;
				}
			}

			@Override
			public void graphReleased(GraphNode arg0, MouseEvent arg1) {
				
			}

            
        });

        JFrame frame = new JFrame("Simple Graph View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv); 
        frame.pack();
        frame.setVisible(true);    
    } 
*/
