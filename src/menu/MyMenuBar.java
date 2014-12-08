package menu;

import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

import canvas.MyJUNGCanvas;

public class MyMenuBar {

	public static JMenuBar create(ArrayList<MyJUNGCanvas> modelList, JTabbedPane tabbedPane) {
		
		JMenuBar menuBar = new JMenuBar();

		ModeMenu modeMenu = new ModeMenu("Mode", modelList, tabbedPane);
		
		JMenu fileMenu = new FileMenu("File", modelList, tabbedPane, modeMenu);
		
		JMenu refactorMenu = new JMenu("Refactor");
		refactorMenu.add(new LayoutMenu("Layout", modelList, tabbedPane));
		
		menuBar.add(fileMenu);
		menuBar.add(refactorMenu);
		menuBar.add(modeMenu);

		return menuBar;
	}

}
