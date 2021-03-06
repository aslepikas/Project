package model;

import java.util.ArrayList;

public class Vertex {

	private int number;
	private boolean endState;
	private boolean startState;
	private ArrayList<Edge> edgesIn;
	private ArrayList<Edge> edgesOut;
	private String toolTip;
	private String tag;

	public Vertex(int n) {
		number = n;
		endState = false;
		edgesIn = new ArrayList<Edge>();
		edgesOut = new ArrayList<Edge>();
		endState = false;
		startState = false;
		toolTip = "";
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getTag() {
		return tag;
	}

	/**
	 * @return returns the edge number.
	 */
	public int getNumber() {
		return number;
	}
	
	public void setnumber(int number) {
		this.number = number;
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

	public void setTooltip(String toolTip) {
		this.toolTip = toolTip;
	}

	public String getToolTip() {
		return toolTip;
	}

	public boolean equals(Vertex n) {
		if (n == null) {
			return false;
		}
		return n.getNumber() == number;
	}

	public String toString() {
		return String.format("%s%d", tag, number);
	}

	public void clear() {
		edgesIn = new ArrayList<Edge>();
		edgesOut = new ArrayList<Edge>();
	}
}
