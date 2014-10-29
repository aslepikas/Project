package Menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MyMenuBar {

	public static JMenuBar create() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem menuItem = new JMenuItem("item");
		fileMenu.add(menuItem);

		JMenu refactorMenu = new JMenu("Refactor");
		menuBar.add(refactorMenu);

		return menuBar;
	}

}
