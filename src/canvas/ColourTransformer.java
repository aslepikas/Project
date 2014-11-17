package canvas;

import java.awt.Color;
import java.awt.Paint;

import org.apache.commons.collections15.Transformer;

import model.Vertex;

public class ColourTransformer implements Transformer<Vertex, Paint>{
	
	public ColourTransformer(){
		super();
	};
	
	@Override
	public Paint transform(Vertex v) {
		if (v.isFinal())
			return Color.CYAN;
		return Color.RED;
	}
	
}
