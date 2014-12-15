package containers;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import animation.AnimationScreen;
import control.TabCloseMenu;
import model.Edge;
import model.Model;
import model.Vertex;
import canvas.MyJUNGCanvas;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

@SuppressWarnings("serial")
public class ModeTabbedPane extends JTabbedPane {

	private JPanel animationPanel;
	private Component animationTab;
	private JTabbedPane creationTab;
	private ArrayList<MyJUNGCanvas> modelList;

	public boolean initialise() {
		if (animationPanel != null)
			return false;

		modelList = new ArrayList<MyJUNGCanvas>();
		MyJUNGCanvas myCanvas = new MyJUNGCanvas(new Model());
		myCanvas.setTitle("New tab");
		myCanvas.initialise(Mode.EDITING);
		modelList.add(myCanvas);

		creationTab = new JTabbedPane();
		creationTab.add(myCanvas.getTitle(), myCanvas.getVisualizationViewer());
		creationTab.addMouseListener(new TabCloseMenu(creationTab, modelList));
		add("Editing", creationTab);
		animationPanel = new JPanel();
		animationTab = add("Animation", animationPanel);

		addMouseListener(new TabListener());
		return true;
	}

	public JTabbedPane getCreationTab() {
		return creationTab;
	}

	public ArrayList<MyJUNGCanvas> getModelList() {
		return modelList;
	}

	private class TabListener implements MouseListener {

		private boolean lastSelected;
		private Dimension preferedSize = new Dimension(350, 350);

		public TabListener() {
			super();
			lastSelected = false;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (!lastSelected) {
				if (getSelectedComponent().equals(animationTab)) {
					lastSelected = true;
					for (MyJUNGCanvas i : modelList) {
						i.setNoMouse();
						VisualizationViewer<Vertex, Edge> vv = i
								.getVisualizationViewer();
						vv.getRenderContext().getPickedVertexState().clear();
						vv.getRenderContext().getPickedEdgeState().clear();
						
						vv.setPreferredSize(new Dimension(350, 325));
					}
					animationPanel.add(new AnimationScreen(modelList));
				}
			} else if (getSelectedComponent().equals(creationTab)) {
				lastSelected = false;
				for (MyJUNGCanvas i : modelList) {
					VisualizationViewer<Vertex, Edge> vv = i
							.getVisualizationViewer();
					vv.setPreferredSize(preferedSize);
					creationTab.add(i.getTitle(), vv);
					i.setMouse();
				}
				animationPanel.removeAll();
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

	}

}
