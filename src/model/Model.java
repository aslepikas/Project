package model;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class Model implements DirectedGraph<Vertex, Edge> {

	public Factory<Vertex> vertexFactory = new Factory<Vertex>() {
		int count;

		public Vertex create() {
			count++;
			return new Vertex(count, 0, 0);
		}
	};

	public Factory<Edge> edgeFactory = new Factory<Edge>() {
		public Edge create() {
			return new Edge(null, null);
		}
	};

	private ArrayList<Vertex> vertexList;
	private Vertex startVertex;
	private int count = 0;

	public Model() {
		vertexList = new ArrayList<Vertex>();
		startVertex = null;
	}

	public ArrayList<Vertex> getNodeList() {
		return vertexList;
	}

	public Vertex getNumber(int number) {
		for (Vertex i : vertexList) {
			if (i.getNumber() == number) {
				return i;
			}
		}

		return null;
	}

	public void addNewVertex(int x, int y) {
		count++;
		vertexList.add(new Vertex(count, x, y));
	}

	public void addNewEdge(Vertex v1, Vertex v2) {
		Edge e = new Edge(v1, v2);
		v1.addEdgeOut(e);
		v2.addEdgeIn(e);
	}

	public void setStartVertex(Vertex v) {
		if (startVertex != null)
			startVertex.unSetStart();
		startVertex = v;
		startVertex.setStart();
	}

	public Vertex getStartVertex() {
		return startVertex;
	}

	public boolean isStartVertex(Vertex v) {
		if (hasStart())
			return startVertex.equals(v);
		return false;
	}

	public boolean hasStart() {
		return startVertex != null;
	}

	public void removeStartVertex() {
		startVertex = null;
	}

	@Override
	public boolean addEdge(Edge e, Vertex v1, Vertex v2) {
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
		return e.getTargetV();
	}

	@Override
	public Pair<Vertex> getEndpoints(Edge e) {
		return new Pair<Vertex>(e.getStartV(), e.getTargetV());
	}

	@Override
	public Collection<Edge> getInEdges(Vertex v) {
		return v.getEdgesIn();
	}

	@Override
	public Vertex getOpposite(Vertex v, Edge e) {
		if (e.getStartV().equals(v))
			return e.getTargetV();
		else
			return e.getStartV();
	}

	@Override
	public Collection<Edge> getOutEdges(Vertex v) {
		return v.getEdgesOut();
	}

	@Override
	public int getPredecessorCount(Vertex v) {
		return v.getEdgesIn().size();
	}

	@Override
	public Collection<Vertex> getPredecessors(Vertex arg0) {
		return null;
	}

	@Override
	public Vertex getSource(Edge e) {
		return e.getStartV();
	}

	@Override
	public int getSuccessorCount(Vertex v) {
		return v.getEdgesOut().size();
	}

	@Override
	public Collection<Vertex> getSuccessors(Vertex v) {
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
		return v.getEdgesIn().size();
	}

	@Override
	public boolean isDest(Vertex v, Edge e) {
		return v.equals(e.getTargetV());
	}

	@Override
	public boolean isPredecessor(Vertex v1, Vertex v2) {
		return this.getPredecessors(v1).contains(v2);
	}

	@Override
	public boolean isSource(Vertex v, Edge e) {
		return e.getStartV().equals(v);
	}

	@Override
	public boolean isSuccessor(Vertex v1, Vertex v2) {
		return this.getSuccessors(v2).contains(v1);
	}

	@Override
	public int outDegree(Vertex v) {
		return v.getEdgesOut().size();
	}

	@Override
	public boolean addEdge(Edge e, Collection<? extends Vertex> vertices) {
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
		if (eT == EdgeType.DIRECTED)
			return addEdge(e, vL);
		return false;
	}

	@Override
	public boolean addVertex(Vertex v) {
		vertexList.add(v);
		return true;
	}

	@Override
	public boolean containsEdge(Edge e) {
		for (Vertex n : vertexList)
			if (n.getEdgesIn().contains(e))
				return true;
		return false;
	}

	@Override
	public boolean containsVertex(Vertex v) {
		return vertexList.contains(v);
	}

	@Override
	public int degree(Vertex v) {
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
		for (Edge e : v1.getEdgesIn()) {
			if (e.getStartV().equals(v2)) {
				return e;
			}
		}
		return null;
	}

	@Override
	public Collection<Edge> findEdgeSet(Vertex v1, Vertex v2) {
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
		return EdgeType.DIRECTED;
	}

	@Override
	public int getEdgeCount() {
		int retNum = 0;
		for (Vertex n : vertexList) {
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
	public EdgeType getEdgeType(Edge e) {
		return EdgeType.DIRECTED;
	}

	@Override
	public Collection<Edge> getEdges() {
		ArrayList<Edge> retList = new ArrayList<Edge>();
		for (Vertex n : vertexList) {
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
	public Collection<Edge> getIncidentEdges(Vertex v) {
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
		ArrayList<Vertex> retList = new ArrayList<Vertex>();
		retList.add(e.getStartV());
		if (!e.getStartV().equals(e.getTargetV()))
			retList.add(e.getTargetV());
		return retList;
	}

	@Override
	public int getNeighborCount(Vertex v) {
		return getNeighbors(v).size();
	}

	@Override
	public Collection<Vertex> getNeighbors(Vertex v) {
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
		return vertexList.size();
	}

	@Override
	public Collection<Vertex> getVertices() {
		return vertexList;
	}

	@Override
	public boolean isIncident(Vertex v, Edge e) {
		return (e.getTargetV().equals(v) || e.getStartV().equals(v));
	}

	@Override
	public boolean isNeighbor(Vertex v1, Vertex v2) {
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
		e.getStartV().removeEdgeOut(e);
		e.getTargetV().removeEdgeIn(e);
		e.clear();
		return true;
	}

	public boolean removeAllEdges(Vertex v) {
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
		removeAllEdges(v);
		return vertexList.remove(v);
	}

}
