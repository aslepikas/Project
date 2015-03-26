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
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;

@SuppressWarnings("serial")
public class RegexMenu extends JMenu {

	private ArrayList<MyJUNGCanvas> canvasList;
	private JTabbedPane tabbedPane;
	private ModeMenu modeMenu;
	private JMenuItem enterRegex;

	public RegexMenu(String title, ArrayList<MyJUNGCanvas> canvasList,
			JTabbedPane tabbedPane, ModeMenu modeMenu) {
		super(title);

		this.canvasList = canvasList;
		this.tabbedPane = tabbedPane;
		this.modeMenu = modeMenu;

		RegexMenuListener listener = new RegexMenuListener();
		enterRegex = new JMenuItem("Build from regex");
		enterRegex.addActionListener(listener);
		this.add(enterRegex);
	}

	private class RegexMenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(enterRegex)) {
				String exp = JOptionPane
						.showInputDialog("Enter a regular expression");
				Model model = Algorithms.parseExpression(exp);
				if (model != null) {
					MyJUNGCanvas nCanvas = new MyJUNGCanvas(model);
					nCanvas.initialise(modeMenu.getMode());
					nCanvas.setTitle("Regex tab");
					VisualizationViewer<Vertex, Edge> vv = nCanvas
							.getVisualizationViewer();
					tabbedPane.add(nCanvas.getTitle(), vv);
					tabbedPane.setSelectedComponent(vv);
					canvasList.add(nCanvas);
					if (model.getVertexCount() == 1) {
						vv.setGraphLayout(new CircleLayout<Vertex, Edge>(vv
								.getGraphLayout().getGraph()));
					} else {
						vv.setGraphLayout(new KKLayout<Vertex, Edge>(vv
								.getGraphLayout().getGraph()));
					}
					vv.repaint();
				}
			}
		}
	}

}
