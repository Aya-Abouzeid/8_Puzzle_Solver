package gui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import jdk.internal.org.objectweb.asm.Label;
import algorithms.Astar;
import algorithms.AstarState;
import algorithms.BFS;
import algorithms.DFS;
import algorithms.State;

public class BeginSolver extends Application {
	private BFS bfs = new BFS();
	private DFS dfs = new DFS();
	private Astar aStar;
	private ArrayList<int[][]> allPath = new ArrayList<>();
	private Scene scene;
	private Color[] colors = { Color.LIGHTSKYBLUE, Color.SANDYBROWN, Color.THISTLE, Color.PALEGREEN,
			Color.LIGHTSTEELBLUE, Color.AQUA, Color.ROSYBROWN, Color.KHAKI, Color.LIGHTPINK };
	private GridPane gridPane = new GridPane();
	private Thread backgroundThread;
	private boolean solving = false;
	private Label costL = new Label();
	private Label timeL = new Label();
	private Label nodesL = new Label();
	private Label depthL = new Label();

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Group group = new Group();
		scene = new Scene(group, 860, 550, Color.BEIGE);

		addFields(gridPane, primaryStage);
		group.getChildren().add(gridPane);
		primaryStage.setTitle("8 Puzzle Solver");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private int[] handleGrid(GridPane gridPane) {
		Set<String> set = new HashSet<>();
		// get puzzle text
		for (Node n : gridPane.getChildren()) {
			String className = n.getClass().getName().split("\\.")[3];
			if (className.equals("TextField")) {
				TextField temp = (TextField) n;
				set.add(temp.getText());
			}
		}
		// Show error message in case of similar digits exist
		if (set.size() < 9) {
			Alert alert = new Alert(AlertType.ERROR, "Invalid table, cell digits must be different !", ButtonType.OK);
			alert.showAndWait();
			return null;
		} else {
			int[] initialState = new int[9];
			int i = 0;
			for (Node n : gridPane.getChildren()) {
				String className = n.getClass().getName().split("\\.")[3];
				if (className.equals("TextField")) {
					TextField textField = (TextField) n;
					textField.setEditable(false);

					initialState[i++] = textField.getText().isEmpty() ? 0 : Integer.parseInt(textField.getText());
				}
			}

			return initialState;
		}
	}

	private void show(GridPane gridPane, int[][] a) {
		int i = 0, j = 0;
		for (Node n : gridPane.getChildren()) {
			String className = n.getClass().getName().split("\\.")[3];
			if (className.equals("TextField")) {
				TextField temp = (TextField) n;
				temp.setText(String.valueOf(a[i][j]));
				j++;
				if (j == 3) {
					i++;
					j = 0;
				}
			}
		}
	}

	private void addFields(GridPane gridPane, Stage primaryStage) {
		gridPane.setAlignment(Pos.CENTER);
		int n = 0;
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				Rectangle rec = new Rectangle();
				TextField inputField = new TextField();
				rec.setWidth(100);
				rec.setHeight(100);
				rec.setFill(colors[n]);
				gridPane.add(rec, col + 1, row);
				inputField.setBackground(Background.EMPTY);
				inputField.setPrefWidth(60);
				inputField.setPrefHeight(60);
				inputField.setStyle(
						"-fx-font-size: 46px; -fx-font-family: Quicksand; -fx-font-weight: bold; -fx-alignment: center;");
				inputField.setTextFormatter(new TextFormatter<String>((Change change) -> {
					String newText = change.getControlNewText();
					if (newText.length() > 1 || !(newText.matches("[0-8]") || newText.isEmpty())) {
						return null;
					} else {
						return change;
					}
				}));
				gridPane.add(inputField, col + 1, row);
				n++;
			}
		}
		Button BFS = new Button();
		BFS.setText("BFS");
		BFS.setPrefSize(100, 35);

		Button DFS = new Button();
		DFS.setText("DFS");
		DFS.setPrefSize(100, 35);

		Button A1 = new Button();
		A1.setText("A* Man");
		A1.setPrefSize(140, 45);

		Button A2 = new Button();
		A2.setText("A* Euc");
		A2.setPrefSize(120, 45);

		Button reset = new Button();
		reset.setText("Reset");
		reset.setPrefSize(100, 35);

		gridPane.setVgap(10);
		gridPane.setHgap(10);

		gridPane.add(BFS, 0, 6);
		gridPane.add(DFS, 1, 6);
		gridPane.add(A1, 4, 6);
		gridPane.add(A2, 3, 6);
		gridPane.add(reset, 2, 6);

		BFS.setStyle("-fx-background-color: #006064; -fx-text-fill: white; -fx-font: normal bold 25px 'serif' ;");
		DFS.setStyle("-fx-background-color: #006064; -fx-text-fill: white; -fx-font: normal bold 25px 'serif' ;");
		A1.setStyle("-fx-background-color: #006064; -fx-text-fill: white; -fx-font: normal bold 25px 'serif' ;");
		A2.setStyle("-fx-background-color: #006064; -fx-text-fill: white; -fx-font: normal bold 25px 'serif' ;");

		reset.setStyle("-fx-background-color: #006064; -fx-text-fill: white; -fx-font: normal bold 25px 'serif' ;");

		gridPane.setAlignment(Pos.BOTTOM_CENTER);
		gridPane.setPadding(new Insets(40, 49, 56, 140));

		BFS.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if (!solving) {
					solving = true;
					int[] initialState = handleGrid(gridPane);

					if (initialState != null) {
						Runnable task = new Runnable() {
							public void run() {
								try {
									boolean success = bfs.search(initialState);
									if (success) {
										System.out.println("Path Cost: " + bfs.pathCost());
										System.out.println("Running Time: " + bfs.runningTime());
										System.out.println("Nodes Expanded: " + bfs.nodesExpanded());
										System.out.println("Depth: " + bfs.searchDepth());
										System.out.println("Path to goal: ");
										for (int k = 0; k < bfs.pathToGoal().size(); k++) {
											System.out.print(" " + bfs.pathToGoal().get(k) + " ");
										}
										System.out.println();

									}

									notifyGUI(success, 0);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						};

						// Run the task in a background thread
						backgroundThread = new Thread(task);
						// Terminate the running thread if the application exits
						backgroundThread.setDaemon(true);
						// Start the thread
						backgroundThread.start();
					}

				}
			}
		});

		DFS.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if (!solving) {
					solving = true;
					int[] initialState = handleGrid(gridPane);

					if (initialState != null) {

						Runnable task = new Runnable() {

							public void run() {

								try {
									boolean success = dfs.search(initialState);
									if (success) {
										System.out.println("Path Cost: " + dfs.pathCost());
										System.out.println("Running Time: " + dfs.runningTime());
										System.out.println("Nodes Expanded: " + dfs.nodesExpanded());
										System.out.println("Depth: " + dfs.searchDepth());
										System.out.println("Path to goal: ");
										for (int k = 0; k < dfs.pathToGoal().size(); k++) {
											System.out.print(" " + dfs.pathToGoal().get(k) + " ");
										}
										System.out.println();

									}
									notifyGUI(success, 1);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						};

						// Run the task in a background thread
						backgroundThread = new Thread(task);
						// Terminate the running thread if the application exits

						backgroundThread.setDaemon(true);
						// Start the thread

						backgroundThread.start();
					}
				}
			}
		});
		A1.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if (!solving) {
					solving = true;
					int[] initialState = handleGrid(gridPane);
					if (initialState != null) {
						aStar = new Astar(false);
						Runnable task = new Runnable() {
							public void run() {
								try {
									boolean success = aStar.search(initialState);
									if (success) {
										System.out.println("Path Cost: " + aStar.pathCost());
										System.out.println("Running Time: " + aStar.runningTime());
										System.out.println("Nodes Expanded: " + aStar.nodesExpanded());
										System.out.println("Depth: " + aStar.searchDepth());
										System.out.println("Path to goal: ");
										for (int k = 0; k < aStar.pathToGoal().size(); k++) {
											System.out.print(" " + aStar.pathToGoal().get(k) + " ");
										}
										System.out.println();

									}
									notifyGUI(success, 2);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						};

						// Run the task in a background thread
						backgroundThread = new Thread(task);
						// Terminate the running thread if the application exits
						backgroundThread.setDaemon(true);
						// Start the thread
						backgroundThread.start();

					}
				}
			}
		});
		A2.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if (!solving) {
					solving = true;
					int[] initialState = handleGrid(gridPane);
					if (initialState != null) {
						aStar = new Astar(true);
						Runnable task = new Runnable() {
							public void run() {
								try {
									boolean success = aStar.search(initialState);
									if (success) {
										System.out.println("Path Cost: " + aStar.pathCost());
										System.out.println("Running Time: " + aStar.runningTime());
										System.out.println("Nodes Expanded: " + aStar.nodesExpanded());
										System.out.println("Depth: " + aStar.searchDepth());
										System.out.println("Path to goal: ");
										for (int k = 0; k < aStar.pathToGoal().size(); k++) {
											System.out.print(" " + aStar.pathToGoal().get(k) + " ");
										}
										System.out.println();

									}
									notifyGUI(success, 2);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						};

						// Run the task in a background thread
						backgroundThread = new Thread(task);
						// Terminate the running thread if the application exits
						backgroundThread.setDaemon(true);
						// Start the thread
						backgroundThread.start();
					}
				}
			}
		});

		reset.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

				// clear text fields
				solving = false;
				for (Node n : gridPane.getChildren()) {
					String className = n.getClass().getName().split("\\.")[3];
					if (className.equals("TextField")) {
						TextField textField = (TextField) n;
						textField.clear();
						textField.setEditable(true);
					}
				}
			}
		});

	}

	public void notifyGUI(boolean success, int algorithm) throws InterruptedException {

		if (success) {

			State s = null;
			allPath = new ArrayList<>();
			if (algorithm == 0)
				s = bfs.getFinalState();
			if (algorithm == 1)
				s = dfs.getFinalState();
			if (algorithm == 2)
				s = aStar.getFinalState().getState();
			while (s != null) {
				allPath.add(s.getGame());
				s = s.getParent();
			}
			for (int i = allPath.size() - 1; i > -1; i--) {
				show(gridPane, allPath.get(i));
				backgroundThread.sleep(2000);
			}
		}
	}

	public void startApp(String args[]) throws ClassNotFoundException, SQLException {
		launch(args);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
