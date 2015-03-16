package control;

import model.Edge;
import model.Vertex;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.annotations.AnnotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;

public class MyGraphMouse extends EditingModalGraphMouse<Vertex, Edge> {

	public MyGraphMouse(RenderContext<Vertex, Edge> renderContext,
			Factory<Vertex> vertexFactory, Factory<Edge> edgeFactory) {

		super(renderContext, vertexFactory, edgeFactory);

	}

	@Override
	protected void loadPlugins() {
		pickingPlugin = new PickingGraphMousePlugin<Vertex, Edge>();// needed
		editingPlugin = new MyEditingGraphMousePlugin<Vertex, Edge>(
				vertexFactory, edgeFactory); // needed
		annotatingPlugin = new AnnotatingGraphMousePlugin<Vertex, Edge>(rc);
		popupEditingPlugin = new MyPopupPlugin(vertexFactory, edgeFactory);

		add(scalingPlugin);
		setMode(Mode.EDITING);
	}

	@Override
	protected void setTransformingMode() {
	}

}
