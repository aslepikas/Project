import java.awt.*;
import javax.swing.*;

public class Container {

	public static JFrame initialise () {
		
		JFrame frame = new JFrame ("stuff");
		
		frame.setSize(new Dimension (150, 150));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		return frame;
	};
	
}
