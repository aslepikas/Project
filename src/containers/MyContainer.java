package containers;
import java.awt.*;

import javax.swing.*;

public class MyContainer {

	public static JFrame initialise(String s) {

		JFrame frame = new JFrame(s);
		frame.setSize(new Dimension(700, 500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}
}
