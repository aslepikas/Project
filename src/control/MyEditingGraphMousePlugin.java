package control;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.Edge;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingGraphMousePlugin;

public class MyEditingGraphMousePlugin<V, E> extends
		EditingGraphMousePlugin<V, E> {

	public MyEditingGraphMousePlugin(Factory<V> vertexFactory,
			Factory<E> edgeFactory) {
		super(vertexFactory, edgeFactory);
	}

	public MyEditingGraphMousePlugin(int modifiers, Factory<V> vertexFactory,
			Factory<E> edgeFactory) {
		super(modifiers, vertexFactory, edgeFactory);
	}

	@SuppressWarnings("unchecked")
	public void mouseReleased(MouseEvent e) {
		if (checkModifiers(e)) {
			final VisualizationViewer<V, E> vv = (VisualizationViewer<V, E>) e
					.getSource();
			final Point2D p = e.getPoint();
			Layout<V, E> layout = vv.getModel().getGraphLayout();
			GraphElementAccessor<V, E> pickSupport = vv.getPickSupport();
			if (pickSupport != null) {
				final V vertex = pickSupport.getVertex(layout, p.getX(),
						p.getY());
				if (vertex != null && startVertex != null) {
					Graph<V, E> graph = vv.getGraphLayout().getGraph();
					Edge newEdge = (Edge) edgeFactory.create();
					if (layout.getGraph().isNeighbor(vertex, startVertex)) {
						ArrayList<Edge> set = (ArrayList<Edge>) (layout
								.getGraph().findEdgeSet(startVertex, vertex));
						if (set.size() == 2) {
							JOptionPane
									.showMessageDialog(vv,
											"An edge between these states already exists");
							vv.removePostRenderPaintable(edgePaintable);
							vv.removePostRenderPaintable(arrowPaintable);
							vv.repaint();
							return;
						} else if (set.size() == 1) {
							if (set.get(0).getStartV().equals(startVertex)) {
								JOptionPane
										.showMessageDialog(vv,
												"An edge between these states already exists");
								vv.removePostRenderPaintable(edgePaintable);
								vv.removePostRenderPaintable(arrowPaintable);
								vv.repaint();
								return;
							}
						}
					}

					JFrame topFrame = (JFrame) SwingUtilities
							.getWindowAncestor(vv);
					EdgeEditMenu labelchange = new EdgeEditMenu(topFrame,
							newEdge);

					labelchange.setVisible(true);
					if (!newEdge.getLabels().isEmpty())
						graph.addEdge((E) newEdge, startVertex, vertex,
								edgeIsDirected);
					vv.repaint();
				}
			}
			startVertex = null;
			down = null;
			edgeIsDirected = EdgeType.UNDIRECTED;
			vv.removePostRenderPaintable(edgePaintable);
			vv.removePostRenderPaintable(arrowPaintable);
		}
	}

}
