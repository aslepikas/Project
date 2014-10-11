package canvas;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;


public class MyCanvas extends JPanel{

	private int x;
	private int y;
	
	public MyCanvas (){
		
		addMouseListener(new MouseListener());
	}
	
	private class MouseListener extends MouseInputAdapter {
		
		public void mouseReleased (MouseEvent me){
			x = me.getX();
			y = me.getY();
			System.out.printf("x = %d; y = %d \n", x, y);
			update();
		}
		
	}
	
	private void update(){
		repaint();
	}
	
	public void paint(Graphics g){
		Dimension d = this.getSize();
		g.setColor(Color.WHITE);
		g.fillRect(0, 1, d.width, d.height);
		g.setColor(Color.BLACK);
		g.fillRect(x, y, 2, 2);
	}
}
