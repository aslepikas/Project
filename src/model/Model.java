package model;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class Model implements DirectedGraph<Vertex, Edge> {

	public Factory<Vertex> vertexFactory = new Factory<Vertex>() {
		public Vertex create() {
			count++;
			return new Vertex(count);
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
	private String tag;
	
	public Model() {
		vertexList = new ArrayList<Vertex>();
		startVertex = null;
		tag = TagSetter.getTag();
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

	/**
	 * 
	 * @param v1
	 *            - vertex that gets v2 merged into
	 * @param v2
	 *            - vertex merged into v1
	 * 
	 * @code this method does not remove v2 from the model, it has to be done
	 *       manually
	 */
	public void mergeVertices(Vertex v1, Vertex v2) {
		ArrayList<Edge> v2outEdges = v2.getEdgesOut();
		ArrayList<Edge> v2inEdges = v2.getEdgesIn();

		for (Edge e : v2inEdges) {
			if (!isPredecessor(e.getStartV(), v1)) {
				addEdge(new Edge(e.getStartV(), v1, e.getLabels()),
						e.getStartV(), v1);
			} else {
				for (Edge i : v1.getEdgesIn()) {
					if (i.getStartV().equals(e.getStartV())) {
						i.addLabels(e.getLabels());
						break;
					}
				}
			}
		}

		for (Edge e : v2outEdges) {
			if (!isPredecessor(v1, e.getTargetV())) {
				addEdge(new Edge(v1, e.getTargetV(), e.getLabels()), v1,
						e.getTargetV());
			} else {
				for (Edge i : v1.getEdgesOut()) {
					if (i.getTargetV().equals(e.getTargetV())) {
						i.addLabels(e.getLabels());
						break;
					}
				}
			}
		}

		if (isStartVertex(v2)) {
			setStartVertex(v1);
		}
	}

	public Model copy() {

		Model m = new Model();
		m.count = count;

		for (Vertex v : vertexList) {
			Vertex nv = new Vertex(v.getNumber());
			m.vertexList.add(nv);

			if (v.isFinal()) {
				nv.setFinal();
			}
			if (v.isStarting()) {
				m.setStartVertex(nv);
			}
		}

		for (int i = 0; i < vertexList.size(); i++) {
			Vertex v = vertexList.get(i);
			ArrayList<Edge> edges = v.getEdgesOut();
			for (Edge e : edges) {
				Edge eNew = new Edge(m.vertexList.get(i),
						m.vertexList.get(vertexList.indexOf(e.getTargetV())));
				m.vertexList.get(i).addEdgeOut(eNew);
				m.vertexList.get(vertexList.indexOf(e.getTargetV())).addEdgeIn(
						eNew);
				for (Character c : e.getLabels()) {
					eNew.getLabels().add(c);
				}
			}
		}

		return m;
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
	public ArrayList<Vertex> getPredecessors(Vertex v) {
		ArrayList<Vertex> retList = new ArrayList<Vertex>();
		for (Edge e : v.getEdgesIn()) {
			retList.add(e.getStartV());
		}
		return retList;
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
		return this.getPredecessors(v2).contains(v1);
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
		if (v.getNumber() > count) {
			count = v.getNumber();
		}
		v.setTag(tag);
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
		for (Edge e : v1.getEdgesOut()) {
			if (e.getTargetV().equals(v2)) {
				return e;
			}
		}
		return null;
	}

	@Override
	public Collection<Edge> findEdgeSet(Vertex v1, Vertex v2) {
		ArrayList<Edge> retSet = new ArrayList<Edge>();
		for (Edge e : v1.getEdgesOut()) {
			if (e.getTargetV().equals(v2)) {
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
	public ArrayList<Edge> getEdges() {
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
	public ArrayList<Vertex> getVertices() {
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

	private static class TagSetter {

		private static boolean initialised = false;
		private static int count;
		private static int len;
		private static char start;

		private static void initialise() {
			initialised = true;
			count = 0;
			len = 10;
			start = 'q';
		}

		public static String getTag() {
			if (!initialised) {
				initialise();
			}
			String retString = "";
			int temp = count;
			while (temp/len > 0) {
				temp = temp/len;
				retString = (char)(temp - 1 + (int)start) + retString;
			}
			retString = retString + (char)(count%len + (int)start);
			
			count++;
			System.out.println(retString);//TODO
			return retString;
		}
	}

}
