package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import model.Model;
import canvas.MyJUNGCanvas;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

@SuppressWarnings("serial")
public class FileMenu extends JMenu {

	ArrayList<MyJUNGCanvas> canvasList;
	JTabbedPane tabbedPane;
	JMenuItem addTab;
	ModeMenu modeMenu;

	public FileMenu(String title, ArrayList<MyJUNGCanvas> canvasList,
			JTabbedPane tabbedPane, ModeMenu modeMenu) {
		super(title);

		this.canvasList = canvasList;
		this.tabbedPane = tabbedPane;
		this.modeMenu = modeMenu;

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
				myCanvas.initialise(modeMenu.getMode());
				tabbedPane.add("new tab", myCanvas.getVisualizationViewer());
				canvasList.add(myCanvas);
			}
		}

	}

}
