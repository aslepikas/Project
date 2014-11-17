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
		
		String msg = "Enter transition labels:";

		changePanel = new JPanel();
		labelPanel = new JPanel();

		labelPanelLayout = new GridLayout(textFields.size(), 1);
		labelPanel.setLayout(labelPanelLayout);
		for (int i = 0; i < textFields.size(); i++)
			labelPanel.add(textFields.get(i));
		
		changePanel.setLayout(new BorderLayout());

		moreButton = new JButton("more");
		moreButton.addActionListener(new TextFieldAdder(this));

		changePanel.add(moreButton, BorderLayout.SOUTH);
		changePanel.add(labelPanel, BorderLayout.NORTH);

		Object[] options = { buttonstr1, buttonstr2 };
		optionPane = new JOptionPane(changePanel, JOptionPane.PLAIN_MESSAGE,
				JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);
		setContentPane(optionPane);
		// setDefaultCloseOperation();
		this.setMinimumSize(new Dimension(300, textFields.size()*19 + 100)); // 19*#labels +
														// buttonheight +
														// buttonheight + c
		System.out.println(getHeight());
		System.out.println(getWidth());

		optionPane.addPropertyChangeListener(this);
		this.setLocationRelativeTo(root);
	}

	private class TextFieldAdder implements ActionListener {

		EdgeEditMenu eem;

		public TextFieldAdder(EdgeEditMenu eem) {
			this.eem = eem;
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
				eem.setMinimumSize(new Dimension(eem.getMinimumSize().width,
						eem.getMinimumSize().height + 19));
				eem.repaint();
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {

		String val = e.getPropertyName();

		if ((e.getSource() == optionPane)
				&& (JOptionPane.VALUE_PROPERTY.equals(val) || JOptionPane.INPUT_VALUE_PROPERTY
						.equals(val))) {

			Object value = optionPane.getValue();

			if (buttonstr1.equals(value)) {
				ArrayList<String> retVals = new ArrayList<String>();
				for (JTextField i : textFields) {
					retVals.add(i.getText());
					System.out.println(i.getText());

					// TODO : add string confirmation
				}
				edge.setLabels(retVals);
			} else {
				//currently nothing is happening after pressing cancel button
			}
			// TODO make the below method work
			this.getRootPane().repaint();
			this.dispose();
		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
