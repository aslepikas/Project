import javax.swing.JFrame;
import javax.swing.JMenuBar;

import containers.ModeTabbedPane;
import menu.MyMenuBar;

public class Main {

	public static void main(String args[]) {
		
		JFrame frame = new JFrame("Editing Graph Viewer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ModeTabbedPane modeTabPane = new ModeTabbedPane();
		modeTabPane.initialise();

		frame.getContentPane().add(modeTabPane);

		JMenuBar menuBar = MyMenuBar.create(modeTabPane.getModelList(), modeTabPane.getCreationTab());
		
		frame.setJMenuBar(menuBar);
		
		frame.pack();
		frame.setVisible(true);
	}
}
