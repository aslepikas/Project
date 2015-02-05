package canvas;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;

import canvas.transform.ColourTransformer;
import canvas.transform.OutlineColourTransformer;
import canvas.transform.VertexStrokeWidthTransformer;
import control.MyGraphMouse;
import control.TooltipDisplay;
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
	private VisualizationViewer<Vertex, Edge> vv;
	private MyGraphMouse gm;
	private String title;

	public MyJUNGCanvas(Model model) {
		this.model = model;
	}

	public VisualizationViewer<Vertex, Edge> initialise(Mode mode) {
		layout = new StaticLayout<Vertex, Edge>(this.model);
		vv = new VisualizationViewer<Vertex, Edge>(layout);
		vv.setPreferredSize(new Dimension(500, 350));

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
		vv.setBorder(new LineBorder(Color.BLACK, 1));
		
		vv.addMouseMotionListener(new TooltipDisplay());
		
		return vv;
	}

	public VisualizationViewer<Vertex, Edge> getVisualizationViewer() {
		return vv;
	}

	public MyGraphMouse getGraphMouse() {
		return gm;
	}
	
	public Model getModel(){
		return model;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}
	
	public void setNoMouse(){
		vv.setGraphMouse(null);
	}
	
	public void setMouse(){
		vv.setGraphMouse(gm);
	}
}
