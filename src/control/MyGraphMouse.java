package control;

import model.Edge;
import model.Vertex;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.LensMagnificationGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.RotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;

public class MyGraphMouse extends EditingModalGraphMouse<Vertex, Edge> {

	public MyGraphMouse(RenderContext<Vertex, Edge> renderContext,
			Factory<Vertex> vertexFactory, Factory<Edge> edgeFactory) {
		
		super(renderContext, vertexFactory, edgeFactory);
		
	}

}
