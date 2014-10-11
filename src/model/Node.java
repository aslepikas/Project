package model;

import java.util.ArrayList;

public class Node {

	private int number;
	private int x;
	private int y;
	private ArrayList<Connection> connectionListOut;
	private ArrayList<Connection> connectionListIn;

	public Node(int n) {
		number = n;
		connectionListOut = new ArrayList<Connection>();
		connectionListIn = new ArrayList<Connection>();

	}

	public int getNumber() {
		return number;
	}
	
	public void addConnectionOut(Connection c){
		connectionListOut.add(c);
	}
	
	public void addConnectionIn(Connection c){
		connectionListIn.add(c);
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

}
