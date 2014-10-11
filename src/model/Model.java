package model;

import java.util.ArrayList;

public class Model {

	private ArrayList<Node> nodeList;
	private ArrayList<Connection> connectionList;
	private int startNode;
	private int count;

	public Model() {
		nodeList = new ArrayList<Node>();
		connectionList = new ArrayList<Connection>();
		startNode = -1;
		count = 0;
	}

	public ArrayList<Node> getNodeList() {
		return nodeList;
	}

	public void addNewNode(int x, int y) {
		count++;
		nodeList.add(new Node(count, x, y));
	}

	public Node getNode(int number) {
		for (Node i : nodeList) {
			if (i.getNumber() == number) {
				return i;
			}
		}

		return null;
	}

	public int findNode(int x, int y) {
		for (Node i : nodeList) {
			if (i.pointInNode(x, y)) {
				return i.getNumber();
			}
		}
		return -1;
	}

	public ArrayList<Connection> getConnectionList() {
		return connectionList;
	}

	public void setStartNode(int x) {
		startNode = x;
	}

	public boolean hasStart() {
		return startNode == -1;
	}

	public void removeStartNode() {
		startNode = -1;
	}

}
