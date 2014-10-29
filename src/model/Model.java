package model;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.MultiGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class Model implements DirectedGraph<Node, Edge>, MultiGraph<Node, Edge> {

	public Factory<Node> vertexFactory = new Factory<Node>() {
		int count;

		public Node create() {
			System.out.println(0);
			count++;
			return new Node(count, 0, 0);
		}
	};

	public Factory<Edge> edgeFactory = new Factory<Edge>() {
		public Edge create() {

			System.out.println(1);
			return new Edge(null, null);
		}
	};

	private ArrayList<Node> nodeList;
	private int startNode;
	private int count = 0;

	public Model() {
		System.out.println(2);
		nodeList = new ArrayList<Node>();
		startNode = -1;
	}

	public ArrayList<Node> getNodeList() {
		System.out.println(3);
		return nodeList;
	}

	public Node getNode(int number) {
		System.out.println(4);
		for (Node i : nodeList) {
			if (i.getNumber() == number) {
				return i;
			}
		}

		return null;
	}

	public void addNewNode(int x, int y) {
		System.out.println(5);
		count++;
		nodeList.add(new Node(count, x, y));
	}

	public void addNewEdge(Node n1, Node n2) {
		System.out.println(6);
		Edge e = new Edge(n1, n2);
		n1.addEdgeOut(e);
		n2.addEdgeIn(e);
	}

	public int findNode(int x, int y) {
		System.out.println(7);
		int retnum = -1;
		for (Node i : nodeList) {
			if (i.pointInNode(x, y)) {
				retnum = i.getNumber();
			}
		}
		return retnum;
	}

	public void setStartNode(int x) {
		System.out.println(8);
		startNode = x;
	}

	public boolean hasStart() {
		System.out.println(9);
		return startNode == -1;
	}

	public void removeStartNode() {
		System.out.println(10);
		startNode = -1;
	}

	@Override
	public boolean addEdge(Edge e, Node n1, Node n2) {
		System.out.println(11);
		n1.addEdgeOut(e);
		n2.addEdgeIn(e);
		e.setStartN(n1);
		e.setTargetN(n2);
		return true;
	}

	/**
	 * @arg3 this method is unused in my program. all edges have one direction
	 *       and no different types. One should preferably use the method
	 *       without the extra argument
	 */
	@Override
	public boolean addEdge(Edge e, Node n1, Node n2, EdgeType arg3) {
		return addEdge(e, n1, n2);
	}

	@Override
	public Node getDest(Edge e) {
		System.out.println(13);
		return e.getTargetN();
	}

	@Override
	public Pair<Node> getEndpoints(Edge e) {
		System.out.println(14);
		return new Pair<Node>(e.getStartN(), e.getTargetN());
	}

	@Override
	public Collection<Edge> getInEdges(Node n) {
		System.out.println(15);
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
		System.out.println(16);
		if (e.getStartN().equals(n))
			return e.getTargetN();
		else
			return e.getStartN();

	}

	@Override
	public Collection<Edge> getOutEdges(Node n) {
		System.out.println(17);
		return n.getEdgesOut();
	}

	@Override
	public int getPredecessorCount(Node n) {
		System.out.println(18);
		return n.getEdgesIn().size();
	}

	@Override
	public Collection<Node> getPredecessors(Node arg0) {
		System.out.println(19);
		return null;
	}

	@Override
	public Node getSource(Edge e) {
		System.out.println(20);
		return e.getStartN();
	}

	@Override
	public int getSuccessorCount(Node n) {
		System.out.println(21);
		return n.getEdgesOut().size();
	}

	@Override
	public Collection<Node> getSuccessors(Node n) {
		System.out.println(22);
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
		System.out.println(23);
		return n.getEdgesIn().size();
	}

	@Override
	public boolean isDest(Node n, Edge e) {
		System.out.println(24);
		return n.equals(e.getTargetN());
	}

	@Override
	public boolean isPredecessor(Node n1, Node n2) {
		System.out.println(25);
		return this.getPredecessors(n1).contains(n2);
	}

	@Override
	public boolean isSource(Node n, Edge e) {
		System.out.println(26);
		return e.getStartN().equals(n);
	}

	@Override
	public boolean isSuccessor(Node n1, Node n2) {
		System.out.println(27);
		return this.getSuccessors(n2).contains(n1);
	}

	@Override
	public int outDegree(Node n) {
		System.out.println(28);
		return n.getEdgesOut().size();
	}

	@Override
	public boolean addEdge(Edge e, Collection<? extends Node> nodes) {
		System.out.println(29);
		if (nodes.size() == 0)
			return false;
		Node[] nodeArray = nodes.toArray(new Node[0]);
		e.setStartN(nodeArray[0]);
		nodeArray[0].addEdgeOut(e);
		if (nodes.size() == 2) {
			nodeArray[1].addEdgeIn(e);
			e.setTargetN(nodeArray[1]);
		} else
			nodeArray[0].addEdgeIn(e);
		e.setTargetN(nodeArray[0]);
		return true;
	}

	@Override
	public boolean addEdge(Edge e, Collection<? extends Node> nl, EdgeType et) {
		System.out.println(30);
		return addEdge(e, nl);
	}

	@Override
	public boolean addVertex(Node e) {
		System.out.println(31);
		nodeList.add(e);
		return true;
	}

	/**
	 * not implemented
	 */
	@Override
	public boolean containsEdge(Edge e) {
		System.out.println(32);
		for (Node n : nodeList)
			if (n.getEdgesIn().contains(e))
				return true;
		return false;
	}

	@Override
	public boolean containsVertex(Node n) {
		System.out.println(33);
		return nodeList.contains(n);
	}

	@Override
	public int degree(Node n) {
		System.out.println(34);
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
		System.out.println(35);
		for (Edge e : n1.getEdgesIn()) {
			if (e.getStartN().equals(n2)) {
				return e;
			}
		}
		return null;
	}

	@Override
	public Collection<Edge> findEdgeSet(Node n1, Node n2) {
		System.out.println(36);
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
		System.out.println(37);
		return EdgeType.DIRECTED;
	}

	@Override
	public int getEdgeCount() {
		System.out.println(38);
		int retNum = 0;
		for (Node n : nodeList) {
			retNum = retNum + n.getEdgesOut().size();
		}
		return retNum;
	}

	@Override
	public int getEdgeCount(EdgeType et) {
		System.out.println(39);
		if (et == EdgeType.UNDIRECTED) {
			return getEdgeCount();
		}
		return 0;
	}

	@Override
	public EdgeType getEdgeType(Edge arg0) {
		System.out.println(40);
		return EdgeType.DIRECTED;
	}

	@Override
	public Collection<Edge> getEdges() {
		System.out.println(41);
		ArrayList<Edge> retList = new ArrayList<Edge>();
		for (Node n : nodeList) {
			retList.addAll(n.getEdgesIn());
		}
		return retList;
	}

	@Override
	public Collection<Edge> getEdges(EdgeType et) {
		System.out.println(42);
		if (et == EdgeType.DIRECTED)
			return getEdges();
		return null;
	}

	@Override
	public int getIncidentCount(Edge e) {
		System.out.println(43);
		return getIncidentVertices(e).size();
	}

	@Override
	public Collection<Edge> getIncidentEdges(Node n) {
		System.out.println(44);
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
		System.out.println(45);
		ArrayList<Node> retList = new ArrayList<Node>();
		retList.add(e.getStartN());
		if (!e.getStartN().equals(e.getTargetN()))
			retList.add(e.getTargetN());
		return retList;
	}

	@Override
	public int getNeighborCount(Node n) {
		System.out.println(46);
		return getNeighbors(n).size();
	}

	@Override
	public Collection<Node> getNeighbors(Node n) {
		System.out.println(47);
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
		System.out.println(48);
		return nodeList.size();
	}

	@Override
	public Collection<Node> getVertices() {
		System.out.println(49);
		return nodeList;
	}

	@Override
	public boolean isIncident(Node n, Edge e) {
		System.out.println(50);
		return (e.getTargetN().equals(n) || e.getStartN().equals(n));
	}

	@Override
	public boolean isNeighbor(Node n1, Node n2) {
		System.out.println(51);
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
		System.out.println(52);
		e.getStartN().removeEdgeOut(e);
		e.getTargetN().removeEdgeIn(e);
		e.clear();
		return true;
	}

	public boolean removeAllEdges(Node n) {
		System.out.println(53);
		for (Edge e : n.getEdgesIn()) {
			removeEdge(e);
		}
		return true;
	}

	@Override
	public boolean removeVertex(Node n) {
		System.out.println(54);
		return nodeList.remove(n);
	}

}
