package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import model.Edge;
import model.Vertex;
import canvas.MyJUNGCanvas;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;

@SuppressWarnings("serial")
public class LayoutMenu extends JMenu {

	ArrayList<MyJUNGCanvas> canvasList;
	JTabbedPane tabbedPane;
	JMenuItem circle;
	JMenuItem kk;
	JMenuItem fr;

	public LayoutMenu(String title, final ArrayList<MyJUNGCanvas> modelList,
			JTabbedPane tabbedPane) {
		super(title);

		this.canvasList = modelList;
		this.tabbedPane = tabbedPane;

		circle = new JMenuItem("Circle");
		circle.addActionListener(new LayoutItemListener());
		this.add(circle);

		kk = new JMenuItem("Kamada-Kawai");
		kk.addActionListener(new LayoutItemListener());
		this.add(kk);

		fr = new JMenuItem("Force directed");
		fr.addActionListener(new LayoutItemListener());
		this.add(fr);
	}

	private class LayoutItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(circle)) {
				VisualizationViewer<Vertex, Edge> vv = canvasList.get(
						tabbedPane.getSelectedIndex()).getVisualizationViewer();
				vv.setGraphLayout(new CircleLayout<Vertex, Edge>(vv
						.getGraphLayout().getGraph()));
				vv.repaint();
			} else if (e.getSource().equals(kk)) {
				VisualizationViewer<Vertex, Edge> vv = canvasList.get(
						tabbedPane.getSelectedIndex()).getVisualizationViewer();
				vv.setGraphLayout(new KKLayout<Vertex, Edge>(vv
						.getGraphLayout().getGraph()));
				vv.repaint();
			} else if (e.getSource().equals(fr)) {
				VisualizationViewer<Vertex, Edge> vv = canvasList.get(
						tabbedPane.getSelectedIndex()).getVisualizationViewer();
				vv.setGraphLayout(new FRLayout<Vertex, Edge>(vv
						.getGraphLayout().getGraph()));
				vv.repaint();
			}

		}
	}

}
