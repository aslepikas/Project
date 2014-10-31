package canvas;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import model.Edge;
import model.Model;
import model.Vertex;

public class MyJUNGCanvas {

	private Model model;

	public MyJUNGCanvas(Model model){ 
		this.model = model;
		Layout<Vertex, Edge> layout = new StaticLayout<Vertex, Edge>(this.model);
	}

}
