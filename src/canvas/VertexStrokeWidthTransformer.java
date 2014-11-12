package canvas;

import java.awt.BasicStroke;
import java.awt.Stroke;

import model.Edge;
import model.Vertex;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.visualization.RenderContext;

public class VertexStrokeWidthTransformer implements
		Transformer<Vertex, Stroke> {

	RenderContext<Vertex, Edge> rc;
	
	public VertexStrokeWidthTransformer(RenderContext<Vertex, Edge> rc) {
		this.rc = rc;
	}

	@Override
	public Stroke transform(Vertex v) {
		if (rc.getPickedVertexState().isPicked(v))
			return new BasicStroke(4);
		return new BasicStroke(1);
	}

}
