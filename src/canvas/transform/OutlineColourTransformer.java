package canvas.transform;

import java.awt.Color;
import java.awt.Paint;

import model.Edge;
import model.Vertex;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.visualization.RenderContext;

public class OutlineColourTransformer implements Transformer<Vertex, Paint> {

	RenderContext<Vertex, Edge> rc;

	public OutlineColourTransformer(RenderContext<Vertex, Edge> rc) {
		this.rc = rc;
	}

	public Paint transform(Vertex v) {
		if (v.isStarting()) {
			return Color.BLUE;
		}
		return Color.BLACK;
	}

}
