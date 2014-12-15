package animation;

import org.apache.commons.lang3.StringUtils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import canvas.MyJUNGCanvas;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import model.Edge;
import model.Model;
import model.Vertex;

@SuppressWarnings("serial")
public class AnimationScreen extends JPanel {

	private JPanel animationPanel;
	private JPanel textPanel;
	private JButton textEntry;
	private JButton stepButton;
	private JButton goButton;
	private JPanel textLabelPanel;

	private ArrayList<MyJUNGCanvas>modelList;
	private String tape;

	public AnimationScreen(ArrayList<MyJUNGCanvas> modelList) {

		this.modelList = modelList;
		
		tape = "";

		LayoutManager layout = new BorderLayout();
		setLayout(layout);

		animationPanel = new JPanel();
		
		for (MyJUNGCanvas i : modelList) {
			animationPanel.add(i.getVisualizationViewer());
		}
		this.add(animationPanel, BorderLayout.NORTH);

		textPanel = new JPanel();
		textPanel.setLayout(new BorderLayout());

		JPanel ButtonPanel = new JPanel();
		ButtonPanel.setLayout(new BorderLayout());

		ButtonListener buttonListener = new ButtonListener();

		textEntry = new JButton("tape");
		textEntry.addMouseListener(buttonListener);
		ButtonPanel.add(textEntry, BorderLayout.WEST);

		stepButton = new JButton("step");
		stepButton.addMouseListener(buttonListener);
		ButtonPanel.add(stepButton, BorderLayout.EAST);

		goButton = new JButton("go");
		goButton.addMouseListener(buttonListener);
		ButtonPanel.add(stepButton, BorderLayout.CENTER);

		textPanel.add(ButtonPanel, BorderLayout.WEST);

		textLabelPanel = new JPanel();
		JPanel placeHolder = new JPanel(new GridLayout(2, 1));
		placeHolder.add(new JLabel("	"));
		placeHolder.add(new JLabel("	"));
		textLabelPanel.add(placeHolder);
		
		textPanel.add(textLabelPanel, BorderLayout.EAST);

		this.add(textPanel, BorderLayout.SOUTH);

		this.setMinimumSize(new Dimension(350, 350));

		this.setVisible(true);
	}

	private class ButtonListener implements MouseListener {

		private ArrayList<JLabel> arrow;
		
		public ButtonListener(){
			arrow = new ArrayList<JLabel>();
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource().equals(textEntry)) {
				String message = JOptionPane.showInputDialog(textPanel, "",
						"Enter a string");
				textLabelPanel.removeAll();
				if (StringUtils.isAlphanumeric(message)) {
					tape = message;
					JLabel character;
					JPanel characterPanel;
					for (int i = 0, n = message.length(); i < n; i++) {
					    char c = message.charAt(i);
					    character = new JLabel(Character.toString(c));
					    characterPanel = new JPanel();
					    characterPanel.setLayout(new GridLayout(2, 1));
					    JLabel arrowLabel = new JLabel("");
					    arrow.add(arrowLabel);
					    characterPanel.add(arrowLabel);
					    characterPanel.add(character);
					    
					    textLabelPanel.add(characterPanel);
					}
					SwingUtilities.getWindowAncestor(textLabelPanel).repaint();
				} else {
					tape = "";
					JPanel placeHolder = new JPanel(new GridLayout(2, 1));
					placeHolder.add(new JLabel("	"));
					placeHolder.add(new JLabel("	"));
					textLabelPanel.add(placeHolder);
				}
			} else if (e.getSource().equals(stepButton)) {

			} else if (e.getSource().equals(goButton)) {

			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

	}

}
