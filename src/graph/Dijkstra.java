package graph;

import java.util.ArrayList;
import java.util.LinkedList;

import edu.uci.ics.jung.graph.Graph;

public class Dijkstra {

	public static ArrayList<DirectedEdge> shortestPath(Graph<GraphNode, DirectedEdge> graph, GraphNode src, GraphNode dst) {
		// TODO fazer retorno
		// TODO corrigir atributos nós e edges
		LinkedList<GraphNode> nodes = new LinkedList<GraphNode>(graph.getVertices());
		LinkedList<DirectedEdge> edges = new LinkedList<DirectedEdge>(graph.getEdges());
		
		for(GraphNode n : nodes) {
			n.distance = Double.MAX_VALUE;
			n.selected = false;
			n.shortestPath = null;
		}
		for(DirectedEdge e : edges) {
			e.selected = false;
		}
		LinkedList<GraphNode> settledNodes = new LinkedList<GraphNode>();
		LinkedList<GraphNode> unsettledNodes = new LinkedList<GraphNode>();
		
		unsettledNodes.add(src);
		src.distance = 0;
		
		GraphNode evaluationNode = null;
		while(!unsettledNodes.isEmpty()) {
			evaluationNode = getNodeWithLowestDistance(unsettledNodes);
			evaluationNode.selected = true;
			if(evaluationNode == dst) {
				System.out.println(evaluationNode.distance);
				break;
			}
			unsettledNodes.remove(evaluationNode);
			settledNodes.add(evaluationNode);
			unsettledNodes = evaluatedNeighbors(evaluationNode, unsettledNodes, settledNodes);
		}
		
		while(evaluationNode != null && evaluationNode != src && evaluationNode.shortestPath != null) {
			evaluationNode.shortestPath.selected = true;
			evaluationNode = evaluationNode.shortestPath.from();
		}
		
		return null;
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
