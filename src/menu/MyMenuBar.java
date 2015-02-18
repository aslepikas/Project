package menu;

import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

import canvas.MyJUNGCanvas;

public class MyMenuBar {

	private static JMenuBar menuBar;
	private static ModeMenu modeMenu;
	private static JMenu fileMenu;
	private static JMenu refactorMenu;

	public static JMenuBar create(ArrayList<MyJUNGCanvas> modelList,
			JTabbedPane tabbedPane) {

		menuBar = new JMenuBar();

		modeMenu = new ModeMenu("Mode", modelList);

		fileMenu = new FileMenu("File", modelList, tabbedPane, modeMenu);

		refactorMenu = new RefactorMenu("Refactor", modelList, tabbedPane, modeMenu);

		menuBar.add(fileMenu);
		menuBar.add(refactorMenu);
		menuBar.add(modeMenu);

		return menuBar;
	}

}
