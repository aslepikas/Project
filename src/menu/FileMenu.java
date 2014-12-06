package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import model.Model;
import canvas.MyJUNGCanvas;
import edu.uci.ics.jung.visualization.VisualizationViewer;

@SuppressWarnings("serial")
public class FileMenu extends JMenu {

	ArrayList<MyJUNGCanvas> canvasList;
	JTabbedPane tabbedPane;
	JMenuItem addTab;

	public FileMenu(String title, ArrayList<MyJUNGCanvas> canvasList,
			JTabbedPane tabbedPane) {
		super(title);

		this.canvasList = canvasList;
		this.tabbedPane = tabbedPane;

		addTab = new JMenuItem("New model");
		addTab.addActionListener(new FileMenuListener());
		this.add(addTab);
	}

	private class FileMenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(addTab)) {
				Model myGraph = new Model();
				MyJUNGCanvas myCanvas = new MyJUNGCanvas(myGraph);
				myCanvas.initialise();
				tabbedPane.add("new tab", myCanvas.getVisualizationViewer());
				canvasList.add(myCanvas);
			}
		}

	}

}
