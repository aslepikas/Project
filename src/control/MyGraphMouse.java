package control;

import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.plaf.basic.BasicIconFactory;

import model.Edge;
import model.Vertex;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.annotations.AnnotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.AnimatedPickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.EditingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.EditingPopupGraphMousePlugin;
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
		editingPlugin = new EditingGraphMousePlugin<Vertex, Edge>(
				vertexFactory, edgeFactory); // needed
		labelEditingPlugin = new LabelEditingGraphMousePlugin<Vertex, Edge>();// not
																				// working
																				// atm
		annotatingPlugin = new AnnotatingGraphMousePlugin<Vertex, Edge>(rc);
		popupEditingPlugin = new EditingPopupGraphMousePlugin<Vertex, Edge>(
				vertexFactory, edgeFactory);
		add(scalingPlugin);
		setMode(Mode.EDITING);
	}

	/*
	 * protected void setPickingMode() { remove(translatingPlugin);
	 * remove(rotatingPlugin); remove(shearingPlugin); remove(editingPlugin);
	 * remove(annotatingPlugin); add(pickingPlugin); add(animatedPickingPlugin);
	 * add(labelEditingPlugin); add(popupEditingPlugin); }
	 */

	/*
	 * @Override protected void setTransformingMode() { remove(pickingPlugin);
	 * remove(animatedPickingPlugin); remove(editingPlugin);
	 * remove(annotatingPlugin); add(translatingPlugin); add(rotatingPlugin);
	 * add(shearingPlugin); add(labelEditingPlugin); add(popupEditingPlugin); }
	 */
	@Override
	protected void setTransformingMode() {
		super.setTransformingMode();
		remove(shearingPlugin);
		remove(labelEditingPlugin);
	}

	/*
	 * protected void setEditingMode() { remove(pickingPlugin);
	 * remove(animatedPickingPlugin); remove(translatingPlugin);
	 * remove(rotatingPlugin); remove(shearingPlugin);
	 * remove(labelEditingPlugin); remove(annotatingPlugin); add(editingPlugin);
	 * add(popupEditingPlugin); }
	 */

	// Annotating mode not being used atm. And i didn't even manage to invoke it.

	/*
	 * protected void setAnnotatingMode() { remove(pickingPlugin);
	 * remove(animatedPickingPlugin); remove(translatingPlugin);
	 * remove(rotatingPlugin); remove(shearingPlugin);
	 * remove(labelEditingPlugin); remove(editingPlugin);
	 * remove(popupEditingPlugin); add(annotatingPlugin); }
	 */

}
