package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import algorithms.Algorithms;
import canvas.MyJUNGCanvas;

public class MyMenuBar {

	private static JMenuItem minimise;
	private static JMenuItem removeUnreachable;
	private static JMenuBar menuBar;
	private static ModeMenu modeMenu;
	private static JMenu fileMenu;
	private static JMenu refactorMenu;
	
	public static JMenuBar create(ArrayList<MyJUNGCanvas> modelList, JTabbedPane tabbedPane) {
		
		menuBar = new JMenuBar();

		modeMenu = new ModeMenu("Mode", modelList);
		
		fileMenu = new FileMenu("File", modelList, tabbedPane, modeMenu);
		
		refactorMenu = new JMenu("Refactor");
		refactorMenu.add(new LayoutMenu("Layout", modelList, tabbedPane));
		
		minimise = new JMenuItem("Minimise");
		refactorMenu.add(minimise);
		minimise.addActionListener(new MinimiseListener(modelList, tabbedPane));
		
		removeUnreachable = new JMenuItem("Remove unreachable");
		refactorMenu.add(removeUnreachable);
		removeUnreachable.addActionListener(new MinimiseListener(modelList, tabbedPane));
		
		menuBar.add(fileMenu);
		menuBar.add(refactorMenu);
		menuBar.add(modeMenu);

		return menuBar;
	}
	
	private static class MinimiseListener implements ActionListener {

		ArrayList<MyJUNGCanvas> modelList;
		JTabbedPane tabbedPane;
		
		public MinimiseListener(ArrayList<MyJUNGCanvas> modelList, JTabbedPane tabbedPane) {
			super();
			this.modelList = modelList;
			this.tabbedPane = tabbedPane;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource().equals(minimise)) {
				Algorithms.minimise(modelList.get(tabbedPane.getSelectedIndex()).getModel());
				modelList.get(tabbedPane.getSelectedIndex()).getVisualizationViewer().repaint();
			}
			
			if (e.getSource().equals(removeUnreachable)) {
				Algorithms.removeUnreachable(modelList.get(tabbedPane.getSelectedIndex()).getModel());
				modelList.get(tabbedPane.getSelectedIndex()).getVisualizationViewer().repaint();
			}
		}
		
	}

}
