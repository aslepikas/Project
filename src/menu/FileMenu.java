package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import model.Edge;
import model.Model;
import model.Vertex;
import canvas.MyJUNGCanvas;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
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
				File file = FileOpener.fileSaveChoose();
				if (file != null) {
					String fileName = file.getPath();
					if (fileName.length() > 4) {
						if (!fileName.substring(fileName.length() - 4,
								fileName.length()).equals(".txt")) {
							fileName = fileName + ".txt";
						}
					} else {
						fileName = fileName + ".txt";
					}
					FileOpener.saveModel(fileName,
							canvasList.get(tabbedPane.getSelectedIndex())
									.getModel());
				}
			} else if (e.getSource().equals(saveSession)) {
				File file = FileOpener.fileSaveChoose();
				if (file != null) {
					String fileName = file.getPath();
					if (fileName.length() > 4) {
						if (!fileName.substring(fileName.length() - 4,
								fileName.length()).equals(".txt")) {
							fileName = fileName + ".txt";
						}
					} else {
						fileName = fileName + ".txt";
					}
					FileOpener.saveWorkspace(fileName, canvasList);
				}
			} else if (e.getSource().equals(openFile)) {
				File file = FileOpener.fileOpenChoose();
				if (file == null) {
					return;
				}
				int mode = FileOpener.isModelOrWorkspace(file);
				if (mode == 1) {
					Model myGraph = FileOpener.readModel(file);
					if (myGraph != null) {
						MyJUNGCanvas nCanvas = new MyJUNGCanvas(myGraph);
						nCanvas.setTitle("example");
						nCanvas.initialise(modeMenu.getMode());

						canvasList.add(nCanvas);
						tabbedPane.add(nCanvas.getTitle(),
								nCanvas.getVisualizationViewer());
						tabbedPane
								.setSelectedIndex(tabbedPane.getTabCount() - 1);
						nCanvas.getVisualizationViewer().repaint();

						VisualizationViewer<Vertex, Edge> vv = nCanvas
								.getVisualizationViewer();
						vv.setGraphLayout(new KKLayout<Vertex, Edge>(vv
								.getGraphLayout().getGraph()));
						vv.repaint();
					}
				} else if (mode == 2) {
					ArrayList<Model> retList = new ArrayList<Model>();
					ArrayList<String> titleList = new ArrayList<String>();
					if (FileOpener.readWorkspace(file, retList, titleList)) {
						for (int i = 0; i < retList.size(); i++) {
							Model myGraph = retList.get(i);
							if (myGraph != null) {
								MyJUNGCanvas nCanvas = new MyJUNGCanvas(myGraph);
								nCanvas.setTitle(titleList.get(i));
								nCanvas.initialise(modeMenu.getMode());

								canvasList.add(nCanvas);
								tabbedPane.add(nCanvas.getTitle(),
										nCanvas.getVisualizationViewer());
								tabbedPane
										.setSelectedIndex(tabbedPane.getTabCount() - 1);
								nCanvas.getVisualizationViewer().repaint();

								VisualizationViewer<Vertex, Edge> vv = nCanvas
										.getVisualizationViewer();
								vv.setGraphLayout(new KKLayout<Vertex, Edge>(vv
										.getGraphLayout().getGraph()));
								vv.repaint();
							}
						}
					}
				} else {
				}
			}
		}

	}

}
