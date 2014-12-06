import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

import canvas.MyJUNGCanvas;
import menu.MyMenuBar;
import model.*;

public class Main {

	public static void main(String args[]) {
		
		Model myGraph = new Model();
		
		ArrayList<MyJUNGCanvas> modelList = new ArrayList<MyJUNGCanvas>();
		
		MyJUNGCanvas canvasModel = new MyJUNGCanvas(myGraph);

		canvasModel.initialise();
		
		JFrame frame = new JFrame("Editing Graph Viewer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("Tab 1", canvasModel.getVisualizationViewer());
		frame.getContentPane().add(tabbedPane);

		JMenuBar menuBar = MyMenuBar.create(modelList, tabbedPane);
		
		JMenu modeMenu = canvasModel.getGraphMouse().getModeMenu(); // Obtain mode menu from the mouse
		modeMenu.setText("Mouse Mode");
		modeMenu.setIcon(null); // I'm using this in a main menu
		modeMenu.setPreferredSize(new Dimension(80, 20)); // Change the size
		menuBar.add(modeMenu);
		
		canvasModel.getGraphMouse().setMode(ModalGraphMouse.Mode.EDITING);
		
		modelList.add(canvasModel);
		
		frame.setJMenuBar(menuBar);
		
		frame.pack();
		frame.setVisible(true);
	}
}
