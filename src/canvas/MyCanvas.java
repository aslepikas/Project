package canvas;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import model.Model;
import model.Node;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class MyCanvas extends JPanel {

	private int x;
	private int y;
	private Model model;
	private int nodeWidth = Node.NODE_WIDTH;
	private int selectedNode = -1;

	public MyCanvas(Model m) {
		model = m;
		addMouseListener(new MouseListener());
	}

	private class MouseListener extends MouseInputAdapter {

		public void mouseReleased(MouseEvent me) {
			x = me.getX();
			y = me.getY();

			selectedNode = model.findNode(x, y);
			if (selectedNode == -1) {
				model.addNewNode(x, y);
			}
			System.out.printf("x = %d; y = %d \n", x, y);
			update();
		}

	}

	private void update() {
		repaint();
	}

	public void paint(Graphics g) {
		Dimension d = this.getSize();
		g.setColor(Color.WHITE);
		g.fillRect(0, 1, d.width, d.height);

		ArrayList<Node> nodeList = model.getNodeList();
		g.setColor(Color.ORANGE);

		for (Node i : nodeList) {
			g.fillOval(i.getX() - nodeWidth / 2, i.getY() - nodeWidth / 2,
					nodeWidth, nodeWidth);
		}
	}
}
