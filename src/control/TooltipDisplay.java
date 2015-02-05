package control;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import model.Edge;
import model.Vertex;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;

public class TooltipDisplay implements MouseMotionListener{

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// not used
	}

	@SuppressWarnings("unchecked")
	@Override //TODO
	public void mouseMoved(MouseEvent e) {
		VisualizationViewer<Vertex, Edge> vv = (VisualizationViewer<Vertex, Edge>)e.getSource();
		Layout<Vertex, Edge> layout = vv.getGraphLayout(); 
		Point p = e.getPoint();
		
		GraphElementAccessor<Vertex,Edge> pickSupport = vv.getPickSupport();
		if (pickSupport != null) {
			Vertex v = pickSupport.getVertex(layout, p.getX(), p.getY());
			if (v!= null) {
				vv.setToolTipText(v.getToolTip());
			} else {
				vv.setToolTipText(null);
			}
		}
	}

}
