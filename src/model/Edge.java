package model;

import java.util.ArrayList;

public class Edge {

	private Vertex startV;
	private Vertex targetV;
	private ArrayList<Character> labels;

	public Edge(Vertex start, Vertex target) {
		startV = start;
		targetV = target;
		labels = new ArrayList<Character>();
	}

	public Edge(Vertex start, Vertex target, ArrayList<Character> labels) {
		startV = start;
		targetV = target;
		this.labels = labels;
	}

	public void setLabels(ArrayList<Character> labels) {
		this.labels = labels;
	}

	public void setLabels() {
		labels = new ArrayList<Character>();
	}

	public ArrayList<Character> getLabels() {
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

		for (Character i : e.getLabels()) {
			for (Character j : labels) {
				intermediate = intermediate || (j.charValue() == i.charValue());
			}
			equal = equal && intermediate;
		}

		for (Character j : e.getLabels()) {
			for (Character i : labels) {
				intermediate = intermediate || (j.charValue() == i.charValue());
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
				str = Character.toString(labels.get(i));
			else
				str = String.format("%s, %s", str, Character.toString(labels.get(i)));
		}
		return str;
	}

}
