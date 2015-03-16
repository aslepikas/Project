package animation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import canvas.MyJUNGCanvas;

@SuppressWarnings("serial")
public class SelectionDialogue extends JDialog implements
		PropertyChangeListener {

	private ArrayList<MyJUNGCanvas> modelList;
	private ArrayList<MyJUNGCanvas> retList;

	private ArrayList<JCheckBox> checkBoxes;
	private String buttonstr1 = "Done";

	private JPanel changePanel;

	private GridLayout labelPanelLayout;
	private JPanel labelPanel;

	private JOptionPane optionPane;

	public SelectionDialogue(JFrame root, ArrayList<MyJUNGCanvas> modelList,
			ArrayList<MyJUNGCanvas> retList) {
		super(root, true);

		this.modelList = modelList;
		this.retList = retList;

		checkBoxes = new ArrayList<JCheckBox>();
		for (MyJUNGCanvas i : this.modelList) {
			checkBoxes.add(new JCheckBox(i.getTitle()));
		}

		changePanel = new JPanel();
		labelPanel = new JPanel();

		labelPanelLayout = new GridLayout(checkBoxes.size(), 1);
		labelPanel.setLayout(labelPanelLayout);
		for (int i = 0; i < checkBoxes.size(); i++) {
			labelPanel.add(checkBoxes.get(i));
			if (!modelList.get(i).getModel().hasStart()) {
				checkBoxes.get(i).setEnabled(false);
			}
		}

		changePanel.setLayout(new BorderLayout());

		changePanel.add(labelPanel, BorderLayout.NORTH);

		Object[] options = { buttonstr1 };
		optionPane = new JOptionPane(changePanel, JOptionPane.PLAIN_MESSAGE,
				JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);
		setContentPane(optionPane);

		this.setMinimumSize(new Dimension(200, checkBoxes.size() * 19 + 100));
		// 19*#labels + 2*button height + c

		optionPane.addPropertyChangeListener(this);

		this.pack();
		this.setLocationRelativeTo(root);
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {

		String val = e.getPropertyName();
		if ((e.getSource() == optionPane)
				&& (JOptionPane.VALUE_PROPERTY.equals(val) || JOptionPane.INPUT_VALUE_PROPERTY
						.equals(val))) {

			Object value = optionPane.getValue();
			if (value == JOptionPane.UNINITIALIZED_VALUE) {
				// ignore reset
				return;
			}

			optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
			if (buttonstr1.equals(value)) {
				for (int i = 0; i < checkBoxes.size(); i++) {
					if (checkBoxes.get(i).isSelected()) {
						retList.add(modelList.get(i));
					}
				}
			}

			this.getRootPane().repaint();
			this.dispose();

		}
	}

}
