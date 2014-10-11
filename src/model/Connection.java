package model;

public class Connection {

	private int startN;
	private int targetN;
	private char symbol;

	public Connection(int start, int target) {
		startN = start;
		targetN = target;
		symbol = '\0';
	}

	public Connection(int start, int target, char c) {
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

	public int getStartN() {
		return startN;
	}

	public int getTargetN() {
		return targetN;
	}

}
