package animation;

import org.apache.commons.lang3.StringUtils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import canvas.MyJUNGCanvas;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import model.Edge;
import model.Model;
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

	private JPanel placeHolder;

	private ArrayList<MyJUNGCanvas> modelList;

	public AnimationScreen(ArrayList<MyJUNGCanvas> modelList) {

		this.modelList = modelList;
		tape = "";

		LayoutManager layout = new BorderLayout();
		setLayout(layout);

		animationPanel = new JPanel();

		for (MyJUNGCanvas i : modelList) {
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

	private ArrayList<Vertex> findPath() {
		Vertex startVertex = modelList.get(0).getModel().getStartVertex();
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
		for (Vertex i : path) {
			System.out.println(i.toString());
		}
		Vertex v = path.get(path.size() - 1);
		if (tape.length() >= path.size()) {
			ArrayList<Edge> edges = v.getEdgesOut();
			for (Edge i : edges) {
				ArrayList<Character> labels = i.getLabels();
				boolean hasTransition = false;
				for (Character j : labels) {
					hasTransition = hasTransition
							|| (j.charValue() == tape.charAt(step));
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

		ArrayList<Vertex> path;
		private ArrayList<JLabel> arrowPosition;
		private boolean started;
		private int index;

		public ButtonListener() {
			arrowPosition = new ArrayList<JLabel>();
			started = false;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource().equals(textEntry) && textEntry.isEnabled()) {
				started = false;
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
			} else if (e.getSource().equals(stepButton) && stepButton.isEnabled()) {
				if (!started) {
					index = 0;
					path = findPath();
					if (path != null) {
						started = true;
						stepButton.setText("step");
						textEntry.setEnabled(false);
						arrowPosition.get(0).setText("↓");
						for (Vertex i : path) {
							System.out.println(i.toString());
						}
						modelList.get(0).getVisualizationViewer()
								.getPickedVertexState().clear();
						modelList.get(0).getVisualizationViewer()
								.getPickedVertexState()
								.pick(path.get(index), true);
					} else {
					}
				} else {
					if (index < arrowPosition.size() - 1) {
						arrowPosition.get(index).setText("");
						index++;
						arrowPosition.get(index).setText("↓");
						modelList.get(0).getVisualizationViewer()
								.getPickedVertexState().clear();
						modelList.get(0).getVisualizationViewer()
								.getPickedVertexState()
								.pick(path.get(index), true);
						if (index == arrowPosition.size() - 1)
							stepButton.setText("finish");
					} else {
						started = false;
						arrowPosition.get(index).setText("");
						index = 0;
						textEntry.setEnabled(true);
						stepButton.setText("start");
						modelList.get(0).getVisualizationViewer()
								.getPickedVertexState().clear();
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
