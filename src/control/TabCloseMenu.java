package control;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import canvas.MyJUNGCanvas;

@SuppressWarnings("serial")
public class TabCloseMenu implements MouseListener {

	JTabbedPane tabbedPane;
	ArrayList<MyJUNGCanvas> modelList;

	public TabCloseMenu(JTabbedPane tabbedPane,
			ArrayList<MyJUNGCanvas> modelList) {
		this.tabbedPane = tabbedPane;
		this.modelList = modelList;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {

			if (tabbedPane.getUI()
					.getTabBounds(tabbedPane, tabbedPane.getSelectedIndex())
					.contains(e.getPoint())) {
				JPopupMenu popup = new JPopupMenu();
				popup.add(new AbstractAction("Close tab") {

					@Override
					public void actionPerformed(ActionEvent e) {
						int selected = tabbedPane.getSelectedIndex();
						tabbedPane.remove(selected);
						modelList.remove(selected);
					}
				});

				popup.add(new AbstractAction("Rename") {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						int selected = tabbedPane.getSelectedIndex();
						String title = JOptionPane
								.showInputDialog("Enter new title");
						if (title != null) {
							if (!(title.equals(""))) {
								tabbedPane.setTitleAt(selected, title);
								modelList.get(selected).setTitle(title);
							}
						}
					}
				});

				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
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

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

}
