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

	public boolean isDFA(Model model) {

		Collection<Vertex> vertices = model.getVertices();

		for (Vertex v : vertices) {
			ArrayList<Edge> edges = v.getEdgesOut();
			ArrayList<Character> characters = new ArrayList<Character>();
			for (Edge e : edges) {
				ArrayList<Character> labels = e.getLabels();
				for (Character c : labels) {
					if (characters.contains(c)) {
						return false;
					} else {
						characters.add(c);
					}
				}
			}
		}

		return true;
	}

}
