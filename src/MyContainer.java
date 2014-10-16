import java.awt.*;

import javax.swing.*;

import Menu.MyMenuBar;
import model.Model;
import canvas.MyCanvas;

public class MyContainer {

	public static JFrame initialise(Model model) {

		JFrame frame = new JFrame("stuff");
		frame.setSize(new Dimension(700, 500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}
}
