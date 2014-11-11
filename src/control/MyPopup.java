package control;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Factory;

//import com.google.common.base.Supplier;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingPopupGraphMousePlugin;
import edu.uci.ics.jung.visualization.picking.PickedState;

/**
 * a plugin that uses popup menus to create vertices, undirected edges, and
 * directed edges.
 * 
 * @author Tom Nelson. Bugfix added by Arnas Gabrielius Slepikas
 *
 */
public class MyPopup<V, E> extends EditingPopupGraphMousePlugin<V, E> {

	public MyPopup(Factory<V> vertexFactory, Factory<E> edgeFactory) {
		super(vertexFactory, edgeFactory);
	}

	@SuppressWarnings({ "unchecked", "serial" })
	protected void handlePopup(MouseEvent e) {
		final VisualizationViewer<V, E> vv = (VisualizationViewer<V, E>) e
				.getSource();
		final Layout<V, E> layout = vv.getGraphLayout();
		final Graph<V, E> graph = layout.getGraph();
		final Point2D p = e.getPoint();
		GraphElementAccessor<V, E> pickSupport = vv.getPickSupport();
		if (pickSupport != null) {

			final V vertex = pickSupport.getVertex(layout, p.getX(), p.getY());
			final E edge = pickSupport.getEdge(layout, p.getX(), p.getY());
			final PickedState<V> pickedVertexState = vv.getPickedVertexState();
			final PickedState<E> pickedEdgeState = vv.getPickedEdgeState();

			JPopupMenu popup = new JPopupMenu();
			if (vertex != null) {
				Set<V> picked = pickedVertexState.getPicked();
				if (picked.size() > 0) {
					if (graph instanceof UndirectedGraph == false) {
						JMenu directedMenu = new JMenu("Create Directed Edge");
						popup.add(directedMenu);
						for (final V other : picked) {
							directedMenu.add(new AbstractAction("[" + other
									+ "," + vertex + "]") {
								public void actionPerformed(ActionEvent e) {
									graph.addEdge(edgeFactory.create(), other,
											vertex, EdgeType.DIRECTED);
									vv.repaint();
								}
							});
						}
					}
					if (graph instanceof DirectedGraph == false) {
						JMenu undirectedMenu = new JMenu(
								"Create Undirected Edge");
						popup.add(undirectedMenu);
						for (final V other : picked) {
							undirectedMenu.add(new AbstractAction("[" + other
									+ "," + vertex + "]") {
								public void actionPerformed(ActionEvent e) {
									graph.addEdge(edgeFactory.create(), other,
											vertex);
									vv.repaint();
								}
							});
						}
					}
				}
				popup.add(new AbstractAction("Delete Vertex") {
					public void actionPerformed(ActionEvent e) {
						pickedVertexState.pick(vertex, false);
						graph.removeVertex(vertex);
						vv.repaint();
					}
				});
			} else if (edge != null) {
				popup.add(new AbstractAction("Delete Edge") {
					public void actionPerformed(ActionEvent e) {
						pickedEdgeState.pick(edge, false);
						graph.removeEdge(edge);
						vv.repaint();
					}
				});
			} else {
				popup.add(new AbstractAction("Create Vertex") {
					public void actionPerformed(ActionEvent e) {
						V newVertex = vertexFactory.create();
						graph.addVertex(newVertex);
						layout.setLocation(newVertex, vv.getRenderContext()
								.getMultiLayerTransformer().inverseTransform(p));
						vv.repaint();
					}
				});
			}
			if (popup.getComponentCount() > 0) {
				popup.show(vv, e.getX(), e.getY());
			}
		}
	}
}
