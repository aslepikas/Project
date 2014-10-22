package model;

import java.util.ArrayList;
import java.util.Collection;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class Model implements DirectedGraph<Node, Edge> {

	private ArrayList<Node> nodeList;
	// private ArrayList<Edge> edgeList;
	private int startNode;

	public Model() {
		nodeList = new ArrayList<Node>();
		// edgeList = new ArrayList<Edge>();
		startNode = -1;
	}

	/*
	 * public ArrayList<Node> getNodeList() { return nodeList; }
	 * 
	 * public void addNewNode(int x, int y) { count++; nodeList.add(new
	 * Node(count, x, y)); }
	 * 
	 * public Node getNode(int number) { for (Node i : nodeList) { if
	 * (i.getNumber() == number) { return i; } }
	 * 
	 * return null; } //using retnum instead of returning from inside loop,
	 * because if nodes are //on top of each other, then we want the topmost one
	 * highlighted. public int findNode(int x, int y) { int retnum = -1; for
	 * (Node i : nodeList) { if (i.pointInNode(x, y)) { retnum = i.getNumber();
	 * } } return retnum; }
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
	public boolean addEdge(Edge e, Node n1, Node n2) {
		return n1.addEdgeOut(e) && n2.addEdgeIn(e);
	}

	/**
	 * @arg3 this method is unused in my program. all edges have one direction
	 *       and no different types. One should preferably use the method
	 *       without the extra argument
	 */
	@Override
	public boolean addEdge(Edge e, Node n1, Node n2, EdgeType arg3) {
		return n1.addEdgeOut(e) && n2.addEdgeIn(e);
	}

	@Override
	public Node getDest(Edge e) {
		return e.getTargetN();
	}

	@Override
	public Pair<Node> getEndpoints(Edge e) {
		return new Pair<Node>(e.getStartN(), e.getTargetN());
	}

	@Override
	public Collection<Edge> getInEdges(Node n) {
		return n.getEdgesIn();
	}

	/**
	 * Not expected to be used, but implemented just in case
	 * 
	 * @param n
	 *            Node variable
	 * @param e
	 *            edge, either starting or ending with n
	 * @return node on the other end of the edge
	 */
	@Override
	public Node getOpposite(Node n, Edge e) {
		if (e.getStartN() == n) {
			return e.getTargetN();
		}
		;
		if (e.getTargetN() == n) {
			return e.getStartN();
		}
		;
		return null;
	}

	@Override
	public Collection<Edge> getOutEdges(Node n) {
		return n.getEdgesOut();
	}

	@Override
	public int getPredecessorCount(Node n) {
		return n.getEdgesIn().size();
	}

	@Override
	public Collection<Node> getPredecessors(Node arg0) {
		return null;
	}

	@Override
	public Node getSource(Edge e) {
		return e.getStartN();
	}

	/**
	 * not implemented
	 */
	@Override
	public int getSuccessorCount(Node arg0) {
		return 0;
	}

	/**
	 * not implemented
	 */
	@Override
	public Collection<Node> getSuccessors(Node arg0) {
		return null;
	}

	/**
	 * not implemented
	 */
	@Override
	public int inDegree(Node arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * not implemented
	 */
	@Override
	public boolean isDest(Node arg0, Edge arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * not implemented
	 */
	@Override
	public boolean isPredecessor(Node arg0, Node arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * not implemented
	 */
	@Override
	public boolean isSource(Node arg0, Edge arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * not implemented
	 */
	@Override
	public boolean isSuccessor(Node arg0, Node arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * not implemented
	 */
	@Override
	public int outDegree(Node arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * not implemented
	 */
	@Override
	public boolean addEdge(Edge arg0, Collection<? extends Node> arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * not implemented
	 */
	@Override
	public boolean addEdge(Edge arg0, Collection<? extends Node> arg1,
			EdgeType arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addVertex(Node e) {
		nodeList.add(e);
		return false;
	}

	/**
	 * not implemented
	 */
	@Override
	public boolean containsEdge(Edge arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * not implemented
	 */
	@Override
	public boolean containsVertex(Node arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * not implemented
	 */
	@Override
	public int degree(Node arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * not implemented
	 */
	@Override
	public Edge findEdge(Node arg0, Node arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * not implemented
	 */
	@Override
	public Collection<Edge> findEdgeSet(Node arg0, Node arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * not implemented
	 */
	@Override
	public EdgeType getDefaultEdgeType() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * not implemented
	 */
	@Override
	public int getEdgeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * not implemented
	 */
	@Override
	public int getEdgeCount(EdgeType arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * not implemented
	 */
	@Override
	public EdgeType getEdgeType(Edge arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * not implemented
	 */
	@Override
	public Collection<Edge> getEdges() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * not implemented
	 */
	@Override
	public Collection<Edge> getEdges(EdgeType arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * not implemented
	 */
	@Override
	public int getIncidentCount(Edge arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * not implemented
	 */
	@Override
	public Collection<Edge> getIncidentEdges(Node arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * not implemented
	 */
	@Override
	public Collection<Node> getIncidentVertices(Edge arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * not implemented
	 */
	@Override
	public int getNeighborCount(Node arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * not implemented
	 */
	@Override
	public Collection<Node> getNeighbors(Node arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getVertexCount() {
		return nodeList.size();
	}

	@Override
	public Collection<Node> getVertices() {
		return nodeList;
	}

	/**
	 * not implemented
	 */
	@Override
	public boolean isIncident(Node arg0, Edge arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * not implemented
	 */
	@Override
	public boolean isNeighbor(Node arg0, Node arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeEdge(Edge e) {
		e.getStartN().removeEdgeOut(e);
		e.getTargetN().removeEdgeIn(e);
		e.clear();
		return true;
	}

	public boolean removeAllEdges(Node n) {
		for (Edge e : n.getEdgesIn()) {
			removeEdge(e);
		}
		return true;
	}

	@Override
	public boolean removeVertex(Node n) {
		nodeList.remove(n);

		return false;
	}

}
