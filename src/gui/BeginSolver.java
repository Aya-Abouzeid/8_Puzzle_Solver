package gui;

import java.io.File;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BeginSolver extends Application {
	private Scene scene;



	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		GridPane gridPane = new GridPane();
		Group group = new Group();
		scene = new Scene(group, 980, 610 ,Color.BEIGE);
		
		addFields(gridPane, primaryStage);
		group.getChildren().add(gridPane);
		primaryStage.setTitle("8 Puzzle Game");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void addFields(GridPane gridPane, Stage primaryStage) {

		 TextField inputField = new TextField();
		 Button solve = new Button();
		solve.setText("Solve!");
		solve.setPrefSize(150, 35);

		
		gridPane.setVgap(35);
		gridPane.setHgap(7);
		gridPane.setAlignment(Pos.CENTER);

		gridPane.add(solve, 6, 5);
		gridPane.add(inputField, 2, 5);

		solve.setStyle("-fx-background-color: #006064; -fx-text-fill: white; -fx-font: normal bold 25px 'serif' ;");


		gridPane.setAlignment(Pos.BOTTOM_CENTER);
		gridPane.setPadding(new Insets(49, 49, 56, 280));

		solve.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				
				
			}
		});
		
	}

	public void startApp(String args[]) throws ClassNotFoundException, SQLException {
		launch(args);
	}

}
