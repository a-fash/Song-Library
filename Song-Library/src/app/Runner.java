package app;

//Andrew Fash

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.Controller;

public class Runner extends Application {
	
	public void start(Stage primaryStage) 
	throws IOException {
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/songList.fxml"));
		AnchorPane root = (AnchorPane)loader.load();


		Controller controller = loader.getController();
		controller.start(primaryStage);

		Scene scene = new Scene(root, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.show(); 
		primaryStage.setOnCloseRequest(event -> {
			try {
				controller.createFileFromLibrary();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
