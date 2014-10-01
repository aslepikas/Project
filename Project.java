import javax.swing.*;
import src.Container;
import java.awt.*;

public class Project {
	
	public static void main (String args[]) {
		
		JFrame frame = Container.initialise();
		
		JPanel pane = new MyPanel ();
		
		frame.add(pane);
		
		frame.setVisible(true);

	}
	
	private static class MyPanel extends JPanel {
		
		public void paintComponent (Graphics g){
            g.fillOval(25, 25, 50, 50);
        }
		
	}
	
}
