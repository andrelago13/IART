package graph;

import java.util.LinkedList;

import edu.uci.ics.jung.graph.Graph;

public class Dijkstra {

	public static LinkedList<DirectedEdge> shortestPath(Graph<GraphNode, DirectedEdge> graph, GraphNode src, GraphNode dst) {
		LinkedList<GraphNode> nodes = new LinkedList<GraphNode>(graph.getVertices());
		LinkedList<DirectedEdge> edges = new LinkedList<DirectedEdge>(graph.getEdges());

		for(GraphNode n : nodes) {
			n.distance = Double.MAX_VALUE;
			n.processed = false;
			n.partOfShortestPath = false;
			n.shortestPath = null;
			n.pathPart = GraphNode.PathPart.PATH;
		}
		src.pathPart = GraphNode.PathPart.SOURCE;
		dst.pathPart = GraphNode.PathPart.DESTINATION;
		
		for(DirectedEdge e : edges) {
			e.processed = false;
			e.partOfShortestPath = false;
		}
		LinkedList<GraphNode> settledNodes = new LinkedList<GraphNode>();
		LinkedList<GraphNode> unsettledNodes = new LinkedList<GraphNode>();
		
		unsettledNodes.add(src);
		src.distance = 0;
		
		GraphNode evaluationNode = null;
		while(!unsettledNodes.isEmpty()) {
			evaluationNode = getNodeWithLowestDistance(unsettledNodes);
			evaluationNode.processed = true;
			if(evaluationNode == dst) {
				break;
			}
			unsettledNodes.remove(evaluationNode);
			settledNodes.add(evaluationNode);
			unsettledNodes = evaluatedNeighbors(evaluationNode, unsettledNodes, settledNodes);
		}
		
		LinkedList<DirectedEdge> result = new LinkedList<DirectedEdge>();
		
		while(evaluationNode != null && evaluationNode != src && evaluationNode.shortestPath != null) {
			evaluationNode.partOfShortestPath = true;
			evaluationNode.shortestPath.partOfShortestPath = true;
			result.addFirst(evaluationNode.shortestPath);
			evaluationNode = evaluationNode.shortestPath.from();
		}
		src.partOfShortestPath = true;
		
		return result;
	}
		
	public static GraphNode getNodeWithLowestDistance(LinkedList<GraphNode> nodes) {
		GraphNode result = null;
		double minDist = Double.MAX_VALUE;

		for(GraphNode n : nodes) {
			if(n.distance < minDist) {
				result = n;
				minDist = n.distance;
			}
		}
		return result;
	}
	
	private static LinkedList<GraphNode> evaluatedNeighbors(GraphNode evaluationNode, LinkedList<GraphNode> unsettledNodes, LinkedList<GraphNode> settledNodes) {
		LinkedList<DirectedEdge> outgoing = evaluationNode.from();
		for(DirectedEdge outgoingEdge : outgoing) {
			outgoingEdge.processed = true;
			GraphNode dest = outgoingEdge.to();
			if(settledNodes.contains(dest))
				continue;
			
			double newDistance = evaluationNode.distance + outgoingEdge.getLength();
			if(dest.distance > newDistance) {
				dest.distance = newDistance;
				dest.shortestPath = outgoingEdge;
				unsettledNodes.add(dest);
			}
		}
		
		return unsettledNodes;
	}
	
	
}
