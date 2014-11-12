package canvas;

import java.awt.Color;
import java.awt.Paint;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.picking.PickedState;
import model.Edge;
import model.Vertex;

public class ColourTransformer implements Transformer<Vertex, Paint>{

	RenderContext<Vertex, Edge> rc;
	
	public ColourTransformer(RenderContext<Vertex, Edge> rc){
		super();
		this.rc = rc;
	};
	
	@Override
	public Paint transform(Vertex v) {
		if (v.isFinal())
			return Color.CYAN;
//		PickedState<Vertex> state = rc.getPickedVertexState();
//		if (state.isPicked(v))
//			return Color.GREEN;
		return Color.RED;
	}
	
}
