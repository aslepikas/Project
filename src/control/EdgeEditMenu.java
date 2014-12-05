package control;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Edge;

import org.apache.commons.lang3.*;

@SuppressWarnings("serial")
public class EdgeEditMenu extends JDialog implements ActionListener,
		PropertyChangeListener {

	private Edge edge;
	private ArrayList<JTextField> textFields;
	private String buttonstr1 = "Done";
	private String buttonstr2 = "Cancel";

	private JPanel changePanel;
	private JButton moreButton;

	private GridLayout labelPanelLayout;
	private JPanel labelPanel;

	private JOptionPane optionPane;

	public EdgeEditMenu(JFrame root, Edge edge) {
		super(root, true);
		this.edge = edge;
		ArrayList<String> labels = edge.getLabels();
		textFields = new ArrayList<JTextField>();
		for (String i : labels) {
			textFields.add(new JTextField(i));
		}
		textFields.add(new JTextField());

		changePanel = new JPanel();
		labelPanel = new JPanel();

		labelPanelLayout = new GridLayout(textFields.size(), 1);
		labelPanel.setLayout(labelPanelLayout);
		for (int i = 0; i < textFields.size(); i++)
			labelPanel.add(textFields.get(i));

		changePanel.setLayout(new BorderLayout());

		moreButton = new JButton("more");
		moreButton.addActionListener(this);

		changePanel.add(moreButton, BorderLayout.SOUTH);
		changePanel.add(labelPanel, BorderLayout.NORTH);

		Object[] options = { buttonstr1, buttonstr2 };
		optionPane = new JOptionPane(changePanel, JOptionPane.PLAIN_MESSAGE,
				JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);
		setContentPane(optionPane);

		this.setMinimumSize(new Dimension(200, textFields.size() * 19 + 100));
		// 19*#labels + 2*button height + c

		optionPane.addPropertyChangeListener(this);
		this.setLocationRelativeTo(root);
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {

		boolean finish = true;
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
				boolean finishedLoop = true;
				ArrayList<String> retVals = new ArrayList<String>();
				for (JTextField i : textFields) {
					String textString = i.getText();
					if (textString.compareTo("") != 0) {
						if (StringUtils.isAlphanumeric(textString)) {
							boolean inRetVals = false;
							for (String s : retVals) {

								inRetVals = inRetVals || textString.matches(s);
							}
							if (!inRetVals) {
								retVals.add(i.getText());
								finish = finish && true;
							}
						} else {
							JOptionPane.showMessageDialog(this,
									"Alphanumeric characters only");
							System.out.println("tracker");
							finish = false;
							retVals.clear();
							finishedLoop = false;
							break;

						}
					}
				}
				if (retVals.isEmpty() && finishedLoop) {
					JOptionPane.showMessageDialog(this,
							"Set at least one label");
					finish = false;
				} else {
					edge.setLabels(retVals);
				}
			} else {
				// currently nothing is happening after pressing cancel button
				// i.e. it literally cancels the action
			}
			if (finish) {
				this.getRootPane().repaint();
				this.dispose();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(moreButton)) {
			labelPanelLayout.setRows(labelPanelLayout.getRows() + 1);
			JTextField textField = new JTextField();
			textFields.add(textField);
			labelPanel.add(textField);
			System.out.println(labelPanel.getComponents().length);
			labelPanel.updateUI();
			this.setMinimumSize(new Dimension(this.getMinimumSize().width, this
					.getMinimumSize().height + 19));
			this.repaint();
		}
	}

}
