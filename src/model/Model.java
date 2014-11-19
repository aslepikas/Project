package model;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.MultiGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class Model implements DirectedGraph<Vertex, Edge>,
		MultiGraph<Vertex, Edge> {

	public Factory<Vertex> vertexFactory = new Factory<Vertex>() {
		int count;

		public Vertex create() {
			System.out.println(0);
			count++;
			return new Vertex(count, 0, 0);
		}
	};

	public Factory<Edge> edgeFactory = new Factory<Edge>() {
		public Edge create() {

			System.out.println(1);
			return new Edge(null, null);
		}
	};

	private ArrayList<Vertex> vertexList;
	private Vertex startVertex;
	private int count = 0;

	public Model() {
		System.out.println(2);
		vertexList = new ArrayList<Vertex>();
		startVertex = null;
	}

	public ArrayList<Vertex> getNodeList() {
		System.out.println(3);
		return vertexList;
	}

	public Vertex getNumber(int number) {
		System.out.println(4);
		for (Vertex i : vertexList) {
			if (i.getNumber() == number) {
				return i;
			}
		}

		return null;
	}

	public void addNewVertex(int x, int y) {
		System.out.println(5);
		count++;
		vertexList.add(new Vertex(count, x, y));
	}

	public void addNewEdge(Vertex v1, Vertex v2) {
		System.out.println(6);
		Edge e = new Edge(v1, v2);
		v1.addEdgeOut(e);
		v2.addEdgeIn(e);
	}

	/*
	 * public int findVertex(int x, int y) { System.out.println(7); int retnum =
	 * -1; for (Vertex i : nodeList) { if (i.pointInNode(x, y)) { retnum =
	 * i.getNumber(); } } return retnum; }
	 */
	public void setStartVertex(Vertex v) {
		System.out.println(8);
		if (startVertex != null)
			startVertex.unSetStart();
		startVertex = v;
		startVertex.setStart();
	}

	public Vertex getStartVertex() {
		return startVertex;
	}

	public boolean isStartVertex(Vertex v) {
		System.out.println(8.5);
		if (hasStart())
			return startVertex.equals(v);
		return false;
	}

	public boolean hasStart() {
		System.out.println(9);
		return startVertex != null;
	}

	public void removeStartVertex() {
		System.out.println(10);
		startVertex = null;
	}

	@Override
	public boolean addEdge(Edge e, Vertex v1, Vertex v2) {
		System.out.println(11);
		v1.addEdgeOut(e);
		v2.addEdgeIn(e);
		e.setStartV(v1);
		e.setTargetV(v2);
		return true;
	}

	@Override
	public boolean addEdge(Edge e, Vertex v1, Vertex v2, EdgeType eT) {
		if (eT == EdgeType.DIRECTED)
			return addEdge(e, v1, v2);
		return false;
	}

	@Override
	public Vertex getDest(Edge e) {
		System.out.println(13);
		return e.getTargetV();
	}

	@Override
	public Pair<Vertex> getEndpoints(Edge e) {
		System.out.println(14);
		return new Pair<Vertex>(e.getStartV(), e.getTargetV());
	}

	@Override
	public Collection<Edge> getInEdges(Vertex v) {
		System.out.println(15);
		return v.getEdgesIn();
	}

	/**
	 * Not expected to be used, but implemented just in case
	 * 
	 * @param v
	 *            Node variable
	 * @param e
	 *            edge, either starting or ending with n
	 * @return node on the other end of the edge
	 */
	@Override
	public Vertex getOpposite(Vertex v, Edge e) {
		System.out.println(16);
		if (e.getStartV().equals(v))
			return e.getTargetV();
		else
			return e.getStartV();

	}

	@Override
	public Collection<Edge> getOutEdges(Vertex v) {
		System.out.println(17);
		return v.getEdgesOut();
	}

	@Override
	public int getPredecessorCount(Vertex v) {
		System.out.println(18);
		return v.getEdgesIn().size();
	}

	@Override
	public Collection<Vertex> getPredecessors(Vertex arg0) {
		System.out.println(19);
		return null;
	}

	@Override
	public Vertex getSource(Edge e) {
		System.out.println(20);
		return e.getStartV();
	}

	@Override
	public int getSuccessorCount(Vertex v) {
		System.out.println(21);
		return v.getEdgesOut().size();
	}

	@Override
	public Collection<Vertex> getSuccessors(Vertex v) {
		System.out.println(22);
		ArrayList<Vertex> retList = new ArrayList<Vertex>();
		for (Edge e : v.getEdgesOut()) {
			if (!retList.contains(e.getTargetV())) {
				retList.add(e.getTargetV());
			}
		}
		return retList;
	}

	@Override
	public int inDegree(Vertex v) {
		System.out.println(23);
		return v.getEdgesIn().size();
	}

	@Override
	public boolean isDest(Vertex v, Edge e) {
		System.out.println(24);
		return v.equals(e.getTargetV());
	}

	@Override
	public boolean isPredecessor(Vertex v1, Vertex v2) {
		System.out.println(25);
		return this.getPredecessors(v1).contains(v2);
	}

	@Override
	public boolean isSource(Vertex v, Edge e) {
		System.out.println(26);
		return e.getStartV().equals(v);
	}

	@Override
	public boolean isSuccessor(Vertex v1, Vertex v2) {
		System.out.println(27);
		return this.getSuccessors(v2).contains(v1);
	}

	@Override
	public int outDegree(Vertex v) {
		System.out.println(28);
		return v.getEdgesOut().size();
	}

	@Override
	public boolean addEdge(Edge e, Collection<? extends Vertex> vertices) {
		System.out.println(29);
		if (vertices.size() == 0)
			return false;
		Vertex[] nodeArray = vertices.toArray(new Vertex[0]);
		e.setStartV(nodeArray[0]);
		nodeArray[0].addEdgeOut(e);
		if (vertices.size() == 2) {
			nodeArray[1].addEdgeIn(e);
			e.setTargetV(nodeArray[1]);
		} else
			nodeArray[0].addEdgeIn(e);
		e.setTargetV(nodeArray[0]);
		return true;
	}

	@Override
	public boolean addEdge(Edge e, Collection<? extends Vertex> vL, EdgeType eT) {
		System.out.println(30);
		if (eT == EdgeType.DIRECTED)
			return addEdge(e, vL);
		return false;
	}

	@Override
	public boolean addVertex(Vertex v) {
		System.out.println(31);
		vertexList.add(v);
		return true;
	}

	/**
	 * not implemented
	 */
	@Override
	public boolean containsEdge(Edge e) {
		System.out.println(32);
		for (Vertex n : vertexList)
			if (n.getEdgesIn().contains(e))
				return true;
		return false;
	}

	@Override
	public boolean containsVertex(Vertex v) {
		System.out.println(33);
		return vertexList.contains(v);
	}

	@Override
	public int degree(Vertex v) {
		System.out.println(34);
		int retnum = 0;
		retnum = v.getEdgesIn().size();
		retnum = retnum + v.getEdgesOut().size();
		for (Edge e : v.getEdgesIn()) {
			if (e.getStartV().equals(v)) {
				retnum--;
			}
		}
		return retnum;
	}

	@Override
	public Edge findEdge(Vertex v1, Vertex v2) {
		System.out.println(35);
		for (Edge e : v1.getEdgesIn()) {
			if (e.getStartV().equals(v2)) {
				return e;
			}
		}
		return null;
	}

	@Override
	public Collection<Edge> findEdgeSet(Vertex v1, Vertex v2) {
		System.out.println(36);
		ArrayList<Edge> retSet = new ArrayList<Edge>();
		for (Edge e : v1.getEdgesIn()) {
			if (e.getStartV().equals(v2)) {
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
		for (Vertex n : vertexList) {
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
	public EdgeType getEdgeType(Edge e) {
		System.out.println(40);
		return EdgeType.DIRECTED;
	}

	@Override
	public Collection<Edge> getEdges() {
		System.out.println(41);
		ArrayList<Edge> retList = new ArrayList<Edge>();
		for (Vertex n : vertexList) {
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
	public Collection<Edge> getIncidentEdges(Vertex v) {
		System.out.println(44);
		if (v == null)
			return null;
		ArrayList<Edge> retList = new ArrayList<Edge>();
		retList.addAll(v.getEdgesIn());
		for (Edge i : v.getEdgesOut())
			if (!retList.contains(i))
				retList.add(i);
		return retList;
	}

	@Override
	public Collection<Vertex> getIncidentVertices(Edge e) {
		System.out.println(45);
		ArrayList<Vertex> retList = new ArrayList<Vertex>();
		retList.add(e.getStartV());
		if (!e.getStartV().equals(e.getTargetV()))
			retList.add(e.getTargetV());
		return retList;
	}

	@Override
	public int getNeighborCount(Vertex v) {
		System.out.println(46);
		return getNeighbors(v).size();
	}

	@Override
	public Collection<Vertex> getNeighbors(Vertex v) {
		System.out.println(47);
		ArrayList<Vertex> retList = new ArrayList<Vertex>();
		for (Edge e : v.getEdgesIn())
			if (!retList.contains(e.getStartV()) && !e.getStartV().equals(v))
				retList.add(e.getStartV());
		for (Edge e : v.getEdgesOut())
			if (!retList.contains(e.getTargetV()) && !e.getTargetV().equals(v))
				retList.add(e.getTargetV());
		return retList;
	}

	@Override
	public int getVertexCount() {
		System.out.println(48);
		return vertexList.size();
	}

	@Override
	public Collection<Vertex> getVertices() {
		System.out.println(49);
		return vertexList;
	}

	@Override
	public boolean isIncident(Vertex v, Edge e) {
		System.out.println(50);
		return (e.getTargetV().equals(v) || e.getStartV().equals(v));
	}

	@Override
	public boolean isNeighbor(Vertex v1, Vertex v2) {
		System.out.println(51);
		for (Edge e : v1.getEdgesIn())
			if (isIncident(v2, e))
				return true;
		for (Edge e : v2.getEdgesOut())
			if (isIncident(v2, e))
				return true;
		return false;
	}

	@Override
	public boolean removeEdge(Edge e) {
		System.out.println(52);
		e.getStartV().removeEdgeOut(e);
		e.getTargetV().removeEdgeIn(e);
		e.clear();
		return true;
	}

	public boolean removeAllEdges(Vertex v) {
		System.out.println(53);
		for (Edge e : v.getEdgesIn()) {
			e.getStartV().removeEdgeOut(e);
		}
		for (Edge e : v.getEdgesOut()) {
			e.getTargetV().removeEdgeIn(e);
		}
		v.clear();
		return true;
	}

	@Override
	public boolean removeVertex(Vertex v) {
		System.out.println(54);
		removeAllEdges(v);
		return vertexList.remove(v);
	}

}
