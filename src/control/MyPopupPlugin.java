package control;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import model.Edge;
import model.Vertex;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingPopupGraphMousePlugin;
import edu.uci.ics.jung.visualization.picking.PickedState;

public class MyPopupPlugin extends EditingPopupGraphMousePlugin<Vertex, Edge> {

	/*
	 * protected Factory<V> vertexFactory;
	 * 
	 * protected Factory<E> edgeFactory;
	 * 
	 * protected JPopupMenu popup = new JPopupMenu();
	 */

	public MyPopupPlugin(Factory<Vertex> vertexFactory,
			Factory<Edge> edgeFactory) {
		super(vertexFactory, edgeFactory);
	}

	@SuppressWarnings("serial")
	protected void handlePopup(MouseEvent e) {
		popup = new JPopupMenu();

		@SuppressWarnings("unchecked")
		final VisualizationViewer<Vertex, Edge> vv = (VisualizationViewer<Vertex, Edge>) e
				.getSource();

		GraphElementAccessor<Vertex, Edge> pickSupport = vv.getPickSupport();
		Layout<Vertex, Edge> layout = vv.getGraphLayout();
		Graph<Vertex, Edge> graph = layout.getGraph();
		Point2D p = e.getPoint();
		if (pickSupport != null) {
			final Edge edge = pickSupport.getEdge(layout, p.getX(), p.getY());
			Vertex vertex = pickSupport.getVertex(layout, p.getX(), p.getY());
			if (vertex == null && edge != null) {
				popup.add(new AbstractAction("Edit label") {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						JFrame topFrame = (JFrame) SwingUtilities
								.getWindowAncestor(vv);
						EdgeEditMenu labelchange = new EdgeEditMenu(topFrame,
								edge);
						labelchange.setVisible(true);
					}

				});
			}
		}
		super.handlePopup(e);
	}

	/*
	 * @SuppressWarnings({ "unchecked", "serial", "serial" }) protected void
	 * handlePopup(MouseEvent e) { final VisualizationViewer<Vertex, Edge> vv =
	 * (VisualizationViewer<Vertex, Edge>) e .getSource(); final Layout<Vertex,
	 * Edge> layout = vv.getGraphLayout(); final Graph<Vertex, Edge> graph =
	 * layout.getGraph(); final Point2D p = e.getPoint(); final Point2D ivp = p;
	 * GraphElementAccessor<Vertex, Edge> pickSupport = vv.getPickSupport(); if
	 * (pickSupport != null) {
	 * 
	 * final Vertex vertex = pickSupport.getVertex(layout, ivp.getX(),
	 * ivp.getY()); final Edge edge = pickSupport.getEdge(layout, ivp.getX(),
	 * ivp.getY()); final PickedState<Vertex> pickedVertexState =
	 * vv.getPickedVertexState(); final PickedState<Edge> pickedEdgeState =
	 * vv.getPickedEdgeState();
	 * 
	 * if (vertex != null) { Set<Vertex> picked = pickedVertexState.getPicked();
	 * if (picked.size() > 0) { if (graph instanceof UndirectedGraph == false) {
	 * JMenu directedMenu = new JMenu("Create Directed Edge");
	 * popup.add(directedMenu); for (final Vertex other : picked) {
	 * directedMenu.add(new AbstractAction("[" + other + "," + vertex + "]") {
	 * public void actionPerformed(ActionEvent e) {
	 * graph.addEdge(edgeFactory.create(), other, vertex, EdgeType.DIRECTED);
	 * vv.repaint(); } }); } } if (graph instanceof DirectedGraph == false) {
	 * JMenu undirectedMenu = new JMenu( "Create Undirected Edge");
	 * popup.add(undirectedMenu); for (final Vertex other : picked) {
	 * undirectedMenu.add(new AbstractAction("[" + other + "," + vertex + "]") {
	 * public void actionPerformed(ActionEvent e) {
	 * graph.addEdge(edgeFactory.create(), other, vertex); vv.repaint(); } }); }
	 * } } popup.add(new AbstractAction("Delete Vertex") { public void
	 * actionPerformed(ActionEvent e) { pickedVertexState.pick(vertex, false);
	 * graph.removeVertex(vertex); vv.repaint(); } }); } else if (edge != null)
	 * { popup.add(new AbstractAction("Delete Edge") { public void
	 * actionPerformed(ActionEvent e) { pickedEdgeState.pick(edge, false);
	 * graph.removeEdge(edge); vv.repaint(); } }); popup.add(new
	 * EdgeEditMenu(edge)); } else { popup.add(new
	 * AbstractAction("Create Vertex") { public void actionPerformed(ActionEvent
	 * e) { Vertex newVertex = vertexFactory.create();
	 * graph.addVertex(newVertex); layout.setLocation(newVertex,
	 * vv.getRenderContext() .getMultiLayerTransformer().inverseTransform(p));
	 * vv.repaint(); } }); } if (popup.getComponentCount() > 0) { popup.show(vv,
	 * e.getX(), e.getY()); } } }
	 */

}
