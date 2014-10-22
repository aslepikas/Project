package model;

public class Edge {

	private Node startN;
	private Node targetN;
	private char symbol;

	public Edge(Node start, Node target) {
		startN = start;
		targetN = target;
		symbol = '\0';
	}

	public Edge(Node start, Node target, char c) {
		startN = start;
		targetN = target;
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

	public Node getStartN() {
		return startN;
	}

	public Node getTargetN() {
		return targetN;
	}
	
	public void clear() {
		startN = null;
		targetN = null;
	}
	
	public boolean equals(Edge e){
		return (e.getStartN() == startN) && (e.getTargetN() == targetN) && (e.getSymbol() == symbol);
	}

}
