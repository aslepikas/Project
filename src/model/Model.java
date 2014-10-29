package model;

import java.util.ArrayList;
import java.util.Collection;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.MultiGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class Model implements DirectedGraph<Node, Edge>, MultiGraph<Node, Edge> {

	private ArrayList<Node> nodeList;
	// private ArrayList<Edge> edgeList;
	private int startNode;
	private int count = 0;

	public Model() {
		nodeList = new ArrayList<Node>();
		// edgeList = new ArrayList<Edge>();
		startNode = -1;
	}

	public ArrayList<Node> getNodeList() {
		return nodeList;
	}

	public Node getNode(int number) {
		for (Node i : nodeList) {
			if (i.getNumber() == number) {
				return i;
			}
		}

		return null;
	}

	public void addNewNode(int x, int y) {
		count++;
		nodeList.add(new Node(count, x, y));
	}

	public void addNewEdge(Node n1, Node n2) {
		Edge e = new Edge(n1, n2);
		n1.addEdgeOut(e);
		n2.addEdgeIn(e);
	}

	public int findNode(int x, int y) {
		int retnum = -1;
		for (Node i : nodeList) {
			if (i.pointInNode(x, y)) {
				retnum = i.getNumber();
			}
		}
		return retnum;
	}

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
		if (e.getStartN().equals(n))
			return e.getTargetN();
		else
			return e.getStartN();

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

	@Override
	public int getSuccessorCount(Node n) {
		return n.getEdgesOut().size();
	}

	@Override
	public Collection<Node> getSuccessors(Node n) {
		ArrayList<Node> retList = new ArrayList<Node>();
		for (Edge e : n.getEdgesOut()) {
			if (!retList.contains(e.getTargetN())) {
				retList.add(e.getTargetN());
			}
		}
		return retList;
	}

	@Override
	public int inDegree(Node n) {
		return n.getEdgesIn().size();
	}

	@Override
	public boolean isDest(Node n, Edge e) {
		return n.equals(e.getTargetN());
	}

	@Override
	public boolean isPredecessor(Node n1, Node n2) {
		return this.getPredecessors(n1).contains(n2);
	}

	@Override
	public boolean isSource(Node n, Edge e) {
		return e.getStartN().equals(n);
	}

	@Override
	public boolean isSuccessor(Node n1, Node n2) {
		return this.getSuccessors(n2).contains(n1);
	}

	@Override
	public int outDegree(Node n) {
		return n.getEdgesOut().size();
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
		return true;
	}

	/**
	 * not implemented
	 */
	@Override
	public boolean containsEdge(Edge arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsVertex(Node n) {
		return nodeList.contains(n);
	}

	@Override
	public int degree(Node n) {
		int retnum = 0;
		retnum = n.getEdgesIn().size();
		retnum = retnum + n.getEdgesOut().size();
		for (Edge e : n.getEdgesIn()) {
			if (e.getStartN().equals(n)) {
				retnum--;
			}
		}
		return retnum;
	}

	@Override
	public Edge findEdge(Node n1, Node n2) {
		for (Edge e : n1.getEdgesIn()) {
			if (e.getStartN().equals(n2)) {
				return e;
			}
		}
		return null;
	}

	@Override
	public Collection<Edge> findEdgeSet(Node n1, Node n2) {
		ArrayList<Edge> retSet = new ArrayList<Edge>();
		for (Edge e : n1.getEdgesIn()) {
			if (e.getStartN().equals(n2)) {
				retSet.add(e);
			}
		}
		return retSet;
	}

	@Override
	public EdgeType getDefaultEdgeType() {
		return EdgeType.DIRECTED;
	}

	@Override
	public int getEdgeCount() {
		int retNum = 0;
		for (Node n : nodeList) {
			retNum = retNum + n.getEdgesOut().size();
		}
		return retNum;
	}

	@Override
	public int getEdgeCount(EdgeType et) {
		if (et == EdgeType.UNDIRECTED) {
			return getEdgeCount();
		}
		return 0;
	}

	@Override
	public EdgeType getEdgeType(Edge arg0) {
		return EdgeType.DIRECTED;
	}

	@Override
	public Collection<Edge> getEdges() {
		ArrayList<Edge> retList = new ArrayList<Edge>();
		for (Node n : nodeList) {
			retList.addAll(n.getEdgesIn());
		}
		return retList;
	}

	@Override
	public Collection<Edge> getEdges(EdgeType et) {
		if (et == EdgeType.DIRECTED)
			return getEdges();
		return null;
	}

	@Override
	public int getIncidentCount(Edge e) {
		return getIncidentVertices(e).size();
	}

	@Override
	public Collection<Edge> getIncidentEdges(Node n) {
		if (n == null)
			return null;
		ArrayList<Edge> retList = new ArrayList<Edge>();
		retList.addAll(n.getEdgesIn());
		for (Edge i : n.getEdgesOut())
			if (!retList.contains(i))
				retList.add(i);
		return retList;
	}

	@Override
	public Collection<Node> getIncidentVertices(Edge e) {
		ArrayList<Node> retList = new ArrayList<Node>();
		retList.add(e.getStartN());
		if (!e.getStartN().equals(e.getTargetN()))
			retList.add(e.getTargetN());
		return retList;
	}

	@Override
	public int getNeighborCount(Node n) {
		return getNeighbors(n).size();
	}

	@Override
	public Collection<Node> getNeighbors(Node n) {
		ArrayList<Node> retList = new ArrayList<Node>();
		for (Edge e : n.getEdgesIn())
			if (!retList.contains(e.getStartN()) && !e.getStartN().equals(n))
				retList.add(e.getStartN());
		for (Edge e : n.getEdgesOut())
			if (!retList.contains(e.getTargetN()) && !e.getTargetN().equals(n))
				retList.add(e.getTargetN());
		return retList;
	}

	@Override
	public int getVertexCount() {
		return nodeList.size();
	}

	@Override
	public Collection<Node> getVertices() {
		return nodeList;
	}

	@Override
	public boolean isIncident(Node n, Edge e) {
		return (e.getTargetN().equals(n) || e.getStartN().equals(n));
	}

	@Override
	public boolean isNeighbor(Node n1, Node n2) {
		for (Edge e : n1.getEdgesIn())
			if (isIncident(n2, e))
				return true;
		for (Edge e : n2.getEdgesOut())
			if (isIncident(n2, e))
				return true;
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
		return nodeList.remove(n);
	}

}
