import javax.swing.JFrame;

import Menu.MyMenuBar;
import canvas.MyCanvas;
import model.Model;

public class Main {

	public static void main(String args[]) {

		Model model = new Model();
		JFrame stuff = MyContainer.initialise(model);
		stuff.setJMenuBar(MyMenuBar.create());
		stuff.add(new MyCanvas(model));
		stuff.setVisible(true);
	}
}
