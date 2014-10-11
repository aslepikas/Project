package model;

//import java.util.ArrayList;

public class Node {

	private int number;
	private int x;
	private int y;
	private boolean endState;
	public static final int NODE_WIDTH = 40;

	// private ArrayList<Connection> connectionListOut;
	// private ArrayList<Connection> connectionListIn;

	public Node(int n, int x, int y) {
		number = n;
		endState = false;
		this.x = x;
		this.y = y;
		// connectionListOut = new ArrayList<Connection>();
		// connectionListIn = new ArrayList<Connection>();

	}

	public int getNumber() {
		return number;
	}

	/*
	 * public void addConnectionOut(Connection c){ connectionListOut.add(c); }
	 * 
	 * public void addConnectionIn(Connection c){ connectionListIn.add(c); }
	 */

	public void setFinal(boolean f) {
		endState = f;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setEndState(boolean s) {
		endState = s;
	}

	public boolean getEndState() {
		return endState;
	}

	public boolean pointInNode(int x, int y) {
		int diffX = this.x - x;
		int diffY = this.y - y;
		int r = Node.NODE_WIDTH / 2;
		return ((diffX * diffX) + (diffY * diffY)) < (r * r);
	}

}
