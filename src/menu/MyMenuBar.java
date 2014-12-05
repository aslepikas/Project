package menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import canvas.MyJUNGCanvas;

public class MyMenuBar {

	public static JMenuBar create(MyJUNGCanvas canvasModel) {
		
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenu refactorMenu = new JMenu("Refactor");
		refactorMenu.add(new LayoutMenu("Layout", canvasModel));
		
		menuBar.add(refactorMenu);
		

		return menuBar;
	}

}
