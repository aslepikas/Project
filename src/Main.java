import javax.swing.JFrame;

import model.Model;

public class Main {

	public static void main(String args[]) {

		Model model = new Model();
		JFrame stuff = MyContainer.initialise(model);
		stuff.setVisible(true);
	}
}
