package containers;

import javax.swing.JTabbedPane;

import canvas.MyJUNGCanvas;

@SuppressWarnings("serial")
public class MyTabbedPane extends JTabbedPane {

	MyJUNGCanvas canvas;

	public MyTabbedPane(MyJUNGCanvas canvas) {
		super();

		this.canvas = canvas;
	}

	public MyJUNGCanvas getCanvas() {
		return canvas;
	}

}
