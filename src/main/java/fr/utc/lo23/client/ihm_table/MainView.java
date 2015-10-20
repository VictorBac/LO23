package fr.utc.lo23.client.ihm_table;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;

public class MainView extends Application{

	public static void main (String[] args){
		launch(args);
	}
	
	public static Stage WindowStage = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		System.out.println(getClass().getResource("."));
		System.out.println(getClass().getResource("MainView.fxml"));
		loader.setLocation(getClass().getResource("MainView.fxml"));
		Parent root = loader.load();
		
		primaryStage.setTitle("Test Main");
		primaryStage.setScene(new Scene(root));
		primaryStage.setResizable(false);
		primaryStage.setWidth(1220);
		primaryStage.setHeight(770);
		
	   	MainController controller = loader.getController();

		primaryStage.show();
		WindowStage = primaryStage;
	
	}
	
}
