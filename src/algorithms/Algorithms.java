package algorithms;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JTabbedPane;

import canvas.MyJUNGCanvas;
import menu.ModeMenu;
import model.Edge;
import model.Model;
import model.Vertex;

public class Algorithms {

	public static void copyActive (ArrayList<MyJUNGCanvas> modelList, JTabbedPane tabbedPane, ModeMenu modeMenu) {
		
		MyJUNGCanvas canvas = modelList.get(tabbedPane.getSelectedIndex());
		Model copyModel = canvas.getModel().copy();
		MyJUNGCanvas nCanvas = new MyJUNGCanvas(copyModel);
		nCanvas.setTitle(String.format("copy of %s", canvas.getTitle()));
		nCanvas.initialise(modeMenu.getMode());
		
		modelList.add(nCanvas);
		tabbedPane.add(nCanvas.getTitle(), nCanvas.getVisualizationViewer());
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
		nCanvas.getVisualizationViewer().repaint();
	}
	
	public static synchronized void removeUnreachable(Model model) {

		ArrayList<Vertex> visitedVertices = new ArrayList<Vertex>();
		tagReachable(model, model.getStartVertex(), visitedVertices);
		Collection<Vertex> vertices = model.getVertices();

		ArrayList<Vertex> purgeList = new ArrayList<Vertex>();
		for (Vertex v : vertices) {

			if (!visitedVertices.contains(v)) {
				purgeList.add(v);
			}
		}

		for (Vertex v : purgeList) {
			model.removeVertex(v);
		}

	}

	private static void tagReachable(Model model, Vertex v,
			ArrayList<Vertex> visitedList) {
		if (v != null) {
			if (!visitedList.contains(v)) {
				visitedList.add(v);
				ArrayList<Edge> edgeList = v.getEdgesOut();
				for (Edge e : edgeList) {
					tagReachable(model, e.getTargetV(), visitedList);
				}

			}
		}
	}

	public static ArrayList<Character> getAlphabet(Model model) {

		Collection<Edge> edges = model.getEdges();
		ArrayList<Character> retList = new ArrayList<Character>();
		if (edges != null) {
			for (Edge e : edges) {
				ArrayList<Character> charList = e.getLabels();
				if (charList != null) {
					for (Character c : charList) {
						if (!retList.contains(c)) {
							retList.add(c);
						}
					}
				}
			}
		}

		return retList;
	}

	public static boolean isDFA(Model model) {

		Collection<Vertex> vertices = model.getVertices();

		for (Vertex v : vertices) {
			ArrayList<Edge> edges = v.getEdgesOut();
			ArrayList<Character> characters = new ArrayList<Character>();
			for (Edge e : edges) {
				ArrayList<Character> labels = e.getLabels();
				for (Character c : labels) {
					if (characters.contains(c)) {
						return false;
					} else {
						characters.add(c);
					}
				}
			}
		}

		return true;
	}

	/**
	 * 
	 * @param model
	 *            - a DFA to be minimised. Removes unreachable states by
	 *            default.
	 * @return Success of minimisation operation. If supplied with an NDFA,
	 *         returns false.
	 */
	public static synchronized boolean minimise(Model model) {
		if (isDFA(model)) {
			removeUnreachable(model);

			ArrayList<ArrayList<Entry>> table = createTable(model);

			for (int i = 0; i < table.size(); i++) { //TODO
				for (int j = 0; j < table.get(i).size(); j++) {
					System.out.print(table.get(i).get(j).isEquivalent());
					System.out.print(" ");
				}
				System.out.print("\n");
			}

			ArrayList<Vertex> vertices = model.getVertices();
			// first iteration
			for (int i = 0; i < table.size(); i++) {
				for (int j = 0; j < table.get(i).size(); j++) {
					if (vertices.get(i).isFinal()
							^ (!vertices.get(j).isFinal())) {
						table.get(i).get(j).setEquivalent(false);
					}
				}
			}
			// TODO
			for (int i = 0; i < table.size(); i++) {
				for (int j = 0; j < table.get(i).size(); j++) {
					System.out.print(table.get(i).get(j).isEquivalent());
					System.out.print(" ");
				}
				System.out.print("\n");
			}

			boolean hadChange = true;
			while (hadChange) {
				hadChange = false;
				for (int i = 0; i < table.size(); i++) {
					for (int j = 0; j < table.get(i).size(); j++) {

						if (table.get(i).get(j).isEquivalent()) {
							ArrayList<Pairing> pairs = table.get(i).get(j)
									.getAllPairs();
							for (Pairing p : pairs) {
								int col = p.getCol();
								int row = p.getRow();
								if (row == -1) {
									row = table.size() - 1;
								}
								if (!table.get(row).get(col).isEquivalent()) {
									table.get(i).get(j).setEquivalent(false);
									hadChange = true;
									break;
								}
							}
						}
					}
				}
			}
			for (int i = 0; i < table.size(); i++) { //TODO
				for (int j = 0; j < table.get(i).size(); j++) {
					System.out.print(table.get(i).get(j).isEquivalent());
					System.out.print(" ");
				}
				System.out.print("\n");
			}
			
			System.out.println(table.size()); // TODO
			Boolean[] merged = new Boolean[table.size()];
			for (int i = 0; i < merged.length; i++) {
				merged[i] = false;
			}
			ArrayList<Vertex> purgeList = new ArrayList<Vertex>();
			for (int i = merged.length - 1; i >= 0; i--) {
				if (!merged[i]) {
					for (int j = 0; j < i; j++) {
						if (table.get(i).get(j).isEquivalent()) {
							merged[j] = true;
							model.mergeVertices(model.getVertices().get(i),
									model.getVertices().get(j));
							purgeList.add(model.getVertices().get(j));
						}
					}
				}
			}

			for (Vertex v : purgeList) {
				model.removeVertex(v);
			}

			return true;

		}
		return false;
	}

	private static ArrayList<ArrayList<Entry>> createTable(Model model) {
		int size = model.getVertexCount();
		ArrayList<ArrayList<Entry>> table = new ArrayList<ArrayList<Entry>>();
		ArrayList<Vertex> vertices = model.getVertices();
		ArrayList<Character> alphabet = getAlphabet(model);

		for (int i = 0; i < size; i++) {
			ArrayList<Entry> tableRow = new ArrayList<Entry>();
			table.add(tableRow);
			for (int j = 0; j < i; j++) {
				Entry entry = new Entry(alphabet.size());
				Vertex v1 = vertices.get(i);
				Vertex v2 = vertices.get(j);
				
				table.get(i);
				alphabet.size();

				for (Edge e : v1.getEdgesOut()) {
					for (Character c : e.getLabels()) {
						entry.setCol(alphabet.indexOf(c),
								vertices.indexOf(e.getTargetV()));
					}
				}

				for (Edge e : v2.getEdgesOut()) {
					for (Character c : e.getLabels()) {
						entry.setRow(alphabet.indexOf(c),
								vertices.indexOf(e.getTargetV()));
					}
				}
				tableRow.add(entry);
			}
		}
		System.out.println(table.size());
		return table;
	}

	private static class Entry {

		public ArrayList<Pairing> pairs;
		public boolean equivalent;

		public Entry(int size) {
			equivalent = true;
			pairs = new ArrayList<Pairing>();
			for (int i = 0; i < size; i++) {
				pairs.add(new Pairing(-1, -1));
			}
		}

		/*
		 * public void set(int pos, int row, int col) {
		 * pairs.get(pos).setRow(row); pairs.get(pos).setCol(col); }
		 */

		public void setRow(int pos, int row) {
			pairs.get(pos).setRow(row);

			if ((pairs.get(pos).getCol() > row) && (row != -1)) {
				int temp = pairs.get(pos).getCol();
				pairs.get(pos).setCol(row);
				pairs.get(pos).setRow(temp);
			}
		}

		public void setCol(int pos, int col) {
			pairs.get(pos).setCol(col);
		}

		/*
		 * public Pairing getPair(int pos) { return pairs.get(pos); }
		 */
		public ArrayList<Pairing> getAllPairs() {
			return pairs;
		}

		public void setEquivalent(boolean eq) {
			equivalent = eq;
		}

		public boolean isEquivalent() {
			return equivalent;
		}

	}

	private static class Pairing {

		public int row;
		public int column;

		public Pairing(int row, int column) {
			this.row = row;
			this.column = column;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public void setCol(int col) {
			this.column = col;
		}

		public int getCol() {
			return column;
		}

		public int getRow() {
			return row;
		}

	}
}
