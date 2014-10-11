import javax.swing.JFrame;

import model.Model;

public class Main {

	public static void main(String args[]) {

		Model model = new Model();
		JFrame stuff = Container.initialise(model);
		stuff.setVisible(true);
	}
}
