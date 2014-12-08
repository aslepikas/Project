package canvas;

import java.awt.Dimension;

import canvas.transform.ColourTransformer;
import canvas.transform.OutlineColourTransformer;
import canvas.transform.VertexStrokeWidthTransformer;
import control.MyGraphMouse;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import model.Edge;
import model.Model;
import model.Vertex;

public class MyJUNGCanvas {

	private Model model;
	private Layout<Vertex, Edge> layout;
	VisualizationViewer<Vertex, Edge> vv;
	MyGraphMouse gm;

	public MyJUNGCanvas(Model model) {
		this.model = model;
	}

	public VisualizationViewer<Vertex, Edge> initialise(Mode mode) {
		layout = new StaticLayout<Vertex, Edge>(this.model);
		layout.setSize(new Dimension(350, 350));
		vv = new VisualizationViewer<Vertex, Edge>(layout);
		vv.setPreferredSize(new Dimension(350, 350));

		vv.getRenderContext().setVertexLabelTransformer(
				new ToStringLabeller<Vertex>());
		vv.getRenderContext().setEdgeLabelTransformer(
				new ToStringLabeller<Edge>());

		vv.getRenderContext().setVertexFillPaintTransformer(
				new ColourTransformer());
		vv.getRenderContext().setVertexDrawPaintTransformer(
				new OutlineColourTransformer(vv.getRenderContext()));
		vv.getRenderContext().setVertexStrokeTransformer(
				new VertexStrokeWidthTransformer(vv.getRenderContext()));
		gm = new MyGraphMouse(vv.getRenderContext(), model.vertexFactory,
				model.edgeFactory);
		gm.setMode(mode);
		vv.setGraphMouse(gm);
		return vv;
	}

	public VisualizationViewer<Vertex, Edge> getVisualizationViewer() {
		return vv;
	}

	public MyGraphMouse getGraphMouse() {
		return gm;
	}

}
