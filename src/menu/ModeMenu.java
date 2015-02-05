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
	private JRadioButtonMenuItem transforming;

	public ModeMenu(String title, ArrayList<MyJUNGCanvas> canvasList) {
		super(title);

		this.canvasList = canvasList;

		editing = new JRadioButtonMenuItem("editing");
		picking = new JRadioButtonMenuItem("picking");
		transforming = new JRadioButtonMenuItem("transforming");

		this.add(editing);
		this.add(picking);
		this.add(transforming);

		ModeListener listener = new ModeListener();
		
		editing.addActionListener(listener);
		transforming.addActionListener(listener);
		picking.addActionListener(listener);
		
		editing.setSelected(true);
	}
	
	public Mode getMode(){
		if (editing.isSelected())
			return Mode.EDITING;
		else if (picking.isSelected())
			return Mode.PICKING;
		else return Mode.TRANSFORMING;
	}

	private class ModeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(editing)) {
				editing.setSelected(true);
				picking.setSelected(false);
				transforming.setSelected(false);
				for (MyJUNGCanvas i: canvasList){
					i.getGraphMouse().setMode(Mode.EDITING);
				}
			} else if (e.getSource().equals(transforming)) {
				editing.setSelected(false);
				picking.setSelected(false);
				transforming.setSelected(true);
				for (MyJUNGCanvas i: canvasList){
					i.getGraphMouse().setMode(Mode.TRANSFORMING);
				}
			} else if (e.getSource().equals(picking)) {
				editing.setSelected(false);
				picking.setSelected(true);
				transforming.setSelected(false);
				for (MyJUNGCanvas i: canvasList){
					i.getGraphMouse().setMode(Mode.PICKING);
				}
			}

		}

	}

}
