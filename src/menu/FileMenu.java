package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import model.Edge;
import model.Model;
import model.Vertex;
import canvas.MyJUNGCanvas;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import file.FileOpener;

@SuppressWarnings("serial")
public class FileMenu extends JMenu {

	private ArrayList<MyJUNGCanvas> canvasList;
	private JTabbedPane tabbedPane;
	private JMenuItem addTab;
	private JMenuItem saveCurrent;
	private JMenuItem saveSession;
	private JMenuItem openFile;
	private ModeMenu modeMenu;

	public FileMenu(String title, ArrayList<MyJUNGCanvas> canvasList,
			JTabbedPane tabbedPane, ModeMenu modeMenu) {
		super(title);

		this.canvasList = canvasList;
		this.tabbedPane = tabbedPane;
		this.modeMenu = modeMenu;

		FileMenuListener listener = new FileMenuListener();

		addTab = new JMenuItem("New model");
		addTab.addActionListener(listener);
		this.add(addTab);

		saveCurrent = new JMenuItem("Save model");
		saveCurrent.addActionListener(listener);
		this.add(saveCurrent);
	
		saveSession = new JMenuItem("Save workspace");
		saveSession.addActionListener(listener);
		this.add(saveSession);
		
		openFile = new JMenuItem("Open");
		openFile.addActionListener(listener);
		this.add(openFile);
	}

	private class FileMenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(addTab)) {
				Model myGraph = new Model();
				MyJUNGCanvas myCanvas = new MyJUNGCanvas(myGraph);
				myCanvas.initialise(modeMenu.getMode());
				myCanvas.setTitle("New tab");
				tabbedPane.add(myCanvas.getTitle(),
						myCanvas.getVisualizationViewer());
				canvasList.add(myCanvas);
			} else if (e.getSource().equals(saveCurrent)) {
				String fileName = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()) + ".txt";
				FileOpener.saveModel(fileName, canvasList.get(tabbedPane.getSelectedIndex()).getModel());
			} else if (e.getSource().equals(saveSession)) {
				//TODO
			} else if (e.getSource().equals(openFile)) {
				Model myGraph = FileOpener.readModel("./examples/example.txt");
				if (myGraph != null) {
					MyJUNGCanvas nCanvas = new MyJUNGCanvas(myGraph);
					nCanvas.setTitle("example");
					nCanvas.initialise(modeMenu.getMode());

					canvasList.add(nCanvas);
					tabbedPane.add(nCanvas.getTitle(), nCanvas.getVisualizationViewer());
					tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
					nCanvas.getVisualizationViewer().repaint();
					
					VisualizationViewer<Vertex, Edge> vv = nCanvas.getVisualizationViewer();
					vv.setGraphLayout(new ISOMLayout<Vertex, Edge>(vv
							.getGraphLayout().getGraph()));
					vv.repaint();
				}
			}
		}

	}

}
