import java.awt.*;
import javax.swing.*;
import canvas.MyCanvas;

public class Container {

	public static JFrame initialise () {
		
		JFrame frame = new JFrame ("stuff");
		frame.setSize(new Dimension (500, 500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new MyCanvas());
		
		return frame;
	};
	
}
