package canvas.transform;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import model.Vertex;
import edu.uci.ics.jung.visualization.decorators.AbstractVertexShapeTransformer;

public class MyVertexShapeTransformer extends AbstractVertexShapeTransformer<Vertex> {

	@Override
	public Shape transform(Vertex v) {
		return new Ellipse2D.Double(-15, -15, 30, 30);
	}

}
