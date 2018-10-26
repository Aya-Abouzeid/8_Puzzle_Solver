package gui;

import java.io.File;
import java.sql.SQLException;
import java.util.Random;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class BeginSolver extends Application {
	private Scene scene;
	private Color[] colors = { Color.LIGHTSKYBLUE, Color.SANDYBROWN, Color.THISTLE, Color.PALEGREEN,
			Color.LIGHTSTEELBLUE, Color.AQUA, Color.ROSYBROWN, Color.KHAKI, Color.LIGHTPINK };
	private Random rand = new Random();

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		GridPane gridPane = new GridPane();
		Group group = new Group();
		scene = new Scene(group, 860, 550, Color.BEIGE);

		addFields(gridPane, primaryStage);
		group.getChildren().add(gridPane);
		primaryStage.setTitle("8 Puzzle Solver");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void addFields(GridPane gridPane, Stage primaryStage) {
		gridPane.setAlignment(Pos.CENTER);
		String numbers = "";
		int n = 0;
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				Rectangle rec = new Rectangle();
				TextField inputField = new TextField();
				rec.setWidth(100);
				rec.setHeight(100);
				rec.setFill(colors[n]);
				gridPane.add(rec, row, col);
				inputField.setBackground(Background.EMPTY);
				inputField.setPrefWidth(60);
				inputField.setPrefHeight(60);
				inputField.setStyle(
						"-fx-font-size: 46px; -fx-font-family: Quicksand; -fx-font-weight: bold; -fx-alignment: center;");
				inputField.setTextFormatter(new TextFormatter<String>((Change change) -> {
					String newText = change.getControlNewText();
					if (newText.length() > 1 || !newText.matches("[0-9]")) {
						return null;
					} else {
						return change;
					}
				}));
				gridPane.add(inputField, row, col);
				n++;
			}
		}
		Button solve = new Button();
		solve.setText("Solve!");
		solve.setPrefSize(100, 35);
		Button reset = new Button();
		reset.setText("Reset");
		reset.setPrefSize(100, 35);

		gridPane.setVgap(10);
		gridPane.setHgap(10);

		gridPane.add(solve, 0, 6);
		gridPane.add(reset, 2, 6);

		solve.setStyle("-fx-background-color: #006064; -fx-text-fill: white; -fx-font: normal bold 25px 'serif' ;");
		reset.setStyle("-fx-background-color: #006064; -fx-text-fill: white; -fx-font: normal bold 25px 'serif' ;");

		gridPane.setAlignment(Pos.BOTTOM_CENTER);
		gridPane.setPadding(new Insets(49, 49, 56, 280));

		solve.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

			}
		});
		reset.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				for (int i = 0; i < 9; i++) {
					addFields(gridPane, primaryStage);
				}
			}
		});

	}

	public void startApp(String args[]) throws ClassNotFoundException, SQLException {
		launch(args);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
