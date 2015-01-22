package algorithms;

import java.util.ArrayList;
import java.util.Collection;

import model.Edge;
import model.Model;
import model.Vertex;

public class Algorithms {

	public void removeUnreachable(Model model) {
		ArrayList<Vertex> visitedVertices = new ArrayList<Vertex>();
		tagReachable(model, model.getStartVertex(), visitedVertices);
		Collection<Vertex> vertices = model.getVertices();
		for (Vertex v : vertices) {
			if (!visitedVertices.contains(v)) {
				model.removeVertex(v);
			}
		}
	}

	private void tagReachable(Model model, Vertex v,
			ArrayList<Vertex> visitedList) {
		if (!visitedList.contains(v)) {
			visitedList.add(v);
			ArrayList<Edge> edgeList = v.getEdgesOut();
			for (Edge e : edgeList) {
				tagReachable(model, e.getTargetV(), visitedList);
			}

		}

	}

	public ArrayList<Character> getAlphabet(Model model) {

		Collection<Edge> edges = model.getEdges();
		ArrayList<Character> retList = new ArrayList<Character>();
		if (edges != null) {
			for (Edge e : edges) {
				ArrayList<Character> charList = e.getLabels();
				if (charList != null) {
					for (Character c : charList) {
						if (!retList.contains(c)) {
							charList.add(c);
						}
					}
				}
			}
		}

		return retList;
	}

}
