package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.GraphMouseListener;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import graph.Dijkstra;
import graph.DirectedEdge;
import graph.GraphNode;
import graph.Monument;
import graph.RoadGraph;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;
import org.xmlpull.v1.XmlPullParserException;

import algorithm.MT_ORD_Chromossome;
import algorithm.MT_ORD_Factory;
import algorithm.MT_ORD_Generation;
import algorithm.Solution;
import algorithm.Transport;
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
    private GraphNode hotel_node;
    
    private JFrame frame;
    private int myWidth;
    private int myHeight;
	
	private String background_path = null;
	private int backgound_x_pos;
	private int backgound_y_pos;
	private double backgound_x_scale;
	private double backgound_y_scale;
	
	private ArrayList<Monument> monuments = null;
	private ArrayList<Transport> transports = null;
	
	public TouristGuide_Choice guide = null;
    
	public GraphPanel(JFrame frame) {
		super();
		this.frame = frame;
		this.myWidth = (int) (this.frame.getWidth()*0.8);
		this.myHeight = this.frame.getHeight();
		this.graph = new SparseGraph<GraphNode, DirectedEdge>();
		initializeVisualization();
	}
	
	public void init(String filepath, ProgressListener pl, String background_path, int x_pos, int y_pos, double x_scale, double y_scale) throws FileNotFoundException, IOException, XmlPullParserException {
		init(OSMParser.parseOSM(filepath, pl), background_path, x_pos, y_pos, x_scale, y_scale);
	}
	
	public void init(RoadGraph rg, String background_path, int x_pos, int y_pos, double x_scale, double y_scale) {
		this.background_path = background_path;
		this.backgound_x_pos = x_pos;
		this.backgound_y_pos = y_pos;
		this.backgound_x_scale = x_scale;
		this.backgound_y_scale = y_scale;
				
		processGraph(rg);
		initializeVisualizationGraph();
		processGraphPositions();	
		parseTransports("data/transports.txt");
	}
	
	private void parseTransports(String filepath) {
		transports = new ArrayList<Transport>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	String name = line;
		    	line = br.readLine();
		    	double cost_per_10km = Double.parseDouble(line);
		    	line = br.readLine();
		    	double average_speed = Double.parseDouble(line);
		    	transports.add(new Transport(name, cost_per_10km, average_speed));
		    }
		} catch (Exception e) {
			e.printStackTrace();
			transports = new ArrayList<Transport>();
		}
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
		initializeEdgePaint();
		initializeGraphMouse();
		initializeGraphMouseListener();
		

		
		vv.setGraphMouse(graphMouse);
        vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);
        vv.getRenderContext().setVertexShapeTransformer(vertexSize);
        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<GraphNode,DirectedEdge>());
        vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
        vv.addGraphMouseListener(graphMouseListener);
        
        this.add(vv);
	}
	
	private void initializeVisualizationGraph() {
		this.remove(vv);
		
		initializeLayout();
		initializeVisualizationViewer();
		
		
		initializeVertexColor();
		initializeVertexSize();
		initializeEdgePaint();
		initializeGraphMouse();
		initializeGraphMouseListener();
		

		
		vv.setGraphMouse(graphMouse);
        vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);
        vv.getRenderContext().setVertexShapeTransformer(vertexSize);
        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<GraphNode,DirectedEdge>());
        vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
        vv.addGraphMouseListener(graphMouseListener);

        if(background_path != null) {
        	ImageIcon mapIcon = null;
            String imageLocation = background_path;
            try {
                mapIcon =  new ImageIcon(imageLocation);
                System.out.println("loading....");
            } catch(Exception ex) {
                System.err.println("Can't load \""+imageLocation+"\"");
            }
            final ImageIcon icon = mapIcon;

            System.out.println("map loaded!");

            if(icon != null) {
                vv.addPreRenderPaintable(new VisualizationViewer.Paintable(){
                    public void paint(Graphics g) {
                    	Graphics2D g2d = (Graphics2D)g;
                    	AffineTransform oldXform = g2d.getTransform();

                        AffineTransform lat = 
                        	vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).getTransform();

                        AffineTransform vat = 
                        	vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getTransform();
                        AffineTransform at = new AffineTransform();
                        at.concatenate(g2d.getTransform());
                        at.concatenate(vat);
                        at.concatenate(lat);
                        g2d.setTransform(at);
                        g.drawImage(icon.getImage(), backgound_x_pos, backgound_y_pos,
                        		(int)(icon.getIconWidth()*backgound_x_scale),(int)(icon.getIconHeight()*backgound_y_scale),vv);
                        g2d.setTransform(oldXform);
                    }
                    public boolean useTransform() { return false; }
                });
            }
            
            this.add(vv);	
        }
	}
	
	private void initializeVertexColor() {
		vertexColor = new Transformer<GraphNode,Paint>() {
	        public Paint transform(GraphNode i) {
	        	if(i == hotel_node) return Color.YELLOW;
	            if(i.partOfShortestPath) return Color.GREEN;
	            if(i.processed) return Color.YELLOW;
	            if(i.isMonument()) {
	            	return Color.BLUE;
	            }
	            return Color.GRAY;
	        }
	    };
	}
	
	private void initializeVertexSize() {
		vertexSize = new Transformer<GraphNode,Shape>(){
	        public Shape transform(GraphNode i){
	            Ellipse2D circle = new Ellipse2D.Double(-5, -5, 10, 10);
	            //return AffineTransform.getScaleInstance(1, 1).createTransformedShape(circle);
	            
	            if(i == hotel_node) return AffineTransform.getScaleInstance(0.8, 0.8).createTransformedShape(circle);
	            else if(i.isMonument()) {
	            	return AffineTransform.getScaleInstance(2.6, 2.6).createTransformedShape(circle);
	            } else {
	            	return AffineTransform.getScaleInstance(0.4, 0.4).createTransformedShape(circle);	
	            }
	        }
	    };
	}
	
	private void initializeEdgePaint() {
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
		graphMouseListener = new GraphMouseListener<GraphNode>() {
			@Override
			public void graphClicked(GraphNode node, MouseEvent event) {
				if(!node.isMonument()) {
					System.out.println("Clicked node " + node.getId());
					hotel_node = node;
					vv.revalidate();
					vv.repaint();
				} else {
					System.out.println("Clicked monument.");
					if(guide != null) {
						guide.clickedMonument(node);
					}
				}
				
				/*if(clickedSource == null) {
					System.out.println("Selected source node.");
					clickedSource = node;
				} else {
					System.out.println("Selected destination node. Calculating with Dijkstra [ " + LocalDateTime.now() + " ]");
					LinkedList<DirectedEdge> edges = Dijkstra.shortestPath(graph, clickedSource, node);
					System.out.println("Path calculated [ " + LocalDateTime.now() + " ], " + edges.size() + " edges, total distance " + node.distance + "m.");
					if(edges.get(edges.size() - 1).to() != node) {
						System.out.println("Impossible path");
					} else {
						System.out.println("Possible path");
					}
					
					clickedSource = null;
					vv.repaint();
					
				}*/
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
        vv.setSize(new Dimension(this.myWidth, this.myHeight));
	}

	public void initiate() {
		setVisible(true);
	}

	public void parseMonuments(String filepath) {
		this.monuments = new ArrayList<Monument>();
		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	long node_id = Long.parseLong(line);
		    	String name = br.readLine();
		    	line = br.readLine();
		    	double visit_time = Double.parseDouble(line);

		    	LinkedList<GraphNode> nodes = new LinkedList<GraphNode>(graph.getVertices());
		    	for(int i = 0; i < nodes.size(); ++i) {
		    		if(nodes.get(i).getId() == node_id) {
		    			GraphNode node = nodes.get(i);
		    			node.setMonument(true);
		    			node.setName(line);
		    			Monument m = new Monument(node_id, name, visit_time, node);
		    			this.monuments.add(m);
		    			break;
		    		}
		    	}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		vv.repaint();
	}
	public ArrayList<Monument> getMonuments(){
		return monuments;
	}

	public Solution solve(int number_days, int hours_per_day, double financial_limit, int population_size, int elite, int number_generations, double mutation_prob) {
		System.out.println("Starting execution of genetic algorithms.");
		long startTime = System.currentTimeMillis();
		
		ArrayList<MT_ORD_Chromossome> population = MT_ORD_Factory.generateChromossomes(population_size, transports.size(), this.monuments.size(), number_days);
		MT_ORD_Generation gen = new MT_ORD_Generation(mutation_prob, population);
		
		ArrayList<MT_ORD_Generation> generations = new ArrayList<MT_ORD_Generation>();
		generations.add(gen);
		
		for(int i = 1; i < number_generations; ++i) {
			gen = gen.evolve(this.graph, hours_per_day, financial_limit, transports, this.monuments, hotel_node, elite);
			generations.add(gen);
		}
		gen.evolve(this.graph, hours_per_day, financial_limit, transports, this.monuments, hotel_node, elite);
		
		long endTime = System.currentTimeMillis();
		long dif = endTime-startTime;
		if(dif < 1000) {
			System.out.println("Ended execution of genetic algorithms. Took " + dif + " miliseconds.");
		} else if (dif < 1000*60) {
			System.out.println("Ended execution of genetic algorithms. Took " + (double)(dif/1000.0) + " seconds.");
		} else {
			System.out.println("Ended execution of genetic algorithms. Took " + (double)(dif/(1000.0*60)) + " minutes.");
		}
		
		for(int i = 0; i < generations.size(); ++i) {
			System.out.println("" + '\t' + "Gen#" + i + " -> " + generations.get(i).getStats());
		}
		
		MT_ORD_Chromossome best_individual = gen.getBest();
		return best_individual.getSolution(graph, monuments, hours_per_day, financial_limit, transports, best_individual.getCrossovers(), hotel_node);
	}
	
	
}
