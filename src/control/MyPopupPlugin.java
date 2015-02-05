package control;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import model.Edge;
import model.Vertex;
import model.Model;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingPopupGraphMousePlugin;

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
				final Model model = (Model)(vv.getGraphLayout().getGraph());
				if (!model.isStartVertex(vertex)){
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
		super.handlePopup(e);
	}

}
