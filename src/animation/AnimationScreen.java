package animation;

import org.apache.commons.lang3.StringUtils;

import java.awt.BorderLayout;
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
import model.Edge;
import model.Vertex;

@SuppressWarnings("serial")
public class AnimationScreen extends JPanel {

	private JPanel animationPanel;
	private JPanel textPanel;
	private JButton textEntry;
	private JButton stepButton;
	private JButton goButton;
	private JPanel textLabelPanel;
	private String tape;

	private SelectionDialogue select;

	private JPanel placeHolder;

	private ArrayList<MyJUNGCanvas> modelList;

	public AnimationScreen(ArrayList<MyJUNGCanvas> modelList) {

		this.modelList = new ArrayList<MyJUNGCanvas>();

		select = new SelectionDialogue(
				(JFrame) SwingUtilities.getWindowAncestor(this), modelList,
				this.modelList);
		select.setVisible(true);

		tape = "";

		LayoutManager layout = new BorderLayout();
		setLayout(layout);

		animationPanel = new JPanel();

		for (MyJUNGCanvas i : this.modelList) {
			animationPanel.add(i.getVisualizationViewer());
		}
		this.add(animationPanel, BorderLayout.NORTH);

		textPanel = new JPanel();
		textPanel.setLayout(new BorderLayout());

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
		goButton.setEnabled(false);

		textPanel.add(ButtonPanel, BorderLayout.WEST);

		textLabelPanel = new JPanel();
		placeHolder = new JPanel(new GridLayout(2, 1));
		placeHolder.add(new JLabel("	"));
		placeHolder.add(new JLabel("	"));
		textLabelPanel.add(placeHolder);

		textPanel.add(textLabelPanel, BorderLayout.EAST);

		this.add(textPanel, BorderLayout.SOUTH);

		this.setMinimumSize(new Dimension(500, 350));

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
				String message = JOptionPane.showInputDialog(textPanel, "",
						"Enter a string");
				textLabelPanel.removeAll();
				arrowPosition.clear();

				JLabel firstArrow = new JLabel(" ");
				JPanel firstPanel = new JPanel();
				firstPanel.setLayout(new GridLayout(2, 1));
				firstPanel.add(firstArrow);
				firstPanel.add(new JLabel());
				textLabelPanel.add(firstPanel);
				arrowPosition.add(firstArrow);

				if (StringUtils.isAlphanumeric(message)) {
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
				} else {
					textLabelPanel.add(placeHolder);
					stepButton.setEnabled(false);
				}
				SwingUtilities.getWindowAncestor(textLabelPanel).repaint();
			} else if (e.getSource().equals(stepButton)
					&& stepButton.isEnabled()) {
				if (!started) { // if it's only starting the thing
					resetNotFinished();
					countFinished = 0;
					started = true;
					index = 0;
					for (int i = 0; i < modelList.size(); i++) {
						pathList.add(findPath(modelList.get(i)));
					}
					stepButton.setText("step");
					textEntry.setEnabled(false);
					arrowPosition.get(index).setText("↓");

					for (int i = 0; i < modelList.size(); i++) {
						if (pathList.get(i) != null) {
							notFinishedList.set(i, true);
							for (Vertex j : pathList.get(i)) {
								System.out.println(j.toString());
							}
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
						arrowPosition.get(index).setText("↓");
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
									System.out.printf("%d, %d\n",
											tape.length(), index);
									if (tape.length() >= index) {
										boolean hasTransition = false;
										for (Edge k : current.get(i)
												.getEdgesOut()) {
											for (char j : k.getLabels()) {
												hasTransition = hasTransition
														|| (j == tape
																.charAt(index - 1));
											}
											System.out.println(hasTransition);
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
						started = false;
					}
				}
			} else if (e.getSource().equals(goButton)) {
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
