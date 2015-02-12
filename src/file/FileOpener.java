package file;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import canvas.MyJUNGCanvas;
import model.Edge;
import model.Model;
import model.Vertex;

public class FileOpener {

	public static Model readModel(String fileName) {

		File file = new File(fileName);
		try {
			Scanner lineScanner = new Scanner(file);
			Model retModel = new Model();

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	private static ArrayList<Model> openWorkspace() {
		
		
		return null;
	}

	public static void saveModel(String fileName, Model model) {

		File file = new File(fileName);

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			ArrayList<Vertex> vertices = model.getVertices();

			String writeLine = "";

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
			writeLine = writeLine + model.getStartVertex().getNumber() + "\n";
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void saveWorkspace(String fileName,
			ArrayList<MyJUNGCanvas> canvasList) {
		File file = new File(fileName);

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for (MyJUNGCanvas canvas : canvasList) {
				String title = canvas.getTitle();
				String writeLine = title + "\n";
				writer.write(writeLine);
				
				
				Model model = canvas.getModel();

				ArrayList<Vertex> vertices = model.getVertices();

				writeLine = "";
				ArrayList<Vertex> endVertices = new ArrayList<Vertex>();
				for (Vertex v : vertices) {
					writeLine = "";
					writeLine = writeLine + v.getNumber() + " " + v.getToolTip();
					writer.write(writeLine);
					if (v.isFinal()) {
						endVertices.add(v);
					}
				}
				
				
				writeLine = "---";
				writer.write(writeLine);
				// write all vertices in the first line.

				writeLine = "";
				writeLine = writeLine + model.getStartVertex().getNumber()
						+ "\n";
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
				writeLine = "---";
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
