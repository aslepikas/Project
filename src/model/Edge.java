package model;

public class Edge {

	private Vertex startV;
	private Vertex targetV;
	private char symbol;

	public Edge(Vertex start, Vertex target) {
		startV = start;
		targetV = target;
		symbol = '\0';
	}

	public Edge(Vertex start, Vertex target, char c) {
		startV = start;
		targetV = target;
		symbol = c;
	}

	public void setsymbol(char c) {
		symbol = c;
	}

	public void setSymbol() {
		symbol = '\0';
	}

	public char getSymbol() {
		return symbol;
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
		return (e.getStartV() == startV) && (e.getTargetV() == targetV)
				&& (e.getSymbol() == symbol);
	}
	
	public String toString(){
		return new String();
	}

}
