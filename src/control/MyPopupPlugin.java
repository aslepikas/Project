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
import model.Model;

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
		Point2D p = e.getPoint();
		if (pickSupport != null) {
			final Edge edge = pickSupport.getEdge(layout, p.getX(), p.getY());
			final Vertex vertex = pickSupport.getVertex(layout, p.getX(),
					p.getY());
			if (vertex != null) {
				if (!vertex.isFinal()) {
					popup.add(new AbstractAction("Set " + vertex.toString()
							+ " to be final") {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							vertex.setFinal();
							vv.repaint();
						}
					});
				} else {
					popup.add(new AbstractAction("Set " + vertex.toString()
							+ " to be non-final") {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							vertex.unSetFinal();
							vv.repaint();
						}
					});
				}
				final Model model = (Model) (vv.getGraphLayout().getGraph());
				if (!model.isStartVertex(vertex)) {
					popup.add(new AbstractAction("Set " + vertex.toString()
							+ " to be start") {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							model.setStartVertex(vertex);
							vv.repaint();
						}
					});
				} else {
					popup.add(new AbstractAction("Set " + vertex.toString()
							+ " to be non-start") {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							model.removeStartVertex();
							vv.repaint();
						}
					});
				}
			} else if (edge != null) {
				popup.add(new AbstractAction("Edit label") {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						JFrame topFrame = (JFrame) SwingUtilities
								.getWindowAncestor(vv);
						EdgeEditMenu labelchange = new EdgeEditMenu(topFrame,
								edge);
						labelchange.setVisible(true);
						vv.repaint();
					}

				});
			}
		}
		copiedHandlePopup(e);
	}

	// this method is the public void handlePopup from the superclass. It was
	// modified slightly.
	@SuppressWarnings("serial")
	private void copiedHandlePopup(MouseEvent e) {
		@SuppressWarnings("unchecked")
		final VisualizationViewer<Vertex, Edge> vv = (VisualizationViewer<Vertex, Edge>) e
				.getSource();
		final Layout<Vertex, Edge> layout = vv.getGraphLayout();
		final Graph<Vertex, Edge> graph = layout.getGraph();
		final Point2D p = e.getPoint();
		final Point2D ivp = p;
		GraphElementAccessor<Vertex, Edge> pickSupport = vv.getPickSupport();
		if (pickSupport != null) {

			final Vertex vertex = pickSupport.getVertex(layout, ivp.getX(),
					ivp.getY());
			final Edge edge = pickSupport.getEdge(layout, ivp.getX(),
					ivp.getY());
			final PickedState<Vertex> pickedVertexState = vv
					.getPickedVertexState();
			final PickedState<Edge> pickedEdgeState = vv.getPickedEdgeState();

			if (vertex != null) {
				Set<Vertex> picked = pickedVertexState.getPicked();
				if (picked.size() > 0) {
					if (graph instanceof UndirectedGraph == false) {
						JMenu directedMenu = new JMenu("Create Directed Edge");
						popup.add(directedMenu);
						for (final Vertex other : picked) {
							directedMenu.add(new AbstractAction("[" + other
									+ "," + vertex + "]") {
								public void actionPerformed(ActionEvent arg0) {

									JFrame topFrame = (JFrame) SwingUtilities
											.getWindowAncestor(vv);
									Edge nedge = vv.getGraphLayout().getGraph()
											.findEdge(other, vertex);
									if (nedge == null) {
										nedge = new Edge(other, vertex);
									}

									EdgeEditMenu labelchange = new EdgeEditMenu(
											topFrame, nedge);
									labelchange.setVisible(true);
									if (!nedge.getLabels().isEmpty())
										graph.addEdge(nedge, other, vertex,
												EdgeType.DIRECTED);
									vv.repaint();
								}
							});
						}
					}
					if (graph instanceof DirectedGraph == false) {
						JMenu undirectedMenu = new JMenu(
								"Create Undirected Edge");
						popup.add(undirectedMenu);
						for (final Vertex other : picked) {
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
						Vertex newVertex = vertexFactory.create();
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
