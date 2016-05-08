package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.GraphMouseListener;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import graph.Dijkstra;
import graph.DirectedEdge;
import graph.GraphNode;
import graph.RoadGraph;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;
import org.xmlpull.v1.XmlPullParserException;

import parsing.OSMParser;

@SuppressWarnings("serial")
public class GraphPanel extends JPanel {
	
	private Graph<GraphNode, DirectedEdge> graph;

	public final static double WIDTH_PERCENTAGE = 0.8;
	
	private Layout<GraphNode, DirectedEdge> layout;
	private VisualizationViewer<GraphNode,DirectedEdge> vv;
	private Transformer<GraphNode,Paint> vertexColor;
    private Transformer<GraphNode,Shape> vertexSize;
    private Transformer<DirectedEdge, Paint> edgePaint;
    private AbstractModalGraphMouse graphMouse;
    private GraphMouseListener<GraphNode> graphMouseListener;
    
    private GraphNode clickedSource = null;
    
    private JFrame frame;
    private int myWidth;
    private int myHeight;
	
    public GraphPanel(String filepath, JFrame frame) throws FileNotFoundException, IOException, XmlPullParserException {
    	this(OSMParser.parseOSM(filepath), frame);
    }
    
	public GraphPanel(RoadGraph rg, JFrame frame) {
		super();
		this.frame = frame;
		this.myWidth = (int) (this.frame.getWidth()*0.8);
		this.myHeight = this.frame.getHeight();
		processGraph(rg);
		initializeVisualization();
		processGraphPositions();
	}
	
	private void processGraph(RoadGraph rg) {
		this.graph = new SparseGraph<GraphNode, DirectedEdge>();
		LinkedList<GraphNode> nodes = rg.nodes;
		LinkedList<DirectedEdge> edges = rg.edges;
		
		for(int i = 0; i < nodes.size(); ++i) {
			this.graph.addVertex(nodes.get(i));
		}
		for(int i = 0; i < edges.size(); ++i) {
			this.graph.addEdge(edges.get(i), edges.get(i).from(), edges.get(i).to());
		}
	}
	
	private void processGraphPositions() {
		LinkedList<GraphNode> nodes = new LinkedList<GraphNode>(graph.getVertices());
		
		double min_lat = Double.MAX_VALUE;
		double max_lat = -Double.MAX_VALUE;
		double min_lon = Double.MAX_VALUE;
		double max_lon = -Double.MAX_VALUE; 
		
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
		
		for(int i = 0; i < nodes.size(); ++i) {
			double lat = nodes.get(i).getLat();
			double lon = nodes.get(i).getLon();
			
			lon = myWidth*(lon-min_lon)/(max_lon-min_lon);
			lat = myHeight - (myHeight*(lat-min_lat)/(max_lat-min_lat));
			
			layout.setLocation(nodes.get(i), new Point2D.Double(lon,lat));
		}
	}
	
	private void initializeVisualization() {
		initializeLayout();
		initializeVisualizationViewer();
		
		
		initializeVertexColor();
		initializeVertexSize();
		initializeVertexPaint();
		initializeGraphMouse();
		initializeGraphMouseListener();
		
		
		vv.setGraphMouse(graphMouse);
        vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);
        vv.getRenderContext().setVertexShapeTransformer(vertexSize);
        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<GraphNode,DirectedEdge>());
        vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
        vv.addGraphMouseListener(graphMouseListener);
        
        this.add(vv);
        /*getContentPane().add(vv); 
        pack();*/
	}
	
	private void initializeVertexColor() {
		vertexColor = new Transformer<GraphNode,Paint>() {
	        public Paint transform(GraphNode i) {
	            if(i.partOfShortestPath) return Color.GREEN;
	            if(i.processed) return Color.YELLOW;
	            return Color.GRAY;
	        }
	    };
	}
	
	private void initializeVertexSize() {
		vertexSize = new Transformer<GraphNode,Shape>(){
	        public Shape transform(GraphNode i){
	            Ellipse2D circle = new Ellipse2D.Double(-5, -5, 10, 10);
	            // in this case, the vertex is twice as large
	            if(i.getId() == 2) return AffineTransform.getScaleInstance(2, 2).createTransformedShape(circle);
	            else return circle;
	            //return AffineTransform.getScaleInstance(0, 0).createTransformedShape(circle);
	        }
	    };
	}
	
	private void initializeVertexPaint() {
		edgePaint = new Transformer<DirectedEdge, Paint>() {
	        public Paint transform(DirectedEdge e) {
	        	if(e.partOfShortestPath) {
	        		return Color.GREEN;
	        	}
	        	
	        	if(e.processed) {
	        		return Color.YELLOW;
	        	}
	        	
	            return Color.BLACK;
	        }
	    };
	}
	
	private void initializeGraphMouse() {
		graphMouse = new DefaultModalGraphMouse<GraphNode, DirectedEdge>();
	}
	
	private void initializeGraphMouseListener() {
		final VisualizationViewer<GraphNode,DirectedEdge> vv_t = vv;
		
		graphMouseListener = new GraphMouseListener<GraphNode>() {
			@Override
			public void graphClicked(GraphNode node, MouseEvent event) {		
				if(clickedSource == null) {
					System.out.println("Selected source node.");
					clickedSource = node;
				} else {
					System.out.println("Selected destination node. Calculating with Dijkstra [ " + LocalDateTime.now() + " ]");
					LinkedList<DirectedEdge> edges = Dijkstra.shortestPath(graph, clickedSource, node);
					System.out.println("Path calculated [ " + LocalDateTime.now() + " ], " + edges.size() + " edges.");
					
					clickedSource = null;
					vv_t.repaint();
					
				}
			}

			@Override
			public void graphPressed(GraphNode node, MouseEvent event) {}

			@Override
			public void graphReleased(GraphNode node, MouseEvent event) {}
        };
	}

	private void initializeLayout() {
		layout = new StaticLayout<GraphNode, DirectedEdge>(graph);
        layout.setSize(new Dimension(this.myWidth, this.myHeight));
	}
	
	private void initializeVisualizationViewer() {
		vv = new VisualizationViewer<GraphNode,DirectedEdge>(layout);
        vv.setPreferredSize(new Dimension(this.myWidth, this.myHeight));
	}

	public void initiate() {
		setVisible(true);
	}
}
