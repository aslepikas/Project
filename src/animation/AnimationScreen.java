package animation;

import org.apache.commons.lang3.StringUtils;

import containers.ModeTabbedPane;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;

import canvas.MyJUNGCanvas;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import model.Edge;
import model.Vertex;

@SuppressWarnings("serial")
public class AnimationScreen extends JPanel {

	private static final String MARKER = "|";

	private JPanel animationPanel;
	private JPanel controlPanel;
	private JButton textEntry;
	private JButton stepButton;
	private JButton goButton;
	private JPanel textLabelPanel;
	private String tape;

	private SelectionDialogue select;

	private JPanel placeHolder;

	private ArrayList<MyJUNGCanvas> modelList;

	public AnimationScreen(ArrayList<MyJUNGCanvas> modelList)
			throws NothingSelectedException {

		this.modelList = new ArrayList<MyJUNGCanvas>();

		select = new SelectionDialogue(
				(JFrame) SwingUtilities.getWindowAncestor(this), modelList,
				this.modelList);
		select.setVisible(true);
		if (this.modelList.size() == 0) {
			throw new NothingSelectedException();
		}

		int dim = 1;
		for (int i = 1; i * i < this.modelList.size(); i++) {
			dim++;
		}

		tape = "";

		LayoutManager layout = new BorderLayout();
		setLayout(layout);

		animationPanel = new JPanel();
		animationPanel.setLayout(new GridLayout(dim, dim));
		animationPanel
				.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		for (MyJUNGCanvas i : this.modelList) {
			VisualizationViewer<Vertex, Edge> vv = i.getVisualizationViewer();
			Dimension d = vv.getPreferredSize();
			Dimension dNew = new Dimension(d.width / dim, (d.height - 15) / dim);
			// the -15 is so everything would fit in the other window
			// on windows machines -8 is enough, but on linux, -15 is required

			vv.getRenderContext()
					.getMultiLayerTransformer()
					.getTransformer(Layer.VIEW)
					.setScale(dNew.getWidth() / d.getWidth(),
							dNew.getHeight() / d.getHeight(), vv.getCenter());
			vv.setPreferredSize(dNew);
			vv.getRenderContext()
					.getMultiLayerTransformer()
					.getTransformer(Layer.LAYOUT)
					.setTranslate((dNew.getWidth() - d.getWidth()),
							(dNew.getHeight() - d.getHeight()));
			// TODO fix this
			animationPanel.add(vv);
		}
		if (this.modelList.size() < dim * dim) {
			int empty = dim * dim - this.modelList.size();
			for (int i = 0; i < empty; i++) {
				animationPanel.add(new JPanel());
			}
		} // this is here simply to fix the orientation so components would be
			// in the same row

		this.add(animationPanel, BorderLayout.NORTH);

		controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());

		JPanel ButtonPanel = new JPanel();
		ButtonPanel.setLayout(new BorderLayout());

		ButtonListener buttonListener = new ButtonListener();

		textEntry = new JButton("tape");
		textEntry.addMouseListener(buttonListener);
		ButtonPanel.add(textEntry, BorderLayout.WEST);

		stepButton = new JButton("start");
		stepButton.addMouseListener(buttonListener);
		ButtonPanel.add(stepButton, BorderLayout.CENTER);
		stepButton.setEnabled(false);

		goButton = new JButton("go");
		goButton.addMouseListener(buttonListener);
		ButtonPanel.add(goButton, BorderLayout.EAST);
		// goButton.setEnabled(false);

		controlPanel.add(ButtonPanel, BorderLayout.WEST);

		textLabelPanel = new JPanel();
		placeHolder = new JPanel(new GridLayout(2, 1));
		placeHolder.add(new JLabel("	"));
		placeHolder.add(new JLabel("	"));
		textLabelPanel.add(placeHolder);

		controlPanel.add(textLabelPanel, BorderLayout.EAST);

		this.add(controlPanel, BorderLayout.SOUTH);

		this.setMinimumSize(ModeTabbedPane.PREFERRED_SIZE);

		this.setVisible(true);
	}

	private ArrayList<Vertex> findPath(MyJUNGCanvas canvas) {
		Vertex startVertex = canvas.getModel().getStartVertex();
		if (startVertex == null) {
			JOptionPane.showConfirmDialog(this, "No starting vertex");
			return null;
		}
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		path.add(startVertex);

		if (findPath(0, path))
			return path;
		else
			return null;
	}

	private boolean findPath(int step, ArrayList<Vertex> path) {
		/*
		 * for (Vertex i : path) { System.out.println(i.toString()); }
		 */
		Vertex v = path.get(path.size() - 1);
		if (tape.length() >= path.size()) {
			ArrayList<Edge> edges = v.getEdgesOut();
			for (Edge i : edges) {
				ArrayList<Character> labels = i.getLabels();
				boolean hasTransition = false;
				for (char j : labels) {
					hasTransition = hasTransition || (j == tape.charAt(step));
				}
				if (hasTransition) {
					path.add(i.getTargetV());
					if (findPath(step + 1, path)) {
						return true;
					} else {
						path.remove(path.size() - 1);
					}
				}
			}
			return false;
		} else if (v.isFinal()) {
			return true;
		} else
			return false;
	}

	private class ButtonListener implements MouseListener {

		ArrayList<ArrayList<Vertex>> pathList;
		private ArrayList<JLabel> arrowPosition;
		private ArrayList<Boolean> notFinishedList;
		private int index;
		private ArrayList<Vertex> current;
		private boolean started;
		private int countFinished;

		public ButtonListener() {
			arrowPosition = new ArrayList<JLabel>();
			notFinishedList = new ArrayList<Boolean>();
			pathList = new ArrayList<ArrayList<Vertex>>();
			current = new ArrayList<Vertex>();
			for (int i = 0; i < modelList.size(); i++) {
				notFinishedList.add(false);
				started = false;
				countFinished = 0;
				current.add(null);
			}
		}

		private void resetNotFinished() {
			for (int i = 0; i < notFinishedList.size(); i++) {
				notFinishedList.set(i, false);
			}
			;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource().equals(textEntry) && textEntry.isEnabled()) {
				String message = JOptionPane.showInputDialog(controlPanel, "",
						"Enter a string");
				JLabel firstArrow = new JLabel(" ");
				JPanel firstPanel = new JPanel();
				firstPanel.setLayout(new GridLayout(2, 1));
				firstPanel.add(firstArrow);
				firstPanel.add(new JLabel());

				if (StringUtils.isAlphanumeric(message)) {
					textLabelPanel.removeAll();
					arrowPosition.clear();
					textLabelPanel.add(firstPanel);
					arrowPosition.add(firstArrow);
					tape = message;
					JLabel character;
					JPanel characterPanel;
					for (int i = 0, n = message.length(); i < n; i++) {
						char c = message.charAt(i);
						character = new JLabel(Character.toString(c));
						characterPanel = new JPanel();
						characterPanel.setLayout(new GridLayout(2, 1));
						JLabel arrowLabel = new JLabel("");
						arrowPosition.add(arrowLabel);
						characterPanel.add(arrowLabel);
						characterPanel.add(character);

						textLabelPanel.add(characterPanel);
					}
					stepButton.setEnabled(true);
					goButton.setEnabled(true);
				} else if (message == null){
					System.out.println(tape);
					goButton.setEnabled(true);
				} else {
					tape = "";
					textLabelPanel.add(placeHolder);
					stepButton.setEnabled(false);
					goButton.setEnabled(true);
				}
				SwingUtilities.getWindowAncestor(textLabelPanel).repaint();
			} else if (e.getSource().equals(stepButton)
					&& stepButton.isEnabled()) {
				goButton.setEnabled(false);
				if (!started) { // if it's only starting the thing
					resetNotFinished();
					countFinished = 0;
					started = true;
					index = 0;
					pathList.clear();
					for (int i = 0; i < modelList.size(); i++) {
						pathList.add(findPath(modelList.get(i)));
					}
					stepButton.setText("step");
					textEntry.setEnabled(false);
					arrowPosition.get(index).setText(MARKER);

					for (int i = 0; i < modelList.size(); i++) {
						if (pathList.get(i) != null) {
							notFinishedList.set(i, true);
							modelList.get(i).getVisualizationViewer()
									.getPickedVertexState().clear();
							modelList.get(i).getVisualizationViewer()
									.getPickedVertexState()
									.pick(pathList.get(i).get(index), true);
						} else {
							current.set(i, modelList.get(i).getModel()
									.getStartVertex());
							if (current.get(i) != null) {
								notFinishedList.set(i, true);
								modelList.get(i).getVisualizationViewer()
										.getPickedVertexState()
										.pick(current.get(i), true);
							}
						}
					}
				} else { // if it is further stepping
					if (index < arrowPosition.size() - 1) { // arrow is not out
															// of bounds
						arrowPosition.get(index).setText("");
						index++;
						arrowPosition.get(index).setText(MARKER);
						if (index == arrowPosition.size() - 1)
							stepButton.setText("finish");
						for (int i = 0; i < modelList.size(); i++) {
							if (notFinishedList.get(i)) {
								if (pathList.get(i) != null) {
									modelList.get(i).getVisualizationViewer()
											.getPickedVertexState().clear();
									modelList
											.get(i)
											.getVisualizationViewer()
											.getPickedVertexState()
											.pick(pathList.get(i).get(index),
													true);
								} else {
									if (tape.length() >= index) {
										boolean hasTransition = false;
										for (Edge k : current.get(i)
												.getEdgesOut()) {
											for (char j : k.getLabels()) {
												hasTransition = hasTransition
														|| (j == tape
																.charAt(index - 1));
											}
											if (hasTransition) {

												modelList
														.get(i)
														.getVisualizationViewer()
														.getPickedVertexState()
														.clear();

												modelList
														.get(i)
														.getVisualizationViewer()
														.getPickedVertexState()
														.pick(k.getTargetV(),
																true);
												current.set(i, k.getTargetV());
												break;
											}
										}
										if (!hasTransition) {
											notFinishedList.set(i, false);
											countFinished++;
											modelList.get(i)
													.getVisualizationViewer()
													.getPickedVertexState()
													.clear();
										}
									}
								}
							}
						}

					} else { // arrow went out of bounds

						for (int i = 0; i < modelList.size(); i++) {
							if (notFinishedList.get(i)) {
								notFinishedList.set(i, false);
								countFinished++;
								modelList.get(i).getVisualizationViewer()
										.getPickedVertexState().clear();
							}
						}
					}
					if (countFinished == notFinishedList.size()) {
						arrowPosition.get(index).setText("");
						index = 0;
						stepButton.setText("start");
						textEntry.setEnabled(true);
						goButton.setEnabled(true);
						started = false;
					}
				}
			} else if (e.getSource().equals(goButton)) {
				String retString = "";
				if (tape.equals("")) {
					retString = "Acceptance status for the empty string:\n";
				} else {
					retString = "Acceptance status for the string " + tape + ":\n";
				}
				for (int i = 0; i < modelList.size(); i++) {
					if (findPath(modelList.get(i)) == null) {
						retString = retString + modelList.get(i).getTitle() + " does not accept this string\n";
					}
					else {
						retString = retString + modelList.get(i).getTitle()
								+ " accepts this string\n";
					}
				}
				JOptionPane.showMessageDialog(animationPanel.getRootPane(), retString);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

	}

}
