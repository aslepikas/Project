package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import model.Edge;
import model.Model;
import model.Vertex;
import algorithms.Algorithms;
import canvas.MyJUNGCanvas;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;

public class MyMenuBar {

	private static JMenuItem minimise;
	private static JMenuItem removeUnreachable;
	private static JMenuItem duplicate;
	private static JMenuItem nfaToDfa;
	private static JMenuBar menuBar;
	private static ModeMenu modeMenu;
	private static JMenu fileMenu;
	private static JMenu refactorMenu;

	public static JMenuBar create(ArrayList<MyJUNGCanvas> modelList,
			JTabbedPane tabbedPane) {

		menuBar = new JMenuBar();

		modeMenu = new ModeMenu("Mode", modelList);

		fileMenu = new FileMenu("File", modelList, tabbedPane, modeMenu);

		refactorMenu = new JMenu("Refactor");
		refactorMenu.add(new LayoutMenu("Layout", modelList, tabbedPane));

		AlgorithmListener refactorListener = new AlgorithmListener(modelList,
				tabbedPane);

		minimise = new JMenuItem("Minimise");
		refactorMenu.add(minimise);
		minimise.addActionListener(refactorListener);

		removeUnreachable = new JMenuItem("Remove unreachable");
		refactorMenu.add(removeUnreachable);
		removeUnreachable.addActionListener(refactorListener);

		duplicate = new JMenuItem("Duplicate");
		refactorMenu.add(duplicate);
		duplicate.addActionListener(refactorListener);

		nfaToDfa = new JMenuItem("NFA to DFA");
		refactorMenu.add(nfaToDfa);
		nfaToDfa.addActionListener(refactorListener);

		menuBar.add(fileMenu);
		menuBar.add(refactorMenu);
		menuBar.add(modeMenu);

		return menuBar;
	}

	private static class AlgorithmListener implements ActionListener {

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
				Algorithms.copyActive(modelList, tabbedPane, modeMenu);
				VisualizationViewer<Vertex, Edge> vv = modelList.get(
						tabbedPane.getSelectedIndex()).getVisualizationViewer();
				vv.setGraphLayout(new KKLayout<Vertex, Edge>(vv
						.getGraphLayout().getGraph()));

				Algorithms.minimise(modelList
						.get(tabbedPane.getSelectedIndex()).getModel());
				modelList.get(tabbedPane.getSelectedIndex())
						.getVisualizationViewer().repaint();
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
					nCanvas.setTitle(String.format("copy of %s",
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
