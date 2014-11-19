package model;

import java.util.ArrayList;

public class Vertex {

	private int number;
	private int x;
	private int y;
	private boolean endState;
	private boolean startState;
	private ArrayList<Edge> edgesIn;
	private ArrayList<Edge> edgesOut;

	public Vertex(int n, int x, int y) {
		number = n;
		endState = false;
		edgesIn = new ArrayList<Edge>();
		edgesOut = new ArrayList<Edge>();
		this.x = x;
		this.y = y;
		endState = false;
		startState = false;
	}

	/**
	 * @return returns the edge number.
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Sets the node to be final
	 */
	public void setFinal() {
		endState = true;
	}

	/**
	 * Sets node to not be final
	 */
	public void unSetFinal() {
		endState = false;
	}
	
	/**
	 * To be only used from the model.
	 */
	public void setStart() {
		startState = true;
	}

	/**
	 * To be only used from the model.
	 */
	public void unSetStart() {
		startState = false;
	}
	
	public boolean isStarting() {
		return startState;
	}

	/**
	 * sets x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * sets y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return X coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return Y coordinate
	 */
	public int getY() {
		return y;
	}

	public boolean isFinal() {
		return endState;
	}

	/**
	 * @return ArrayList of edges that lead to this node
	 */
	public ArrayList<Edge> getEdgesIn() {
		return edgesIn;
	}

	/**
	 * @return ArrayList of edges that lead out of this node
	 */
	public ArrayList<Edge> getEdgesOut() {
		return edgesOut;
	}

	/*
	 * /**
	 * 
	 * @param x : x coordinate
	 * 
	 * @param y : y coordinate
	 * 
	 * @return returns whether the point is in the node
	 * 
	 * public boolean pointInNode(int x, int y) { int diffX = this.x - x; int
	 * diffY = this.y - y; int r = Vertex.NODE_WIDTH / 2; return ((diffX *
	 * diffX) + (diffY * diffY)) < (r * r); }
	 */
	public boolean addEdgeIn(Edge e) {
		return edgesIn.add(e);
	}

	public boolean addEdgeOut(Edge e) {
		return edgesOut.add(e);
	}

	public void removeEdgeIn(Edge e) {
		edgesIn.remove(e);
	}

	public void removeEdgeOut(Edge e) {
		edgesOut.remove(e);
	}

	public boolean equals(Vertex n) {
		return n.getNumber() == number;
	}

	public String toString() {
		return String.format("q%d", number);
	}

	public void clear() {
		edgesIn = new ArrayList<Edge>();
		edgesOut = new ArrayList<Edge>();
	}
}
