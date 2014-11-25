package menu;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import model.Edge;
import model.Vertex;
import canvas.MyJUNGCanvas;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;

@SuppressWarnings("serial")
public class LayoutMenu extends JMenu {

	MyJUNGCanvas canvas;
	JMenuItem circle;
	JMenuItem isom;
	JMenuItem kk;
	JMenuItem fr;
	
	public LayoutMenu(String title, final MyJUNGCanvas canvas) {
		super(title);

		this.canvas = canvas;

		circle = new JMenuItem("Circle");
		circle.addActionListener(new LayoutItemListener());
		this.add(circle);

		isom = new JMenuItem("Self-organising");
		isom.addActionListener(new LayoutItemListener());
		this.add(isom);

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
				VisualizationViewer<Vertex, Edge> vv = canvas
						.getVisualizationViewer();
				vv.setGraphLayout(new CircleLayout<Vertex, Edge>(vv
						.getGraphLayout().getGraph()));
				vv.repaint();
			} else if (e.getSource().equals(isom)) {
				VisualizationViewer<Vertex, Edge> vv = canvas
						.getVisualizationViewer();
				vv.setGraphLayout(new ISOMLayout<Vertex, Edge>(vv
						.getGraphLayout().getGraph()));
				vv.repaint();
			} else if (e.getSource().equals(kk)) {
				VisualizationViewer<Vertex, Edge> vv = canvas
						.getVisualizationViewer();
				vv.setGraphLayout(new KKLayout<Vertex, Edge>(vv
						.getGraphLayout().getGraph()));
				vv.repaint();
			} else if (e.getSource().equals(fr)) {
				VisualizationViewer<Vertex, Edge> vv = canvas
						.getVisualizationViewer();
				vv.setGraphLayout(new FRLayout<Vertex, Edge>(vv
						.getGraphLayout().getGraph()));
				vv.repaint();
			}

		}
	}

}
