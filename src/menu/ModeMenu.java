package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

import canvas.MyJUNGCanvas;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

@SuppressWarnings("serial")
public class ModeMenu extends JMenu {

	private ArrayList<MyJUNGCanvas> canvasList;
	private JRadioButtonMenuItem editing;
	private JRadioButtonMenuItem picking;

	public ModeMenu(String title, ArrayList<MyJUNGCanvas> canvasList) {
		super(title);

		this.canvasList = canvasList;

		editing = new JRadioButtonMenuItem("editing");
		picking = new JRadioButtonMenuItem("picking");

		this.add(editing);
		this.add(picking);

		ModeListener listener = new ModeListener();

		editing.addActionListener(listener);
		picking.addActionListener(listener);

		editing.setSelected(true);
	}

	public Mode getMode() {
		if (editing.isSelected())
			return Mode.EDITING;
		else
			return Mode.PICKING;
	}

	private class ModeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(editing)) {
				editing.setSelected(true);
				picking.setSelected(false);
				for (MyJUNGCanvas i : canvasList) {
					i.getGraphMouse().setMode(Mode.EDITING);
				}
			} else if (e.getSource().equals(picking)) {
				editing.setSelected(false);
				picking.setSelected(true);
				for (MyJUNGCanvas i : canvasList) {
					i.getGraphMouse().setMode(Mode.PICKING);
				}
			}

		}

	}

}
