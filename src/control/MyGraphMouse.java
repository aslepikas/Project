package control;


import java.awt.event.InputEvent;

import model.Edge;
import model.Vertex;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.annotations.AnnotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.AnimatedPickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.LabelEditingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.RotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ShearingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;

public class MyGraphMouse extends EditingModalGraphMouse<Vertex, Edge> {

	public MyGraphMouse(RenderContext<Vertex, Edge> renderContext,
			Factory<Vertex> vertexFactory, Factory<Edge> edgeFactory) {

		super(renderContext, vertexFactory, edgeFactory);

	}

	@Override
	protected void loadPlugins() {
		pickingPlugin = new PickingGraphMousePlugin<Vertex, Edge>();// needed
		animatedPickingPlugin = new AnimatedPickingGraphMousePlugin<Vertex, Edge>();
		// ^ works by pressing mouse button 1 + crtl
		translatingPlugin = new TranslatingGraphMousePlugin(
				InputEvent.BUTTON1_MASK); // needed
		scalingPlugin = new ScalingGraphMousePlugin(
				new CrossoverScalingControl(), 0, in, out);
		rotatingPlugin = new RotatingGraphMousePlugin(); // needed
		shearingPlugin = new ShearingGraphMousePlugin(); // removed
		editingPlugin = new MyEditingGraphMousePlugin<Vertex, Edge>(
				vertexFactory, edgeFactory); // needed
		labelEditingPlugin = new LabelEditingGraphMousePlugin<Vertex, Edge>();
		//labelEditingPlugin does not work, since i'm using toStringLabeler
		
		annotatingPlugin = new AnnotatingGraphMousePlugin<Vertex, Edge>(rc);
		popupEditingPlugin = new MyPopupPlugin(vertexFactory,
				edgeFactory);
		add(scalingPlugin);
		setMode(Mode.EDITING);
	}

	@Override
	protected void setTransformingMode() {
		super.setTransformingMode();
		remove(shearingPlugin);
	}

}
