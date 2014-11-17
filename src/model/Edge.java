package model;

import java.util.ArrayList;

public class Edge {

	private Vertex startV;
	private Vertex targetV;
	private ArrayList<String> labels;

	public Edge(Vertex start, Vertex target) {
		startV = start;
		targetV = target;
		labels = new ArrayList<String>();
	}

	public Edge(Vertex start, Vertex target, ArrayList<String> labels) {
		startV = start;
		targetV = target;
		this.labels = labels;
	}

	public void setLabels(ArrayList<String> labels) {
		this.labels = labels;
	}

	public void setLabels() {
		labels = new ArrayList<String>();
	}

	public ArrayList<String> getLabels() {
		return labels;
	}

	public void setStartV(Vertex n) {
		startV = n;
	}

	public void setTargetV(Vertex n) {
		targetV = n;
	}

	public Vertex getStartV() {
		return startV;
	}

	public Vertex getTargetV() {
		return targetV;
	}

	public void clear() {
		startV = null;
		targetV = null;
	}

	public boolean equals(Edge e) {
		boolean equal = true;
		boolean intermediate = false;

		for (String i : e.getLabels()) {
			for (String j : labels) {
				intermediate = intermediate || (j.compareToIgnoreCase(i) == 0);
			}
			equal = equal && intermediate;
		}

		for (String j : e.getLabels()) {
			for (String i : labels) {
				intermediate = intermediate || (j.compareToIgnoreCase(i) == 0);
			}
			equal = equal && intermediate;
		}

		return (e.getStartV() == startV) && (e.getTargetV() == targetV)
				&& equal;
	}

	public String toString() {
		String str = "";
		for (int i = 0; i < labels.size(); i++) {
			if (i == 0)
				str = labels.get(i);
			else
				str = String.format("%s, %s", str, labels.get(i));
		}
		return str;
	}

}
