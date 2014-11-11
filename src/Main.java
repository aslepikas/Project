import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import canvas.MyJUNGCanvas;
import model.*;

public class Main {

	public static void main(String args[]) {
		/*
		 * Model model = new Model(); JFrame stuff =
		 * MyContainer.initialise(model); stuff.setJMenuBar(MyMenuBar.create());
		 * stuff.add(new MyCanvas(model)); stuff.setVisible(true);
		 */
		Model myGraph = new Model();
		
		//myGraph.addNewNode(15, 15);
		//myGraph.addNewNode(80, 80);
		//myGraph.addNewNode(10, 120);
		//ArrayList<Node> vertices = (ArrayList<Node>) myGraph.getVertices();
		//myGraph.addNewEdge(vertices.get(0), vertices.get(1));
		//myGraph.addNewEdge(vertices.get(0), vertices.get(2));
		//myGraph.addNewEdge(vertices.get(0), vertices.get(2));

		// Layout<V, E>, VisualizationViewer<V,E>
		MyJUNGCanvas canvasModel = new MyJUNGCanvas(myGraph);
		
		canvasModel.initialise();
		/*
		Layout<Vertex, Edge> layout = new StaticLayout(myGraph);
		layout.setSize(new Dimension(300, 300));
		VisualizationViewer<Vertex, Edge> vv = new VisualizationViewer<Vertex, Edge>(
				layout);
		vv.setPreferredSize(new Dimension(350, 350));
		// Show vertex and edge labels
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		// Create a graph mouse and add it to the visualization viewer
		//
		EditingModalGraphMouse<Vertex, Edge> gm = new EditingModalGraphMouse<Vertex, Edge>(
				vv.getRenderContext(), myGraph.vertexFactory, myGraph.edgeFactory);
		vv.setGraphMouse(gm);*/
		JFrame frame = new JFrame("Editing Graph Viewer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(canvasModel.getVisualizationViewer());
		// Let's add a menu for changing mouse modes
		JMenuBar menuBar = new JMenuBar();
		JMenu modeMenu = canvasModel.getGraphMouse().getModeMenu(); // Obtain mode menu from the mouse
		modeMenu.setText("Mouse Mode");
		modeMenu.setIcon(null); // I'm using this in a main menu
		modeMenu.setPreferredSize(new Dimension(80, 20)); // Change the size
		menuBar.add(modeMenu);
		
		canvasModel.getGraphMouse().setMode(ModalGraphMouse.Mode.EDITING);
		
		frame.setJMenuBar(menuBar);
		
		frame.pack();
		frame.setVisible(true);
	}
}
