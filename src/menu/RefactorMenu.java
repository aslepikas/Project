package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import model.Edge;
import model.Model;
import model.Vertex;
import algorithms.Algorithms;
import canvas.MyJUNGCanvas;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;

@SuppressWarnings("serial")
public class RefactorMenu extends JMenu {

	private JMenuItem minimise;
	private JMenuItem removeUnreachable;
	private JMenuItem duplicate;
	private JMenuItem nfaToDfa;
	private ModeMenu modeMenu;

	public RefactorMenu(String title, ArrayList<MyJUNGCanvas> modelList,
			JTabbedPane tabbedPane, ModeMenu modeMenu) {

		super(title);

		this.modeMenu = modeMenu;
		add(new LayoutMenu("Layout", modelList, tabbedPane));

		AlgorithmListener refactorListener = new AlgorithmListener(modelList,
				tabbedPane);

		minimise = new JMenuItem("Minimise");
		add(minimise);
		minimise.addActionListener(refactorListener);

		removeUnreachable = new JMenuItem("Remove unreachable");
		add(removeUnreachable);
		removeUnreachable.addActionListener(refactorListener);

		duplicate = new JMenuItem("Duplicate");
		add(duplicate);
		duplicate.addActionListener(refactorListener);

		nfaToDfa = new JMenuItem("NFA to DFA");
		add(nfaToDfa);
		nfaToDfa.addActionListener(refactorListener);

	}

	private class AlgorithmListener implements ActionListener {

		ArrayList<MyJUNGCanvas> modelList;
		JTabbedPane tabbedPane;

		public AlgorithmListener(ArrayList<MyJUNGCanvas> modelList,
				JTabbedPane tabbedPane) {
			super();
			this.modelList = modelList;
			this.tabbedPane = tabbedPane;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(minimise)) {
				if (!Algorithms.isDFA(modelList.get(
						tabbedPane.getSelectedIndex()).getModel())) {
					JOptionPane.showMessageDialog(tabbedPane.getRootPane(),
							"Your model is not a DFA");
					return;
				}
				String title = modelList.get(tabbedPane.getSelectedIndex()).getTitle();
				Algorithms.copyActive(modelList, tabbedPane, modeMenu);
				VisualizationViewer<Vertex, Edge> vv = modelList.get(
						tabbedPane.getSelectedIndex()).getVisualizationViewer();
				vv.setGraphLayout(new KKLayout<Vertex, Edge>(vv
						.getGraphLayout().getGraph()));

				Algorithms.minimise(modelList
						.get(tabbedPane.getSelectedIndex()).getModel());
				modelList.get(tabbedPane.getSelectedIndex())
						.getVisualizationViewer().repaint();
				modelList.get(tabbedPane.getSelectedIndex()).setTitle("min " + title);
			}

			else if (e.getSource().equals(removeUnreachable)) {
				Algorithms.removeUnreachable(modelList.get(
						tabbedPane.getSelectedIndex()).getModel());
				modelList.get(tabbedPane.getSelectedIndex())
						.getVisualizationViewer().repaint();
			}

			else if (e.getSource().equals(duplicate)) {
				Algorithms.copyActive(modelList, tabbedPane, modeMenu);
				VisualizationViewer<Vertex, Edge> vv = modelList.get(
						tabbedPane.getSelectedIndex()).getVisualizationViewer();
				vv.setGraphLayout(new KKLayout<Vertex, Edge>(vv
						.getGraphLayout().getGraph()));
				modelList.get(tabbedPane.getSelectedIndex())
						.getVisualizationViewer().repaint();
			}

			else if (e.getSource().equals(nfaToDfa)) {
				Model dfa = Algorithms.nfaToDfa(modelList.get(
						tabbedPane.getSelectedIndex()).getModel());
				if (dfa != null) {
					MyJUNGCanvas nCanvas = new MyJUNGCanvas(dfa);
					nCanvas.setTitle(String.format("%s to DFA",
							modelList.get(tabbedPane.getSelectedIndex())
									.getTitle()));
					nCanvas.initialise(modeMenu.getMode());

					modelList.add(nCanvas);
					tabbedPane.add(nCanvas.getTitle(),
							nCanvas.getVisualizationViewer());
					tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
					VisualizationViewer<Vertex, Edge> vv = modelList.get(
							tabbedPane.getSelectedIndex())
							.getVisualizationViewer();
					vv.setGraphLayout(new KKLayout<Vertex, Edge>(vv
							.getGraphLayout().getGraph()));
					nCanvas.getVisualizationViewer().repaint();
				}
			}
		}
	}

}
