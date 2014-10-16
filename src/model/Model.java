package model;

import java.util.ArrayList;
import java.util.Collection;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class Model implements DirectedGraph<Node, Edge> {
	
	

	private ArrayList<Node> nodeList;
	//private ArrayList<Connection> connectionList;
	private int startNode;
	private int count;

	public Model() {
		nodeList = new ArrayList<Node>();
		startNode = -1;
		count = 0;
	}
/*
	public ArrayList<Node> getNodeList() {
		return nodeList;
	}

	public void addNewNode(int x, int y) {
		count++;
		nodeList.add(new Node(count, x, y));
	}

	public Node getNode(int number) {
		for (Node i : nodeList) {
			if (i.getNumber() == number) {
				return i;
			}
		}

		return null;
	}
	//using retnum instead of returning from inside loop, because if nodes are
	//on top of each other, then we want the topmost one highlighted.
	public int findNode(int x, int y) {
		int retnum = -1;
		for (Node i : nodeList) {
			if (i.pointInNode(x, y)) {
				retnum = i.getNumber();
			}
		}
		return retnum;
	}
*/

	public void setStartNode(int x) {
		startNode = x;
	}

	public boolean hasStart() {
		return startNode == -1;
	}

	public void removeStartNode() {
		startNode = -1;
	}
	@Override
	public boolean addEdge(Edge arg0, Node arg1, Node arg2) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addEdge(Edge arg0, Node arg1, Node arg2, EdgeType arg3) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Node getDest(Edge arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Pair<Node> getEndpoints(Edge arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Collection<Edge> getInEdges(Node arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Node getOpposite(Node arg0, Edge arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Collection<Edge> getOutEdges(Node arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getPredecessorCount(Node arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Collection<Node> getPredecessors(Node arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Node getSource(Edge arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getSuccessorCount(Node arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Collection<Node> getSuccessors(Node arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int inDegree(Node arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean isDest(Node arg0, Edge arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isPredecessor(Node arg0, Node arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isSource(Node arg0, Edge arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isSuccessor(Node arg0, Node arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int outDegree(Node arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean addEdge(Edge arg0, Collection<? extends Node> arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addEdge(Edge arg0, Collection<? extends Node> arg1,
			EdgeType arg2) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addVertex(Node arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean containsEdge(Edge arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean containsVertex(Node arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int degree(Node arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Edge findEdge(Node arg0, Node arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Collection<Edge> findEdgeSet(Node arg0, Node arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public EdgeType getDefaultEdgeType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getEdgeCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getEdgeCount(EdgeType arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public EdgeType getEdgeType(Edge arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Collection<Edge> getEdges() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Collection<Edge> getEdges(EdgeType arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getIncidentCount(Edge arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Collection<Edge> getIncidentEdges(Node arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Collection<Node> getIncidentVertices(Edge arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getNeighborCount(Node arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Collection<Node> getNeighbors(Node arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getVertexCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Collection<Node> getVertices() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isIncident(Node arg0, Edge arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isNeighbor(Node arg0, Node arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean removeEdge(Edge arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean removeVertex(Node arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
