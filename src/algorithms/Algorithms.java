package algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JTabbedPane;

import canvas.MyJUNGCanvas;
import menu.ModeMenu;
import model.Edge;
import model.Model;
import model.Vertex;

public class Algorithms {

	public static void copyActive(ArrayList<MyJUNGCanvas> modelList,
			JTabbedPane tabbedPane, ModeMenu modeMenu) {

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
	
	public static Model parseExpression(String exp) {
		if (!isExpression(exp)) {
			return null;
		}
		Model retModel = new Model();
		
		return retModel;
	}

	//TODO not done actually, might delete it after all
	private static boolean isExpression(String exp) {
		int bracketCount = 0;
		for (int i = 0; i < exp.length(); i++) {
			char c = exp.charAt(i);
			if (c == '(') {
				bracketCount++;
			} else if (c == ')') {
				bracketCount--;
			} else if (c == '*' || c == '|' || c == ' ') {
			} else if (!Character.isLetterOrDigit(c)) {
				return false;
			}
			if (bracketCount < 0)
				return false;
		}
		if (bracketCount!=0)
			return false;

		return true;
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
	 * Requires the model to have a starting node.
	 * 
	 * @param model
	 *            - the model to be converted.
	 * @return a new model, that has been converted.
	 */
	public static Model nfaToDfa(Model model) {
		Vertex start = model.getStartVertex();

		if (start != null) {

			ArrayList<Character> alphabet = getAlphabet(model);
			HashMap<Integer, ArrayList<Integer>[]> referenceTable = new HashMap<Integer, ArrayList<Integer>[]>();
			ArrayList<Vertex> vertices = model.getVertices();

			// construct vertex to position table here
			int[] vertexPos = new int[vertices.get(vertices.size() - 1)
					.getNumber() + 1];
			for (int i = 0; i < vertices.size(); i++) {
				vertexPos[vertices.get(i).getNumber()] = i;
			}

			// construct reference table for every single vertex
			for (Vertex v : vertices) {
				ArrayList<Edge> edges = v.getEdgesOut();
				@SuppressWarnings("unchecked")
				ArrayList<Integer>[] destinations = new ArrayList[alphabet
						.size()];
				for (Edge e : edges) {
					ArrayList<Character> labels = e.getLabels();
					Vertex target = e.getTargetV();
					for (Character c : labels) {
						int pos = alphabet.indexOf(c);
						if (destinations[pos] == null) {
							destinations[pos] = new ArrayList<Integer>();
						}
						destinations[pos].add(target.getNumber());

					}
				}
				referenceTable
						.put(Integer.valueOf(v.getNumber()), destinations);
			}
			/*
			 * reference table works as such: key is the number of the vertex it
			 * holds an array, where each position corresponds to letter
			 * positions in alphabet each number in the arraylist is a node that
			 * is lead to.
			 */

			// -------------------- preliminary work done

			HashMap<Long, ArrayList<Integer>[]> table = new HashMap<Long, ArrayList<Integer>[]>();

			ArrayList<Integer> queueItem = new ArrayList<Integer>();
			queueItem.add(start.getNumber());

			LinkedList<ArrayList<Integer>> queue = new LinkedList<ArrayList<Integer>>();
			queue.add(queueItem);

			ArrayList<Long> keyList = new ArrayList<Long>();

			while (!queue.isEmpty()) {
				queueItem = queue.poll();

				long key = getKey(queueItem, vertexPos);
				if (!keyList.contains(new Long(key))) {
					keyList.add(key);
					@SuppressWarnings("unchecked")
					ArrayList<Integer>[] tableItem = new ArrayList[alphabet
							.size()];

					for (Integer vertex : queueItem) {
						ArrayList<Integer>[] transitions = referenceTable
								.get(vertex);
						for (int i = 0; i < transitions.length; i++) {
							if (transitions[i] != null) {
								if (tableItem[i] == null) {
									tableItem[i] = new ArrayList<Integer>();
								}

								for (int j : transitions[i]) {
									if (!tableItem[i].contains(j)) {
										tableItem[i].add(j);
									}
								}

							}
						}
					}
					table.put(key, tableItem);

					for (ArrayList<Integer> i : tableItem) {
						if (i != null) {
							queue.add(i);

						}
					}
				}

			}

			// constructing model
			Model retModel = new Model();

			for (Long key : keyList) {
				Vertex nv = retModel.vertexFactory.create();
				retModel.addVertex(nv);
				String tooltip = null;
				long num = key;
				for (int i = 0; i < vertices.size(); i++) {
					if (num % 2 == 1) {
						Vertex v = vertices.get(i);
						if (v.isFinal()) {
							nv.setFinal();
						}
						if (tooltip == null) {
							tooltip = v.toString();
						} else {
							tooltip = tooltip + " " + v.toString();
						}
					}
					num = num / 2;
				}
				nv.setTooltip(tooltip);
			}
			retModel.setStartVertex(retModel.getVertices().get(0));
			for (int i = 0; i < keyList.size(); i++) {
				Vertex nv = retModel.getVertices().get(i);
				ArrayList<Integer>[] tableItem = table.get(keyList.get(i));
				for (int j = 0; j < tableItem.length; j++) {
					if (tableItem[j] != null) {
						long key = getKey(tableItem[j], vertexPos);
						Vertex target = retModel.getVertices().get(
								keyList.indexOf(new Long(key)));
						if (!retModel.isPredecessor(nv, target)) {
							Edge e = new Edge(nv, target);
							ArrayList<Character> label = new ArrayList<Character>();
							label.add(alphabet.get(j));
							e.addLabels(label);
							retModel.addEdge(e, nv, target);
						} else {
							Edge e = retModel.findEdge(nv, target);

							ArrayList<Character> label = new ArrayList<Character>();
							label.add(alphabet.get(j));
							e.addLabels(label);
						}
					}
				}
			}
			return retModel;
		}
		return null;
	}

	private static long getKey(ArrayList<Integer> items, int[] vertexPos) {
		long key = 0;
		for (Integer v : items) {
			key += Math.round(Math.pow(2, vertexPos[v]));
		}
		return key;
	}

	/**
	 * 
	 * @param model
	 *            - a DFA to be minimised. Removes unreachable states by
	 *            default.
	 * @return Success of minimisation operation. If supplied with an NDFA,
	 *         returns false.
	 */
	public static boolean minimise(Model model) {
		if (isDFA(model)) {

			removeUnreachable(model);

			ArrayList<ArrayList<Entry>> table = createTable(model);
			ArrayList<Vertex> vertices = model.getVertices();
			// first iteration
			for (int i = 0; i < table.size() - 1; i++) {
				for (int j = 0; j < table.get(i).size(); j++) {
					if (vertices.get(i).isFinal() ^ (vertices.get(j).isFinal())) {
						table.get(i).get(j).setEquivalent(false);
					}
				}
			}
			for (int j = 0; j < table.get(table.size() - 1).size(); j++) {
				if (vertices.get(j).isFinal()) {
					table.get(table.size() - 1).get(j).setEquivalent(false);
				}
			}
			// end of first iteration

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

								if (col == -1) {
									col = table.size() - 1;
								}

								if (!(row == col)) {

									if (!table.get(row).get(col).isEquivalent()) {
										table.get(i).get(j)
												.setEquivalent(false);
										hadChange = true;
										break;
									}
								}
							}
						}
					}
				}
			}

			Boolean[] merged = new Boolean[table.size() - 1];
			for (int i = 0; i < merged.length; i++) {
				merged[i] = false;
			}
			ArrayList<Vertex> purgeList = new ArrayList<Vertex>();
			for (int i = merged.length - 1; i >= 0; i--) {
				if (!merged[i]) {
					for (int j = 0; j < i; j++) {
						if (table.get(i).get(j).isEquivalent()) {
							Vertex v1 = vertices.get(i);
							Vertex v2 = vertices.get(j);
							merged[j] = true;
							model.mergeVertices(v1, v2);
							purgeList.add(v2);
							v1.setTooltip(v1.toString() + ", " + v2.toString());
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
		// last iteration, adding the -1th line.

		ArrayList<Entry> tableRow = new ArrayList<Entry>();
		table.add(tableRow);
		for (int j = 0; j < size; j++) {
			Entry entry = new Entry(alphabet.size());
			Vertex v2 = vertices.get(j);

			for (Edge e : v2.getEdgesOut()) {
				for (Character c : e.getLabels()) {
					entry.setRow(alphabet.indexOf(c),
							vertices.indexOf(e.getTargetV()));
				}
			}
			tableRow.add(entry);
		}
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

		public void setRow(int pos, int row) {
			pairs.get(pos).setRow(row);

			if (((pairs.get(pos).getCol() > row) && (row != -1))
					|| (pairs.get(pos).getCol() == -1)) {
				int temp = pairs.get(pos).getCol();
				pairs.get(pos).setCol(row);
				pairs.get(pos).setRow(temp);
			}
		}

		public void setCol(int pos, int col) {
			pairs.get(pos).setCol(col);
		}

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
