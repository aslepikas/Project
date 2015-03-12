package file;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import canvas.MyJUNGCanvas;
import model.Edge;
import model.Model;
import model.Vertex;

public class FileOpener {

	public static File fileOpenChoose() {
		JFileChooser chooser = new JFileChooser("./examples/");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"text files", "txt");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		return null;
	}

	public static File fileSaveChoose() {
		JFileChooser chooser = new JFileChooser("./examples/");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"text files", "txt");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		return null;
	}

	public static Model readModel(File file) {
		try {
			Scanner lineScanner = new Scanner(file);
			Model retModel = new Model();
			if (lineScanner.hasNextLine()) {
				lineScanner.nextLine();
			}
			int last = 0;
			if (lineScanner.hasNextLine()) {
				String line = lineScanner.nextLine();
				Scanner vertexScanner = new Scanner(line);
				while (vertexScanner.hasNextInt()) {
					last = vertexScanner.nextInt();
					Vertex vertex = new Vertex(last);
					retModel.addVertex(vertex);
				}
				vertexScanner.close();
			}

			if (lineScanner.hasNextLine()) {
				String line = lineScanner.nextLine();
				Scanner vertexScanner = new Scanner(line);
				if (vertexScanner.hasNextInt()) {
					int vertex = vertexScanner.nextInt();
					ArrayList<Vertex> vertices = retModel.getVertices();
					for (Vertex v : vertices) {
						if (v.getNumber() == vertex) {
							retModel.setStartVertex(v);
							break;
						}
					}
				}
				vertexScanner.close();
			}

			if (lineScanner.hasNextLine()) {
				String line = lineScanner.nextLine();
				Scanner vertexScanner = new Scanner(line);
				while (vertexScanner.hasNextInt()) {
					int vertex = vertexScanner.nextInt();
					ArrayList<Vertex> vertices = retModel.getVertices();
					for (Vertex v : vertices) {
						if (v.getNumber() == vertex) {
							v.setFinal();
							break;
						}
					}
				}
				vertexScanner.close();
			}

			while (lineScanner.hasNextLine()) {
				String line = lineScanner.nextLine();
				Scanner edgeScanner = new Scanner(line);
				if (edgeScanner.hasNext()) {
					String edge = edgeScanner.next();
					String[] endPoints = edge.split(":");
					String labels = edgeScanner.next();

					ArrayList<Character> labelList = new ArrayList<Character>();

					for (int i = 0; i < labels.length(); i++) {
						labelList.add(labels.charAt(i));
					}

					Vertex v1 = retModel.getNumber(Integer
							.parseInt(endPoints[0]));
					Vertex v2 = retModel.getNumber(Integer
							.parseInt(endPoints[1]));

					Edge e = new Edge(v1, v2, labelList);

					retModel.addEdge(e, v1, v2);
				} else {
					edgeScanner.close();
					break;
				}

				edgeScanner.close();
			}

			lineScanner.close();
			return retModel;
		} catch (FileNotFoundException e) {
			// TODO
		}

		return null;
	}

	public static boolean readWorkspace(File file, ArrayList<Model> retList,
			ArrayList<String> titleList) {
		Scanner lineScanner;
		try {
			lineScanner = new Scanner(file);

			if (lineScanner.hasNextLine()) {
				lineScanner.nextLine();
			}

			while (lineScanner.hasNextLine()) {
				Model retModel = new Model();

				if (lineScanner.hasNextLine()) {
					String title = lineScanner.nextLine();
					titleList.add(title);
				}

				while (lineScanner.hasNextLine()) {
					String line = lineScanner.nextLine();
					if (!line.equals("---")) {
						Scanner vertexScanner = new Scanner(line);
						if (vertexScanner.hasNextInt()) {
							Vertex vertex = new Vertex(vertexScanner.nextInt());
							retModel.addVertex(vertex);
							if (vertexScanner.hasNext()) {
								String tooltip = vertexScanner.nextLine();
								vertex.setTooltip(tooltip);
							}
						}
						vertexScanner.close();
					} else {
						break;
					}
				}

				if (lineScanner.hasNextLine()) {
					String line = lineScanner.nextLine();
					Scanner vertexScanner = new Scanner(line);
					if (vertexScanner.hasNextInt()) {
						int vertex = vertexScanner.nextInt();
						ArrayList<Vertex> vertices = retModel.getVertices();
						for (Vertex v : vertices) {
							if (v.getNumber() == vertex) {
								retModel.setStartVertex(v);
								break;
							}
						}
					}
					vertexScanner.close();
				}

				if (lineScanner.hasNextLine()) {
					String line = lineScanner.nextLine();
					Scanner vertexScanner = new Scanner(line);
					while (vertexScanner.hasNextInt()) {
						int vertex = vertexScanner.nextInt();
						ArrayList<Vertex> vertices = retModel.getVertices();
						for (Vertex v : vertices) {
							if (v.getNumber() == vertex) {
								v.setFinal();
								break;
							}
						}
					}
					vertexScanner.close();
				}

				while (lineScanner.hasNextLine()) {
					String line = lineScanner.nextLine();

					Scanner edgeScanner = new Scanner(line);
					if (edgeScanner.hasNext()) {
						String edge = edgeScanner.next();
						if (!edge.equals("---")) {
							String[] endPoints = edge.split(":");
							String labels = edgeScanner.next();

							ArrayList<Character> labelList = new ArrayList<Character>();

							for (int i = 0; i < labels.length(); i++) {
								labelList.add(labels.charAt(i));
							}

							Vertex v1 = retModel.getNumber(Integer
									.parseInt(endPoints[0]));
							Vertex v2 = retModel.getNumber(Integer
									.parseInt(endPoints[1]));

							Edge e = new Edge(v1, v2, labelList);

							retModel.addEdge(e, v1, v2);
							edgeScanner.close();
						} else {
							edgeScanner.close();
							break;
						}
					} else {
						edgeScanner.close();
						break;
					}

					edgeScanner.close();
				}
				retList.add(retModel);
			}
			lineScanner.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO
		}
		return false;
	}

	public static int isModelOrWorkspace(File file) {
		Scanner lineScanner;
		try {
			lineScanner = new Scanner(file);

			if (lineScanner.hasNextLine()) {
				String line = lineScanner.next();
				if (line.equals("model")) {
					lineScanner.close();
					return 1;
				}
				if (line.equals("workspace")) {
					lineScanner.close();
					return 2;
				}
				lineScanner.close();
			}
		} catch (FileNotFoundException e) {
			// TODO
		}

		return 0;
	}

	public static void saveModel(String fileName, Model model) {
		File file = new File(fileName);
		if (model.getVertices().size() > 0) {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));

				ArrayList<Vertex> vertices = model.getVertices();

				String writeLine = "model\n";

				writer.write(writeLine);

				writeLine = "";
				ArrayList<Vertex> endVertices = new ArrayList<Vertex>();
				for (Vertex v : vertices) {
					writeLine = writeLine + v.getNumber() + " ";
					if (v.isFinal()) {
						endVertices.add(v);
					}
				}
				writeLine = writeLine + "\n";
				writer.write(writeLine);
				// write all vertices in the first line.

				writeLine = "";
				if (model.hasStart()) {
					writeLine = writeLine + model.getStartVertex().getNumber()
							+ "\n";
				} else {
					writeLine = "\n";
				}
				writer.write(writeLine);

				writeLine = "";
				for (Vertex v : endVertices) {
					writeLine = writeLine + v.getNumber() + " ";
				}
				writeLine = writeLine + "\n";
				writer.write(writeLine);

				for (Vertex v : vertices) {
					for (Edge e : v.getEdgesOut()) {
						writeLine = "";
						writeLine = writeLine + e.getStartV().getNumber() + ":"
								+ e.getTargetV().getNumber() + " ";
						for (char c : e.getLabels()) {
							writeLine = writeLine + c;
						}
						writeLine = writeLine + "\n";
						writer.write(writeLine);
					}
				}

				writer.close();
			} catch (IOException e) {
				// TODO
			}
		}
	}

	public static void saveWorkspace(String fileName,
			ArrayList<MyJUNGCanvas> canvasList) {

		File file = new File(fileName);

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			writer.write("workspace\n");
			for (MyJUNGCanvas canvas : canvasList) {
				Model model = canvas.getModel();
				if (model.getVertices().size() > 0) {
					String title = canvas.getTitle();
					String writeLine = title + "\n";
					writer.write(writeLine);

					ArrayList<Vertex> vertices = model.getVertices();
					ArrayList<Vertex> endVertices = new ArrayList<Vertex>();
					for (Vertex v : vertices) {
						writeLine = "";
						writeLine = writeLine + v.getNumber() + " "
								+ v.getToolTip() + "\n";
						writer.write(writeLine);
						if (v.isFinal()) {
							endVertices.add(v);
						}
					}

					writeLine = "---\n";
					writer.write(writeLine);
					// write all vertices in the first line.

					writeLine = "";
					if (model.hasStart()) {
						writeLine = writeLine
								+ model.getStartVertex().getNumber() + "\n";
					} else {
						writeLine = "\n";
					}
					writer.write(writeLine);

					writeLine = "";
					for (Vertex v : endVertices) {
						writeLine = writeLine + v.getNumber() + " ";
					}
					writeLine = writeLine + "\n";
					writer.write(writeLine);

					for (Vertex v : vertices) {
						for (Edge e : v.getEdgesOut()) {
							writeLine = "";
							writeLine = writeLine + e.getStartV().getNumber()
									+ ":" + e.getTargetV().getNumber() + " ";
							for (char c : e.getLabels()) {
								writeLine = writeLine + c;
							}
							writeLine = writeLine + "\n";
							writer.write(writeLine);
						}
					}
					writeLine = "---\n";
					writer.write(writeLine);
				}
			}
			writer.close();
		} catch (IOException e) {
			// TODO
		}
	}

}
